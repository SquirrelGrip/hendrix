package org.hendrix.step.decorator;

import com.google.common.util.concurrent.SettableFuture;
import org.hendrix.domain.Result;
import org.hendrix.step.StepCommand;

import java.util.concurrent.*;

public class JoinAfter extends StepCommandDecorator {

    public JoinAfter(StepCommand stepCommand) {
        super(stepCommand);
    }

    @Override
    public StepCommand call() throws Exception {
        this.stepCommand.call();
        getStepDefinition().complete(this);
        return this;
    }

    @Override
    public void submit(CompletionService<StepCommand> executeService) {
        executeService.submit(this);
    }

    @Override
    public Future<Result> getResult() {
        if(getStepDefinition().isCompleted().isDone()) {
            return super.getResult();
        }
        return SettableFuture.create();
    }
}
