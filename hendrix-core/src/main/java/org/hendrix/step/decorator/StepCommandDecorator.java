package org.hendrix.step.decorator;

import org.hendrix.domain.Result;
import org.hendrix.domain.Scenario;
import org.hendrix.step.StepCommand;
import org.hendrix.step.StepDefinition;
import pickles.PickleStep;

import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;

public class StepCommandDecorator implements StepCommand {
    protected final StepCommand stepCommand;

    public StepCommandDecorator(StepCommand stepCommand) {
        this.stepCommand = stepCommand;
    }

    @Override
    public StepDefinition getStepDefinition() {
        return stepCommand.getStepDefinition();
    }

    @Override
    public Object getInstance() {
        return stepCommand.getInstance();
    }

    @Override
    public PickleStep getPickleStep() {
        return stepCommand.getPickleStep();
    }

    @Override
    public Scenario getScenario() {
        return stepCommand.getScenario();
    }

    @Override
    public void setScenario(Scenario scenario) {
        stepCommand.setScenario(scenario);
    }

    @Override
    public void submit(CompletionService<StepCommand> executeService) {
        executeService.submit(this);
    }

    @Override
    public void skip() {
        stepCommand.skip();
    }

    @Override
    public Future<Result> getResult() {
        return stepCommand.getResult();
    }

    @Override
    public StepCommand call() throws Exception {
        stepCommand.call();
        return this;
    }
}
