package com.drinksleo.dao;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Deprecated
//@Data
@Document


@Builder(builderMethodName = "create", builderClassName = "Builder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    public String id;
    public String quant;
    public MeasureTypes quantType;
    @DBRef
    public Ingredient ingredient;



}
