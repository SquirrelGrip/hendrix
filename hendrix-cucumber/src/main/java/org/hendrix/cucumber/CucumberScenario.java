package org.hendrix.cucumber;

import org.hendrix.core.BeforeScenarioMethod;
import org.hendrix.domain.ResultStatus;
import org.hendrix.domain.Scenario;
import org.hendrix.step.StepCommand;
import pickles.Pickle;
import pickles.PickleTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CucumberScenario extends Scenario implements cucumber.api.Scenario {

    public CucumberScenario(Pickle pickle, List<StepCommand> stepCommands, List<BeforeScenarioMethod> beforeScenarioMethods) {
        super(pickle, stepCommands, beforeScenarioMethods);
    }

    @Override
    public Collection<String> getSourceTagNames() {
        Collection<String> strings = new ArrayList<>();
        for (PickleTag tag : getPickle().getTags()) {
            strings.add(tag.getName());
        }
        return strings;
    }

    @Override
    public String getStatus() {
        return getResult().getResultStatus().name();
    }

    @Override
    public boolean isFailed() {
        return getResult().getResultStatus() == ResultStatus.FAIL;
    }

    @Override
    public void embed(byte[] bytes, String s) {

    }

    @Override
    public String getName() {
        return getPickle().getName();
    }

    @Override
    public String getId() {
        return getPickle().getName();
    }
}
