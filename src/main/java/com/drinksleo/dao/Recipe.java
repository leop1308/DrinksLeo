package com.drinksleo.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document //Para o Bancos relacionais utilizamos @Entity, mas para não relacionais como o mongo uitilizamos @Document
public class Recipe {

    @Id
    private String name;
    private String type;
    @DBRef
    private List<RecipeItem> recipeItems;
    private String prepare;

}
