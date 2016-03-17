package org.hendrix.core;

import com.google.common.collect.Sets;
import org.hendrix.reporter.ReporterFactory;

import java.io.InputStream;
import java.util.Set;

public class RunnerBuilder {
    private ScenarioFactory scenarioFactory;
    private Set<String> reporters = Sets.newHashSet();

    public RunnerBuilder feature(String... features) {
        try {
            for (String feature : features) {
                getScenarioFactory().addFeature(feature);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating runner.", e);
        }
        return this;
    }

    public RunnerBuilder feature(InputStream inputStream) {
        try {
            getScenarioFactory().addFeature(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error creating runner.", e);
        }
        return this;
    }

    public RunnerBuilder glue(String... glues) {
        try {
            for (String glue : glues) {
                Class<?> glueClass = Class.forName(glue);
                getScenarioFactory().addGlue(glueClass);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public RunnerBuilder glue(Class... glues) {
        for (Class glue : glues) {
            getScenarioFactory().addGlue(glue);
        }
        return this;
    }

    public RunnerBuilder reporter(String... strings) {
        for (String string : strings) {
            reporters.add(string);
        }
        return this;
    }

    public RunnerBuilder reporter(Class... classes) {
        for (Class clazz : classes) {
            reporters.add(clazz.getName());
        }
        return this;
    }

    public Runner create() {
        Runner runner = new Runner(getScenarioFactory());
        for (String reporter : reporters) {
            runner.addReporter(ReporterFactory.create(reporter));
        }
        return runner;
    }

    public RunnerBuilder tag(String tags) {
        String[] split = tags.split(",");
        for (String tag : split) {
            getScenarioFactory().addTag(tag);
        }
        return this;
    }

    private ScenarioFactory getScenarioFactory() {
        if (scenarioFactory == null) {
            scenarioFactory = createScenarioFactory();
        }
        return scenarioFactory;
    }

    protected ScenarioFactory createScenarioFactory() {
        return new ScenarioFactory();
    }
}
