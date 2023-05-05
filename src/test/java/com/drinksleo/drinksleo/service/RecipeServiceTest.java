package com.drinksleo.drinksleo.service;

import com.drinksleo.config.ImageConfigs;
import com.drinksleo.controller.RecipeDtoValidator;
import com.drinksleo.dao.*;
import com.drinksleo.drinksleo.auxTestClasses.AuxTest;
import com.drinksleo.service.RecipeService;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;


import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Recipe Service Test")
public class RecipeServiceTest {

    @InjectMocks
    //@Autowired
    RecipeService recipeService;


    //@MockBean
    @Mock
    private ImageConfigs imageConfigs;

    @Mock
    //@MockBean
    RecipeDtoValidator recipeDtoValidator;

    @Mock
    //@MockBean
    private RecipeRepository recipeRepository;

   @Mock
    // @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private RecipeItemInterface recipeItemInterface;


    @Test
    @DisplayName("Get All Recipes")
    public void getAllTest(){

        when(recipeRepository.findAll()).thenReturn(getRecipes());
        List<Recipe> recipeList = recipeService.getAll();
        Assertions.assertEquals(recipeList, AuxTest.getRecipes());


    }
    @Test
    @DisplayName("Get Test")
    public void getTest(){

        List<Recipe> list1 = Arrays.asList(getRecipe(), getRecipe());
        List<Recipe> list2 = Arrays.asList(getRecipe(), getRecipe());

        Recipe rec1 = getRecipe();
        Recipe rec2 = getRecipe();

        Ingredient ing1 = getIngredient();
        Ingredient ing2 = getIngredient();


        Assertions.assertEquals(ing1, ing2);
        //Assertions.assertEquals(rec1, rec2);
        //Assertions.assertEquals(list1, list2);


    }
}
