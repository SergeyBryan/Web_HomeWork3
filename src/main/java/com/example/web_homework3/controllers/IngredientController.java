package com.example.web_homework3.controllers;

import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.services.IngredientService;
import com.example.web_homework3.services.impl.exception.ValidationException;
import com.example.web_homework3.services.impl.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping()
    public ResponseEntity<Void> ingredientGet(@RequestParam(required = false) int number) {
        Ingredient ingredient = ingredientService.getIngredient(number);
        if (ingredient == null) {
            ingredientService.getAllIngredients();
            return ResponseEntity.ok().build();
        }
        ingredientService.getIngredient(number);
        System.out.println("Ингредиент под номером " + number + " содержит: " + ingredientService.getIngredient(number));
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Void> ingredientCreate(@RequestBody Ingredient newIngredient) throws ValidationException {
        Validator.check(newIngredient.getName());
        Validator.check(newIngredient.getAmount());
        Validator.check(newIngredient.getMeasureUnit());
        ingredientService.addIngredient(newIngredient);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.ingredientEdit(id, newIngredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        if (ingredientService.deleteIngredient(id)) {

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
