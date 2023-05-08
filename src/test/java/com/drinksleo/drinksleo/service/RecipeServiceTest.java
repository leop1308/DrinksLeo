package com.drinksleo.drinksleo.service;

import com.drinksleo.config.ImageConfigs;
import com.drinksleo.controller.RecipeDtoValidator;
import com.drinksleo.dao.*;
import com.drinksleo.drinksleo.auxTestClasses.AuxTest;
import com.drinksleo.exception.BadRequestException;
import com.drinksleo.service.RecipeService;


import com.drinksleo.util.UploadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    UploadUtil uploadUtil;

    @Mock
    //@MockBean
    private RecipeRepository recipeRepository;

    @Mock
    // @MockBean
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeItemInterface recipeItemInterface;


    @Test
    @DisplayName("Get All Recipes")
    public void getAllTest() {

        when(recipeRepository.findAll()).thenReturn(getRecipes());
        List<Recipe> recipeList = recipeService.getAll();
        Assertions.assertAll(
                () -> Assertions.assertEquals(recipeList.get(0).getName(), AuxTest.getRecipes().get(0).getName()),
                () -> Assertions.assertEquals(recipeList.get(0).getImageUrl(), AuxTest.getRecipes().get(0).getImageUrl()),
                () -> Assertions.assertEquals(recipeList.get(0).getBackgroundColor(), AuxTest.getRecipes().get(0).getBackgroundColor()),
                () -> Assertions.assertEquals(recipeList.get(0).getTemperature(), AuxTest.getRecipes().get(0).getTemperature()),
                () -> Assertions.assertEquals(recipeList.get(0).getPrepare(), AuxTest.getRecipes().get(0).getPrepare()),
                () -> Assertions.assertEquals(recipeList.get(0).getRecipeItems().get(0).getIngredient().getName(), AuxTest.getRecipes().get(0).getRecipeItems().get(0).getIngredient().getName()),
                () -> Assertions.assertEquals(recipeList.get(0).getDecorationPrepare(), AuxTest.getRecipes().get(0).getDecorationPrepare())
        );
        //;


    }

    @Test
    @DisplayName("Get Test")
    public void getTest() {


        when(recipeRepository.findById(any())).thenReturn(Optional.of(getRecipe()));

        Recipe rec1 = recipeService.getRecipe(getRecipe().getName());
        Recipe rec2 = getRecipe();


        Assertions.assertAll(
                () -> Assertions.assertEquals(rec1.getName(), rec2.getName()),
                () -> Assertions.assertEquals(rec1.getImageUrl(), rec2.getImageUrl()),
                () -> Assertions.assertEquals(rec1.getBackgroundColor(), rec2.getBackgroundColor()),
                () -> Assertions.assertEquals(rec1.getTemperature(), rec2.getTemperature()),
                () -> Assertions.assertEquals(rec1.getPrepare(), rec2.getPrepare()),
                () -> Assertions.assertEquals(rec1.getRecipeItems().get(0).getIngredient().getName(), rec2.getRecipeItems().get(0).getIngredient().getName()),
                () -> Assertions.assertEquals(rec1.getDecorationPrepare(), rec2.getDecorationPrepare())
        );

    }@Test
    @DisplayName("Delete Test")
    public void deleteTest() {


        when(recipeRepository.findById(any())).thenReturn(Optional.of(getRecipe()));
        doNothing().when(recipeRepository).deleteById(any());

        Recipe rec1 = recipeService.deleteRecipe(getRecipe().getName());
        Recipe rec2 = getRecipe();


        Assertions.assertAll(
                () -> Assertions.assertEquals(rec1.getName(), rec2.getName()),
                () -> Assertions.assertEquals(rec1.getImageUrl(), rec2.getImageUrl()),
                () -> Assertions.assertEquals(rec1.getBackgroundColor(), rec2.getBackgroundColor()),
                () -> Assertions.assertEquals(rec1.getTemperature(), rec2.getTemperature()),
                () -> Assertions.assertEquals(rec1.getPrepare(), rec2.getPrepare()),
                () -> Assertions.assertEquals(rec1.getRecipeItems().get(0).getIngredient().getName(), rec2.getRecipeItems().get(0).getIngredient().getName()),
                () -> Assertions.assertEquals(rec1.getDecorationPrepare(), rec2.getDecorationPrepare())
        );

    }

    @Test
    @DisplayName("Delete Test Recipe that Not exists")
    public void deleteTestNonexisting() {
        when(recipeRepository.findById(any())).thenThrow(new BadRequestException(""));

        Assertions.assertThrows(BadRequestException.class, () -> recipeService.deleteRecipe(""));
    }

    @Test
    @DisplayName("Create Recipe ")
    public void createRecipe() {
        when(recipeRepository.save(any())).thenReturn(getRecipe());

        MockMultipartFile file = new MockMultipartFile("file",
                "hello.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World!".getBytes());

        try (MockedStatic<UploadUtil> utilities = Mockito.mockStatic(UploadUtil.class)) {
            utilities.when(() -> UploadUtil.uploadImage(any(),any())).thenReturn(true);
            Assertions.assertEquals(recipeService.createRecipe(getRecipe(), file).getName(), getRecipe().getName());
            Assertions.assertNotEquals(recipeService.createRecipe(getRecipe(), file), getRecipe());
            Assertions.assertDoesNotThrow(() ->recipeService.createRecipe(getRecipe(), file));
        }
        }

    @Test
    @DisplayName("Up and Change Recipe Test")
    public void UpAndChangeImageRecipeTest()  {

        String URL_UPDATED = "url updated";

        MockMultipartFile file = new MockMultipartFile("file",
                "hello.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World!".getBytes());

        Recipe rec1 = getRecipe();
        rec1.setImageUrl(URL_UPDATED);


        when(recipeRepository.findById(any())).thenReturn(Optional.of(rec1));
        when(recipeRepository.save(any())).thenReturn(rec1);
        //when(imageConfigs.getPath()).thenReturn("");

        try (MockedStatic<UploadUtil> utilities = Mockito.mockStatic(UploadUtil.class)) {
            utilities.when(() -> UploadUtil.uploadImage(any(),any())).thenReturn(true);
            rec1 = recipeService.upAndChangeImageRecipe( rec1.getName(), file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotEquals(rec1.getImageUrl(), getRecipe().getImageUrl());

    }
    @Test
    @DisplayName("Up and Change Recipe Image Fail Upload Test")
    public void UpAndChangeImageRecipeImageFailUploadTest()  {

        String URL_UPDATED = "url updated";

        MockMultipartFile file = new MockMultipartFile("file",
                "hello.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World!".getBytes());

        Recipe rec1 = getRecipe();


        when(recipeRepository.findById(any())).thenReturn(Optional.of(rec1));
        //when(imageConfigs.getPath()).thenReturn("");

        try (MockedStatic<UploadUtil> utilities = Mockito.mockStatic(UploadUtil.class)) {
            utilities.when(() -> UploadUtil.uploadImage(any(),any())).thenThrow(new Exception());
            Assertions.assertThrows( Exception.class,() -> recipeService.upAndChangeImageRecipe( getRecipe().getName(), file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    @DisplayName("Up and Change a Nonexist Recipe Test")
    public void UpAndChangeImageRecipeNonexistTest()  {

        String URL_UPDATED = "url updated";

        MockMultipartFile file = new MockMultipartFile("file",
                "hello.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World!".getBytes());

        Recipe rec1 = getRecipe();
        rec1.setImageUrl(URL_UPDATED);


        when(recipeRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class, () -> recipeService.updateRecipe(getRecipe(), file));

    }

    @Test
    @DisplayName("GetJson test")
    public void getJsonTest() throws JsonProcessingException {

        when(recipeDtoValidator.recipeValidate(any())).thenReturn(getRecipeRecipeDtoIn());

        Assertions.assertEquals(recipeService.getJson("{\n" +
                "    \"name\": \"Agora\",\n" +
                "    \"temperature\": \"gelada\",\n" +
                "    \"prepare\": \"Macere 2 morangos e 2 amoras na coqueteleira. Adicione a água, suco de limão, xarope de morango, algumas pedras de gelo (~4 pedras grandes) na coqueteleira e bata. Faça uma dupla coagem para o copo (com gelo). Complete com GingerAle (ou água com gás)\",\n" +
                "    \"backgroundColor\": \"red\",\n" +
                "    \"recipeItems\":[\n" +
                "        {\"ingredient\":{\"name\":\"Amoras\"}, \"quant\":\"3\", \"quantType\":\"UNIT\"},\n" +
                "        {\"ingredient\":{\"name\":\"Monrango\"}, \"quant\":\"3\", \"quantType\":\"UNIT\"},\n" +
                "        {\"ingredient\":{\"name\":\"Limão Siciliano\"}, \"quant\":\"15\", \"quantType\":\"ML\"},\n" +
                "        {\"ingredient\":{\"name\":\"Ginger Ale\"}, \"quant\":\"0\", \"quantType\":\"COMPLETAR\"},\n" +
                "        {\"ingredient\":{\"name\":\"Xarope de Morango\"}, \"quant\":\"15\", \"quantType\":\"ML\"}\n" +
                "    ]\n" +
                "}").getName(), getRecipeRecipeDtoIn().getName());
    }
    @Test
    @DisplayName("GetJson Throw test ")
    public void getJsonThrowTest() throws JsonProcessingException {

        //when(recipeDtoValidator.recipeValidate(any())).thenReturn(getRecipeRecipeDtoIn());
        //when(recipeDtoValidator.recipeValidate(any())).thenThrow(Exception.class);

        given(recipeDtoValidator.recipeValidate(any())).willAnswer( invocation -> { throw new Exception("");} );


        Assertions.assertThrows(Exception.class,() -> recipeService.getJson("{\n" +
                "    \"name\": \"Agora\",\n" +
                "    \"temperature\": \"gelada\",\n" +
                "    \"prepare\": \"Macere 2 morangos e 2 amoras na coqueteleira. Adicione a água, suco de limão, xarope de morango, algumas pedras de gelo (~4 pedras grandes) na coqueteleira e bata. Faça uma dupla coagem para o copo (com gelo). Complete com GingerAle (ou água com gás)\",\n" +
                "    \"backgroundColor\": \"red\",\n" +
                "    \"recipeItems\":[\n" +
                "        {\"ingredient\":{\"name\":\"Amoras\"}, \"quant\":\"3\", \"quantType\":\"UNIT\"},\n" +
                "        {\"ingredient\":{\"name\":\"Monrango\"}, \"quant\":\"3\", \"quantType\":\"UNIT\"},\n" +
                "        {\"ingredient\":{\"name\":\"Limão Siciliano\"}, \"quant\":\"15\", \"quantType\":\"ML\"},\n" +
                "        {\"ingredient\":{\"name\":\"Ginger Ale\"}, \"quant\":\"0\", \"quantType\":\"COMPLETAR\"},\n" +
                "        {\"ingredient\":{\"name\":\"Xarope de Morango\"}, \"quant\":\"15\", \"quantType\":\"ML\"}\n" +
                "    ]\n" +
                "}"));

    }


}
