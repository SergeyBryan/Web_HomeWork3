package com.example.web_homework3.controllers;

import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.services.IngredientService;
import com.example.web_homework3.services.impl.exception.ValidationException;
import com.example.web_homework3.services.impl.Validator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping(value = "/show")
    public Ingredient getIngredient(@RequestParam int number) {
        System.out.println("Ингредиент под номером " + number + " содержит: " + ingredientService.getIngredient(number));
        return ingredientService.getIngredient(number);
    }

    @PostMapping("/create")
    public void createIngredient(@RequestBody Ingredient ingredient) throws ValidationException {
        Validator.check(ingredient.getName());
        Validator.check(ingredient.getAmount());
        Validator.check(ingredient.getMeasureUnit());
        ingredientService.addIngredient(ingredient);

    }

}
