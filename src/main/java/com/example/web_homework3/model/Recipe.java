package com.example.web_homework3.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
@Data
@AllArgsConstructor
public class Recipe {
    private String name;
    private int duration;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String>steps;

}
