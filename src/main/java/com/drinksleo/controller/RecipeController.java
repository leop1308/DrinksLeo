package com.drinksleo.controller;

import com.drinksleo.dao.Recipe;
import com.drinksleo.dto.RecipeDtoOut;
import com.drinksleo.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Get all Recipes", description = "")
    public ResponseEntity<List<RecipeDtoOut>> registerReceita() {
        return ResponseEntity.ok(mapper.toDtoOut(recipeService.getAll()));
    }

    @PostMapping(value = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Create a new Recipe", description = "")
    public ResponseEntity<Recipe> create(@RequestPart(value = "file", required = false) MultipartFile image,
                                         @RequestPart("recipe") String recipeDtoIn) throws JsonProcessingException {

        Recipe recipe = mapper.toDomain(recipeService.getJson(recipeDtoIn));

        return new ResponseEntity( recipeService.createRecipe(recipe, image), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update a Recipe", description = "Update all fileds of a Recipe")
    public ResponseEntity<Recipe> fullUpdate(@RequestPart(value = "file", required = false) MultipartFile image,
                                         @RequestPart("recipe") String recipeDtoIn) throws JsonProcessingException, Exception {

        Recipe recipe = mapper.toDomain(recipeService.getJson(recipeDtoIn));

        return ResponseEntity.ok(recipeService.updateRecipe(recipe, image));
    }

    @PatchMapping(value = "/update-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Create a new Recipe", description = "")
    public ResponseEntity<Recipe> recipeImageUpdate(@RequestPart(value = "file", required = false) MultipartFile image,
                                         @RequestPart("recipeName") String recipeName) throws JsonProcessingException, Exception {

        return ResponseEntity.ok(recipeService.UpAndChangeImageRecipe(recipeName, image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable String id) {
        return ResponseEntity.ok(recipeService.getRecipe(id));
    }
}
