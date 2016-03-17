package org.hendrix.cucumber.sample;

import org.hendrix.core.Runner;
import org.hendrix.core.sample.AppConfig;
import org.hendrix.cucumber.CucumberRunnerBuilder;
import org.hendrix.domain.ResultStatus;
import org.hendrix.reporter.ConsoleReporter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SampleTest {

    @Test
    public void sample() throws Exception {
        Runner runner = new CucumberRunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/sample.feature"))
                .glue(SampleStepdefs.class)
                .create();

        runner.run();
        assertEquals(7, runner.getScenarioResults().size());
        assertEquals(ResultStatus.FAIL, runner.getResults().getResultStatus());
    }

    @Test
    public void tags() throws Exception {
        Runner runner = new CucumberRunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/sample.feature"))
                .tag("@zap")
                .glue(SampleStepdefs.class)
                .create();

        runner.run();
        assertEquals(2, runner.getScenarioResults().size());
        assertEquals(ResultStatus.PASS, runner.getResults().getResultStatus());
    }

    @Test
    public void spring() throws Exception {
        Runner runner = new CucumberRunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/spring.feature"))
                .glue(SpringStepdefs.class)
                .reporter(ConsoleReporter.class)
                .reporter("junit:target/TEST-SpringSample.xml")
                .create();

        runner.run();
        assertEquals(2, runner.getScenarioResults().size());
        assertEquals(ResultStatus.PASS, runner.getResults().getResultStatus());
    }

    @Test
    public void assumption() throws Exception {
        Runner runner = new CucumberRunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/assumption.feature"))
                .glue(StandardStepdefs.class)
                .reporter(ConsoleReporter.class)
                .create();

        runner.run();
        assertEquals(3, runner.getResults().passCount());
        assertEquals(1, runner.getResults().failCount());
        assertEquals(ResultStatus.FAIL, runner.getResults().getResultStatus());
    }

    @Test
    public void multiGlue() throws Exception {
        Runner runner = new CucumberRunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/spring.feature"))
                .feature(ClassLoader.getSystemResourceAsStream("features/sample.feature"))
                .tag("@zap,@spring")
                .glue(SpringStepdefs.class)
                .glue(SampleStepdefs.class)
                .reporter(ConsoleReporter.class)
                .reporter("junit:target/TEST-SpringSample.xml")
                .create();

        runner.run();
        assertEquals(3, runner.getResults().passCount());
        assertEquals(ResultStatus.PASS, runner.getResults().getResultStatus());
    }


}
