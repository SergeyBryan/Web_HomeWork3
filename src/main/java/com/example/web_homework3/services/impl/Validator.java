package com.example.web_homework3.services.impl;

import com.example.web_homework3.services.impl.exception.MyException;

public class Validator {
   public static int check(int name) throws MyException {
       if (name <= 0) {
           throw new MyException("Поле не может быть пустым");
       }return name;
   }
    public static String check(String name) throws MyException {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new MyException("Поле не может быть пустым");
        }return name;
    }
}
