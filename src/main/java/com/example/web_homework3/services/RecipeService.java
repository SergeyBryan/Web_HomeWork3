package com.example.web_homework3.services;

import com.example.web_homework3.model.Recipe;

public interface RecipeService {

    Recipe getRecipe(int number);

    void addRecipe(Recipe recipe);
}
