package com.gamevision.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserManageBindingModel {

    @NotBlank
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters (inclusive)")
    private String username;

    //if changing usernames is implemented, add id here, should be able to get it from comments

    public UserManageBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserManageBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }


}
