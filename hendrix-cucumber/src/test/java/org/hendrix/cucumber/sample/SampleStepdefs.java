package org.hendrix.cucumber.sample;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class SampleStepdefs {

    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
    }

    @After
    public void tearDown(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("backgroundStep")
    public void backgroundStep() {
        System.out.println("\tsampleGlue.backgroundStep()");
        scenario.write("\tsampleGlue.backgroundStep()");
    }

    @When("scenarioStep")
    public void scenarioStep() {
        System.out.println("\tsampleGlue.scenarioStep()");
        scenario.write("\tsampleGlue.scenarioStep()");
    }

    @When("scenarioOutline step x (.*)")
    public void scenarioOutlineStepX(String value) {
        System.out.println("\tsampleGlue.scenarioOutlineStepX(" + value + ")");
    }

    @When("scenarioOutline step y")
    public void scenarioOutlineStepY() {
        System.out.println("\tsampleGlue.scenarioOutlineStepY()");
    }

    @Then("(.*) plus (.*) equals (.*)")
    public void add(int a, int b, int c) {
        int result = a + b;
        assertEquals(c, result);
    }
}
