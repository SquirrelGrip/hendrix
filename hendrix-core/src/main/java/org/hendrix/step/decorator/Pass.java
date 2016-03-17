package org.hendrix.step.decorator;

import com.google.common.util.concurrent.Futures;
import org.hendrix.domain.Result;
import org.hendrix.step.StepCommand;

import java.util.concurrent.Future;

public class Pass extends StepCommandDecorator {

    public Pass(StepCommand stepCommand) {
        super(stepCommand);
    }

    @Override
    public Future<Result> getResult() {
        return Futures.immediateFuture(Result.pass());
    }
}
