package org.hendrix.core;

import com.google.common.collect.Lists;
import org.hendrix.domain.Scenario;
import org.hendrix.gherkin.Pickle;
import org.hendrix.step.StepCommand;
import org.junit.Test;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class ScenarioTest {
    @Mock
    private Pickle pickle;
    private List<StepCommand> steps = Lists.newArrayList();
    private List<BeforeScenarioMethod> beforeMethod = Lists.newArrayList();

    @Test
    public void scenario() throws Exception {
        Scenario scenario = new Scenario(pickle, steps, beforeMethod);
        assertSame(pickle, scenario.getPickle());
    }

}