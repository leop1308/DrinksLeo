package com.drinksleo.controller;

import com.drinksleo.dto.RecipeDtoIn;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Slf4j
@Service
public class RecipeDtoValidator {

    @Autowired
    private Validator validator;

    public RecipeDtoIn recipeValidate(@Valid RecipeDtoIn readValue) {
        Set<ConstraintViolation<RecipeDtoIn>> violations = validator.validate(readValue);

        int count=0;
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RecipeDtoIn> constraintViolation : violations) {
                sb.append("\n Violation "+(++count)+": "+constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        return readValue;
    }

}
