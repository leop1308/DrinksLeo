package com.drinksleo.dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderMethodName = "create", builderClassName = "Builder")
public class Ingredient {

    @Id
    public String id;
    @Indexed(unique = true)
    public String name;


}
