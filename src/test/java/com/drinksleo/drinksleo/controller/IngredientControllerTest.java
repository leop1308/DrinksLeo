package com.drinksleo.drinksleo.controller;

import com.drinksleo.controller.IngredientController;
import com.drinksleo.service.IngredientServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.getIngredients;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IngredientServiceInterface ingredientService;




    /**
     * getAll Recipes
     * givenExistsRecipesInDataBase_whenRequestRecipeList_ThenRecipeList
     */
    @Test
    @DisplayName("Get Ingredient List: status ok")
    public void getRecipesController() throws Exception {
        when(ingredientService.getAll()).thenReturn(getIngredients());
        mockMvc.perform(get("http://localhost:8080/ingredient/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty());

    }

    /**
     * Get valid Recipe
     */
    @Test
    @DisplayName("Get Measure Types List: status ok and notEmpty")
    public void getRecipeExist() throws Exception {
        mockMvc.perform(get("http://localhost/ingredient/allMeasureTypes"))
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(status().isOk());
    }


}
