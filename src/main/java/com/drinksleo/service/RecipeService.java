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
        list.stream().forEach(p -> log.info("{}", p.toString()));

        return list;
    }

    @Override
    public Recipe getRecipe(String id) {
        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe do not exist."));
        log.info("New Recipe registerd: {}",recipe.toString());
        return recipe;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {

        setItems(regit adcipe);


        log.info("New Recipe registerd: {}",recipe.toString());
        return  repository.save(recipe);//recipe;
    }

    /**
     *    save itemsRecipe in database and Add it in Recipe
     *
     * @param recipe
     * @return recipe
     */
    private void setItems(Recipe recipe){

        List<RecipeItem> recipeItemsVerified = new ArrayList<>();
        Ingredient ingAux;
        log.info("New Recipe: {}",recipe.toString());


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

        recipe.setRecipeItems(recipeItemsVerified);
        //return recipe;
    }


}
