package com.drinksleo.drinksleo.controller;


import com.drinksleo.config.SecurityConfig;
import com.drinksleo.controller.RecipeController;
import com.drinksleo.controller.RecipeMapper;
import com.drinksleo.dao.Recipe;
import com.drinksleo.dao.UserRepository;
import com.drinksleo.drinksleo.auxTestClasses.FakeUserRepository;
import com.drinksleo.drinksleo.auxTestClasses.WithMockAdmin;
import com.drinksleo.drinksleo.auxTestClasses.WithMockBarman;
import com.drinksleo.exception.BadRequestException;
import com.drinksleo.exception.ExceptionEnum;
import com.drinksleo.service.RecipeService;
import com.drinksleo.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@WebMvcTest(RecipeController.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RecipeController.class, SecurityConfig.class, UserService.class, FakeUserRepository.class})
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    RecipeService recipeService;

    @MockBean
    RecipeMapper mapper;

    @Nested
    @DisplayName("Get Methods")
    class getMethods {


        /**
         * Given the database do not have a list of Recipes registered
         * When Anonymous User request it
         * Then status HTTP 200-Ok is sent with a JSON of that a list of Recipes with a field name empty
         */
        @Test
        @DisplayName("ANONYMOUS Get all Recipes without any Recipe: return status 200 with empty list")
        @WithAnonymousUser
        public void getRecipeExistAnonUser() throws Exception {
            when(recipeService.getAll()).thenReturn(getRecipes());
            when(mapper.toDtoOut(any())).thenReturn(null);
            mockMvc.perform(get("http://localhost/recipe/all"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").doesNotExist());
        }

        /**
         * Given the database have a list of Recipes registered
         * When Anonymous User request it
         * Then status HTTP 200-Ok is sent with a JSON of that a list of Recipes with a field name filled
         */
        @Test
        @DisplayName("ANONYMOUS Get all Recipes: return status 200")
        @WithAnonymousUser
        public void getRecipeNotExistAnonUser() throws Exception {
            when(recipeService.getAll()).thenReturn(getRecipes());
            when(mapper.toDtoOut(any())).thenReturn(getRecipesDtoOut());
            mockMvc.perform(get("http://localhost/recipe/all"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").isNotEmpty());
        }

        /**
         * Given a Recipe exists
         * When Anonymous User request it
         * Then status HTTP 201-Ok is sent with a JSON of that Recipe with a field name is returned
         */
        @Test
        @DisplayName("ANONYMOUS Get existing Recipe: return status 200")
        @WithAnonymousUser
        public void getRecipeExist() throws Exception {
            when(recipeService.getRecipe(any())).thenReturn(getRecipe());
            mockMvc.perform(get("http://localhost/recipe/1"))
                    .andExpect(jsonPath("$.name").isNotEmpty())
                    .andExpect(status().isOk());
        }

        /**
         * Given an anonymous User
         * When that user request a Recipe that not exists in the database
         * Then an error http status 400 is thrown
         */
        @Test
        @DisplayName("ANONYMOUS Add existing Recipe: return status 400")
        @WithAnonymousUser
        public void getRecipeNotExists() throws Exception {
            when(recipeService.getRecipe(any())).thenThrow(new BadRequestException(ExceptionEnum.RECIPE_NOT_EXISTS.getMessage()));
            mockMvc.perform(get("http://localhost/recipe/1"))
                    .andExpect(status().isBadRequest());
        }
    }


    @Nested
    @DisplayName("Post Methods")
    class PostMethods {
        /**
         * Given an authenticated User with the Role Admin and a Recipe well formatted
         * When that user a request to add that new JSON of that Recipe
         * Then status HTTP 201-Created is sent with a JSON of that Recipe
         */
        @Test
        @DisplayName("ADMIN Add Recipe: return status 201")
        @WithMockAdmin
        public void addRecipe() throws
                Exception {
            MockMultipartFile file = new MockMultipartFile("file",
                    "hello.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "Hello, World!".getBytes());
            MockMultipartFile recipe = new MockMultipartFile("recipe",
                    "recipe",
                    MediaType.TEXT_PLAIN_VALUE,
                    asJsonString(getRecipeDto()).getBytes());
            when(recipeService.createRecipe(any(), any())).thenReturn(getRecipe());
            mockMvc.perform(multipart("http://localhost/recipe/new")
                            .file(file)
                            .file(recipe))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                            is(getRecipe().getName())))
                    .andExpect(status().is(201));
        }

        /**
         * Given an authenticated User with the Role Admin and a Recipe null
         * When that user do a request to add that new JSON of that Recipe
         * Then error status HTTP 400-BadRequest is sent with message "Required part 'recipe' is not present"
         */
        @Test
        @DisplayName("ADMIN Add null Recipe: return status 400")
        @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
        public void addRecipeRecipeNull() throws Exception {
            MockMultipartFile file = new MockMultipartFile("file",
                    "hello.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "Hello, World!".getBytes());
            MockMultipartFile recipe = new MockMultipartFile("recipe",
                    "recipe",
                    MediaType.TEXT_PLAIN_VALUE,
                    "".getBytes());

            mockMvc.perform(multipart("http://localhost/recipe/new")
                            .file(file)
                            .file(recipe)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        }

        /**
         * Given an authenticated User without the Role Admin and a Recipe well formatted
         * When that user a request to add that new JSON of that Recipe
         * Then status HTTP 403-Forbidden is sent with a JSON of that Recipe
         */
        @Test
        @DisplayName("USER Add Recipe: return status 403")
        @WithMockUser
        public void addRecipeForbiden() throws
                Exception {
            MockMultipartFile file = new MockMultipartFile("file",
                    "hello.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "Hello, World!".getBytes());
            MockMultipartFile recipe = new MockMultipartFile("recipe",
                    "recipe",
                    MediaType.TEXT_PLAIN_VALUE,
                    asJsonString(getRecipeDto()).getBytes());
            when(recipeService.createRecipe(any(), any())).thenReturn(getRecipe());
            mockMvc.perform(multipart("http://localhost/recipe/new")
                            .file(file)
                            .file(recipe))
                    .andExpect(status().is(403));
        }
        /**
         * Given an anonymous User (without the Role Admin) and a Recipe well formatted
         * When that user a request to add that new JSON of that Recipe
         * Then status HTTP 401-Unauthorized is sent with a JSON of that Recipe
         */
        @Test
        @DisplayName("ANONYMOUS Add Recipe: return status 401")
        @WithAnonymousUser
        public void addRecipeUnauthorized() throws
                Exception {
            MockMultipartFile file = new MockMultipartFile("file",
                    "hello.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "Hello, World!".getBytes());
            MockMultipartFile recipe = new MockMultipartFile("recipe",
                    "recipe",
                    MediaType.TEXT_PLAIN_VALUE,
                    asJsonString(getRecipeDto()).getBytes());
            when(recipeService.createRecipe(any(), any())).thenReturn(getRecipe());
            mockMvc.perform(multipart("http://localhost/recipe/new")
                            .file(file)
                            .file(recipe))
                    .andExpect(status().is(401));
        }

        /**
         * Given an authenticated User with the Role Barman and a Recipe well formatted
         * When that user a request to add that new JSON of that Recipe
         * Then status HTTP 200-Ok is sent with a JSON of that Recipe
         */
        @Test
        @DisplayName("BARMAN Add Recipe: return status 201")
        @WithMockBarman
        public void addRecipeBarman() throws
                Exception {
            MockMultipartFile file = new MockMultipartFile("file",
                    "hello.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "Hello, World!".getBytes());
            MockMultipartFile recipe = new MockMultipartFile("recipe",
                    "recipe",
                    MediaType.TEXT_PLAIN_VALUE,
                    asJsonString(getRecipeDto()).getBytes());
            when(recipeService.createRecipe(any(), any())).thenReturn(getRecipe());
            mockMvc.perform(multipart("http://localhost/recipe/new")
                            .file(file)
                            .file(recipe))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                            is(getRecipe().getName())))
                    .andExpect(status().is(201));
        }


    }

    /**
     * Given an authenticated User with the Role Admin and a Recipe null
     * When that user do a request to add that new JSON of that Recipe
     * Then error status HTTP 400-BadRequest is sent with message "Required part 'recipe' is not present"
     */
    @Nested
    @DisplayName("Put Methods")
    class PutMethods {

        /**
         * Given an authenticated User with the Role Admin with a new Image JPG and Recipe valid fields is sent
         * When that user do a request to edit an existing Recipe with his Image and Recipe
         * Then status HTTP 200-Ok is sent with a JSON of the Recipe updated
         */
        @Test
        @DisplayName("ADMIN Update Recipe with image: return status 200")
        @WithMockAdmin
        public void updateRecipe() throws
                Exception {
            MockMultipartFile file = new MockMultipartFile("file",
                    "hello.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "Hello, World!".getBytes());
            MockMultipartFile recipe = new MockMultipartFile("recipe",
                    "recipe",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello, World!".getBytes());
            Recipe recipeUpdated = getRecipe();
            recipeUpdated.setBackgroundColor("new background color");
            when(recipeService.updateRecipe(any(), any())).thenReturn(recipeUpdated);

            MockMultipartHttpServletRequestBuilder builder =
                    MockMvcRequestBuilders.multipart("http://localhost/recipe/update-with-image");
            builder.with(new RequestPostProcessor() {
                @Override
                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                    request.setMethod("PUT");
                    return request;
                }
            });
            mockMvc.perform(builder
                            .file(file)
                            .file(recipe))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").isNotEmpty())
                    .andExpect(jsonPath("$.backgroundColor").value(recipeUpdated.getBackgroundColor()));
        }

        /**
         * Given an authenticated User with the Role Admin with a new Image JPG
         * When that user do a request to edit an existing Recipe by RecipeName with his Image
         * Then status HTTP 200-Ok is sent with a JSON of the Recipe updated
         */
        @Test
        @DisplayName("ADMIN Update just Image of Recipe: return status 200")
        @WithMockAdmin
        public void imageUpdateRecipe() throws
                Exception {
            MockMultipartFile file = new MockMultipartFile("file",
                    "hello.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "Hello, World!".getBytes());
            MockMultipartFile recipeName = new MockMultipartFile("recipeName",
                    "recipeName",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello, World!".getBytes());
            when(recipeService.upAndChangeImageRecipe(any(), any())).thenReturn(getRecipe());

            MockMultipartHttpServletRequestBuilder builder =
                    MockMvcRequestBuilders.multipart("http://localhost/recipe/updateimage");
            builder.with(new RequestPostProcessor() {
                @Override
                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                    request.setMethod("PATCH");
                    return request;
                }
            });
            mockMvc.perform(builder
                            .file(file)
                            .file(recipeName))
                    .andExpect(status().isOk());
        }

        /**
         * Given an authenticated User with the Role Admin with a valid Recipe
         * When that user do a request to edit an existing Recipe without Image
         * Then status HTTP 200-Ok is sent with a JSON of the Recipe updated
         */
        @Test
        @DisplayName("ADMIN Update Recipe without image: status ok")
        @WithMockAdmin
        public void updateRecipeWithoutImage() throws
                Exception {

            Recipe recipe = getRecipe();
            when(mapper.toDomain(any())).thenReturn(recipe);
            when(recipeService.updateRecipe(any())).thenReturn(recipe);


            mockMvc.perform(put("http://localhost/recipe/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(asJsonString(getRecipeDtoIn()))

            ).andExpect(status().isOk());

        }

        /**
         * Given an authenticated User without the Role Admin with a new Image JPG and Recipe valid fields is sent
         * When that user do a request to edit an existing Recipe with his Image and Recipe
         * Then status HTTP 403-Forbidden is sent with a JSON of the Recipe updated
         */
        @Test
        @DisplayName("USER Update Recipe with image: return status 403")
        @WithMockUser
        public void updateRecipeForbidden() throws
                Exception {
            MockMultipartFile file = new MockMultipartFile("file",
                    "hello.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "Hello, World!".getBytes());
            MockMultipartFile recipe = new MockMultipartFile("recipe",
                    "recipe",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello, World!".getBytes());
            Recipe recipeUpdated = getRecipe();
            recipeUpdated.setBackgroundColor("new background color");
            when(recipeService.updateRecipe(any(), any())).thenReturn(recipeUpdated);

            MockMultipartHttpServletRequestBuilder builder =
                    MockMvcRequestBuilders.multipart("http://localhost/recipe/update-with-image");
            builder.with(new RequestPostProcessor() {
                @Override
                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                    request.setMethod("PUT");
                    return request;
                }
            });
            mockMvc.perform(builder
                            .file(file)
                            .file(recipe))
                    .andExpect(status().is(403));
        }
        /**
         * Given an authenticated User with the Role barman with a new Image JPG and Recipe valid fields is sent
         * When that user do a request to edit an existing Recipe with his Image and Recipe
         * Then status HTTP 403-Forbidden is sent with a JSON of the Recipe updated
         */
        @Test
        @DisplayName("BARMAN Update Recipe with image: return status 200")
        @WithMockBarman
        public void updateRecipeBarman() throws
                Exception {
            MockMultipartFile file = new MockMultipartFile("file",
                    "hello.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "Hello, World!".getBytes());
            MockMultipartFile recipe = new MockMultipartFile("recipe",
                    "recipe",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello, World!".getBytes());
            Recipe recipeUpdated = getRecipe();
            recipeUpdated.setBackgroundColor("new background color");
            when(recipeService.updateRecipe(any(), any())).thenReturn(recipeUpdated);

            MockMultipartHttpServletRequestBuilder builder =
                    MockMvcRequestBuilders.multipart("http://localhost/recipe/update-with-image");
            builder.with(new RequestPostProcessor() {
                @Override
                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                    request.setMethod("PUT");
                    return request;
                }
            });
            mockMvc.perform(builder
                            .file(file)
                            .file(recipe))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").isNotEmpty())
                    .andExpect(jsonPath("$.backgroundColor").value(recipeUpdated.getBackgroundColor()));
        }

        /**
         * Given an anonymous User (without the Role Admin) with a new Image JPG and Recipe valid fields is sent
         * When that user do a request to edit an existing Recipe with his Image and Recipe
         * Then status HTTP 401-Unauthorized is sent with a JSON of the Recipe updated
         */
        @Test
        @DisplayName("ANONYMOUS Update Recipe with image: return status 401")
        @WithAnonymousUser
        public void updateRecipeForbiddenAnonymous() throws
                Exception {
            MockMultipartFile file = new MockMultipartFile("file",
                    "hello.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "Hello, World!".getBytes());
            MockMultipartFile recipe = new MockMultipartFile("recipe",
                    "recipe",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello, World!".getBytes());
            Recipe recipeUpdated = getRecipe();
            recipeUpdated.setBackgroundColor("new background color");
            when(recipeService.updateRecipe(any(), any())).thenReturn(recipeUpdated);

            MockMultipartHttpServletRequestBuilder builder =
                    MockMvcRequestBuilders.multipart("http://localhost/recipe/update-with-image");
            builder.with(new RequestPostProcessor() {
                @Override
                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                    request.setMethod("PUT");
                    return request;
                }
            });
            mockMvc.perform(builder
                            .file(file)
                            .file(recipe))
                    .andExpect(status().is(401));
        }

    }







    @Nested
    @DisplayName("Delete Methods")
    class DeleteMethods {

        /**
         * Given an authenticated User with the Role Admin
         * When that user do a request to delete an existing Recipe by RecipeName
         * Then status HTTP 200-Ok is returned
         */
        @Test
        @DisplayName("ADMIN Delete existing Recipe: return status 200 ")
        @WithMockAdmin
        public void deleteRecipe() throws Exception {
            when(recipeService.deleteRecipe(any())).thenReturn(getRecipe());
            mockMvc.perform(delete("http://localhost/recipe/delete/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(200));
        }
        /**
         * Given an authenticated User with the Role Barman
         * When that user do a request to delete an existing Recipe by RecipeName
         * Then status HTTP 200-Ok is returned
         */
        @Test
        @DisplayName("BARMAN Delete existing Recipe: return status 200 ")
        @WithMockAdmin
        public void deleteRecipeBarman() throws Exception {
            when(recipeService.deleteRecipe(any())).thenReturn(getRecipe());
            mockMvc.perform(delete("http://localhost/recipe/delete/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(200));
        }

        /**
         * Given an authenticated User with the Role Admin
         * When that user do a request to delete a non-existing Recipe by RecipeName
         * Then return error status HTTP 400-BarRequest is returned
         */
        @Test
        @DisplayName("ADMIN Delete Recipe Nonexistent: return status 400 ")
        @WithMockAdmin
        public void deleteRecipeNonexistent() throws Exception {
            when(recipeService.deleteRecipe(any())).thenThrow(new BadRequestException("The Recipe do not exists!"));
            mockMvc.perform(delete("http://localhost/recipe/delete/4")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        }
        /**
         * Given an authenticated User without the Role Admin
         * When that user do a request to delete a non-existing Recipe by RecipeName
         * Then return error status HTTP 403-Forbidden is returned
         */
        @Test
        @DisplayName("USER Delete Recipe Nonexistent: return status 403 ")
        @WithMockUser
        public void deleteRecipeForbidden() throws Exception {
            when(recipeService.deleteRecipe(any())).thenThrow(new BadRequestException("The Recipe do not exists!"));
            mockMvc.perform(delete("http://localhost/recipe/delete/4")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(403));
        }
        /**
         * Given anonymous User (without the Role Admin)
         * When that user do a request to delete an existing Recipe by RecipeName
         * Then return error status HTTP 401-Unauthorized is returned
         */
        @Test
        @DisplayName("ANONYMOUS Delete Recipe Nonexistent: return status 401 ")
        @WithAnonymousUser
        public void deleteRecipeUnauthorized() throws Exception {
            when(recipeService.deleteRecipe(any())).thenThrow(new BadRequestException("The Recipe do not exists!"));
            mockMvc.perform(delete("http://localhost/recipe/delete/4")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(401));
        }

        /**
         * Given an authenticated User with the Role Admin
         * When that user do a request to delete an existing Recipe but is failed through a Internal Error
         * Then return error status HTTP 400-BarRequest is returned
         */
        @Test
        @DisplayName("ADMIN Delete Recipe: return status 500")
        @WithMockAdmin
        public void deleteRecipeFailed() throws Exception {
            when(recipeService.deleteRecipe(any())).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "The Recipe do not exists!"));
            mockMvc.perform(delete("http://localhost/recipe/delete/4")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(500));
        }


    }

}
