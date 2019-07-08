package org.hendrix.core.sample;

import org.hendrix.annotation.BeforeScenario;
import org.hendrix.annotation.Step;
import org.hendrix.domain.Scenario;
import org.hendrix.step.decorator.JoinAfter;
import org.hendrix.step.decorator.JoinBefore;
import org.hendrix.step.decorator.RunOnce;
import org.hendrix.util.WaitForCondition;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

@ContextConfiguration(classes = {AppConfig.class})
public class ForkJoinStepdefs {

    private int seconds;
    private Scenario scenario;

    @BeforeScenario
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Step("^a wait of (.*) seconds$")
    public void secondsToWait(int seconds) {
        this.seconds = seconds;
        final LocalDateTime waitUntil = LocalDateTime.now().plusSeconds(seconds);
        WaitForCondition.waitFor(new WaitForCondition.Condition() {
            @Override
            public boolean getResult() {
                return LocalDateTime.now().isAfter(waitUntil);
            }
        }, 15);
        System.out.println("Waited " + seconds + " seconds.");
    }

    @Step(value = "^all threads join after$", decorators = {JoinAfter.class})
    public void joinThreadsAfter() {
        System.out.println(seconds + " is waiting for all threads to catch up!");
    }

    @Step(value = "^all threads join before$", decorators = {JoinBefore.class})
    public void joinThreadsBefore() {
        System.out.println(seconds + " all threads caught up!");
    }

    @Step(value = "^all examples finish$", decorators = {RunOnce.class})
    public void allDone() {
        System.out.println(seconds + " Finished!");
    }
}