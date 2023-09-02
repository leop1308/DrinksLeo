package com.drinksleo.drinksleo.service;

import com.drinksleo.dao.Ingredient;
import com.drinksleo.dao.IngredientRepository;
import com.drinksleo.service.IngredientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.getIngredients;
import static org.mockito.Mockito.when;

@DisplayName("Ingredient Service Test")
@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {

    @InjectMocks
    IngredientService ingredientService;

    @Mock
    IngredientRepository ingredientRepository;

    @Test
    @DisplayName("Get All Ingredients")
    public void getAllTest(){
        when(ingredientRepository.findAll()).thenReturn(getIngredients());
        List<Ingredient> ingredientList = getIngredients();

        Assertions.assertAll(
                () -> Assertions.assertEquals(ingredientService.getAll().get(0).getName(), ingredientList.get(0).getName())
        );
    }
}
