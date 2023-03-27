package com.drinksleo.dto;



import com.drinksleo.dao.RecipeItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "create", builderClassName = "Builder")
public class RecipeDtoIn implements Serializable {


    @NotNull
    @Size(min=5, max = 30)
    @Schema(description = "This is the Drink`s name.", example = "Drink de Uva")
    private String name;
    @NotNull
    @Schema(description = "This is the Drink`s temperature. Can be 'gelada' or 'quente' ", example = "gelada")
    private String temperature;
    @NotNull
    @Schema(description = "This is the Drink`s item list.", example = "[\n" +
            "        {\"ingredient\":{\"name\":\"Amoras\"}, \"quant\":\"3\", \"quantType\":\"UNIT\"},\n" +
            "        {\"ingredient\":{\"name\":\"Monrango\"}, \"quant\":\"3\", \"quantType\":\"UNIT\"},\n" +
            "        {\"ingredient\":{\"name\":\"Limão Siciliano\"}, \"quant\":\"15\", \"quantType\":\"ML\"},\n" +
            "    ]")
    private List<RecipeItem> recipeItems;
    @NotNull
    @Schema(description = "This is the Drink`s preparation steps. Separe the steps with new line ", example = "Coloque os ingredientes na coqueteleira \n Bata tudo \n Sirva em um copo")
    private String prepare;
    private String backgroundColor;
    private String DecorationPrepare;
    private List<RecipeItem> decorationItems;


}
