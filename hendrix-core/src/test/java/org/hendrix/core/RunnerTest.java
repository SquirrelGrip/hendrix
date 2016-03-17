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

    private Runner testObj;

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

        testObj = new Runner(scenarioFactory);
    }

    @Test
    public void run_WhenSinglePassStepScenario() throws Exception {
        scenarios.add(ScenarioFixture.createScenarioWithSteps(ScenarioFixture.createPassStepCommand()));

        testObj.run();

        assertEquals(ResultStatus.PASS, testObj.getResults().getResultStatus());
        verify(scenarioFactory).createScenarios();
        verifyZeroInteractions(reporter);
    }

    @Test
    public void run_WhenDoublePassStepScenario() throws Exception {
        scenarios.add(ScenarioFixture.createScenarioWithSteps(ScenarioFixture.createPassStepCommand(), ScenarioFixture.createPassStepCommand()));

        testObj.run();

        assertEquals(ResultStatus.PASS, testObj.getResults().getResultStatus());
        verify(scenarioFactory).createScenarios();
        verifyZeroInteractions(reporter);
    }

    @Test
    public void run_WhenPassingStepThenFailStepScenario() throws Exception {
        scenarios.add(ScenarioFixture.createScenarioWithSteps(ScenarioFixture.createPassStepCommand(), ScenarioFixture.createFailStepCommand()));

        testObj.run();

        assertEquals(ResultStatus.FAIL, testObj.getResults().getResultStatus());
        verify(scenarioFactory).createScenarios();
        verifyZeroInteractions(reporter);
    }

    @Test
    public void run_WhenFailStepThenPassStepScenario() throws Exception {
        scenarios.add(ScenarioFixture.createScenarioWithSteps(ScenarioFixture.createFailStepCommand(), ScenarioFixture.createPassStepCommand()));

        testObj.run();

        assertEquals(ResultStatus.FAIL, testObj.getResults().getResultStatus());
        verify(scenarioFactory).createScenarios();
        verifyZeroInteractions(reporter);
    }

    @Test
    public void run_WhenReporterIsAdded() throws Exception {
        scenarios.add(ScenarioFixture.createScenarioWithSteps(ScenarioFixture.createPassStepCommand()));

        testObj.addReporter(reporter);
        testObj.run();

        verify(reporter).close();
        verify(reporter).reportOne(any(ScenarioResult.class));
        verifyNoMoreInteractions(reporter);
    }

}