package com.drinksleo.service;

import com.drinksleo.dao.Ingredient;
import com.drinksleo.dao.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService implements IngredientServiceInterface {
    @Autowired
    private IngredientRepository repository;
    public List<Ingredient> getAll(){
        return repository.findAll();
    }
}
