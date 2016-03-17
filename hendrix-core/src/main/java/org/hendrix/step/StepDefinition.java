package org.hendrix.step;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.SettableFuture;
import org.hendrix.annotation.Step;
import pickles.PickleStep;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

public class StepDefinition {

    protected final Method method;
    private final SettableFuture<Boolean> completed = SettableFuture.create();
    private final Set<StepCommand> commands = Sets.newConcurrentHashSet();

    public StepDefinition(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        Step step = method.getAnnotation(Step.class);
        if (step != null) {
            return Pattern.compile(step.value());
        }
        return null;
    }

    public Method getMethod() {
        return method;
    }

    public Class<? extends StepCommand>[] getDecorators() {
        Step step = method.getAnnotation(Step.class);
        if (step != null) {
            return step.decorators();
        }
        return new Class[] {};
    }

    public StepCommand create(Object instance, PickleStep pickleStep) {
        StepCommand stepCommand = new SimpleStepCommand(this, instance, pickleStep);
        for (Class<? extends StepCommand> decorators : getDecorators()) {
            try {
                Constructor<? extends StepCommand> constructor = decorators.getConstructor(StepCommand.class);
                stepCommand = constructor.newInstance(stepCommand);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        commands.add(stepCommand);
        return stepCommand;
    }

    public void complete(StepCommand stepCommand) {
        commands.remove(stepCommand);
        if (commands.isEmpty()) {
            completed.set(true);
        }
    }

    public Future<Boolean> isCompleted() {
        return completed;
    }

}
