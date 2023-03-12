package com.drinksleo.controller;

import com.drinksleo.dao.Recipe;
import com.drinksleo.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Recipe>> registerReceita(){
        return ResponseEntity.ok(recipeService.getAll());
    }

    @PostMapping(value = "/new", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Recipe> create( @RequestPart( value = "file", required = false) MultipartFile image ,
                          @RequestPart("recipe") String recipeDto) throws JsonProcessingException {

        Recipe recipe = mapper.toDomain(recipeService.getJson(recipeDto));

        return ResponseEntity.ok(recipeService.createRecipe(recipe, image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable String id){
        return ResponseEntity.ok(recipeService.getRecipe(id));
    }
}
