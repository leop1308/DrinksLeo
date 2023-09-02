package com.drinksleo.drinksleo.controller;



import com.drinksleo.config.SecurityConfig;
import com.drinksleo.controller.IngredientController;
import com.drinksleo.controller.RecipeController;
import com.drinksleo.drinksleo.auxTestClasses.FakeUserRepository;
import com.drinksleo.drinksleo.auxTestClasses.WithMockAdmin;
import com.drinksleo.service.IngredientServiceInterface;
import com.drinksleo.service.UserService;
import jakarta.servlet.Filter;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.getIngredients;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(IngredientController.class)
@ContextConfiguration(classes = { IngredientController.class, SecurityConfig.class, UserService.class,  FakeUserRepository.class})
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IngredientServiceInterface ingredientService;



    /**
     * getAll Recipes
     * givenExistsRecipesInDataBase_whenRequestRecipeList_ThenGetRecipeList
     *
     * Given an authenticated User with the Role admin
     * When that user do a request to list the Ingredient List
     * Then status HTTP 200-Ok is sent with a JSON of that a list of Ingredient with a field name filled
     */
    @Test
    @DisplayName("ADMIN Get Ingredient List: return status ok")
    @WithMockAdmin
    public void getRecipesController() throws Exception {
        when(ingredientService.getAll()).thenReturn(getIngredients());
        mockMvc.perform(get("http://localhost:8080/ingredient/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty());

    }
    /**
     * Given an authenticated User with the Role admin and the database do not have a list of Recipes registered
     * When that user do a request to list the Ingredient List
     * Then status HTTP 200-Ok is sent with a JSON of that a list of Ingredient with a field name filled
     */
    @Test
    @DisplayName("ADMIN Get Ingredient List without any Ingredient: return status 200 with empty list")
    @WithMockAdmin
    public void getRecipesControllerNull() throws Exception {
        when(ingredientService.getAll()).thenReturn(null);
        mockMvc.perform(get("http://localhost:8080/ingredient/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * Given an authenticated User with the Role admin
     * When that user do a request to get an existing Ingredient
     * Then status HTTP 200-Ok is sent with a JSON of that a list of Ingredient with a field name filled
     */
    @Test
    @DisplayName("ADMIN Get Measure Types List: status 200 and notEmpty")
    @WithMockAdmin
    public void getRecipeExist() throws Exception {
        mockMvc.perform(get("http://localhost/ingredient/allMeasureTypes"))
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(status().isOk());
    }




}
