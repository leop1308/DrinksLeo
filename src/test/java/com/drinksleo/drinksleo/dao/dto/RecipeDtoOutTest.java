package com.drinksleo.drinksleo.dao.dto;

import com.drinksleo.drinksleo.auxTestClasses.AuxTest;
import com.drinksleo.dto.RecipeDtoOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.getRecipeDtoOut;

public class RecipeDtoOutTest {

    @Test
    @DisplayName("Test No Args Constructor")
    public void testNoArgsConstructor() {
        RecipeDtoOut recipeDtoOut = new RecipeDtoOut();

        Assertions.assertAll(
                () -> Assertions.assertNotNull(recipeDtoOut),
                () -> Assertions.assertNull(recipeDtoOut.getName())
        );
    }
    @Test
    @DisplayName("Test Setters")
    public void testSetters() {

        RecipeDtoOut recipeDtoOut = getRecipeDtoOut();
        recipeDtoOut.setName("Other Name");

        Assertions.assertAll(
                () -> Assertions.assertNotNull(recipeDtoOut),
                () -> Assertions.assertNotEquals(recipeDtoOut.getName(), getRecipeDtoOut().getName())
        );

    }
}
