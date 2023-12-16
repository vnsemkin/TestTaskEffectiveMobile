package org.vnsemkin.taskmanagementsystem.exception;

public class AppTokenExpiredException extends RuntimeException {
    public AppTokenExpiredException(String message) {
        super(message);
    }
}
