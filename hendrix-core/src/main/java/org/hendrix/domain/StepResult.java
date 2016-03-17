package org.hendrix.domain;

import pickles.PickleStep;

public class StepResult {
    private final PickleStep pickleStep;
    private final Result result;
    
    public StepResult(PickleStep pickleStep, Result result) {
        this.pickleStep = pickleStep;
        this.result = result;
    }

    public PickleStep getPickleStep() {
        return pickleStep;
    }

    public Result getResult() {
        return result;
    }

}
