package com.example.web_homework3.controllers;

import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.services.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Управление ингредиентами",
        description = "Создаёт, запрашивает, удаляет или редактирует создаваемый ингредиент")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации об ингредиенте или ингредиентах",
            description = "Запрашиваем информацию об ингредиенте по id")
    @Parameters(value = {@Parameter(name = "id", example = "1")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ингредиент был найдён"
    )})
    public ResponseEntity<String> getIngredient(@PathVariable int id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            System.out.println("Ингрдениент не найден");
            return ResponseEntity.notFound().build();
        }
        ingredientService.getIngredient(id);
        return ResponseEntity.ok("Ингредиент под номером " + id + " содержит: " + ingredientService.getIngredient(id));
    }

    @GetMapping()
    @Operation(summary = "Получение информации об ингредиентах",
            description = "Запрашиваем информацию по всем ингредиентам")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Ингредиенты были найдёны"
    )})
    public ResponseEntity<String> getAllIngredient() {
        return ResponseEntity.ok(ingredientService.getAllIngredients().toString());
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


    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузить свой список ингредиентов", description = "Загрузить свой список ингредиентов в формате .json")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Список загружен")})
    public ResponseEntity<File> uploadFile(@RequestParam MultipartFile file) throws IOException {
        ingredientService.updateFile(file);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/report")
    @Operation(summary = "Выгрузить все ингредиенты в файл .txt")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Список выгружен"), @ApiResponse(responseCode = "204", description = "Данных нет")})
    private ResponseEntity<Object> report() throws IOException {
        try {
            Path path = ingredientService.createReport();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"-report.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
