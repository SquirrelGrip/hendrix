package org.hendrix.cucumber;

import cucumber.runtime.java.StepDefAnnotation;
import org.hendrix.annotation.Step;
import org.hendrix.step.StepDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class CucumberStepDefinition extends StepDefinition {
    public CucumberStepDefinition(Method method) {
        super(method);
    }

    public Pattern getPattern() {
        Pattern pattern = super.getPattern();
        String value = null;
        if (pattern == null) {
            value = getStepDefinitionValue();
        }
        if (value != null) {
            return Pattern.compile(value);
        }
        return Pattern.compile(".^");
    }


    protected String getStepDefinitionValue() {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            StepDefAnnotation stepDefAnnotation = annotation.annotationType().getAnnotation(StepDefAnnotation.class);
            if (stepDefAnnotation != null) {
                try {
                    Method value = annotation.annotationType().getMethod("value");
                    return value.invoke(annotation).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
