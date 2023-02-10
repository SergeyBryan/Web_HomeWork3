package com.example.web_homework3.services;

import com.example.web_homework3.model.Ingredient;

public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int number);

    void getAllIngredients();

    Ingredient ingredientEdit(int number, Ingredient ingredient);

    boolean deleteIngredient(int number);
}
