package com.example.springtest.exeption;

public class EmployeeAlreadyExistException extends RuntimeException {
    public EmployeeAlreadyExistException(String s) {
        super(s);
    }

    public EmployeeAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
