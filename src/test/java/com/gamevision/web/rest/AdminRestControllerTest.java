package com.gamevision.web.rest;

import com.gamevision.model.entity.UserEntity;
import com.gamevision.service.AdminService;
import com.gamevision.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest //(AdminRestController.class) //added this todo check if it gets better nope
public class AdminRestControllerTest {
//TODO: note: we plonk all that's used by the controller with @Autowired and @Autowired MockMvc

//TODO: how to mock USER and ADMIN for the same test

    //Here for better visibility and reusability
    private static final String ADMINNAME = "TestAdmin";

    private static final String USERNAME = "TestUser";
    @MockBean(name = "mockUserDetails")
    private UserDetails userDetails;
    @Autowired//todo note how we mock the service used by the controller
    private AdminService adminService; //actual service
    @Autowired
    private MockMvc mockMvc; //just for the initial view; don't forget your private access modifier...
    @Autowired
    private TestDataUtils testDataUtils;
    //Users
    private UserEntity testUser, testAdmin;

    //private static final String ADMINPASSWORD = "testpass";

    @BeforeEach
    void setUp() {
        testAdmin = testDataUtils.createTestAdmin(ADMINNAME);
        testUser = testDataUtils.createTestUser(USERNAME);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.wipeDatabase();
        //userRepository.deleteAll();
    }


    //TODO GOT TO LOG AN ADMIN FIRST, OF COURSE......


    //Instead of login first in order to see the admin panel as it actually happens, we just test @WithMockUser
    @Test
    @WithMockUser(username = ADMINNAME, roles = {"ADMIN", "USER"})
    void adminPanelShown() throws Exception {
        mockMvc.perform(get("/admin"))
                //  .andExpect(status().isOk());
                .andExpect(view().name("admin-panel")); //no ModelAndView found
    }



    @Test
    @WithMockUser(username = ADMINNAME, roles = {"ADMIN", "USER"})
    void promoteExistingUserToAdminSuccessfully() throws Exception {

        mockMvc.perform(put("/admin/promote")
                .content(USERNAME)
                        .contentType("application/json")
                        .accept("application/json")
                .with(csrf()))
               // .andExpect(status().is(200));
                .andExpect(status().isOk());
              //  .andExpect(view().name("admin-panel")); //no ModelAndView found
    }

    @Test
    @WithMockUser(username = ADMINNAME, roles = {"ADMIN", "USER"})
    void doesNotPromoteNonexistentUser() throws Exception {

        mockMvc.perform(put("/admin/promote")
                        .content("DoesNotExist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is(404));
        //  .andExpect(status().isOk());
        //  .andExpect(view().name("admin-panel")); //no ModelAndView found
    }


}
