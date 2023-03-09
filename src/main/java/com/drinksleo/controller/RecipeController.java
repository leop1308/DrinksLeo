package com.drinksleo.controller;

import com.drinksleo.dao.Recipe;
import com.drinksleo.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


@Slf4j
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

    @PostMapping(value = "/new", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Recipe create( @RequestPart(value = "file", required = false) MultipartFile image , @RequestPart("recipe") String recipeDto) throws JsonProcessingException {

        Recipe recipe = mapper.toDomain(recipeService.getJson(recipeDto));

        return recipeService.createRecipe(recipe, image);
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable String id){
        return recipeService.getRecipe(id);
    }
}
