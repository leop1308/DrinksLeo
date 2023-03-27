package com.drinksleo.drinksleo.dao.dto;


import com.drinksleo.dto.RecipeDto;
import org.junit.jupiter.api.*;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.RECIPE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("RecipeDtoTest")
public class RecipeDtoTest {



    private RecipeDto recipe;

    @BeforeEach
    void setUp() {
        recipe = new RecipeDto();
    }

    @Nested
    @DisplayName("Getter Tests ")
    class getterTest {
        @Test
        void getId() {
            recipe.setName(RECIPE_NAME);
            Assertions.assertEquals(RECIPE_NAME, recipe.getName());
        }
    }
    @Nested
    @DisplayName("Builder Tests ")
    class builderTest {
        @Test
        void createTest() {
            Assertions.assertNotNull(RecipeDto.create().build());
        }
        @Test
        void createwithName() {
            RecipeDto recipe = RecipeDto.create().name(RECIPE_NAME).build();
            Assertions.assertEquals(recipe.getName(), RECIPE_NAME);

        }
    }
}
