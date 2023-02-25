package com.example.web_homework3.controllers;

import com.example.web_homework3.model.Recipe;
import com.example.web_homework3.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.*;


@RestController
@RequestMapping("/recipe")
@Tag(name = "Управление рецептами",
        description = "Создание, удаление, редактирование и вызов рецептов")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping()
    @Operation(summary = "Получить информацию по рецептам", description = "Получить информацию по всем рецептам")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепты найдёны")})
    public ResponseEntity<String> getAllRecipe() {
        return ResponseEntity.ok(recipeService.getRecipeAll().toString());
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
    public ResponseEntity<Recipe> createRecipe(@RequestBody @Valid Recipe newRecipe) {
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

    @GetMapping("/export")
    @Operation(summary = "Выгрузить файл с рецептами", description = "Выгружает все созданные рецепты в файл в формате .json")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Файл выгружается")})
    public ResponseEntity<InputStreamResource> downloadFile() throws FileNotFoundException {
        File file = recipeService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().
                    contentType(MediaType.APPLICATION_OCTET_STREAM).
                    contentLength(file.length()).
                    header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipeLog.json\"").
                    body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IOException {
        recipeService.uploadFile(file);
        return ResponseEntity.ok().build();
    }

}
