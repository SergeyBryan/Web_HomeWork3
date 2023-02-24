package com.example.web_homework3.services.impl;

import com.example.web_homework3.controllers.FileProcessingException;
import com.example.web_homework3.model.Recipe;
import com.example.web_homework3.services.FilesService;
import com.example.web_homework3.services.RecipeService;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

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
    public String getRecipeAll() {
        for (Map.Entry<Integer, Recipe> integerRecipeHashMap : recipes.entrySet()) {
            int key = integerRecipeHashMap.getKey();
            Recipe recipe = integerRecipeHashMap.getValue();
            System.out.println("Рецепт №" + key + ", Рецепт: " + recipe);
            return "Рецепт № " + key + ", Рецепт: " + recipe;
        }
        throw new FileProcessingException("Рецепт не найден");
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
        return null;
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
}



