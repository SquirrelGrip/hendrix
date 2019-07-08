package org.hendrix.step;

import com.google.common.util.concurrent.SettableFuture;
import org.hendrix.core.IgnoredException;
import org.hendrix.domain.Result;
import org.hendrix.domain.Scenario;
import org.hendrix.domain.StepResult;
import org.hendrix.gherkin.PickleStep;
import org.hendrix.util.TypeCoercer;
import org.junit.AssumptionViolatedException;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;
import java.util.regex.Matcher;

public class SimpleStepCommand implements StepCommand {
    private final PickleStep pickleStep;
    private final StepDefinition stepDefinition;
    private final Object instance;
    private Scenario scenario;
    private SettableFuture<Result> result = SettableFuture.create();;

    public SimpleStepCommand(StepDefinition stepDefinition, Object instance, PickleStep pickleStep) {
        this.stepDefinition = stepDefinition;
        this.instance = instance;
        this.pickleStep = pickleStep;
    }

    @Override
    public StepCommand call() {
        if(!getResult().isDone()) {
            try {
                stepDefinition.getMethod().invoke(instance, getArguments());
                getStepDefinition().complete(this);
                result.set(Result.pass());
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof IgnoredException) {
                    result.set(Result.ignored());
                } else if (e.getTargetException() instanceof AssumptionViolatedException) {
                    result.set(Result.assumeFail(e.getTargetException()));
                } else {
                    result.set(Result.fail(e.getTargetException()));
                }
            } catch (IllegalAccessException e) {
                result.set(Result.fail(e));
            }
        }
        return this;
    }

    Object[] getArguments() {
        String[] rawArguments = getStringArguments();
        Class<?>[] parameterTypes = stepDefinition.getMethod().getParameterTypes();
        Object[] arguments = new Object[rawArguments.length];
        for (int i = 0; i < rawArguments.length; i++) {
            arguments[i] = TypeCoercer.getInstance().coerce(rawArguments[i], parameterTypes[i]);
        }
        return arguments;
    }

    String[] getStringArguments() {
        Matcher matcher = stepDefinition.getPattern().matcher(pickleStep.getText());
        matcher.find();
        int count = matcher.groupCount();
        String[] args = new String[count];
        for (int i = 0; i < count; i++) {
            args[i] = matcher.group(i + 1);
        }
        return args;
    }

    public PickleStep getPickleStep() {
        return pickleStep;
    }

    public Object getInstance() {
        return instance;
    }

    public StepDefinition getStepDefinition() {
        return stepDefinition;
    }

    @Override
    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public void submit(CompletionService<StepCommand> completionService) {
        completionService.submit(this);
    }

    @Override
    public Future<Result> getResult() {
        return result;
    }

    @Override
    public void skip() {
        result.set(Result.skipped());
        try {
            scenario.addStepResult(new StepResult(getPickleStep(), result.get()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stepDefinition.complete(this);
    }
}
