package org.hendrix.core.sample;

import org.hendrix.annotation.Step;
import org.hendrix.step.decorator.Fail;
import org.hendrix.step.decorator.Ignore;
import org.hendrix.step.decorator.Pass;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

public class StandardStepdefs {

    @Step("assumption (.*)")
    public void assumption(boolean value) {
        assumeTrue(value);
    }

    @Step("pass")
    public void passed() {
        // Nothing
    }

    @Step("fail")
    public void failed() {
        fail("Forcing Fail");
    }

    @Step(value = "force pass after (.*)", decorators = {Pass.class})
    public void forcePass(String result) {
        if ("fail".equals(result)) {
            fail("Forcing Fail");
        }
    }

    @Step(value = "force fail after (.*)", decorators = {Fail.class})
    public void forceFail(String result) {
        if ("fail".equals(result)) {
            fail("Forcing Fail");
        }
    }

    @Step(value = "force ignore after (.*)", decorators = {Ignore.class})
    public void forceIgnore(String result) {
        if ("fail".equals(result)) {
            fail("Forcing Fail");
        }
    }

}
