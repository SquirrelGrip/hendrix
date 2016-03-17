package org.hendrix.reporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonLogger {
    private ObjectMapper mapper = new ObjectMapper();

    public String toLog(String key, Object value) throws JsonProcessingException {
        return "JSON:" + key + ":" + value.getClass().getName() + ":" + mapper.writeValueAsString(value);
    }

    public Map.Entry<String, Object> fromLog(String message) throws LoggerException {
        try {
            String[] split = message.split(":");
            String key = split[1];
            String clazzName = split[2];
            int fromIndex = split[0].length() + split[1].length() + split[2].length() + 3;
            String json = message.substring(fromIndex);
            Object value = mapper.readValue(json, Class.forName(clazzName));
            return new JsonEntry(key, value);
        } catch(Exception e) {
            throw new LoggerException(e);
        }
    }

    public class JsonEntry implements Map.Entry<String, Object> {
        private String key;
        private Object value;

        public JsonEntry(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            Object oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }
}
