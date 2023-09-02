package com.drinksleo.drinksleo.controller;

import com.drinksleo.config.SecurityConfig;
import com.drinksleo.controller.IngredientController;
import com.drinksleo.controller.UserController;
import com.drinksleo.drinksleo.auxTestClasses.FakeUserRepository;
import com.drinksleo.drinksleo.auxTestClasses.WithMockAdmin;
import com.drinksleo.drinksleo.auxTestClasses.WithMockBarman;
import com.drinksleo.service.IngredientServiceInterface;
import com.drinksleo.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.drinksleo.drinksleo.auxTestClasses.AuxTest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = { UserController.class, SecurityConfig.class, UserService.class,  FakeUserRepository.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    private WebApplicationContext context;
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Given an authenticated User with the Role Admin
     * When that user do a request to get a specified data of a user
     * Then status HTTP 200-Ok is sent with a JSON with a User specified data, like field name filled
     */
    @Test
    @DisplayName("ADMIN Get existing User: return status 200")
    @WithMockAdmin
    public void getUserAdmin() throws Exception {
        when(userService.loadUserByUsername(any())).thenReturn(getUser());
        mockMvc.perform(get("http://localhost/user/1"))
                .andExpect(jsonPath("$.username").isNotEmpty())
                .andExpect(status().isOk());
    }

    /**
     * Given an authenticated User with the Role Admin
     * When that user do a request
     * Then status HTTP 201-Created is sent with a JSON with a User specified data, like field name filled
     */
    @Test
    @DisplayName("ADMIN Add User with role-user ADMIN: return status 200")
    @WithMockAdmin
    public void CreateWithRoleAdmin() throws Exception {
        when(userService.saveUser(any())).thenReturn(getUser());
        this.mockMvc.perform(
                post("http://localhost/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"username\": \"user2\",\n" +
                                "    \"password\": \"pastel\",\n" +
                                "    \"authorities\": \"ROLE_USER\"\n" +
                                "    \n" +
                                "}"))
//                        .content(asJsonString(getUser()))) cannot be used because getAuthorities return a Collection instead of String, generating http status error "400 message=null"
                .andExpect(status().is(201));
    }

    /**
     * Given an authenticated User without the Role Admin
     * When that user do a request to register a new User
     * Then an error status HTTP 403-Forbidden is sent
     */
    @Test
    @DisplayName("USER Add User with role-user USER: return status 403")
    @WithMockUser
    public void createWithRoleUserForbidden() throws Exception {
        this.mockMvc.perform(post("http://localhost/user/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(getUser())))
                .andExpect(status().is(403));
    }

    /**
     * Given an authenticated User with the Role Barman but without the Role Admin
     * When that user do a request to register a new User
     * Then an error status HTTP 403-Forbidden is sent
     */
    @Test
    @DisplayName("BARMAN Add User with role-user USER: return status 403")
    @WithMockBarman
    public void createWithRoleBarmanForbidden() throws Exception {
        this.mockMvc.perform(post("http://localhost/user/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(getUser())))
                .andExpect(status().is(403));
    }
}
