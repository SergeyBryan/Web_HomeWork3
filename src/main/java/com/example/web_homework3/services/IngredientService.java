package com.example.web_homework3.services;

import com.example.web_homework3.model.Ingredient;

public interface IngredientService {

    void addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int number);
}
