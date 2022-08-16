package com.gamevision.web;

import com.gamevision.model.entity.GameEntity;
import com.gamevision.model.entity.UserEntity;
import com.gamevision.service.GameService;
import com.gamevision.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    GameService gameService;

    @Autowired
    private TestDataUtils testDataUtils;

    //  private UserEntity testUser, te

    @AfterEach
    void tearDown() {
        testDataUtils.wipeDatabase();
    }


    @Test
    void messageShownWhenNoGamesAvailable() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(model().attributeExists("noGames"))
                .andExpect(model().attributeExists("quoteAuthor"))
                .andExpect(view().name("home"));
    }

    @Test
    void existingGamesOfTheWeekShown() throws Exception {
        UserEntity admin = testDataUtils.createTestAdmin("Adder");
        GameEntity testGameOfTheWeek = testDataUtils.createTestGame(admin);
        this.mockMvc
                .perform(get("/"))
                .andExpect(model().attributeExists("carouselGames"));
    }

}
