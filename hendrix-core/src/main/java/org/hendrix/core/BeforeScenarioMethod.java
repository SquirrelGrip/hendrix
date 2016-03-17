package org.hendrix.core;

import org.hendrix.domain.Scenario;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeforeScenarioMethod {
    private final Object object;
    private final Method method;

    public BeforeScenarioMethod(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public void execute(Scenario scenario) {
        try {
            method.invoke(object, new Object[] {scenario});
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
