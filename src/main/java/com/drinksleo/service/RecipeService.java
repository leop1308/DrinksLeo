package com.drinksleo.service;

import com.drinksleo.config.ImageConfigs;
import com.drinksleo.controller.RecipeDtoValidator;
import com.drinksleo.dao.*;
import com.drinksleo.dto.RecipeDtoIn;
import com.drinksleo.dto.RecipeDtoOut;
import com.drinksleo.exception.BadRequestException;
import com.drinksleo.util.UploadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
                .orElseThrow(() -> new BadRequestException("Recipe do not exist."));
        log.info("New Recipe registerd: {}",recipe.toString());
        return recipe;
    }

    @Override
    public Recipe createRecipe(Recipe recipe, MultipartFile image) {

        try {
            setImageUrl(recipe, image);

            setItems(recipe);
        }catch (Exception x){
            log.error("Error: {}", x);
        }

        log.info("New Recipe registerd: recipe.printRecipe(): {}",recipe.toString());
        log.info("New Recipe registerd: recipe.toString(): {}",recipe.toString());
        return  repository.save(recipe);//recipe;
    }

    /**
     *
     *
     *    save itemsRecipe in database and Add it in Recipe
     *
     * @param recipe
     * @return recipe
     */
    private void setItems(Recipe recipe) throws Exception {

        List<RecipeItem> recipeItemsVerified = new ArrayList<>();
        Ingredient ingAux;
        log.info("New Recipe: {}",recipe.toString());

        if(recipe.getRecipeItems() != null) {
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
        }else{
            throw new Exception("Error: RecipeItems is null");
        }
        recipe.setRecipeItems(recipeItemsVerified);
        //return recipe;
    }

    private void setImageUrl(Recipe recipe, MultipartFile image) throws Exception {
        try{
            log.info("LOG IMAGEM Recipe: {}", recipe.toString());
            if(UploadUtil.uploadImage(image, imageConfigs)){
                recipe.setImageUrl(imageConfigs.getPath() + File.separator + image.getOriginalFilename());
            }

        }catch (Exception e){
            log.error("Erro: {}",e);
        }
        //return recipe;
    }

    public RecipeDtoIn getJson(String string) throws JsonProcessingException {
        RecipeDtoIn recipeJson = new RecipeDtoIn();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            recipeJson = recipeDtoValidator.recipeValidate(objectMapper.readValue(string, RecipeDtoIn.class));

        }catch (Exception e){
            throw e;
        }
        return recipeJson;
    }


}
