package com.example.web_homework3.controllers;

import com.example.web_homework3.model.Recipe;
import com.example.web_homework3.services.RecipeService;
import com.example.web_homework3.services.impl.Validator;
import com.example.web_homework3.services.impl.exception.ValidationException;
import org.springframework.web.bind.annotation.*;


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


    @PostMapping("/create")
    public Recipe recipeCreator(@RequestBody() Recipe recipe) throws ValidationException {
        Validator.check(recipe.getName());
        Validator.check(recipe.getDuration());
        recipeService.addRecipe(recipe);
        return recipe;
    }
}
