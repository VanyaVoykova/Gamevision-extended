package com.gamevision.web;

import com.gamevision.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

//import org.springframework.boot.test.mock.mockito.MockBean; //no mock email service so not needed

import static org.mockito.Mockito.verify;


import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc; //could not autowire, but it's OK!!!!
    @Autowired
    private TestDataUtils testDataUtils;

    //  private UserEntity testUser, testAdmin;

    @AfterEach
    void tearDown() {
        testDataUtils.wipeDatabase();
    }


    @Test
    void testRegistrationPageShown() throws Exception {
        mockMvc.perform(get("/users/register"))
                //.andExpect(status().isOk()) //not a REST controller, so no status
                .andExpect(view().name("register"));
    }

    @Test
//make sure to follow field order; REAL repo is used, so change name and email to keep them unique or delete User from the DB
    void testUserRegistrationWithValidData() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "Gabriel")
                        .param("email", "gabe@example.com")
                        .param("password", "gabe")
                        .param("confirmPassword", "gabe")
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));

        System.out.println("MockMvc: " + mockMvc);

    }

    @Test
        // existing username !isUserNameFree
    void testUserRegistrationWithExistingUsername() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "Gabriel")
                        .param("email", "gabe@example.com")
                        .param("password", "gabe")
                        .param("confirmPassword", "gabe")
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));

        mockMvc.perform(post("/users/register")
                        .param("username", "Gabriel")
                        .param("email", "gabri@example.com") //different email
                        .param("password", "gabe")
                        .param("confirmPassword", "gabe")
                        .with(csrf()))
                .andExpect(redirectedUrl("/users/register"));

        System.out.println("MockMvc: " + mockMvc);

    }

    @Test //Maybe @Aftereach wipes it
//existing email - !isEmailFree
    void testUserRegistrationWithExistingPasswordDifferentUsername() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "Gabriel")
                        .param("email", "gabe@example.com")
                        .param("password", "gabe")
                        .param("confirmPassword", "gabe")
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));

        mockMvc.perform(post("/users/register")
                        .param("username", "Karl") //existing username
                        .param("email", "gabe@example.com") //different email
                        .param("password", "gabe")
                        .param("confirmPassword", "gabe")
                        .with(csrf()))
                .andExpect(redirectedUrl("/users/register"));

        System.out.println("MockMvc: " + mockMvc);

    }

    @Test
//make sure to follow field order; REAL repo is used, so change name and email to keep them unique or delete User from the DB
    void testUserRegistrationWithInvalidBindingModel() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "Aa") //name too short
                        .param("email", "not-a-valid-email")
                        .param("password", "a") //not a valid password
                        .param("confirmPassword", "a")
                        .with(csrf()))
                .andExpect(redirectedUrl("/users/register"));

        System.out.println("MockMvc: " + mockMvc);

    }


    //Spring is taking care of login, so no need to test that

}
