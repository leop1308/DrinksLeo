package com.drinksleo.drinksleo.dao.dto;


import com.drinksleo.dto.RecipeDtoIn;
import org.junit.jupiter.api.*;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.*;
import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("RecipeDtoTest")
public class RecipeDtoInTest {



    private RecipeDtoIn recipe;

    @BeforeEach
    void setUp() {
        recipe = new RecipeDtoIn();
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
    @DisplayName("Setter Tests ")
    class setterTest {
        @Test
        void setDecorationPrepare() {
            RecipeDtoIn recipe = new RecipeDtoIn();
            recipe.setDecorationPrepare(RECIPE_PREPARE_STRING);
            Assertions.assertEquals(recipe.getDecorationPrepare(), RECIPE_PREPARE_STRING);

        }
    }

    @Nested
    @DisplayName("Builder Tests ")
    class builderTest {
        @Test
        void createTest() {
            Assertions.assertNotNull(RecipeDtoIn.create().build());
        }
        @Test
        void createwithName() {
            RecipeDtoIn recipe = RecipeDtoIn.create().name(RECIPE_NAME).build();
            Assertions.assertEquals(recipe.getName(), RECIPE_NAME);

        }

    }
}
