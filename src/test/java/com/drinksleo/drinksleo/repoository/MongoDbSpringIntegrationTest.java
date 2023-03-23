package com.drinksleo.drinksleo.repoository;

import com.drinksleo.dao.Recipe;
import com.drinksleo.dao.RecipeRepository;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.assertj.core.api.BDDSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.getRecipe;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataMongoTest
@ExtendWith({SpringExtension.class,SoftAssertionsExtension.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ExtendWith(SoftAssertionsExtension.class)
//@EnableMongoRepositories(basePackageClasses = RecipeRepository.class)
public class MongoDbSpringIntegrationTest {


    //@InjectSoftAssertions
    //BDDSoftAssertions bdd;

    @Autowired
    private RecipeRepository repository;

    @BeforeAll
    public void setup(){
        repository.deleteAll();
    }


    @Test
    public void test1(@Autowired MongoTemplate mongoTemplate, SoftAssertions softly) {
        // given


        // when
        //mongoTemplate.save(objectToSave, "collection");
        repository.save(getRecipe());
        mongoTemplate.save(getRecipe());
        // then
//        softly.assertThat(mongoTemplate.findAll(Recipe.class)).hasSize(1);
        softly.assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    public void test3(@Autowired MongoTemplate mongoTemplate, SoftAssertions softly) {
        // given


        // when
        //mongoTemplate.save(objectToSave, "collection");
        repository.save(getRecipe());
        mongoTemplate.save(getRecipe());
        // then
//        softly.assertThat(mongoTemplate.findAll(Recipe.class)).hasSize(1);
        softly.assertThat(repository.findAll()).hasSize(1);
    }
    @DisplayName("given object to save"
            + " when save object using MongoDB template"
            + " then object is saved")
    @Test
    public void test(@Autowired MongoTemplate mongoTemplate, SoftAssertions softly) {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        // when
        mongoTemplate.save(objectToSave, "collection");

        // then
        softly.assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key").containsOnly("value");
    }

}