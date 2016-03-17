package org.hendrix.core.sample;

import org.hendrix.annotation.BeforeScenario;
import org.hendrix.annotation.Step;
import org.hendrix.domain.Scenario;

import static org.junit.Assert.assertEquals;

public class ExampleGlue {

    private Scenario scenario;

    @BeforeScenario
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
    }

    @Step("(.*) plus (.*) equals (.*)")
    public void add(int a, int b, int c) {
        int result = a + b;
        assertEquals(c, result);
    }
}
