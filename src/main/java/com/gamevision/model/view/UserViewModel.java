package com.gamevision.model.view;

import com.gamevision.model.entity.ProfilePicture;

public class UserViewModel {
    private String username;
    private ProfilePicture profilePicture;

    public UserViewModel() {
    }

    public UserViewModel(String username, ProfilePicture profilePicture) {
        this.username = username;
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public UserViewModel setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }
}
