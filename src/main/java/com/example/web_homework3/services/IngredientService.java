package com.example.web_homework3.services;

import com.example.web_homework3.model.Ingredient;

public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int number);

    String getAllIngredients();

    Ingredient editIngredient(int number, Ingredient ingredient);


    Ingredient deleteIngredient(int id, Ingredient ingredient);

}
