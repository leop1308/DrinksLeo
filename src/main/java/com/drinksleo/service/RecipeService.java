package com.drinksleo.service;

import com.drinksleo.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService implements RecipeServiceInterface {

    Logger log = LoggerFactory.getLogger("SampleLogger");

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
        list.stream().forEach(p -> log.info("{}",p));

        return list;
    }

    @Override
    public Recipe getRecipe(String id) {
        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe do not exist."));
        log.info("Get Recipe: "+recipe.toString());
        return recipe;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {

        List<RecipeItem> recipeItemsVerified = new ArrayList<>();
        Ingredient ingAux;

        //Loop to ensure that all ingredients are registered
        for(int i=0; i< recipe.getRecipeItems().size(); i++){
            RecipeItem item = recipe.getRecipeItems().get(i);
            log.debug("Listando itens {} - {}", item.getId(), item.getIngredient().getName());

            String name = item.getIngredient().getName();
            ingAux = ingredientRepository.findByName(name);
            if(ingAux != null){
                log.info("Listando itens {} - {}, encontrado!", ingAux.getId(), item.getIngredient().getName());
                item.setIngredient(ingAux);
            }else{
                log.info("Listando itens {} - {}, não encontrado!", item.getId(), item.getIngredient().getName());
                item.setIngredient(ingredientRepository.save(item.ingredient));
            }
            recipeItemInterface.save(item);
            recipeItemsVerified.add(item);

        }


        //This new Set method serves to put the ID information of Ingredients into Recipe Data.
        //Because we don`t know the ID of each one Ingredient
        //That flow could be different with in the front end we guarantee the insertion and selection
        //before the recipe register, throwing the IDs informations in the Recipe register request.
        recipe.setRecipeItems(recipeItemsVerified);
        log.info("New Recipe registerd: {}",recipe.printRecipe());

        return repository.save(recipe);
    }
}
