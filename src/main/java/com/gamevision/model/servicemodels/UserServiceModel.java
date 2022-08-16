package com.gamevision.model.servicemodels;

import java.util.List;

public class UserServiceModel {

    private Long id;
    private String username;
    private String email;
    private String profilePictureUrl;
    private List<String> userRoles;
    private boolean isActive;
    private List<String> games; //titles
    private List<String> playthroughs; //titles

    public UserServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public UserServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public UserServiceModel setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
        return this;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public UserServiceModel setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserServiceModel setActive(boolean active) {
        isActive = active;
        return this;
    }

    public List<String> getGames() {
        return games;
    }

    public UserServiceModel setGames(List<String> games) {
        this.games = games;
        return this;
    }

    public List<String> getPlaythroughs() {
        return playthroughs;
    }

    public UserServiceModel setPlaythroughs(List<String> playthroughs) {
        this.playthroughs = playthroughs;
        return this;
    }
}
