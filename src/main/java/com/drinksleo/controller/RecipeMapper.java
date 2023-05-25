package com.drinksleo.controller;


import com.drinksleo.dao.Recipe;
import com.drinksleo.dto.RecipeDtoIn;
import com.drinksleo.dto.RecipeDtoOut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {


    @Mapping(expression = "java(stringToList(recipeDtoIn.getPrepare()))", target = "prepare")
    Recipe toDomain (RecipeDtoIn recipeDtoIn);

    @Mapping(expression = "java(listToString(recipe.getPrepare()))", target = "prepare")
    RecipeDtoIn toDtoIn(Recipe recipe);

    //@Mapping(expression = "java(listToString(recipe.getPrepare()))", target = "recipeDto.prepare")
    List<RecipeDtoOut> toDtoOut(List<Recipe> recipe);

    default List<String> stringToList (String string){
        String[] rows;
        List<String> stringList = new ArrayList<>();
        if(string != null) {
            rows = string.split("\n");
            for (int i = 0; i < rows.length; i++) {
                stringList.add(rows[i]);
            }
        }
        return stringList;
    }
    default String listToString (List<String> stringList){
        String string="";
        for(String s: stringList){
            string += s;
        }


        return string;
    }

}
