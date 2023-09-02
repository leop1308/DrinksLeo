package com.drinksleo.dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(builderMethodName = "create", builderClassName = "Builder")
@Document //Para o Bancos relacionais utilizamos @Entity, mas para não relacionais como o mongo uitilizamos @Document
public class Recipe implements Serializable {

    @Id
    private String name;

    private String temperature;
    @DBRef
    private List<RecipeItem> recipeItems;
    private List <String> prepare;
    private String imageUrl;
    private String backgroundColor;
    private String DecorationPrepare;
    @DBRef
    private List<RecipeItem> decorationItems;






}
