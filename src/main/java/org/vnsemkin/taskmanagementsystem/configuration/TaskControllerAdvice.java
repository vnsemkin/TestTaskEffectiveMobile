package org.vnsemkin.taskmanagementsystem.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.vnsemkin.taskmanagementsystem.exception.*;

@RestControllerAdvice
public class TaskControllerAdvice {
    @ExceptionHandler(AppTaskValidationException.class)
    public ResponseEntity<String> handleAppTaskValidationException(AppTaskValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String errorMessage = result.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<String> handleAppUserNotFoundException(AppUserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(AppTokenExpiredException.class)
    public ResponseEntity<String> handleAppTokenExpiredExceptionException(AppTokenExpiredException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(AppInvalidSignatureException.class)
    public ResponseEntity<String> handleAppInvalidSignatureException(AppInvalidSignatureException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(AppTaskNotFoundException.class)
    public ResponseEntity<String> handleAppTaskNotFoundException(AppTaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
