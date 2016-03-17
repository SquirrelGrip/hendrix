package org.hendrix.core.sample;

import org.hendrix.annotation.BeforeScenario;
import org.hendrix.annotation.Step;
import org.hendrix.domain.Scenario;

import static org.junit.Assert.assertEquals;

public class SimpleStepdefs {

    private Scenario scenario;

    @BeforeScenario
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
    }

    @Step("backgroundStep")
    public void backgroundStep() {
        System.out.println("\tsampleGlue.backgroundStep()");
        scenario.write("\tsampleGlue.backgroundStep()");
    }

    @Step("scenarioStep")
    public void scenarioStep() {
        System.out.println("\tsampleGlue.scenarioStep()");
        scenario.write("\tsampleGlue.scenarioStep()");
    }

    @Step("scenarioOutline step x (.*)")
    public void scenarioOutlineStepX(String value) {
        System.out.println("\tsampleGlue.scenarioOutlineStepX(" + value + ")");
    }

    @Step("scenarioOutline step y")
    public void scenarioOutlineStepY() {
        System.out.println("\tsampleGlue.scenarioOutlineStepY()");
    }

    @Step("(.*) plus (.*) equals (.*)")
    public void add(int a, int b, int c) {
        int result = a + b;
        assertEquals(c, result);
    }
}
