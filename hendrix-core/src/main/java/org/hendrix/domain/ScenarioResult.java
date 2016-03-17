package org.hendrix.domain;

import pickles.Pickle;

import java.util.List;

public class ScenarioResult {
    private final Pickle pickle;
    private final List<StepResult> stepResults;
    private final List<String> messages;
    private String timeStamp;

    public ScenarioResult(Pickle pickle, List<StepResult> stepResults, List<String> messages) {
        this.pickle = pickle;
        this.stepResults = stepResults;
        this.messages = messages;
    }

    public ResultStatus getResultStatus() {
        if (stepResults.isEmpty()) {
            return ResultStatus.SKIPPED;
        }
        for (StepResult stepResult : stepResults) {
            switch (stepResult.getResult().getResultStatus()) {
                case FAIL:
                    return ResultStatus.FAIL;
                case ASSUME_FAIL:
                    return ResultStatus.PASS;
                case IGNORED:
                case PASS:
                    continue;
                default:
                    return ResultStatus.UNKNOWN;
            }
        }
        return ResultStatus.PASS;
    }

    public List<StepResult> getStepResults() {
        return stepResults;
    }

    public Pickle getPickle() {
        return pickle;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
