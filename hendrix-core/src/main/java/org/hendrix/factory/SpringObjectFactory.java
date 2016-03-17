package org.hendrix.factory;

import com.google.common.collect.Sets;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.Set;

public class SpringObjectFactory extends AbstractObjectFactory implements ObjectFactory {
    private ConfigurableListableBeanFactory beanFactory;
    private Set<Class<?>> stepClasses = Sets.newHashSet();

    public SpringObjectFactory(ObjectFactory objectFactory) {
        super(objectFactory);
        this.beanFactory = createBeanFactory();
    }

    public SpringObjectFactory() {
        this(null);
    }

    private ConfigurableListableBeanFactory createBeanFactory() {
        ConfigurableApplicationContext applicationContext;
        if (getClass().getClassLoader().getResource("cucumber.xml") != null) {
            applicationContext = new ClassPathXmlApplicationContext("cucumber.xml");
        } else {
            applicationContext = new GenericApplicationContext();
        }
        applicationContext.registerShutdownHook();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        return beanFactory;
    }

    private void registerStepClassBeanDefinition(Class<?> glueClass) {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(glueClass).setScope("prototype").getBeanDefinition();
        ((BeanDefinitionRegistry) beanFactory).registerBeanDefinition(glueClass.getName(), beanDefinition);
    }

    @Override
    public <T> T createInstance(final Class<T> type) {
        if (annotatedWithSupportedSpringRootAnnotations(type)) {
            try {
                registerStepClassBeanDefinition(type);
                return beanFactory.getBean(type);
            } catch (BeansException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return null;
    }

    private boolean annotatedWithSupportedSpringRootAnnotations(Class<?> type) {
        return type.isAnnotationPresent(ContextConfiguration.class) || type.isAnnotationPresent(ContextHierarchy.class);
    }

}
