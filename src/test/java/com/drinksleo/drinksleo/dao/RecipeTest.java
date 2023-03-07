package com.drinksleo.drinksleo.dao;

import com.drinksleo.dao.Recipe;
import org.junit.jupiter.api.*;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.*;

public class RecipeTest {

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
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
            Assertions.assertNotNull(Recipe.create().build());
        }
        @Test
        void createwithName() {
            Recipe recipe = Recipe.create().name(RECIPE_NAME).build();
            Assertions.assertEquals(recipe.getName(), RECIPE_NAME);

        }
    }


}
