package com.drinksleo.dao;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document //Para o Bancos relacionais utilizamos @Entity, mas para não relacionais como o mongo uitilizamos @Document
public class Recipe {

    @Id
    private String name;
    private String temperature;
    @DBRef
    private List<RecipeItem> recipeItems;
    private String prepare;
    private String imageUrl;
    private String backgroundColor;
    private String DecorationPrepare;
    @DBRef
    private List<DecorationItem> decorationItems;

    public String printRecipe(){
        return "Recipe:::: (name:" + this.name + ", temperature:" + this.getTemperature() + ", recipeItems:" + printRecipeItems(this.recipeItems) + ", prepare:" + this.getPrepare() + ", imageUrl:" + this.getImageUrl() + ", backgroundColor:" + this.getBackgroundColor() + ", DecorationPrepare:" + this.getDecorationPrepare() + ", decorationItems:" + printDecorationItems((this.decorationItems)) + ")";

    }

    public String printRecipeItems(List<RecipeItem> itemList) {
        String itemsConcat ="[ ";
        if(itemList != null) {
            for (ItemInterface items : itemList) {
                System.out.println("LISTANDO: "+items.getIngredient().getName());
                itemsConcat += "( name:" + items.getIngredient().getName() + ", quant:" + items.quant + ", quantType:" + items.quantType.getDescricao()+" )";
            }
        }
        return itemsConcat+" ]";
    }
    public String printDecorationItems(List<DecorationItem> itemList){
        String itemsConcat ="[ ";
        if(itemList != null) {
            for (ItemInterface items : itemList) {
                itemsConcat += "( name:" + items.getIngredient().getName() + ", quant:" + items.quant + ", quantType:" + items.quantType.getDescricao()+" )";
            }
        }
            return itemsConcat+" ]";
    }


}
