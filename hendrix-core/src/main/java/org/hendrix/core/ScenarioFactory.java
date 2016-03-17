package org.hendrix.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.hendrix.annotation.BeforeScenario;
import org.hendrix.annotation.Step;
import org.hendrix.domain.Scenario;
import org.hendrix.factory.DefaultJavaObjectFactory;
import org.hendrix.factory.ObjectFactory;
import org.hendrix.factory.SpringObjectFactory;
import org.hendrix.step.StepCommand;
import org.hendrix.step.StepDefinition;
import gherkin.Parser;
import gherkin.ParserException;
import gherkin.ast.Feature;
import gherkin.compiler.Compiler;
import pickles.Pickle;
import pickles.PickleStep;
import pickles.PickleTag;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScenarioFactory {
    private final Parser<Feature> parser = new Parser<>();
    private final Compiler compiler = new Compiler();
    private final Collection<Pickle> pickles = Lists.newArrayList();
    private final ObjectFactory objectFactory = new DefaultJavaObjectFactory(new SpringObjectFactory());
    protected final List<StepDefinition> stepDefinitions = Lists.newArrayList();
    protected final List<Method> beforeMethods = Lists.newArrayList();
    private final Collection<Class<?>> glueClasses = Lists.newArrayList();
    private final Collection<Pattern> tags = Sets.newHashSet();

    public void addGlue(Class<?> glueClass) {
        glueClasses.add(glueClass);
        for (Method method : glueClass.getMethods()) {
            processMethod(method);
        }
    }

    protected void processMethod(Method method) {
        if (method.isAnnotationPresent(Step.class)) {
            stepDefinitions.add(new StepDefinition(method));
        }
        if (method.isAnnotationPresent(BeforeScenario.class)) {
            beforeMethods.add(method);
        }
    }

    public Scenario create(Pickle pickle) {
        List<StepCommand> stepCommands = Lists.newArrayList();
        List<BeforeScenarioMethod> beforeScenarioMethods = Lists.newArrayList();;
        Map<Class<?>, Object> objectCache = Maps.newHashMap();
        for (PickleStep pickleStep : pickle.getSteps()) {
            StepDefinition stepDefinition = findStepDefinition(pickleStep);
            Class<?> glueClass = stepDefinition.getMethod().getDeclaringClass();
            Object instance = objectCache.get(glueClass);
            if (instance == null) {
                instance = objectFactory.getInstance(glueClass);
                objectCache.put(glueClass, instance);
                for (Method method : beforeMethods) {
                    if (method.getDeclaringClass().equals(glueClass)) {
                        beforeScenarioMethods.add(new BeforeScenarioMethod(instance, method));
                    }
                }
            }
            StepCommand stepCommand = stepDefinition.create(instance, pickleStep);
            stepCommands.add(stepCommand);
        }
        return createScenario(pickle, stepCommands, beforeScenarioMethods);
    }

    protected Scenario createScenario(Pickle pickle, List<StepCommand> stepCommands, List<BeforeScenarioMethod> beforeScenarioMethods) {
        return new Scenario(pickle, stepCommands, beforeScenarioMethods);
    }

    public StepDefinition findStepDefinition(PickleStep pickleStep) {
        for (StepDefinition stepDefinition : stepDefinitions) {
            Matcher matcher = stepDefinition.getPattern().matcher(pickleStep.getText());
            if (matcher.matches()) {
                return stepDefinition;
            }
        }
        throw new RuntimeException("No step definition found for: " + pickleStep.getText());
    }

    public void addFeature(String fileName) throws ParserException, IOException {
        addFeature(new FileInputStream(fileName));
    }

    public void addFeature(InputStream fileName) throws ParserException, IOException {
        InputStreamReader in = new InputStreamReader(fileName, "UTF-8");
        Feature feature = parser.parse(in);
        pickles.addAll(compiler.compile(feature));
    }

    public void addTag(String tag) {
        Pattern pattern = Pattern.compile(tag);
        tags.add(pattern);
    }

    public Collection<Pickle> getPickles() {
        Collection<Pickle> filteredPickles = Lists.newArrayList();
        if (!tags.isEmpty()) {
            for (Pickle pickle : pickles) {
                pickleLoop:
                for (PickleTag pickleTag : pickle.getTags()) {
                    for (Pattern pattern : tags) {
                        if(pattern.matcher(pickleTag.getName()).matches()) {
                            filteredPickles.add(pickle);
                            break pickleLoop;
                        }
                    }
                }
            }
        } else {
            return pickles;
        }
        return filteredPickles;
    }

    public List<Scenario> createScenarios() {
        List<Scenario> scenarios = Lists.newArrayList();
        for (Pickle pickle : getPickles()) {
            Scenario scenario = create(pickle);
            scenarios.add(scenario);
        }
        return scenarios;
    }

    public void validate() {
        if (pickles.isEmpty()) {
            throw new RuntimeException("No features specified.");
        }
        if (glueClasses.isEmpty()) {
            throw new RuntimeException("No glue specified.");
        }
    }
}
