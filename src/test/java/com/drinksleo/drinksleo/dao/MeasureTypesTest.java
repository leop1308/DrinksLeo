package com.drinksleo.drinksleo.dao;

import com.drinksleo.dao.MeasureTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MeasureTypesTest {

    @Test
    public void testGetDesc (){
        Assertions.assertEquals(MeasureTypes.COMPLETAR.getDescricao(), "Completar");
    }
}
