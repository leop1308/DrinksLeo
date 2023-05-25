package com.drinksleo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeItemInterface extends MongoRepository<RecipeItem, String> {
}
