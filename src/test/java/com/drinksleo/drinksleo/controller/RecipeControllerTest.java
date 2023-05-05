package com.drinksleo.drinksleo.controller;

import com.drinksleo.controller.RecipeController;
import com.drinksleo.controller.RecipeMapper;
import com.drinksleo.dao.Recipe;
import com.drinksleo.dto.RecipeDtoIn;
import com.drinksleo.dto.RecipeDtoOut;
import com.drinksleo.exception.BadRequestException;
import com.drinksleo.exception.ExceptionEnum;
import com.drinksleo.service.RecipeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

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
     * getAll Recipes
     * givenExistsRecipesInDataBase_whenRequestRecipeList_ThenRecipeList
     */
    @Test
    @DisplayName("Get Recipes: status ok")
    public void getRecipesController() throws Exception {
        when(mapper.toDtoOut(any())).thenReturn(getRecipesDto());
        mockMvc.perform(get("http://localhost:8080/recipe/all"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty());

    }

    /**
     * Get valid Recipe
     */
    @Test
    @DisplayName("Get existing Recipe: Not Empty")
    public void getRecipeExist() throws Exception {
        when(recipeService.getRecipe(any())).thenReturn(getRecipe());
        mockMvc.perform(get("http://localhost/recipe/1"))
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(status().isOk());
    }

    /**
     * Get Recipe that not exists
     */
    @Test
    @DisplayName("Add existing Recipe: Not Empty")
    public void getRecipeNotExists() throws Exception {
        when(recipeService.getRecipe(any())).thenThrow(new BadRequestException(ExceptionEnum.RECIPE_NOT_EXISTS.getMessage()));
        mockMvc.perform(get("http://localhost/recipe/1"))
                .andExpect(status().isBadRequest());
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
                .andExpect(status().isCreated());
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
        this.mockMvc.perform(multipart("http://localhost/recipe/new")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("Delete Recipe ")
    public void deleteRecipe() throws Exception {
        when(recipeService.deleteRecipe(any())).thenReturn(getRecipe());
        this.mockMvc.perform(delete("http://localhost/recipe/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Delete Recipe Nonexistent ")
    public void deleteRecipeNonexistent() throws Exception {
        when(recipeService.deleteRecipe(any())).thenThrow(new BadRequestException("The Recipe do not exists!"));
        this.mockMvc.perform(delete("http://localhost/recipe/delete/4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("Delete Recipe Failed ")
    public void deleteRecipeFailed() throws Exception {
        when(recipeService.deleteRecipe(any())).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"The Recipe do not exists!"));
        this.mockMvc.perform(delete("http://localhost/recipe/delete/4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500));
    }


}
