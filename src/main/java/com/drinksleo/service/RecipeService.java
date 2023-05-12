package com.drinksleo.service;

import com.drinksleo.config.ImageConfigs;
import com.drinksleo.controller.RecipeDtoValidator;
import com.drinksleo.dao.*;
import com.drinksleo.dto.RecipeDtoIn;
import com.drinksleo.exception.BadRequestException;
import com.drinksleo.exception.ExceptionEnum;
import com.drinksleo.util.UploadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RecipeService implements RecipeServiceInterface {


    //Uses https://native2ascii.net/ to convert çã and other caracters to ASCII
    @Autowired
    private ImageConfigs imageConfigs;

    @Autowired
    RecipeDtoValidator recipeDtoValidator;

    @Autowired
    private RecipeRepository repository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeItemInterface recipeItemInterface;

    @Override
    public List<Recipe> getAll() {
        log.info("getAll()");
        List<Recipe> list = repository.findAll();
        list.stream().
                forEach(p -> log.info("{}", p.toString()));

        return list;
    }

    @Override
    public Recipe getRecipe(String id) {
        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new BadRequestException(ExceptionEnum.RECIPE_NOT_EXISTS.getMessage()));
        log.info("New Recipe registerd: {}", recipe.toString());
        return recipe;
    }

    @Override
    public Recipe createRecipe(Recipe recipe, MultipartFile image) {

        try {
            UpAndSetImage(recipe, image);

            setItems(recipe);
        } catch (Exception x) {
            log.error("Error: {}", x);
        }

        log.info("New Recipe registerd: recipe.printRecipe(): {}", recipe.toString());
        return repository.save(recipe);//recipe;
    }

    @Override
    public Recipe updateRecipe(Recipe recipe){
        Recipe recipeOld = repository.findById(recipe.getName())
                .orElseThrow(() -> new BadRequestException(ExceptionEnum.RECIPE_NOT_EXISTS.getMessage()));

        Recipe recipeUpdated = repository.save(recipe);

        log.info("Recipe Update: recipe.printRecipe(): \n{}\n{}", recipeOld.toString(), recipeUpdated.toString());
        return recipeUpdated;

    }

    @Override
    public Recipe updateRecipe(Recipe recipe, MultipartFile image) throws Exception {


        Recipe recipeOld = repository.findById(recipe.getName())
                .orElseThrow(() -> new BadRequestException(ExceptionEnum.RECIPE_NOT_EXISTS.getMessage()));

        UpAndSetImage(recipe, image);
        setItems(recipe);

        Recipe recipeUpdated = repository.save(recipe);

        log.info("Recipe Update: recipe.printRecipe(): \n{}\n{}", recipeOld.toString(), recipeUpdated.toString());
        return recipeUpdated;
    }

    /**
     * save itemsRecipe in database and Add it in Recipe
     *
     * @param recipe
     * @return recipe
     * @throws Exception
     */
    private void setItems(Recipe recipe) throws Exception {

        List<RecipeItem> recipeItemsVerified = new ArrayList<>();
        Ingredient ingAux;
        log.info("New Recipe: {}", recipe.toString());

        if (recipe.getRecipeItems() != null) {
            //Loop to ensure that all ingredients are registered
            for (int i = 0; i < recipe.getRecipeItems().size(); i++) {
                RecipeItem item = recipe.getRecipeItems().get(i);
                log.info("Listando itens {} - {}", item.getId(), item.getIngredient().getName());

                String name = item.getIngredient().getName();
                ingAux = ingredientRepository.findByName(name);
                if (ingAux != null) {
                    log.info("Listando itens {} - {}, encontrado!", ingAux.getId(), item.getIngredient().getName());
                    item.setIngredient(ingAux);
                } else {
                    log.info("Listando itens {} - {}, não encontrado!", item.getId(), item.getIngredient().getName());
                    item.setIngredient(ingredientRepository.save(item.ingredient));
                }
                recipeItemInterface.save(item);
                recipeItemsVerified.add(item);

            }
        } else {
            throw new Exception("Error: RecipeItems is null");
        }
        recipe.setRecipeItems(recipeItemsVerified);
        //return recipe;
    }

    /**
     * Upload the image and Set the Recipe imageUrl attribute
     *
     * @param recipe
     * @param image
     * @throws Exception
     */
    private void UpAndSetImage(Recipe recipe, MultipartFile image) throws Exception {
        //try {
            log.info("LOG IMAGEM Recipe: {}", recipe.toString());
            if (UploadUtil.uploadImage(image, imageConfigs)) {
                recipe.setImageUrl(imageConfigs.getPath() + File.separator + image.getOriginalFilename());
            }

        /*} catch (Exception e) {
            log.error("Erro: {}", e);
        }*/
        //return recipe;
    }


    public RecipeDtoIn getJson(String string) throws JsonProcessingException {
        RecipeDtoIn recipeJson = new RecipeDtoIn();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            recipeJson = recipeDtoValidator.recipeValidate(objectMapper.readValue(string, RecipeDtoIn.class));

        } catch (Exception e) {
            throw e;
        }
        return recipeJson;
    }

    /**
     * Upload the image and Set the Recipe imageUrl attribute
     *
     * @param recipeName
     * @param image
     * @throws Exception
     */
    public Recipe upAndChangeImageRecipe(String recipeName, MultipartFile image) throws Exception {

        Recipe recipe = repository.findById(recipeName)
                .orElseThrow(() -> new BadRequestException(ExceptionEnum.RECIPE_NOT_EXISTS.getMessage()));
        log.info("Changing Recipe Image: {}", recipeName);
        if (UploadUtil.uploadImage(image, imageConfigs)) {
            UploadUtil.deleteFile(recipe.getImageUrl());

            recipe.setImageUrl(imageConfigs.getPath() + File.separator + image.getOriginalFilename());
            repository.save(recipe);
        }


        return recipe;
    }

    public Recipe deleteRecipe(String id) {
        Recipe recipe = repository.findById(id).orElseThrow(()  -> new BadRequestException("The Recipe do not exists!"));
        repository.deleteById(id);
/*         if(repository.findById(id).isEmpty()){
             return recipe;
         }
         log.error("Deletion Failed!")*/;
        return recipe;
        //throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Delete failed!");
    }
}



