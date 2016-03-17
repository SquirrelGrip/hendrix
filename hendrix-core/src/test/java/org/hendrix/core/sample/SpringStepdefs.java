package org.hendrix.core.sample;

import org.hendrix.annotation.BeforeScenario;
import org.hendrix.core.sample.beans.Ice;
import org.hendrix.domain.Scenario;
import org.hendrix.annotation.Step;
import org.hendrix.core.sample.beans.Glass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration("classpath:cucumber.xml")
public class SpringStepdefs {

    @Autowired
    private Ice ice;

    @Autowired
    private Glass glass;

    @BeforeScenario
    public void before(Scenario scenario) {
        System.out.println("Running @BeforeScenario: " + scenario.getPickle().getName());
    }

    public Glass getGlass() {
        return glass;
    }

    @Step("^I have ice$")
    public void haveIce() throws Throwable {
        assertNotNull(ice);
    }

    @Step("^I have glass$")
    public void haveGlass() throws Throwable {
        assertNotNull(glass);
    }
}