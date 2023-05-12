package com.drinksleo.service;

import com.drinksleo.dao.Recipe;
import com.drinksleo.exception.CustomException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeServiceInterface {
    public List<Recipe> getAll();
    public Recipe getRecipe(String id);
    public Recipe createRecipe(Recipe recipe, MultipartFile image);

    Recipe updateRecipe(Recipe recipe);

    Recipe updateRecipe(Recipe recipe, MultipartFile image) throws CustomException, Exception;
}
