package org.hendrix.reporter;

import com.google.common.collect.Maps;

import java.io.File;
import java.util.Map;

public class ReporterFactory {
    private final static Map<String, Class<? extends Reporter>> reporterMap = Maps.newHashMap();
    
    static {
        reporterMap.put("console", ConsoleReporter.class);
        reporterMap.put("junit", JUnitReporter.class);
        reporterMap.put("json", JsonReporter.class);
    }
    
    public static Reporter create(String definition) {
        String[] split = definition.split(":");
        String type = split[0];
        try {
            Class<? extends Reporter> reporterClass = reporterMap.get(type);
            if (reporterClass == null) {
                reporterClass = (Class<? extends Reporter>)Class.forName(type);
            }
            if (split.length > 1) {
                String fileName = split[1];
                return reporterClass.getConstructor(File.class).newInstance(new File(fileName));
            } else {
                return reporterClass.getConstructor().newInstance();
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("%s doesn't have an empty constructor.", definition), e);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to instantiate %s", definition), e);
        }

    }

}
