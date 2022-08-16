package com.gamevision.init;


import com.gamevision.service.UserRoleService;
import com.gamevision.service.UserService;
import com.gamevision.service.GenreService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GamevisionInit implements CommandLineRunner {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final GenreService genreService;

    public GamevisionInit(UserService userService, UserRoleService userRoleService, GenreService genreService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.genreService = genreService;
    }


    @Override
    public void run(String... args) throws Exception {
        genreService.initGenres();
        userRoleService.initUserRoles();
        userService.initUsers();

    }
}
