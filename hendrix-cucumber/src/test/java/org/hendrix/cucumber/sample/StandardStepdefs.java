package org.hendrix.cucumber.sample;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

public class StandardStepdefs {

    @Given("assumption (.*)")
    public void assumption(boolean value) {
        assumeTrue(value);
    }

    @Then("pass")
    public void passed() {
        // Nothing
    }

    @Then("fail")
    public void failed() {
        fail("Forcing Fail");
    }

}
