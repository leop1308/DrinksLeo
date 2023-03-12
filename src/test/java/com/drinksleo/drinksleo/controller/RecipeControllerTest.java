package com.drinksleo.drinksleo.controller;

import com.drinksleo.controller.RecipeController;
import com.drinksleo.controller.RecipeMapper;
import com.drinksleo.dao.Recipe;
import com.drinksleo.service.RecipeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    RecipeService recipeService;

    @MockBean
    RecipeMapper mapper;



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
        MockMultipartFile file = new MockMultipartFile("file",
                "hello.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World!".getBytes());
        MockMultipartFile recipe = new MockMultipartFile("recipe",
                "recipe",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(recipeService.createRecipe(any(), any())).thenReturn(getRecipe());
        mockMvc.perform(multipart("http://localhost/recipe/new")
                        .file(file)
                        .file(recipe))
                .andExpect(status().isOk());
    }



    /**
     * Add Recipe return recipe
     */
    @Test
    @DisplayName("Add Recipe: return recipe")
    public void addRecipeReturn() throws Exception {
        when(recipeService.createRecipe(any(), any())).thenReturn(getRecipe());
        MockMultipartFile file = new MockMultipartFile("file",
                "hello.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World!".getBytes());
        MockMultipartFile recipe = new MockMultipartFile("recipe",
                "recipe",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(recipeService.createRecipe(any(), any())).thenReturn(getRecipe());
        mockMvc.perform(multipart("http://localhost/recipe/new")
                        .file(file)
                        .file(recipe))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        is(getRecipe().getName())));
    }

    /**
     * Add null Recipes
     */
    @Test
    @DisplayName("Add Recipe: Receiving null recipe")
    public void addRecipeRecipeNull() throws Exception {
        when(recipeService.createRecipe(any(), any())).thenReturn(getRecipe());
        this.mockMvc.perform(post("http://localhost/recipe/new")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }


}
