package org.hendrix.factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class DefaultJavaObjectFactory extends AbstractObjectFactory implements ObjectFactory {
    private final Map<Class<?>, Object> instances = new HashMap<>();

    public DefaultJavaObjectFactory(ObjectFactory objectFactory) {
        super(objectFactory);
    }
    
    public DefaultJavaObjectFactory() {
        super(null);
    }

    public <T> T createInstance(Class<T> type) {
        T instance = type.cast(instances.get(type));
        if (instance == null) {
            instance = cacheNewInstance(type);
        }
        return instance;
    }

    private <T> T cacheNewInstance(Class<T> type) {
        try {
            Constructor<T> constructor = type.getConstructor();
            T instance = constructor.newInstance();
            instances.put(type, instance);
            return instance;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("%s doesn't have an empty constructor.", type), e);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to instantiate %s", type), e);
        }
    }

}
