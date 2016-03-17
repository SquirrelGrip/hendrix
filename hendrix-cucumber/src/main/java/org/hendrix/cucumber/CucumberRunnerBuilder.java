package org.hendrix.cucumber;

import org.hendrix.core.RunnerBuilder;
import org.hendrix.core.ScenarioFactory;

public class CucumberRunnerBuilder extends RunnerBuilder {
    @Override
    protected ScenarioFactory createScenarioFactory() {
        return new CucumberScenarioFactory();
    }
}
