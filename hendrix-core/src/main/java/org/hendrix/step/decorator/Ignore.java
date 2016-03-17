package org.hendrix.step.decorator;

import com.google.common.util.concurrent.Futures;
import org.hendrix.domain.Result;
import org.hendrix.step.StepCommand;

import java.util.concurrent.Future;

public class Ignore extends StepCommandDecorator {

    public Ignore(StepCommand stepCommand) {
        super(stepCommand);
    }

    @Override
    public StepCommand call() throws Exception {
        return this;
    }

    @Override
    public Future<Result> getResult() {
        return Futures.immediateFuture(Result.ignored());
    }
}
