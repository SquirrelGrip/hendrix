package org.hendrix.reporter;

import org.hendrix.domain.Result;
import org.hendrix.domain.ScenarioResult;
import org.hendrix.domain.StepResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pickles.Pickle;
import pickles.PickleStep;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SimpleReporterTest {

    private SimpleReporter testSubject;
    @Mock
    private Pickle pickle;
    @Mock
    private PickleStep pickleStep;
    private List<StepResult> stepResults = new ArrayList<>();
    private List<String> messages = new ArrayList<>();

    @Before
    public void setUp() {
        testSubject = new SimpleReporter();
    }

    @Test
    public void getScenarioCounts_WhenPass() throws Exception {
        ScenarioResult scenarioResult = new ScenarioResult(pickle, stepResults, messages);
        stepResults.add(new StepResult(pickleStep, Result.pass()));
        testSubject.reportOne(scenarioResult);

        assertEquals(1, testSubject.getScenarioPassCount());
        assertEquals(0, testSubject.getScenarioFailCount());
        assertEquals(1, testSubject.getScenarioTotalCount());
        assertEquals(1, testSubject.getStepPassCount());
        assertEquals(0, testSubject.getStepFailCount());
        assertEquals(1, testSubject.getStepTotalCount());
    }

    @Test
    public void getScenarioCounts_WhenFail() throws Exception {
        ScenarioResult scenarioResult = new ScenarioResult(pickle, stepResults, messages);
        stepResults.add(new StepResult(pickleStep, Result.fail(new Exception("Fail Message"))));
        testSubject.reportOne(scenarioResult);

        assertEquals(0, testSubject.getScenarioPassCount());
        assertEquals(1, testSubject.getScenarioFailCount());
        assertEquals(1, testSubject.getScenarioTotalCount());
        assertEquals(0, testSubject.getStepPassCount());
        assertEquals(1, testSubject.getStepFailCount());
        assertEquals(1, testSubject.getStepTotalCount());
    }

    @Test
    public void getScenarioCounts_WhenFailAndSuccess() throws Exception {
        List<StepResult> stepResults1 = new ArrayList<>();
        ScenarioResult scenarioResult1 = new ScenarioResult(pickle, stepResults1, messages);
        stepResults1.add(new StepResult(pickleStep, Result.pass()));
        List<StepResult> stepResults2 = new ArrayList<>();
        ScenarioResult scenarioResult2 = new ScenarioResult(pickle, stepResults2, messages);
        stepResults2.add(new StepResult(pickleStep, Result.fail(new Exception("Fail Message"))));
        testSubject.report(scenarioResult1);
        testSubject.report(scenarioResult2);

        assertEquals(1, testSubject.getScenarioPassCount());
        assertEquals(1, testSubject.getScenarioFailCount());
        assertEquals(2, testSubject.getScenarioTotalCount());
        assertEquals(1, testSubject.getStepPassCount());
        assertEquals(1, testSubject.getStepFailCount());
        assertEquals(2, testSubject.getStepTotalCount());
    }

}
