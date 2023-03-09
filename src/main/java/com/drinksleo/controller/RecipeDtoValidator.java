package com.drinksleo.controller;

import com.drinksleo.dto.RecipeDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class RecipeDtoValidator {

    @Autowired
    private Validator validator;

    public RecipeDto recipeValidate(@Valid RecipeDto readValue) {
        Set<ConstraintViolation<RecipeDto>> violations = validator.validate(readValue);

        int count=0;
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RecipeDto> constraintViolation : violations) {
                sb.append("\n Violation "+(++count)+": "+constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        return readValue;
    }

}
