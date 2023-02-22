package com.drinksleo.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class RecipeItem {

    @Id
    public String id;
    @DBRef
    public Ingredient ingredient;
    public String quant;
    public String quantType;
}
