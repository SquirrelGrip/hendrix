package org.hendrix.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.hendrix.domain.*;
import org.hendrix.reporter.Reporter;
import org.hendrix.step.StepCommand;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Runner {
    private final ScenarioFactory scenarioFactory;
    private final Set<Reporter> reporters = Sets.newHashSet();
    private final List<ScenarioResult> scenarioResults = Lists.newArrayList();
    private final ExecutorService executeService = Executors.newFixedThreadPool(50);
    private final CompletionService<StepCommand> completionService = new ExecutorCompletionService<>(executeService);

    public Runner(ScenarioFactory scenarioFactory) {
        this.scenarioFactory = scenarioFactory;
    }

    public void run() {
        validate();
        try {
            List<Scenario> scenarios = scenarioFactory.createScenarios();
            Set<Scenario> submittedScenarios = submitScenarios(scenarios);
            processScenarios(submittedScenarios);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            executeService.shutdown();
            for (Reporter reporter : reporters) {
                reporter.close();
            }
        }
    }

    private Set<Scenario> submitScenarios(List<Scenario> scenarios) {
        Set<Scenario> submittedScenarios = Sets.newConcurrentHashSet();
        for (Scenario scenario : scenarios) {
            scenario.prepare();
            if (scenario.hasNext()) {
                submit(scenario.next());
                submittedScenarios.add(scenario);
            }
        }
        return submittedScenarios;
    }

    private void submit(StepCommand stepCommand) {
        stepCommand.submit(completionService);
    }

    private void processScenarios(Set<Scenario> submittedScenarios) throws Exception {
        while (!submittedScenarios.isEmpty()) {
            StepCommand stepCommand = take().get();
            if (processStepCommand(stepCommand)) {
                scenarioComplete(submittedScenarios, stepCommand);
            }
        }
    }

    private boolean processStepCommand(StepCommand stepCommand) throws Exception {
        Scenario scenario = stepCommand.getScenario();
        Future<Result> resultFuture = stepCommand.getResult();
        if (resultFuture.isDone()) {
            Result result = resultFuture.get();
            scenario.addStepResult(new StepResult(stepCommand.getPickleStep(), result));
            switch(result.getResultStatus()) {
                case PASS:
                case IGNORED:
                    if (scenario.hasNext()) {
                        submit(scenario.next());
                        return false;
                    }
                    break;
                default:
                    while (scenario.hasNext()) {
                        stepCommand = scenario.next();
                        stepCommand.skip();
                    }

            }
            return true;
        } else {
            submit(stepCommand);
        }
        return false;
    }

    private Future<StepCommand> take() {
        try {
            return completionService.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void scenarioComplete(Set<Scenario> submittedScenarios, StepCommand stepCommand) {
        Scenario scenario = stepCommand.getScenario();
        ScenarioResult result = scenario.getResult();
        notifyReporters(result);
        scenarioResults.add(result);
        submittedScenarios.remove(scenario);
    }

    private void notifyReporters(ScenarioResult result) {
        for (Reporter reporter : reporters) {
            reporter.reportOne(result);
        }
    }

    private void validate() {
        scenarioFactory.validate();
    }

    public RunnerResult getResults() {
        return new RunnerResult(scenarioResults);
    }

    public void addReporter(Reporter reporter) {
        reporters.add(reporter);
    }

    public List<ScenarioResult> getScenarioResults() {
        return scenarioResults;
    }
}
