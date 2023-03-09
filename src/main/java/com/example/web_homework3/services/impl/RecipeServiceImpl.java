package com.example.web_homework3.services.impl;

import com.example.web_homework3.controllers.FileProcessingException;
import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.model.Recipe;
import com.example.web_homework3.services.FilesService;
import com.example.web_homework3.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class RecipeServiceImpl implements RecipeService {
    final private FilesService filesService;
    @Value("src/main/resources")
    private String recipeFilePath;
    @Value("recipeData.json")
    private String recipeFileName;
    private static Map<Integer, Recipe> recipes = new HashMap<>();
    private static int recipeId = 0;

    public RecipeServiceImpl(
            FilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public Recipe getRecipe(int id) {
        return recipes.get(id);
    }

    @Override
    public Collection<Recipe> getRecipeAll() {
        return recipes.values();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (!recipes.containsValue(recipe)) {
            recipes.put(recipeId++, recipe);
            saveToFile();
        }
        return null;
    }

    @Override
    public Recipe editRecipe(int id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            saveToFile();
            return recipe;
        }
        throw new FileProcessingException("Рецепт не найден");
    }

    @Override
    public Recipe deleteRecipe(int id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @PostConstruct
    public void init() throws FileProcessingException {
        readFromFile(recipeFilePath, recipeFileName);

    }


    private void saveToFile() throws FileProcessingException {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesService.saveToFile(json, recipeFilePath, recipeFileName);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не найден");
        }
    }

    private void readFromFile(String dataFilePath, String dataFileName) throws FileProcessingException {
        try {
            String json = filesService.readFromFile(dataFilePath, dataFileName);
            recipes = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new FileProcessingException("Не удалось прочитать");
        }
    }

    @Override
    public File getDataFile() {
        return filesService.getDataFile(recipeFilePath, recipeFileName);
    }


    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        Path filePath = Path.of(recipeFilePath, recipeFileName);
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        readFromFile(recipeFilePath, recipeFileName);
    }

    @Override
    public Path createReport() throws IOException {
        Path path = filesService.createTempFile(recipeFilePath, "RecipeReport");
        for (Recipe recipe : recipes.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(recipe.getName() + "\n");
                writer.append("\n");
                writer.append("Время приготовления: " + recipe.getDuration() + " минут" + "\n");
                writer.append("\n");
                writer.append("Ингредиенты: " + "\n");
                writer.append("\n");
                for (Ingredient ingredient : recipe.getIngredients()) {
                    writer.append(ingredient.getName() + " - " + ingredient.getAmount() + " " + ingredient.getMeasureUnit() + "\n");
                }
                writer.append("\n");
                writer.append("Инструкция приготовления: ");
                writer.append(recipe.getSteps() + "\n");
                writer.append("\n");
                writer.append("---------------" + "\n");
                writer.append("\n");
            }
        }
        return path;
    }
}



