package com.drinksleo.dto;


import com.drinksleo.dao.DecorationItem;
import com.drinksleo.dao.RecipeItem;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "create", builderClassName = "Builder")
public class RecipeDto implements Serializable {
    private String name;
    private String temperature;
    private List<RecipeItem> recipeItems;
    private List <String> prepare;
    private String imageUrl;
    private String backgroundColor;
    private String DecorationPrepare;
    private List<RecipeItem> decorationItems;


}
