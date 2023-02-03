package com.example.web_homework3.controllers;

import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.services.IngredientService;
import com.example.web_homework3.services.impl.exception.MyException;
import com.example.web_homework3.services.impl.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/create")
    public void createIngredient(@RequestParam String name, @RequestParam int duration, @RequestParam String measureUnit) throws MyException {
        Ingredient ingredient = new Ingredient(Validator.check(name), Validator.check(duration), Validator.check(measureUnit));
        System.out.println(ingredient);
        System.out.println(ingredient.getName());
        ingredientService.addIngredient(ingredient);

    }

}
