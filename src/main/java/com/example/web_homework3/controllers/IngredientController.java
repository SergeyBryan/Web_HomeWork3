package com.example.web_homework3.controllers;

import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.services.IngredientService;
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
@RequestMapping("/ingredient")
@Tag(name = "Управление ингредиентами",
        description = "Создаёт, запрашивает, удаляет или редактирует создаваемый ингредиент")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping()
    @Operation(summary = "Получение информации об ингредиенте или ингредиентах",
            description = "Запрашиваем информацию по id ингредиента, если нет id, то выведёт информацию по всем созданным ингредиентам")
    @Parameters(value = {@Parameter(name = "id", example = "1")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ингредиент был найдён"
    )})
    public ResponseEntity<Void> getIngredient(@RequestParam(required = false) int id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            ingredientService.getAllIngredients();
            return ResponseEntity.ok().build();
        }
        ingredientService.getIngredient(id);
        System.out.println("Ингредиент под номером " + id + " содержит: " + ingredientService.getIngredient(id));
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    @Operation(summary = "Добавляет новый ингредиент",
            description = "Добавляет новый ингредиент в список, присваивая ему id при создании"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ингредиент был создан")})
    public ResponseEntity<Ingredient> createIngredient(@RequestBody @Valid Ingredient newIngredient) {
        return ResponseEntity.ok(ingredientService.addIngredient(newIngredient));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Вносит изменения в ингредиент по id",
            description = "Изменяет старые значения ингредиента на новые по id")
    @Parameters(value = {@Parameter(name = "id", example = "1")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ингредиент был изменён")})
    public ResponseEntity<Void> editIngredient(@PathVariable @Min(0) int id, @RequestBody @Valid Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient != null) {
            ingredientService.editIngredient(id, newIngredient);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет ингредиент по id",
            description = "Удаляет уже существующий ингредиент по id навсегда")
    @Parameters(value = {@Parameter(name = "id", example = "1")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ингредиент был удалён")})
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable int id, @RequestBody Ingredient oldIngredient) {
        Ingredient ingredient = ingredientService.deleteIngredient(id, oldIngredient);
        if (ingredient != null) {
            return ResponseEntity.ok(oldIngredient);
        }
        return ResponseEntity.notFound().build();
    }

}
