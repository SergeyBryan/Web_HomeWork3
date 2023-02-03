package com.example.web_homework3.services.impl.exception;

public class MyException extends Exception{

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public MyException(String message) {
        super(message);
    }
}
