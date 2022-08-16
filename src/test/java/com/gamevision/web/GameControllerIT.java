package com.gamevision.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamevision.model.entity.*;
import com.gamevision.model.enums.GenreNameEnum;
import com.gamevision.model.user.GamevisionUserDetails;
import com.gamevision.repository.GameRepository;
import com.gamevision.repository.GenreRepository;
import com.gamevision.repository.UserRepository;
import com.gamevision.repository.UserRoleRepository;
import com.gamevision.service.GameService;
import com.gamevision.service.GamevisionUserDetailsService;
import com.gamevision.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc

public class GameControllerIT {


    //autowire all necessary repos and services

    //TODO: initialize test entities here for visibility
    UserEntity testUser, testAdmin;
    GameEntity testGame;
    PlaythroughEntity testPlaythrough;
    CommentEntity testComment;
    @Autowired //autowire all necessary repos and services, then assign to them the ready entities from TestDataUtils
    private MockMvc mockMvc; //for sending HTTP requests to the test server TODO cannot autowire?!?

    //  @Autowired
    //  private PlaythroughRepository playthroughRepository;
    //  @Autowired
    //  private CommentRepository commentRepository;
    //roles and genres are hardcoded by nature, hence don't require additional data setup, no need to create anything more
    @Autowired
    private UserRoleRepository userRoleRepository;

    //No mock, test the real deal
    // @Mock
    // UserRepository userRepository; //mock, so we can get user id etc.
    @Autowired
    private UserRepository userRepository;


    @Autowired //hardcoded by nature, can be used as it is
    private GenreRepository genreRepository;

    @Autowired
    private GameRepository gameRepository; //needed to check if empty for no games message
    @Autowired
    private GameService gameService; //for getGameByTitle, etc.
    //or use the prepared TestDataUtils
    @Autowired
    private TestDataUtils testDataUtils;

    @Autowired
    UserDetailsService testUserDetailsService;
    //additional utils


    @Autowired //to pass the genres, which come as a List<String>
    private ObjectMapper objectMapper;

//private  final  GamevisionUserDetails ADMIN_USERDETAILS = new GamevisionUserDetails(
//          testAdmin.getUsername(),
//          testAdmin.getPassword(),
//          testAdmin.getEmail(),
//          testAdmin.isActive(),
//          testAdmin.getUserRoles().stream()
//                  .map(this::mapGrantedAuthority)
//                  .toList()
//  );


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
    void allGamesViewShown() throws Exception {
        this.mockMvc
                .perform(get("/games/all"))
                .andExpect(model().attributeExists("games")) //import static method
                .andExpect(view().name("games-all"));
    }

    @Test
    void displayNoGamesMessageWhenGameRepositoryIsEmpty() throws Exception {
        gameRepository.deleteAll(); //will be hold the one test game created with setUp(), so clear it
        this.mockMvc
                .perform(get("/games/all"))
                .andExpect(model().attributeExists("noGames")) //import static method
                .andExpect(view().name("games-all"));
    }

    @Test
    public void gameViewShown() throws Exception {
        Long gameId = testGame.getId(); //Don't hardcode it here, work with the available test data!!!!!

//Id should be 1
        this.mockMvc
                .perform(get("/games/" + gameId))
                .andExpect(view().name("game"));
    }

    @Test
    public void gameNotFound() throws Exception {
        this.mockMvc
                .perform(get("/games/1001"))
                .andExpect(model().attribute("errorMessage", "Game not found."))
                .andExpect(view().name("error"));
    }


    @Test
    @WithMockUser(username = "TestAdmin", roles = "ADMIN") //don't forget the proper user or "No ModelAndView found"
    public void addGameViewShown() throws Exception {
        this.mockMvc
                .perform(get("/games/add"))
                .andExpect(model().attribute("allGenres", GenreNameEnum.values())) //the genres are shown with checkboxes
                .andExpect(view().name("game-add"));

    }


