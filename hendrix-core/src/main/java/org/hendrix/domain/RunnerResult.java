package org.hendrix.domain;

import java.util.List;

public class RunnerResult {

    private List<ScenarioResult> scenarioResults;

    public RunnerResult(List<ScenarioResult> scenarioResults) {
        this.scenarioResults = scenarioResults;
    }
    
    public ResultStatus getResultStatus() {
        ResultStatus resultStatus = ResultStatus.UNKNOWN;
        for (ScenarioResult scenarioResult : scenarioResults) {
            if (scenarioResult.getResultStatus() == ResultStatus.FAIL) {
                return ResultStatus.FAIL;
            }
            resultStatus = ResultStatus.PASS;
        }
        return resultStatus;
    }

    public int passCount() {
        return getCount(ResultStatus.PASS);
    }

    public int failCount() {
        return getCount(ResultStatus.FAIL);
    }

    public int getCount(ResultStatus status) {
        int count = 0;
        for (ScenarioResult scenarioResult : scenarioResults) {
            if(scenarioResult.getResultStatus() == status) {
                count++;
            }
        }
        return count;
    }
}
