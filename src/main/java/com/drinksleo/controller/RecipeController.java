package com.drinksleo.controller;

import com.drinksleo.dao.Recipe;
import com.drinksleo.dto.RecipeDto;
import com.drinksleo.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {



    private final RecipeMapper mapper;

    @Autowired
    RecipeService recipeService;


    @GetMapping("/all")
    public List<Recipe> registerReceita(){
        return recipeService.getAll();
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Recipe create(@RequestBody RecipeDto recipe){


        return recipeService.createRecipe(mapper.toDomain(recipe));
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable String id){
        return recipeService.getRecipe(id);
    }
}
