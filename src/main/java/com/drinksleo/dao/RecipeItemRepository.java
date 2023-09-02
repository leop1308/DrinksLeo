package com.drinksleo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeItemRepository extends MongoRepository<RecipeItem, String> {
}
