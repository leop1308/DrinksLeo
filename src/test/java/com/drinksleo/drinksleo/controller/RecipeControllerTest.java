package com.drinksleo.drinksleo.controller;

import com.drinksleo.controller.RecipeController;
import com.drinksleo.dao.Recipe;
import com.drinksleo.service.RecipeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RecipeService recipeService;


    /**
     * Test the getAll status ok
     * givenExistsRecipesInDataBase_whenRequestRecipeList_ThenRecipeList
     */
    @Test
    @DisplayName("Get Recipes: status ok")
    public void getRecipesController() throws Exception {
        this.mockMvc.perform(get("http://localhost/recipe/all"))
                .andExpect(status().isOk());
    }

    /**
     * Add Recipe status ok
     */
    @Test
    @DisplayName("Add Recipe: status ok")
    public void getAllRecipes() throws Exception {
        when(recipeService.getAll()).thenReturn(getRecipes());
        this.mockMvc.perform(get("http://localhost/recipe/all")
                        .content(asJsonString(getRecipe()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").isNotEmpty())
                .andExpect(status().isOk());
    }

    /**
     * Add Recipe status ok
     */
    @Test
    @DisplayName("Add Recipe: status ok")
    public void addRecipe() throws Exception {
        when(recipeService.createRecipe(any())).thenReturn(getRecipe());
        this.mockMvc.perform(post("http://localhost/recipe/new")
                        .content(asJsonString(getRecipe()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty())
                .andExpect(status().isOk());
    }

    /**
     * Add Recipe return recipe
     */
    @Test
    @DisplayName("Add Recipe: return recipe")
    public void addRecipeReturn() throws Exception {
        when(recipeService.createRecipe(any())).thenReturn(getRecipe());
        this.mockMvc.perform(post("http://localhost/recipe/new")
                        .content(asJsonString(getRecipe()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(getRecipe().getName())));
    }

    /**
     * Add null Recipes
     */
    @Test
    @DisplayName("Add Recipe: Receiving null recipe")
    public void addRecipeRecipeNull() throws Exception {
        when(recipeService.createRecipe(any())).thenReturn(getRecipe());
        this.mockMvc.perform(post("http://localhost/recipe/new")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }


}
