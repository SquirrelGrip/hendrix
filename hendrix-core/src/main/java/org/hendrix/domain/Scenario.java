package org.hendrix.domain;

import com.google.common.collect.Lists;
import org.hendrix.core.BeforeScenarioMethod;
import org.hendrix.gherkin.Pickle;
import org.hendrix.step.StepCommand;

import java.util.Iterator;
import java.util.List;

public class Scenario {
    private final Pickle pickle;
    private final List<StepCommand> steps;
    private final List<BeforeScenarioMethod> beforeMethods;

    private final List<StepResult> stepResultList = Lists.newArrayList();
    private final List<String> messages = Lists.newArrayList();

    private boolean failed = false;
    private StepCommand step;
    private Iterator<StepCommand> iterator;

    public Scenario(Pickle pickle, List<StepCommand> steps, List<BeforeScenarioMethod> beforeMethods) {
        this.pickle = pickle;
        this.steps = steps;
        this.beforeMethods = beforeMethods;
        for (StepCommand step : steps) {
            step.setScenario(this);
        }
    }

    public List<BeforeScenarioMethod> getBeforeMethods() {
        return beforeMethods;
    }

    public Pickle getPickle() {
        return pickle;
    }

    public void write(String message) {
        messages.add(message);
    }

    public ScenarioResult getResult() {
        return new ScenarioResult(pickle, stepResultList, messages);
    }

    public StepCommand next() {
        if (iterator == null) {
            iterator = steps.iterator();
        }
        return iterator.next();
    }

    public boolean hasNext() {
        if (iterator == null) {
            iterator = steps.iterator();
        }
        return iterator.hasNext();
    }

    public void addStepResult(StepResult stepResult) {
        stepResultList.add(stepResult);
    }

    public void prepare() {
        for (BeforeScenarioMethod method : beforeMethods) {
            method.execute(this);
        }

    }
}