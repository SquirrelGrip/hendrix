package org.hendrix.factory;

public interface ObjectFactory {

    <T> T getInstance(Class<T> glueClass);

    <T> T createInstance(Class<T> glueClass);
        
}
