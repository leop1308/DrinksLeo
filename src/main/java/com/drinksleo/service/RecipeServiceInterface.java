package com.drinksleo.service;

import com.drinksleo.dao.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeServiceInterface {
    public List<Recipe> getAll();
    public Recipe getRecipe(String id);
    public Recipe createRecipe(Recipe recipe, MultipartFile image);

}
