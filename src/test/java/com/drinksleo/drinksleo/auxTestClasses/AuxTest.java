package com.drinksleo.drinksleo.auxTestClasses;

import com.drinksleo.dao.Ingredient;
import com.drinksleo.dao.MeasureTypes;
import com.drinksleo.dao.Recipe;
import com.drinksleo.dao.RecipeItem;
import com.drinksleo.dto.RecipeDtoIn;
import com.drinksleo.dto.RecipeDtoOut;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class AuxTest {


    public static String RECIPE_NAME = "Drink Name Example";

    public static List<String> RECIPE_PREPARE = createRecipePrepare();
    public static String RECIPE_TEMPERATURE= "Gelada";
    public static String RECIPE_IMAGE_URL= "images/drink.jpg";
    public static String RECIPE_BACKGROUND_COLOR= "#000";
    public static String INGREDIENT_NAME = "Ingredient Name Example";
    public static String ITEM_RECIPE_QUANT= "10";
    public static MeasureTypes ITEM_RECIPE_QUANT_TYPE= MeasureTypes.ML;


    public static List<Recipe> getRecipes(){
        List<Recipe> list = new ArrayList<>();
        list.add(getRecipe());
        return list;
    }

    public static List<Ingredient> getIngredients(){
        List<Ingredient> list = new ArrayList<>();
        list.add(getIngredient());
        return list;
    }
    public static List<RecipeDtoOut> getRecipesDtoOut(){
        List<RecipeDtoOut> list = new ArrayList<>();
        list.add(getRecipeRecipeDtoOut());
        return list;
    }
    public static List<RecipeDtoIn> getRecipesDtoIn(){
        List<RecipeDtoIn> list = new ArrayList<>();
        list.add(getRecipeRecipeDtoIn());
        return list;
    }



    private static RecipeDtoOut getRecipeRecipeDtoOut() {
        return RecipeDtoOut.create()
                .name(RECIPE_NAME)
                .prepare(RECIPE_PREPARE)
                .temperature(RECIPE_TEMPERATURE)
                .imageUrl(RECIPE_IMAGE_URL)
                .backgroundColor(RECIPE_BACKGROUND_COLOR)
                .recipeItems(getRecipeItems())
                .decorationItems(getRecipeItems())
                .build();
    }


    public static RecipeDtoIn getRecipeRecipeDtoIn() {
        return RecipeDtoIn.create()
                .name(RECIPE_NAME)
                .prepare("Macere 2 morangos e 2 amoras na coqueteleira. \nAdicione a água, suco de limão, xarope de morango, algumas pedras de gelo (~4 pedras grandes) na coqueteleira e bata. \nFaça uma dupla coagem para o copo (com gelo). \nComplete com GingerAle (ou água com gás)")
                .temperature(RECIPE_TEMPERATURE)
                .backgroundColor(RECIPE_BACKGROUND_COLOR)
                .recipeItems(getRecipeItems())
                .decorationItems(getRecipeItems())
                .build();
    }

    private static List<String> createRecipePrepare() {
        List<String> list = new ArrayList<>();
        list.add("Adicione os ingredientes na coqueteleira");
        list.add("Bata tudo");
        list.add("Sirva no copo");

        return list;
    }

    public static Recipe getRecipe(){
        return Recipe.create()
                .name(RECIPE_NAME)
                .prepare(RECIPE_PREPARE)
                .temperature(RECIPE_TEMPERATURE)
                .imageUrl(RECIPE_IMAGE_URL)
                .backgroundColor(RECIPE_BACKGROUND_COLOR)
                .recipeItems(getRecipeItems())
                .decorationItems(getRecipeItems())
                .build();
    }
    public static Recipe getRecipeDto(){
        return Recipe.create()
                .name(RECIPE_NAME)
                .prepare(RECIPE_PREPARE)
                .temperature(RECIPE_TEMPERATURE)
                .imageUrl(RECIPE_IMAGE_URL)
                .backgroundColor(RECIPE_BACKGROUND_COLOR)
                .recipeItems(getRecipeItems())
                .decorationItems(getRecipeItems())
                .build();
    }

    private static List<RecipeItem> getRecipeItems() {
        List<RecipeItem> x = new ArrayList<>();
        x.add(RecipeItem.create()
                .id("12")
                .quant(ITEM_RECIPE_QUANT)
                .quantType(ITEM_RECIPE_QUANT_TYPE)
                .ingredient(getIngredient())
                .build());
        return x;
    }

    public static Ingredient getIngredient() {
        return Ingredient.create()
                .name(INGREDIENT_NAME)
                .build();
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
