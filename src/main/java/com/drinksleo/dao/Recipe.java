package com.drinksleo.dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderMethodName = "create", builderClassName = "Builder")
@Document //Para o Bancos relacionais utilizamos @Entity, mas para não relacionais como o mongo uitilizamos @Document
public class Recipe {

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

    public String printRecipe(){
        return "Recipe:::: (name:" + this.name + ", temperature:" + this.getTemperature() + ", recipeItems:" + printRecipeItems(this.recipeItems) + ", prepare:" + this.getPrepare() + ", imageUrl:" + this.getImageUrl() + ", backgroundColor:" + this.getBackgroundColor() + ", DecorationPrepare:" + this.getDecorationPrepare() + ", decorationItems:" + printRecipeItems((this.decorationItems)) + ")";

    }

    public String printRecipeItems(List<RecipeItem> itemList) {
        String itemsConcat ="[ ";
        if(itemList != null) {
            for (RecipeItem items : itemList) {
                System.out.println("LISTANDO: "+items.getIngredient().getName());
                itemsConcat += "( name:" + items.getIngredient().getName() + ", quant:" + items.quant + ", quantType:" + items.quantType.getDescricao()+" )";
            }
        }
        return itemsConcat+" ]";
    }



}
