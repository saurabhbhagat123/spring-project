package com.example.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(final String message) {
        super(message);
    }
}
