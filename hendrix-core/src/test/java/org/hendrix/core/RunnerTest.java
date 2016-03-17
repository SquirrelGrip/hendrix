package org.hendrix.core;

import com.google.common.collect.Lists;
import org.hendrix.domain.ResultStatus;
import org.hendrix.domain.Scenario;
import org.hendrix.domain.ScenarioResult;
import org.hendrix.reporter.Reporter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class RunnerTest {

    private Runner testSubject;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private Reporter reporter;
    @Mock
    private ScenarioFactory scenarioFactory;
    private List<Scenario> scenarios;

    @Before
    public void setUp() {
        scenarios = Lists.newArrayList();
        when(scenarioFactory.createScenarios()).thenReturn(scenarios);

        testSubject = new Runner(scenarioFactory);
    }

    @Test
    public void run_WhenSinglePassStepScenario() throws Exception {
        scenarios.add(ScenarioFixture.createScenarioWithSteps(ScenarioFixture.createPassStepCommand()));

        testSubject.run();

        assertEquals(ResultStatus.PASS, testSubject.getResults().getResultStatus());
        verify(scenarioFactory).createScenarios();
        verifyZeroInteractions(reporter);
    }

    @Test
    public void run_WhenDoublePassStepScenario() throws Exception {
        scenarios.add(ScenarioFixture.createScenarioWithSteps(ScenarioFixture.createPassStepCommand(), ScenarioFixture.createPassStepCommand()));

        testSubject.run();

        assertEquals(ResultStatus.PASS, testSubject.getResults().getResultStatus());
        verify(scenarioFactory).createScenarios();
        verifyZeroInteractions(reporter);
    }

    @Test
    public void run_WhenPassingStepThenFailStepScenario() throws Exception {
        scenarios.add(ScenarioFixture.createScenarioWithSteps(ScenarioFixture.createPassStepCommand(), ScenarioFixture.createFailStepCommand()));

        testSubject.run();

        assertEquals(ResultStatus.FAIL, testSubject.getResults().getResultStatus());
        verify(scenarioFactory).createScenarios();
        verifyZeroInteractions(reporter);
    }

    @Test
    public void run_WhenFailStepThenPassStepScenario() throws Exception {
        scenarios.add(ScenarioFixture.createScenarioWithSteps(ScenarioFixture.createFailStepCommand(), ScenarioFixture.createPassStepCommand()));

        testSubject.run();

        assertEquals(ResultStatus.FAIL, testSubject.getResults().getResultStatus());
        verify(scenarioFactory).createScenarios();
        verifyZeroInteractions(reporter);
    }

    @Test
    public void run_WhenReporterIsAdded() throws Exception {
        scenarios.add(ScenarioFixture.createScenarioWithSteps(ScenarioFixture.createPassStepCommand()));

        testSubject.addReporter(reporter);
        testSubject.run();

        verify(reporter).close();
        verify(reporter).reportOne(any(ScenarioResult.class));
        verifyNoMoreInteractions(reporter);
    }

}