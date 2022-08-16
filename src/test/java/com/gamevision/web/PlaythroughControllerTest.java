package com.gamevision.web;

import com.gamevision.errorhandling.exceptions.GameNotFoundException;
import com.gamevision.model.entity.CommentEntity;
import com.gamevision.model.entity.GameEntity;
import com.gamevision.model.entity.PlaythroughEntity;
import com.gamevision.model.entity.UserEntity;
import com.gamevision.repository.GameRepository;
import com.gamevision.repository.PlaythroughRepository;
import com.gamevision.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlaythroughControllerTest {
    UserEntity testUser, testAdmin;
    GameEntity testGame;
    PlaythroughEntity testPlaythrough;
    CommentEntity testComment;
    @Autowired //autowire all necessary repos and services, then assign to them the ready entities from TestDataUtils
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;
    @Autowired
    UserDetailsService testUserDetailsService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlaythroughRepository playthroughRepository;

    @BeforeEach
    public void setUp() { //saving to repos included
        testUser = testDataUtils.createTestUser("TestUser");
        testAdmin = testDataUtils.createTestAdmin("TestAdmin");
        testGame = testDataUtils.createTestGame(testAdmin);
        testPlaythrough = testDataUtils.createTestPlaythrough(testAdmin);
        testComment = testDataUtils.createTestComment(testUser);
    }

    @AfterEach
    public void tearDown() {
        testDataUtils.wipeDatabase();
    }


    @Test
    void showPlaythroughsForGame() throws Exception {
        PlaythroughEntity pt = testDataUtils.createTestPlaythrough(testAdmin);
        testGame.getPlaythroughs().add(testPlaythrough);
        gameRepository.save(testGame); //update
        Long testGameId = testGame.getId();
        this.mockMvc.perform(get("/games/" + testGameId + "/playthroughs/all"))
                .andExpect(view().name("playthroughs-all"));

    }


    //no PT: /errors/playthroughs-not-found-error

    @Test
    void showsGameNotFoundWithNonexistentGame() throws Exception {
        this.mockMvc.perform(get("/games/11/playthroughs/all"))
                // .andExpect(model().attribute("gameNotFound", "Game not found."))
                .andExpect(view().name("/errors/game-not-found-error"));

    }


    @Test
    void showsPlaythroughsNotFoundWhenGameHasNone() throws Exception {
        GameEntity gameWithoutPlaythroughs = testDataUtils.createTestGame(testAdmin);
        Long id = gameWithoutPlaythroughs.getId();
        this.mockMvc
                .perform(get("/games/" + id + "/playthroughs/all"))
                .andExpect(view().name("/errors/playthroughs-not-found-error"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"ADMIN", "USER"})
    void showsAddPlaythroughViewForAdminsOnly() throws Exception {
        Long gameId = testGame.getId();
        this.mockMvc.perform(get("/games/{gameId}/playthroughs/add", gameId))
                .andExpect(model().attribute("gameTitle", testGame.getTitle()))
                .andExpect(view().name("playthrough-add"));

    }

    @Test
    @WithMockUser(username = "Admin", roles = {"ADMIN", "USER"})
    void correctMessageWhenAttemptingToAddPlaythroughToNonexistentGame() throws Exception {
        this.mockMvc.perform(get("/games/11/playthroughs/add"))
                .andExpect(model().attribute("exceptionMessage", "Game not found."))
                .andExpect(view().name("playthrough-add"));

    }

    @Test //todo fails // recurring problem with post/put
    @WithMockUser(username = "Admin", roles = {"ADMIN", "USER"})
    void addPlaythroughWithValidData() throws Exception {

        Long gameId = testGame.getId();
        mockMvc.perform(post("/games/{gameId}/playthroughs/add", gameId)
                        //look up binding model
                        .param("title", "Test Playthrough Title")
                        .param("videoUrl", "/test/url")
                        .param("description", "Test Playthrough Description")
                        .with(csrf())) //game not found???
                .andExpect(redirectedUrl("/games/" + gameId + "/playthroughs/all"));

    }

    @Test //todo fails
    @WithMockUser(username = "Admin", roles = {"ADMIN", "USER"})
    void deletePlaythroughRedirectsSuccessfully() throws Exception {
        PlaythroughEntity playthrough = testDataUtils.createTestPlaythrough(testUser);
        testGame.getPlaythroughs().add(playthrough);
        gameRepository.save(testGame); //update with playthrough
        Long gameId = testGame.getId();
        Long playthroughId = playthrough.getId();
        mockMvc.perform(get("/games/{gameId}/playthroughs/{playthroughId}/delete", gameId, playthroughId))
                .andExpect(redirectedUrl("redirect:/games/" + gameId + "/playthroughs/all"));
    }


}
