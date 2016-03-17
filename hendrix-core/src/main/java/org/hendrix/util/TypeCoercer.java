package org.hendrix.util;

import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class TypeCoercer {

    private static TypeCoercer instance = new TypeCoercer();

    private Map<Class, Coercion> coercers = Maps.newConcurrentMap();

    public static TypeCoercer getInstance() {
        return instance;
    }

    private TypeCoercer() {
        seedCoercers();
    }

    public void seedCoercers() {
        coercers.put(String.class, new Coercion<String>() {
            @Override
            public String coerce(String source) {
                return source;
            }
        });
        coercers.put(Boolean.TYPE, new Coercion<Boolean>() {
            @Override
            public Boolean coerce(String source) {
                return Boolean.valueOf(source);
            }
        });
        coercers.put(Character.TYPE, new Coercion<Character>() {
            @Override
            public Character coerce(String source) {
                return source.charAt(0);
            }
        });
        coercers.put(Byte.TYPE, new Coercion<Byte>() {
            @Override
            public Byte coerce(String source) {
                return Byte.valueOf(source);
            }
        });
        coercers.put(Short.TYPE, new Coercion<Short>() {
            @Override
            public Short coerce(String source) {
                return Short.valueOf(source);
            }
        });
        coercers.put(Integer.TYPE, new Coercion<Integer>() {
            @Override
            public Integer coerce(String source) {
                return Integer.valueOf(source);
            }
        });
        coercers.put(Long.TYPE, new Coercion<Long>() {
            @Override
            public Long coerce(String source) {
                return Long.valueOf(source);
            }
        });
        coercers.put(Double.TYPE, new Coercion<Double>() {
            @Override
            public Double coerce(String source) {
                return Double.valueOf(source);
            }
        });
        coercers.put(Float.TYPE, new Coercion<Float>() {
            @Override
            public Float coerce(String source) {
                return Float.valueOf(source);
            }
        });
        coercers.put(BigInteger.class, new Coercion<BigInteger>() {
            @Override
            public BigInteger coerce(String source) {
                return new BigInteger(source);
            }
        });
        coercers.put(BigDecimal.class, new Coercion<BigDecimal>() {
            @Override
            public BigDecimal coerce(String source) {
                return new BigDecimal(source);
            }
        });
    }

    public <T> T coerce(String input, Class<T> targetType) {
        return getCoercion(targetType).coerce(input);
    }

    public <T> Coercion<T> getCoercion(Class<T> targetType) {
        return coercers.get(targetType);
    }
}
