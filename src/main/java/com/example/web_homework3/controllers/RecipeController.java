package com.example.web_homework3.controllers;

import com.example.web_homework3.model.Recipe;
import com.example.web_homework3.services.RecipeService;
import com.example.web_homework3.services.impl.Validator;
import com.example.web_homework3.services.impl.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping()
    public ResponseEntity<Void> recipeGet(@RequestParam(required = false) int number) {
        Recipe recipe = recipeService.getRecipe(number);
        if (recipe != null) {
            System.out.println("Рецепт под номером " + number + " содержит: " + recipeService.getRecipe(number));
            return ResponseEntity.ok().build();
        }
        recipeService.getRecipeAll();
        return ResponseEntity.ok().build();
    }


    @PostMapping()
    public ResponseEntity<Recipe> recipeCreate(@RequestBody() Recipe recipe) throws ValidationException {
        Validator.check(recipe.getName());
        Validator.check(recipe.getDuration());
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> recipeEdit(@PathVariable int id, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.editRecipe(id, newRecipe);
        if (recipe == null) {
            System.out.println("Рецепт изменён c id " + id);
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> recipeDelete(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe oldRecipe = recipeService.deleteRecipe(id, recipe);
        if (oldRecipe != null) {
            System.out.println("Рецепт с id " + id + " удален");
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }

}
