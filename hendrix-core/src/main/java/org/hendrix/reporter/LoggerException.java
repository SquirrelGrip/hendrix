package org.hendrix.reporter;

public class LoggerException extends Exception {
    public LoggerException(Exception e) {
        super(e.getMessage(), e);
    }
}
