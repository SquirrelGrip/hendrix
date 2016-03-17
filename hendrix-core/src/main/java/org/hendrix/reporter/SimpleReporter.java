package org.hendrix.reporter;

import com.google.common.collect.Maps;
import org.hendrix.domain.ResultStatus;
import org.hendrix.domain.ScenarioResult;
import org.hendrix.domain.StepResult;

import java.util.Collection;
import java.util.Map;

public class SimpleReporter implements Reporter {

    private final Map<ResultStatus, Integer> scenarioResults = Maps.newEnumMap(ResultStatus.class);
    private final Map<ResultStatus, Integer> stepResults = Maps.newEnumMap(ResultStatus.class);

    public SimpleReporter() {
        for (ResultStatus value : ResultStatus.values()) {
            scenarioResults.put(value, 0);
            stepResults.put(value, 0);
        }
    }

    @Override
    public void open() {

    }

    @Override
    public void report(ScenarioResult... results) {
        for (ScenarioResult result : results) {
            reportOne(result);
        }
    }

    @Override
    public void reportOne(ScenarioResult scenarioResult) {
        incrementResults(this.scenarioResults, scenarioResult.getResultStatus());

        for (StepResult stepResult : scenarioResult.getStepResults()) {
            incrementResults(this.stepResults, stepResult.getResult().getResultStatus());
        }
    }

    private void incrementResults(Map<ResultStatus, Integer> results, ResultStatus resultStatus) {
        results.put(resultStatus, results.get(resultStatus) + 1);
    }

    @Override
    public void close() {

    }

    public int getScenarioPassCount() {
        return scenarioResults.get(ResultStatus.PASS);
    }

    public int getScenarioFailCount() {
        return scenarioResults.get(ResultStatus.FAIL);
    }

    public int getStepPassCount() {
        return stepResults.get(ResultStatus.PASS);
    }

    public int getStepFailCount() {
        return stepResults.get(ResultStatus.FAIL);
    }

    public int getScenarioTotalCount() {
        return getTotalCount(scenarioResults.values());
    }

    public int getStepTotalCount() {
        return getTotalCount(stepResults.values());
    }

    private int getTotalCount(Collection<Integer> values) {
        Integer totalCount = 0;
        for (Integer count : values) {
            totalCount += count;
        }
        return totalCount;
    }

    public int getStepCount(ResultStatus resultStatus) {
        return stepResults.get(resultStatus);
    }

    public int getScenarioCount(ResultStatus resultStatus) {
        return scenarioResults.get(resultStatus);
    }
}
