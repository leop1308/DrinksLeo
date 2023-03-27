package com.drinksleo.drinksleo.controller;

import com.drinksleo.controller.RecipeMapper;
import com.drinksleo.controller.RecipeMapperImpl;
import com.drinksleo.dao.Recipe;
import com.drinksleo.dto.RecipeDtoIn;
import org.junit.jupiter.api.Test;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.getRecipe;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipeMapperTest {


    RecipeMapper mapper = new RecipeMapperImpl();

    @Test
    public void shouldMapRecipeToDto() {
        //given
        Recipe recipe = getRecipe();

        //when
        RecipeDtoIn recipeDtoIn = mapper.toDto( recipe );

        //then
        assertThat(recipeDtoIn).isNotNull();
        assertThat( recipeDtoIn.getName() ).isEqualTo( recipe.getName() );


    }
}
