package org.hendrix.reporter;

import org.hendrix.domain.ScenarioResult;

public interface Reporter {
    void open();
    
    void report(ScenarioResult... results);

    void reportOne(ScenarioResult scenarioResult);

    void close();
}
