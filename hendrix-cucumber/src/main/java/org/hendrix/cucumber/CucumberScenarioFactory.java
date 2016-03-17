package org.hendrix.cucumber;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.StepDefAnnotation;
import org.hendrix.core.BeforeScenarioMethod;
import org.hendrix.core.ScenarioFactory;
import org.hendrix.domain.Scenario;
import org.hendrix.step.StepCommand;
import org.hendrix.step.StepDefinition;
import pickles.Pickle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class CucumberScenarioFactory extends ScenarioFactory {

    protected void processMethod(Method method) {
        super.processMethod(method);
        if (hasStepDefinition(method)) {
            stepDefinitions.add(new CucumberStepDefinition(method));
        }
        if (method.isAnnotationPresent(Before.class)) {
            beforeMethods.add(method);
        }
    }

    protected Scenario createScenario(Pickle pickle, List<StepCommand> stepCommands, List<BeforeScenarioMethod> beforeScenarioMethods) {
        return new CucumberScenario(pickle, stepCommands, beforeScenarioMethods);
    }


    private boolean hasStepDefinition(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            StepDefAnnotation stepDefAnnotation = annotation.annotationType().getAnnotation(StepDefAnnotation.class);
            if (stepDefAnnotation != null) {
                return true;
            }
        }
        return false;
    }

}
