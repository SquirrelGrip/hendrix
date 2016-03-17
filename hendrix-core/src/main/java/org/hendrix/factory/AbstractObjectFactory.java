package org.hendrix.factory;

public abstract class AbstractObjectFactory implements ObjectFactory {
    protected ObjectFactory objectFactory = null;

    public AbstractObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public <T> T getInstance(Class<T> glueClass) {
        T instance = null;
        if (objectFactory != null) {
            instance = objectFactory.getInstance(glueClass);
        }
        if (instance == null) {
            instance = createInstance(glueClass);
        }
        return instance;
    }

}
