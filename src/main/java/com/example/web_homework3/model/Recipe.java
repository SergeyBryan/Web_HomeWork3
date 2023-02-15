package com.example.web_homework3.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
@Data
@AllArgsConstructor
public class Recipe {
    @NotBlank
    private String name;
    @Min(1)
    private int duration;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String>steps;

}
