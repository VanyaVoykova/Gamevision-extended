package com.gamevision.web;

import com.gamevision.model.binding.UserRegisterBindingModel;
import com.gamevision.service.UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthenticationController { //REGISTER AND LOGIN
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/users/register")
    public String register() {
        return "register";
    }


    @PostMapping("/users/register")
    public String registerSubmit(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //in constr (@UserRegisterBindingModel bm, BindingResult bindingResult, RedirectAttributes redirectAttributes
        boolean isUserNameFree = userService.isUserNameFree(userRegisterBindingModel.getUsername());
        boolean isEmailFree = userService.isEmailFree(userRegisterBindingModel.getEmail());
        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/users/register";


        } else if (!isUserNameFree) {
            redirectAttributes.addFlashAttribute("usernameTaken", true);
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:/users/register";
        } else if (!isEmailFree) {
            redirectAttributes.addFlashAttribute("emailTaken", true);
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:/users/register";
        }


        //Uses UserService, Authentication is separate only in controllers to avoid UserController getting too fat
        // userService.registerUser(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        try {
            userService.registerAndLogin(userRegisterBindingModel);
        } catch (RuntimeException e) {
            model.addAttribute("exceptionMessage", e.getMessage()); //or with model???
            model.addAttribute("exceptionCause", e.getCause());
            return "error"; //should be returned automatically
        }


        return "redirect:/";
    }


    //Model attribute for register
    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    //==================================================================
//Login - ONLY THIS GET, SS should handle the rest, no login in UserServiceImpl/AuthenticationService
    @GetMapping("/users/login")
    public String login() {

        return "login";
    }
//In SS config:
     //  .antMatchers(HttpMethod.POST, "/users/register", "/users/login").anonymous()


//Todo: doesn't work here; Mobilelele is w/o one
    //Takes care for login errors
    //Should be post mapping!  Original: "/users/login-error"
    @PostMapping("/users/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {
//
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("bad_credentials", true);
//
        return "redirect:/users/login";
    }

    //Better put this in UserController
    @GetMapping("/users/profile")//Principal (userdata for current user) - from SS, Model from Spring
    public String profile(Principal principal, Model model) { //todo check if ModelMapper will be necessary here, otherwise this controller doesn't need it
   //TODO

        return "user-profile";

    }


}
