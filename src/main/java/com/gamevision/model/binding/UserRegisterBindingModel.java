package com.gamevision.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//TODO
//@FieldMatch(
//        first = "password",
//        second = "confirmPassword",
//        message = "Passwords do not match."
//)

public class UserRegisterBindingModel {
    @NotBlank(message = "Username cannot be blank!")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters!")
    private String username;

    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 3, max = 20, message = "Password must be between 3 and 20 characters!")
    private String password;

    @NotBlank(message = "Confirm password cannot be blank!")
    @Size(min = 3, max = 20, message = "Password must be between 3 and 20 characters.")
    private String confirmPassword;

    @Email(message = "Please enter a valid e-mail.")
    private String email;

    public UserRegisterBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
