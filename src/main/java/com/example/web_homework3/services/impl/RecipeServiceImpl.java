package com.example.web_homework3.services.impl;

import com.example.web_homework3.model.Recipe;
import com.example.web_homework3.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final HashMap<Integer, Recipe> recipes = new HashMap<>();
    private static int recipeId = 0;

    @Override
    public Recipe getRecipe(int number) {
        return recipes.get(number);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipes.put(recipeId++, recipe);
    }

}
