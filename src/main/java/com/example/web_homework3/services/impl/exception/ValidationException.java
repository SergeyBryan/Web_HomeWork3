package com.example.web_homework3.services.impl.exception;

public class ValidationException extends RuntimeException{

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ValidationException(String message) {
        super(message);
    }
}
