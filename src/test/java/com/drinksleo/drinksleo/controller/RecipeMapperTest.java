package com.drinksleo.drinksleo.controller;

import com.drinksleo.controller.RecipeMapper;
import com.drinksleo.controller.RecipeMapperImpl;
import com.drinksleo.dao.Recipe;
import com.drinksleo.dto.RecipeDtoIn;
import com.drinksleo.dto.RecipeDtoOut;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipeMapperTest {


    RecipeMapper mapper = new RecipeMapperImpl();

    @Test
    public void shouldMapRecipeToDtoIn() {
        //given
        Recipe recipe = getRecipe();

        //when
        RecipeDtoIn recipeDtoIn = mapper.toDtoIn( recipe );

        //then
        assertThat(recipeDtoIn).isNotNull();
        assertThat( recipeDtoIn.getName() ).isEqualTo( recipe.getName() );

    }
    @Test
    public void shouldMapRecipeToDtoOut() {
        //given
        List<Recipe> recipe = getRecipes();

        //when
        List<RecipeDtoOut> recipeDtoOut = mapper.toDtoOut( recipe );

        //then
        assertThat(recipeDtoOut).isNotNull();
        assertThat( recipeDtoOut.get(0).getName() ).isEqualTo( recipe.get(0).getName() );

    }

    @Test
    public void shouldMapRecipe() {
        //given
        RecipeDtoIn recipeDtoIn = getRecipeRecipeDtoIn();

        //when
        Recipe recipe = mapper.toDomain( recipeDtoIn);

        //then
        assertThat(recipe).isNotNull();
        assertThat( recipe.getName() ).isEqualTo( recipeDtoIn.getName() );
        assertThat( recipe.getPrepare() ).hasSize( 4 );
    }

    @Test
    public void shouldMapRecipeNull() {
        //given
        RecipeDtoIn recipeDtoIn = null;
        List<Recipe> recipeList = null;
        Recipe recipe = null;


        //when
        Recipe recipeResult = mapper.toDomain( recipeDtoIn);
        List<RecipeDtoOut> recipeDtoOutResult = mapper.toDtoOut( recipeList );
        RecipeDtoIn recipeDtoInResult = mapper.toDtoIn( recipe );

        //then
        assertThat(recipeResult).isNull();
        assertThat(recipeDtoOutResult).isNull();
        assertThat(recipeDtoInResult).isNull();
        }


}
