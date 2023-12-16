package org.vnsemkin.taskmanagementsystem.exception;

public class AppTaskNotFoundException extends RuntimeException {
    public AppTaskNotFoundException(String message) {
        super(message);
    }
}
