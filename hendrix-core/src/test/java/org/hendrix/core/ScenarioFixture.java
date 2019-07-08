package org.hendrix.core;

import com.google.common.collect.Lists;
import org.hendrix.annotation.Step;
import org.hendrix.domain.Scenario;
import org.hendrix.gherkin.Pickle;
import org.hendrix.gherkin.PickleLocation;
import org.hendrix.gherkin.PickleStep;
import org.hendrix.gherkin.PickleTag;
import org.hendrix.step.StepCommand;
import org.hendrix.step.StepDefinition;
import org.hendrix.step.SimpleStepCommand;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ScenarioFixture {

    @Step("PASS")
    public void pass() {

    }

    @Step("FAIL")
    public void fail() {
        throw new RuntimeException();
    }

    public void before(Scenario scenario) {

    }

    public static Scenario createPassScenario() {
        return createScenarioWithSteps(createPassStepCommand());
    }

    public static Scenario createFailScenario() {
        return createScenarioWithSteps(createFailStepCommand());
    }

    public final static ScenarioFixture scenarioFixture = new ScenarioFixture();

    public static StepCommand createPassStepCommand() {
        try {
            PickleStep failingPickleStep = new PickleStep("Given", "PASS", new PickleLocation(1, 1));
            Method method = ScenarioFixture.class.getMethod("pass");
            StepDefinition stepDefinition = new StepDefinition(method);
            return new SimpleStepCommand(stepDefinition, scenarioFixture, failingPickleStep);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static StepCommand createFailStepCommand() {
        try {
            PickleStep failingPickleStep = new PickleStep("Given", "FAIL", new PickleLocation(1, 1));
            Method method = ScenarioFixture.class.getMethod("fail");
            StepDefinition stepDefinition = new StepDefinition(method);
            return new SimpleStepCommand(stepDefinition, scenarioFixture, failingPickleStep);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Scenario createScenarioWithSteps(StepCommand... steps) {
        try {
            ArrayList<PickleStep> pickleSteps = Lists.newArrayList();
            for (StepCommand step : steps) {
                pickleSteps.add(step.getPickleStep());
            }
            Pickle pickle = new Pickle("pickle", pickleSteps, Lists.newArrayList(new PickleTag("tag", new PickleLocation(1, 3))), new PickleLocation(1, 2));

            BeforeScenarioMethod beforeMethod = new BeforeScenarioMethod(scenarioFixture, ScenarioFixture.class.getMethod("before", Scenario.class));

            Scenario scenario = new Scenario(pickle, Lists.newArrayList(steps), Lists.newArrayList(beforeMethod));
            for (StepCommand step : steps) {
                ((SimpleStepCommand) step).setScenario(scenario);
            }
            return scenario;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }
}
