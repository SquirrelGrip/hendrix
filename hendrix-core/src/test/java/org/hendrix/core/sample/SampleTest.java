package org.hendrix.core.sample;

import org.hendrix.core.Runner;
import org.hendrix.core.RunnerBuilder;
import org.hendrix.domain.ResultStatus;
import org.hendrix.reporter.ConsoleReporter;
import org.hendrix.reporter.SimpleReporter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SampleTest {
    private SimpleReporter simpleReporter;

    @Before
    public void setUp() throws Exception {
        simpleReporter = new SimpleReporter();
    }

    @Test
    public void sample() throws Exception {
        Runner runner = new RunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/simple.feature"))
                .glue(SimpleStepdefs.class)
                .create();

        runner.addReporter(simpleReporter);
        runner.run();
        assertEquals(7, simpleReporter.getScenarioTotalCount());
        assertEquals(5, simpleReporter.getScenarioPassCount());
        assertEquals(2, simpleReporter.getScenarioFailCount());
        assertEquals(17, simpleReporter.getStepTotalCount());
        assertEquals(14, simpleReporter.getStepPassCount());
        assertEquals(2, simpleReporter.getStepFailCount());
        assertEquals(1, simpleReporter.getStepCount(ResultStatus.SKIPPED));

        assertEquals(ResultStatus.FAIL, runner.getResults().getResultStatus());
    }

    @Test
    public void tags() throws Exception {
        Runner runner = new RunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/simple.feature"))
                .tag("@zap")
                .glue(SimpleStepdefs.class)
                .create();

        runner.run();
        assertEquals(2, runner.getScenarioResults().size());
        assertEquals(ResultStatus.PASS, runner.getResults().getResultStatus());
    }

    @Test
    public void spring() throws Exception {
        Runner runner = new RunnerBuilder()
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
        Runner runner = new RunnerBuilder()
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
    public void forceResult() throws Exception {
        Runner runner = new RunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/force.feature"))
                .glue(StandardStepdefs.class)
                .reporter(ConsoleReporter.class)
                .create();

        runner.run();
        assertEquals(4, runner.getResults().passCount());
        assertEquals(2, runner.getResults().failCount());
        assertEquals(ResultStatus.FAIL, runner.getResults().getResultStatus());
    }

    @Test
    public void forkJoin() throws Exception {
        Runner runner = new RunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/forkjoin.feature"))
                .glue(ForkJoinStepdefs.class)
                .create();

        runner.run();
        assertEquals(5, runner.getResults().passCount());
        assertEquals(ResultStatus.PASS, runner.getResults().getResultStatus());
    }

    @Test
    public void multiGlue() throws Exception {
        Runner runner = new RunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/spring.feature"))
                .feature(ClassLoader.getSystemResourceAsStream("features/simple.feature"))
                .tag("@zap,@spring")
                .glue(SpringStepdefs.class)
                .glue(SimpleStepdefs.class)
                .reporter(ConsoleReporter.class)
                .reporter("junit:target/TEST-SpringSample.xml")
                .create();

        runner.run();
        assertEquals(3, runner.getResults().passCount());
        assertEquals(ResultStatus.PASS, runner.getResults().getResultStatus());
    }


}
