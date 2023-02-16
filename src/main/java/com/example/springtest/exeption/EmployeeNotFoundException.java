package com.example.springtest.exeption;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
