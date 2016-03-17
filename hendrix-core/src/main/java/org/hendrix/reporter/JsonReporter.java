package org.hendrix.reporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.hendrix.domain.Result;
import org.hendrix.domain.ScenarioResult;
import org.hendrix.domain.StepResult;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonReporter implements Reporter {

    private File outputFile;
    private final List<ScenarioResult> scenarioResults = Lists.newArrayList();

    public JsonReporter(File outputFile) {
        this.outputFile = outputFile;
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
        scenarioResults.add(scenarioResult);
    }

    @Override
    public void close() {
        outputFile.getParentFile().mkdirs();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(outputFile, new JsonReport(scenarioResults));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public class JsonReport {
        private List<JsonFeature> jsonFeatures = Lists.newArrayList();

        public JsonReport(List<ScenarioResult> scenarioResults) {
            jsonFeatures.add(new JsonFeature("sample", scenarioResults));
        }

        public List<JsonFeature> getFeatures() {
            return jsonFeatures;
        }        
        
    }
    
    public class JsonFeature {
        private String name;
        private List<ScenarioResult> scenarioResults;

        public JsonFeature(String name, List<ScenarioResult> scenarioResults) {
            this.name = name;
            this.scenarioResults = scenarioResults;
        }

        public String getName() {
            return name;
        }
        
        public List<JsonScenario> getScenarios() {
            List<JsonScenario> scenarios = Lists.newArrayList();
            for (ScenarioResult scenarioResult : scenarioResults) {
                scenarios.add(new JsonScenario(scenarioResult));
            }
            return scenarios;
        }
    }

    public class JsonScenario {
        private ScenarioResult scenarioResult;
        private final List<String> files = Lists.newArrayList();
        private final Map<String, Object> objects = Maps.newLinkedHashMap();
        private final List<String> messages = Lists.newArrayList();

        public JsonScenario(ScenarioResult scenarioResult) {
            this.scenarioResult = scenarioResult;
            for (String message : scenarioResult.getMessages()) {
                    if (message.startsWith("FILE:")) {
                        addFile(message.substring(5));
                    } else if (message.startsWith("JSON:")) {
                        Map.Entry<String, Object> entry = null;
                        try {
                            entry = new JsonLogger().fromLog(message);
                            addObject(entry.getKey(), entry.getValue());
                        } catch (LoggerException ignored) {
                            System.err.println("Invalid message format: " + message);
                        }
                    } else {
                        addMessage(message);
                    }
            }
        }

        private void addMessage(String message) {
            messages.add(message);
        }

        private void addObject(String key, Object value) {
            objects.put(key, value);
        }

        private void addFile(String file) {
            files.add(file);
        }

        public List<JsonStep> getSteps() {
            List<JsonStep> steps = Lists.newArrayList();
            for (StepResult stepResult : scenarioResult.getStepResults()) {
                steps.add(new JsonStep(stepResult));
            }
            return steps;
        }
        
        public String getName() {
            return scenarioResult.getPickle().getName();
        }
        
        public List<String> getMessages() {
            return scenarioResult.getMessages();
        }
        
        public String getResult() {
            return scenarioResult.getResultStatus().name();
        }

        public List<String> getFiles() {
            return files;
        }

        public Map<String, Object> getObjects() {
            return objects;
        }
        
    }
    
    public class JsonStep {
        private StepResult stepResult;
        
        public JsonStep(StepResult stepResult) {
            this.stepResult = stepResult;
        }
        
        public String getName() {
            return stepResult.getPickleStep().getText();
        }
        
        public Result getResult() { 
            return stepResult.getResult();
        }
        
        public String getKeyword() {
            return stepResult.getPickleStep().getKeyword();
        }
    }
}
