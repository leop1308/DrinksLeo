package com.drinksleo.controller;


import com.drinksleo.dao.Recipe;
import com.drinksleo.dto.RecipeDto;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    Recipe toDomain (RecipeDto recipeDto);

    RecipeDto toDto(Recipe recipe);


}
