package com.example.web_homework3.controllers;

import com.example.web_homework3.model.Recipe;
import com.example.web_homework3.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/recipe")
@Tag(name = "Управление рецептами",
        description = "Создание, удаление, редактирование и вызов рецептов")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    //    @GetMapping()
//    @Operation(summary = "Получить информацию по рецепту", description = "Получить информацию о рецепте по id, если не указан id, то получение информации по всем рецептам")
//    @Parameters(value = {@Parameter(name = "id", example = "1")})
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепт найдён")})
//    public ResponseEntity<Void> getRecipe(@RequestParam(required = false) @Min(0) int id) {
//        Recipe recipe = recipeService.getRecipe(id);
//        if (recipe != null) {
//            System.out.println("Рецепт под номером " + id + " содержит: " + recipeService.getRecipe(id));
//            return ResponseEntity.ok().build();
//        }
//        recipeService.getRecipeAll();
//        return ResponseEntity.ok().build();
//    }
    @GetMapping()
    @Operation(summary = "Получить информацию по рецептам", description = "Получить информацию по всем рецептам")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепты найдёны")})
    public ResponseEntity<String> getRecipe() {
        return ResponseEntity.ok(recipeService.getRecipeAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск по id", description = "Получить информацию по рецепту по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепт найдён")})
    public ResponseEntity<String> getRecipeById(@PathVariable @Min(0) int id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe != null) {
            return ResponseEntity.ok("Рецепт под номером " + id + " содержит " + recipeService.getRecipe(id));
        }
        System.out.println("Рецепт под номером " + id + " не существует");
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рецепта", description = "Удалить рецепт по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепт удалён")})
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe oldRecipe = recipeService.deleteRecipe(id, recipe);
        if (oldRecipe != null) {
            System.out.println("Рецепт с id " + id + " удален");
            return ResponseEntity.ok(oldRecipe);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping()
    @Operation(summary = "Создать новый рецепт", description = "Создаёт новый рецепт присваивая ему id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепт создан")})
    public ResponseEntity<Recipe> createRecipe(@RequestBody() @Valid Recipe newRecipe) {
        return ResponseEntity.ok(recipeService.addRecipe(newRecipe));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Редактирование рецепта", description = "Внести изменения в уже существующий рецепт по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепт отредактирован")})
    public ResponseEntity<Recipe> editRecipe(@PathVariable @Min(0) int id, @RequestBody @Valid Recipe newRecipe) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe != null) {
            recipeService.editRecipe(id, newRecipe);
            System.out.println("Рецепт c id " + id + " изменён");
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }


}
