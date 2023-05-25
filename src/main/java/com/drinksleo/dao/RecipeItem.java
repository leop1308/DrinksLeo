package com.drinksleo.dao;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
@Builder(builderMethodName = "create", builderClassName = "Builder")
public class RecipeItem  {

    @Id
    public String id;
    public String quant;
    public MeasureTypes quantType;
    @DBRef
    public Ingredient ingredient;

}
