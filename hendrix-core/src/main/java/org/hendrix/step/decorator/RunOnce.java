package org.hendrix.step.decorator;

import org.hendrix.domain.Result;
import org.hendrix.step.StepCommand;

import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunOnce extends StepCommandDecorator {

    private static final AtomicBoolean flag = new AtomicBoolean(true);
    private static StepCommand singleStepCommand;

    public RunOnce(StepCommand stepCommand) {
        super(stepCommand);
    }

    @Override
    public void submit(CompletionService<StepCommand> executeService) {
        executeService.submit(this);
    }

    @Override
    public StepCommand call() throws Exception {
        if (flag.getAndSet(false)) {
            singleStepCommand = stepCommand;
            return stepCommand.call();
        }
        return this;
    }

    @Override
    public Future<Result> getResult() {
        return singleStepCommand.getResult();
    }
}
