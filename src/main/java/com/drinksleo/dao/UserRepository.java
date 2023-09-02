package com.drinksleo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface UserRepository extends MongoRepository<User, String> {

    @Query("{username:'?0'}")
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
