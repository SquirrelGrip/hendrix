package org.hendrix.annotation;

import org.hendrix.step.StepCommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Step {
    String value();

    Class<? extends StepCommand>[] decorators() default {};

}
