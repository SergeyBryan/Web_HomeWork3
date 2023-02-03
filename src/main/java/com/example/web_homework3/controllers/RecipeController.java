package com.example.web_homework3.controllers;

import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.model.Recipe;
import com.example.web_homework3.services.RecipeService;
import com.example.web_homework3.services.impl.exception.MyException;
import com.example.web_homework3.services.impl.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping(value = "/show")
    public Recipe recipeShow(@RequestParam() int number) {
        System.out.println("Рецепт под номером " + number + " содержит: " + recipeService.getRecipe(number));
        return recipeService.getRecipe(number);
    }

    @GetMapping("/create")
    public void recipeCreator(@RequestParam() String name, @RequestParam int duration) throws MyException {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ArrayList<String> steps = new ArrayList<>();
        Recipe recipe = new Recipe(Validator.check(name), Validator.check(duration), ingredients, steps);
        recipeService.addRecipe(recipe);
    }
}
