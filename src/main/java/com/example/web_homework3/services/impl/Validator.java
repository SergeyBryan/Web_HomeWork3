package com.example.web_homework3.services.impl;

import com.example.web_homework3.services.impl.exception.ValidationException;

public class Validator {
   public static int check(int name) throws ValidationException {
       if (name <= 0) {
           throw new ValidationException("Поле не может быть пустым");
       }return name;
   }
    public static String check(String name) throws ValidationException {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new ValidationException("Поле не может быть пустым");
        }return name;
    }
}
