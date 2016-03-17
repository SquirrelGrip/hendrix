package org.hendrix.reporter;

import org.hendrix.domain.ResultStatus;
import org.hendrix.domain.ScenarioResult;
import org.hendrix.domain.StepResult;

import java.io.*;

public class JUnitReporter implements Reporter {

    private PrintWriter writer;

    public JUnitReporter(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public JUnitReporter(File file) throws IOException {
        this(new FileWriter(file));
    }

    private int getTotalStepCount(ScenarioResult... scenarioResults) {
        int stepCount = 0;
        for (ScenarioResult scenarioResult : scenarioResults) {
            stepCount =+ scenarioResult.getStepResults().size();
        }
        return stepCount;
    }

    private int getTotalFailCount(ScenarioResult... scenarioResults) {
        int failCount = 0;
        for (ScenarioResult scenarioResult : scenarioResults) {
            failCount =+ getScenarioFailCount(scenarioResult);
        }
        return failCount;
    }

    private int getScenarioFailCount(ScenarioResult scenarioResult) {
        int failCount = 0;
        if (scenarioResult.getResultStatus() == ResultStatus.FAIL) {
            for (StepResult stepResult : scenarioResult.getStepResults()) {
                if (stepResult.getResult().getResultStatus() == ResultStatus.FAIL) {
                    failCount++;
                }
            }
        }
        return failCount;
    }

    @Override
    public void open() {
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    }

    @Override
    public void report(ScenarioResult... results) {
        header(getTotalStepCount(results), getTotalFailCount(results));
        for (ScenarioResult result : results) {
            reportOne(result);
        }
        footer();
    }

    public void footer() {
        writer.println("</testsuites>");
    }

    public void header(int testCount, int failures) {
        writer.println("<testsuites disabled=\"0\" errors=\"0\" failures=\"" + failures + "\" name=\"\" tests=\"" + testCount + "\" time=\"\">");
    }

    @Override
    public void reportOne(ScenarioResult scenarioResult) {
        writer.println("\t<testsuite disabled=\"false\" errors=\"0\" failures=\""+getScenarioFailCount(scenarioResult)+"\" hostname=\"\" id=\"\" name=\""+scenarioResult.getPickle().getName()+"\" package=\"\" skipped=\"false\" tests=\""+scenarioResult.getStepResults().size()+"\" time=\"\" timestamp=\""+scenarioResult.getTimeStamp()+"\">");
        for (StepResult stepResult : scenarioResult.getStepResults()) {
            writer.println("\t\t<testcase assertions=\"\" classname=\"\" name=\"" + stepResult.getPickleStep().getText() + "\" status=\"" + stepResult.getResult().getResultStatus() + "\" time=\"\">");
            if (stepResult.getResult().getResultStatus() == ResultStatus.SKIPPED) {
                writer.println("\t\t\t<skipped/>");
            }
            writer.println("\t\t\t<error message=\"\" type=\"\"/>");
            if (stepResult.getResult().getResultStatus() == ResultStatus.SKIPPED) {
                writer.println("\t\t\t<failure message=\"" + stepResult.getResult().getMessage() + "\" type=\"" + stepResult.getResult().getMessage().getClass() + "\"/>");
            }
            writer.println("\t\t\t<system-out/>");
            writer.println("\t\t\t<system-err/>");
            writer.println("\t\t</testcase>");
        }
        writer.println("\t\t<system-out>");
        for (String message : scenarioResult.getMessages()) {
            writer.println(message);
        }
        writer.println("\t\t</system-out>");
        writer.println("\t\t<system-err/>");
        writer.println("\t</testsuite>");
    }

    @Override
    public void close() {
        writer.flush();
        writer.close();
    }


}
