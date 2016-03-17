package org.hendrix.core.sample;

import org.hendrix.core.Runner;
import org.hendrix.core.RunnerBuilder;
import org.hendrix.reporter.SimpleReporter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ExampleTest {
    private SimpleReporter simpleReporter;

    @Test
    public void example() throws Exception {
        Runner runner = new RunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/example.feature"))
                .glue(ExampleGlue.class)
                .create();

        runner.run();
    }


}
