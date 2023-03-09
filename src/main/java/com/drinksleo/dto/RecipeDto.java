package com.drinksleo.dto;



import com.drinksleo.dao.RecipeItem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;



import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "create", builderClassName = "Builder")
public class RecipeDto implements Serializable {


    @NotNull
    @Size(min=5, max = 10)
    private String name;
    @NotNull
    private String temperature;
    @NotNull
    private List<RecipeItem> recipeItems;
    @NotNull
    private String prepare;
    private String imageUrl;
    private String backgroundColor;
    private String DecorationPrepare;
    private List<RecipeItem> decorationItems;


}
