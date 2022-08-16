package com.gamevision.util;

import com.gamevision.model.entity.*;
import com.gamevision.model.enums.GenreNameEnum;
import com.gamevision.model.enums.UserRoleEnum;
import com.gamevision.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class TestDataUtils {
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private GameRepository gameRepository;
    private GenreRepository genreRepository;
    private PlaythroughRepository playthroughRepository;
    private CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    public TestDataUtils(UserRepository userRepository, UserRoleRepository userRoleRepository, GameRepository gameRepository, GenreRepository genreRepository, PlaythroughRepository playthroughRepository, CommentRepository commentRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.playthroughRepository = playthroughRepository;
        this.commentRepository = commentRepository;
        this.passwordEncoder = passwordEncoder;
    }


    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setName(UserRoleEnum.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setName(UserRoleEnum.USER);

            userRoleRepository.save(adminRole);
            userRoleRepository.save(userRole);
        }
    }

    //No password in example test but seems necessary, since we use REAL repo in integration tests
    //Example test was for Unit tests and hence with a MOCK repo!!!!
    public UserEntity createTestAdmin(String username) {
        initRoles();
        UserEntity admin = new UserEntity()
                .setUsername(username)
                .setEmail("admin@test.com")
                .setPassword(passwordEncoder.encode("testAdminPass"))
                .setActive(true)
                .setUserRoles(new HashSet<>(userRoleRepository.findAll())) //admin gets all roles
                .setGames(new HashSet<>())
                .setFavouritePlaythroughs(new HashSet<>());


        //todo try by 'specifying' the type of the set by adding an object to it
        //  admin.getGames().add(createTestGame(admin));
        //  admin.getFavouritePlaythroughs().add(createTestPlaythrough(admin));

        return userRepository.save(admin);

    }


    public UserEntity createTestUser(String username) {
        initRoles();
        UserEntity user = new UserEntity()
                .setUsername(username)
                .setEmail("user@test.com") //don't forget to set a DIFFERENT pass after copying from admin...
                .setPassword(passwordEncoder.encode("testUserPass"))
                .setActive(true)
                // .setUserRoles(Set.of(new UserRoleEntity().setName(UserRoleEnum.USER))); //not a new entity, get it from the repo where initRoles() saves them
                .setUserRoles(Set.of(userRoleRepository.findByName(UserRoleEnum.USER).get()));
        //.setGames(new HashSet<>())
        //.setFavouritePlaythroughs(new HashSet<>());
        System.out.println(user.toString());
        //todo try by 'specifying' the type of the set by adding an object to it - shoud NOT be necessary
        //   user.getGames().add(createTestGame(createTestAdmin("TestAdminWhoAddedATestGame")));
        //   user.getFavouritePlaythroughs().add(createTestPlaythrough(createTestAdmin("TestAdminWhoAddedATestGame")));
        return userRepository.save(user);

    }

    //TODO UNCOMMENT GAME AND PT
    public GameEntity createTestGame(UserEntity addedBy) {
        GameEntity testGame = new GameEntity()
                .setTitle("Test Game Title")
                .setTitleImageUrl("Test Title Image URL")
                .setDescription("Test game description")
                .setGenres(Set.of(genreRepository.findByName(GenreNameEnum.RPG),
                        genreRepository.findByName(GenreNameEnum.AA)))
                .setAddedBy(addedBy) //todo check if forgetting this was flubbing the test
                .setPlaythroughs(new HashSet<>())
                //   .setPlaythroughs(Set.of(createTestPlaythrough(addedBy)))
                .setComments(new HashSet<>());
        return gameRepository.save(testGame);
    }

    public PlaythroughEntity createTestPlaythrough(UserEntity addedBy) {
        PlaythroughEntity testPlaythrough = new PlaythroughEntity()
                .setTitle("Test Playthrough Title")
                .setVideoUrl("Test video URL")
                .setDescription("Test Playthrough Description")
                .setAddedBy(addedBy);

        PlaythroughEntity savedPlaythrough = playthroughRepository.save(testPlaythrough);
        return savedPlaythrough;
    }

    public CommentEntity createTestComment(UserEntity author) {
        CommentEntity testComment = new CommentEntity()
                .setAuthor(author)
                .setText("Test comment text 1.")
                .setLikesCounter(0)
                .setDateTimeCreated(LocalDateTime.of(2017, 7, 7, 7, 7));

        return commentRepository.save(testComment);
    }

    //Genre repo shouldn't be necessary, it's hardcoded stuff


    public void wipeDatabase() { //roles and genres are pretty much hardcoded, so @Autowired in the tests
        //wiping order is important, due to FK constraints (playthrough tied to game, games have addedBy User)
        commentRepository.deleteAll();
        gameRepository.deleteAll();
        playthroughRepository.deleteAll();
        genreRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    // this.commentRepository.deleteAll();
    //   //     this.playthroughRepository.deleteAll();
    //   //     this.gameRepository.deleteAll();
    //   //     this.genreRepository.deleteAll(); //todo
    //   //     this.userRepository.deleteAll();
    //   //     this.userRoleRepository.deleteAll();


}
