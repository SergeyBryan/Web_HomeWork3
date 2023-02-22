package com.example.web_homework3.services.impl;

import com.example.web_homework3.controllers.FileProcessingException;
import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.services.FilesService;
import com.example.web_homework3.services.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    final private FilesService filesService;
    @Value("${name.to.ingredientData.file}")
    private String ingredientFileName;
    @Value("src/main/resources")
    private String ingredientFilePath;
    private static HashMap<Integer, Ingredient> ingredients = new HashMap<>();
    private static int ingredientId = 0;

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    public void init() {
        readFromFile(ingredientFilePath, ingredientFileName);
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (!ingredients.containsValue(ingredient)) {
            ingredients.put(ingredientId++, ingredient);
            saveToFile();
        }
        return null;

    }

    @Override
    public Ingredient getIngredient(int id) {
        return ingredients.get(id);
    }

    @Override
    public String getAllIngredients() {
        for (Map.Entry<Integer, Ingredient> integerIngredientEntry : ingredients.entrySet()) {
            int key = integerIngredientEntry.getKey();
            Ingredient ingredient = integerIngredientEntry.getValue();

            System.out.println("Ингредиент №" + key + ", " + ingredient);
            return "Ингредиент №" + key + ", " + ingredient;
        }
        return null;
    }

    @Override
    public Ingredient editIngredient(int id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
            saveToFile();
        }
        return null;
    }

    @Override
    public Ingredient deleteIngredient(int id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            saveToFile();
            return ingredient;
        }
        return null;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveToFile(json, ingredientFilePath, ingredientFileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile(String ingredientFilePath, String ingredientFileName) throws FileProcessingException {
        try {
            String json = filesService.readFromFile(ingredientFilePath, ingredientFileName);
            ingredients = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new FileProcessingException("Не удалось прочитать файл с ингредиентами");
        }
    }
}
