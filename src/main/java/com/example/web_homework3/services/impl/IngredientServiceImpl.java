package com.example.web_homework3.services.impl;

import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static final HashMap<Integer, Ingredient> ingredients = new HashMap<>();
    private static int ingredientId = 0;

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredients.put(ingredientId++, ingredient);
    }

    @Override
    public Ingredient getIngredient(int number) {
        return ingredients.get(number);
    }

}
