package com.gamevision.model.view;

public class UserViewModel {
    private String username;
    private Long profilePictureId;

    public UserViewModel() {
    }

    public UserViewModel(String username, Long profilePictureId) {
        this.username = username;
        this.profilePictureId = profilePictureId;
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public Long getProfilePictureId() {
        return profilePictureId;
    }

    public UserViewModel setProfilePictureId(Long profilePictureId) {
        this.profilePictureId = profilePictureId;
        return this;
    }
}
