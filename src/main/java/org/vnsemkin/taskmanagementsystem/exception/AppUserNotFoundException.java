package org.vnsemkin.taskmanagementsystem.exception;

public class AppUserNotFoundException extends RuntimeException{
        public AppUserNotFoundException(String message) {
            super(message);
        }
}
