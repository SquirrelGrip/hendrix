package org.hendrix.util;

public interface Coercion<Y> {
    Y coerce(String source);
}
