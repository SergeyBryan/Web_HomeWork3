package com.example.web_homework3.services;

import com.example.web_homework3.model.Recipe;

public interface RecipeService {

    Recipe getRecipe(int number);

    void getRecipeAll();

    void addRecipe(Recipe recipe);

    Recipe editRecipe(int number, Recipe recipe);

    Recipe deleteRecipe(int number, Recipe recipe);
}
