package com.drinksleo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {

    Ingredient findByName(String name);
}