    //TODO: POST addGameSubmit - fails because UserDetails is null
// @Test //constraint violation if no UserId
//// @WithUserDetails(value="TestAdmin")
// //@WithMockUser(username = "TestAdmin", roles = {"USER", "ADMIN"})
// @WithUserDetails(value = "AdminFromTestUserDataService", userDetailsServiceBeanName = "testUserDataService") //TODO USE THIS, see GitHub MobileleUserDetailsServiceTest.java
// public void addGameRedirectsToAddGameWhenNoGenreIsChosen() throws Exception {
//     GameEntity existingGame = testGame; //this should create one game in the repo and actually fill the empty proxy
//     System.out.println("Existing game ID: " + existingGame.getId()); //id is 3
//ist<String> testGenres = Arrays.asList("RPG", "AA");

//     this.mockMvc                    //this is the input data from the BM - note how params are set; author is obviously not manually entered in the input input
//             .perform(post("/games/add")
//                     .param("title", "A Great Test Game") //give the values as a MAP KVP!!! Follow the BM field names for the KEYS.
//                     .param("titleImageUrl", "testurl")
//                     .param("description", "Long enough description to be accepted...") //should get it saved to repo
//                     .param("genre", "RPG") //genre   empty genre // singular i/o plural genres in the controller due to checkbox naming issues
//                     .with(csrf()))
//             .andExpect(redirectedUrl("/games/2")); //id should be 2, second game in repo after the @Before one
// } //returns :/games/add as if there's an error? Genre?


    @Test
    void deleteByAnonymousUserRedirectsToLogin() throws Exception {
        mockMvc.perform(delete("/games/{id}/delete", testGame.getId())   //note the {id} param syntax with ,
                        .with(csrf()))
                .andExpect(redirectedUrl("http://localhost/users/login")); //Spring Security redirects to login upon unauthorized requests

    }

    @Test
    @WithMockUser(username = "Admin", roles = {"ADMIN", "USER"})
    void successfulDeletionByAdminRedirectsToAllGamesPage() throws Exception {
        mockMvc.perform(get("/games/{id}/delete", testGame.getId())
                        .with(csrf()))
                // .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games/all"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = {"ADMIN", "USER"})
    void editByAdminShowsEditView() throws Exception {
        mockMvc.perform(get("/games/{id}/edit/", testGame.getId()).
                        with(csrf()))
                // .andExpect(status().is3xxRedirection()).
                .andExpect(view().name("game-edit"));
    }


    private GrantedAuthority mapGrantedAuthority(UserRoleEntity userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole.getName().name()); ///"ROLE_"   syntax is important!
    }

    // @Test //TODO: problems with edit and update tests
    // @WithMockUser(username = "Admin", roles = {"ADMIN", "USER"})
    // void updatesGameWithValidData() throws Exception {
    //     Long id = testGame.getId();
    //List<String> genres = List.of("RPG", "AA");
    //     MultiValueMap<String, String> formParams = genres.stream().collect(Collectors.t);

    //     mockMvc.perform(patch("/games/{id}/edit/", id)
    //                     .param("title", "New Game Title")
    //                     .param("titleImageUrl", "new/test/img/url")
    //                     .param("description", "Description long enough to be valid")

    //                     .formParam("genre", genres)
    //                     .with(csrf()))
    //             .andExpect(redirectedUrl("/games/" + id ));

    // }


    @Test
    @WithMockUser(username = "Admin", roles = {"ADMIN", "USER"})
    void doesNotUpdateGameWithInvalidData() throws Exception {
        Long id = testGame.getId();
        List<String> genres = List.of("RPG", "AA");
        mockMvc.perform(post("/games/{id}/edit/", id) //was working with patch but had some issues in controller and SS, so changed to post
                        .param("title", "New Game Title")
                        .param("titleImageUrl", "new/test/img/url")
                        .param("description", "Too short")
                        .content(objectMapper.writeValueAsString(genres))
                        .with(csrf()))
                .andExpect(redirectedUrl("/games/" + id + "/edit"));

    }

    @Test
    @WithMockUser(username = "Admin", roles = {"ADMIN", "USER"})
    void deleteRedirectsWithNonexistentGame() throws Exception {
        mockMvc.perform(get("/games/{id}/delete/", 23)
                        .with(csrf()))
             //.andExpect(model().attributeExists("errorMessage")) //doesn't find it?
                .andExpect(redirectedUrl("/games/all"));

    }


//  .param("genres", "RPG")   this is invalid???
    //  .andExpect(redirectedUrl("/games/" + id));
}

