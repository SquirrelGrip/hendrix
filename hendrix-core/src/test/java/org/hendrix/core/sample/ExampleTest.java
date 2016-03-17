package org.hendrix.core.sample;

import org.hendrix.core.Runner;
import org.hendrix.core.RunnerBuilder;
import org.junit.Test;

public class ExampleTest {

    @Test
    public void example() throws Exception {
        Runner runner = new RunnerBuilder()
                .feature(ClassLoader.getSystemResourceAsStream("features/example.feature"))
                .glue(ExampleGlue.class)
                .create();
        runner.run();
    }


}
