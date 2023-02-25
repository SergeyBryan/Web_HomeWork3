package com.example.web_homework3.services;

import com.example.web_homework3.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface RecipeService {

    Recipe getRecipe(int number);

    Collection<Recipe> getRecipeAll();

    Recipe addRecipe(Recipe recipe);

    Recipe editRecipe(int number, Recipe recipe);

    Recipe deleteRecipe(int number, Recipe recipe);

    File getDataFile();


    void uploadFile(MultipartFile file) throws IOException;

}
