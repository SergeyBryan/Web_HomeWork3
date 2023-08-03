package com.example.web_homework3.services.impl;

import com.example.web_homework3.controllers.FileProcessingException;
import com.example.web_homework3.model.Ingredient;
import com.example.web_homework3.services.FilesService;
import com.example.web_homework3.services.IngredientService;
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

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class IngredientServiceImpl implements IngredientService {
    final private FilesService filesService;
    @Value("ingredientData.json")
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
    public Collection<Ingredient> getAllIngredients() {
        return ingredients.values();
    }

    @Override
    public Ingredient editIngredient(int id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
            saveToFile();
        }
        throw new FileProcessingException("Ингредиент не найден");
    }

    @Override
    public Ingredient deleteIngredient(int id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            saveToFile();
            return ingredient;
        }
        throw new FileProcessingException("Ингредиент не найдён");
    }

    private void saveToFile() throws FileProcessingException {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveToFile(json, ingredientFilePath, ingredientFileName);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не найден");
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


    @Override
    public void updateFile(MultipartFile file) throws IOException {
        Path filePath = Path.of(ingredientFilePath, ingredientFileName);
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
        readFromFile(ingredientFilePath, ingredientFileName);
    }

    @Override
    public Path createReport() throws IOException {
        Path path = filesService.createTempFile(ingredientFilePath, "report");
        for (Ingredient ingredient : ingredients.values()) {
            try (
                    Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append("Ингредиент: " + ingredient.getName() + ", количество: " + ingredient.getAmount() + " " + ingredient.getMeasureUnit());
                writer.append("\n");
            }
        }
        return path;
    }


}
