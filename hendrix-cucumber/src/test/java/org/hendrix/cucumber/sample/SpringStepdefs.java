package org.hendrix.cucumber.sample;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.hendrix.core.sample.beans.Glass;
import org.hendrix.core.sample.beans.Ice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration("classpath:cucumber.xml")
public class SpringStepdefs {

    @Autowired
    private Ice ice;

    @Autowired
    private Glass glass;

    @Before
    public void before(Scenario scenario) {
        System.out.println("Running @Before: " + scenario.getName());
    }

    public Glass getGlass() {
        return glass;
    }

    @Given("^I have ice$")
    public void haveIce() throws Throwable {
        assertNotNull(ice);
    }

    @When("^I have glass$")
    public void haveGlass() throws Throwable {
        assertNotNull(glass);
    }
}