package com.example.web_homework3.services;

import com.example.web_homework3.model.Ingredient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int number);

    Collection<Ingredient> getAllIngredients();

    Ingredient editIngredient(int number, Ingredient ingredient);


    Ingredient deleteIngredient(int id, Ingredient ingredient);

    void updateFile(MultipartFile file) throws IOException;
}
