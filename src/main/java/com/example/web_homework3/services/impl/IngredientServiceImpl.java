package com.example.web_homework3.services.impl;

import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static final HashMap<Integer, Ingredient> ingredients = new HashMap<>();
    private static int ingredientId = 0;

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (!ingredients.containsValue(ingredient)) {
            ingredients.put(ingredientId++, ingredient);
        }
        return null;

    }

    @Override
    public Ingredient getIngredient(int id) {
        return ingredients.get(id);
    }

    @Override
    public void getAllIngredients() {
        for (Map.Entry<Integer, Ingredient> integerIngredientEntry : ingredients.entrySet()) {
            int key = integerIngredientEntry.getKey();
            Ingredient ingredient = integerIngredientEntry.getValue();
            System.out.println("Рецепт №" + key + ", ингредиент: " + ingredient);
        }
    }

    @Override
    public Ingredient ingredientEdit(int id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(int number) {
        if (ingredients.containsKey(number)) {
            ingredients.remove(number);
            return true;
        }
        return false;
    }
}
