package org.hendrix.step;

import org.hendrix.domain.Result;
import org.hendrix.domain.Scenario;
import pickles.PickleStep;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;

public interface StepCommand extends Callable<StepCommand> {
    Object getInstance();

    PickleStep getPickleStep();

    StepDefinition getStepDefinition();

    Scenario getScenario();

    void skip();

    void setScenario(Scenario scenario);

    void submit(CompletionService<StepCommand> executeService);

    Future<Result> getResult();

}
