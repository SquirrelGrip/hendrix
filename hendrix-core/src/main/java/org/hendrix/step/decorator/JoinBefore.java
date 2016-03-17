package org.hendrix.step.decorator;

import com.google.common.util.concurrent.SettableFuture;
import org.hendrix.domain.Result;
import org.hendrix.step.StepCommand;

import java.util.concurrent.*;

public class JoinBefore extends StepCommandDecorator {

    public JoinBefore(StepCommand stepCommand) {
        super(stepCommand);
    }

    @Override
    public StepCommand call() throws Exception {
        getStepDefinition().complete(this);
        if(getStepDefinition().isCompleted().isDone()) {
            return this.stepCommand.call();
        }
        return this;
    }

    @Override
    public void submit(CompletionService<StepCommand> executeService) {
        executeService.submit(this);
    }

    @Override
    public Future<Result> getResult() {
        if(super.getResult().isDone()) {
            return super.getResult();
        }
        return SettableFuture.create();
    }

}
