package com.gamevision.web;

import com.gamevision.model.view.UserViewModel;
import com.gamevision.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Better put this in UserController
    @GetMapping("/users/profile/{username}")//Principal (userdata for current user) - from SS, Model from Spring
    public String profile(Principal principal, @PathVariable("username") String username, Model model) { //todo check if ModelMapper will be necessary here, otherwise this controller doesn't need it
        //TODO
        UserViewModel userViewModel = userService.getUserViewModelByUsername(username);

        // model.addAttribute("game", gameViewModel);

        return "user-profile";

    }
}
