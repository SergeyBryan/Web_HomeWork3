package com.example.web_homework3.services.impl;

import com.example.web_homework3.model.Recipe;
import com.example.web_homework3.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final HashMap<Integer, Recipe> recipes = new HashMap<>();
    private static int recipeId = 0;

    @Override
    public Recipe getRecipe(int id) {
        return recipes.get(id);
    }

    @Override
    public void getRecipeAll() {
        for (Map.Entry<Integer, Recipe> integerRecipeHashMap : recipes.entrySet()) {
            int key = integerRecipeHashMap.getKey();
            Recipe recipe = integerRecipeHashMap.getValue();
            System.out.println("Рецепт №" + key + ", Рецепт: " + recipe);
        }

    }

    @Override
    public void addRecipe(Recipe recipe) {
        if (!recipes.containsValue(recipe)) {
            recipes.put(recipeId++, recipe);
        }
    }

    @Override
    public Recipe editRecipe(int id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            return recipe;
        }
        return null;
    }

    @Override
    public Recipe deleteRecipe(int id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            return recipe;
        }
        return null;
    }
}


