package org.hendrix.core;

import org.hendrix.step.StepCommand;

import java.util.concurrent.*;

public class StepCommandCompletionService implements CompletionService<StepCommand> {
    public StepCommandCompletionService(ExecutorService executorService) {
    }

    @Override
    public Future<StepCommand> submit(Callable<StepCommand> task) {
        return null;
    }

    @Override
    public Future<StepCommand> submit(Runnable task, StepCommand result) {
        return null;
    }

    @Override
    public Future<StepCommand> take() throws InterruptedException {
        return null;
    }

    @Override
    public Future<StepCommand> poll() {
        return null;
    }

    @Override
    public Future<StepCommand> poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }
}
