package org.hendrix.reporter;

import org.hendrix.domain.ScenarioResult;
import org.hendrix.domain.StepResult;

import java.io.PrintWriter;

public class ConsoleReporter implements Reporter {

    private PrintWriter writer;

    public ConsoleReporter(PrintWriter writer) {
        this.writer = writer;
    }
    
    public ConsoleReporter() {
        this(new PrintWriter(System.out));
    }

    @Override
    public void open() {
        
    }

    @Override
    public void report(ScenarioResult... results) {
        for (ScenarioResult result : results) {
            reportOne(result);
        }
        close();
    }

    @Override
    public void reportOne(ScenarioResult scenarioResult) {
        writer.println(scenarioResult.getPickle().getName() + ": " + scenarioResult.getResultStatus());
        for (StepResult stepResult : scenarioResult.getStepResults()) {
            writer.println("\t" + stepResult.getPickleStep().getText() + ": " + stepResult.getResult().toString());
        }
        writer.println("Messages:");
        for (String message : scenarioResult.getMessages()) {
            writer.println("\t" + message);
        }
        writer.println();
        writer.flush();
    }

    @Override
    public void close() {
        writer.flush();
    }
}
