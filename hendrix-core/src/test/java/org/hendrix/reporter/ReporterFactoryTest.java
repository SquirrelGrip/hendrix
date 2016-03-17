package org.hendrix.reporter;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class ReporterFactoryTest {

    @Test
    public void create_WhenConsoleReporterClass() throws Exception {
        Reporter reporter = ReporterFactory.create(ConsoleReporter.class.getName());
        assertThat(reporter, instanceOf(ConsoleReporter.class));
    }

    @Test
    public void create_WhenConsole() throws Exception {
        Reporter reporter = ReporterFactory.create("console");
        assertThat(reporter, instanceOf(ConsoleReporter.class));
    }

    @Test
    public void create_WhenJunit() throws Exception {
        Reporter reporter = ReporterFactory.create("junit:target/TEST-sample.xml");
        assertThat(reporter, instanceOf(JUnitReporter.class));
    }

}