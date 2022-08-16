package com.gamevision.model.servicemodels;

import com.gamevision.model.entity.GameEntity;
import com.gamevision.model.entity.PlaythroughEntity;
import com.gamevision.model.entity.ProfilePicture;
import com.gamevision.model.entity.UserRoleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserRegisterServiceModel {

    //Values received from the validated BM
    private String username;
    private String password;
    private String email;

    //Complete with the rest of the entity data
    private ProfilePicture profilePicture;

    private List<UserRoleEntity> userRoles;

    private boolean isActive;

    private Set<GameEntity> games;

    private Set<PlaythroughEntity> favouritePlaythroughs;

    public UserRegisterServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public UserRegisterServiceModel setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public UserRegisterServiceModel setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserRegisterServiceModel setActive(boolean active) {
        isActive = active;
        return this;
    }

    public Set<GameEntity> getGames() {
        return games;
    }

    public UserRegisterServiceModel setGames(Set<GameEntity> games) {
        this.games = games;
        return this;
    }

    public Set<PlaythroughEntity> getFavouritePlaythroughs() {
        return favouritePlaythroughs;
    }

    public UserRegisterServiceModel setFavouritePlaythroughs(Set<PlaythroughEntity> favouritePlaythroughs) {
        this.favouritePlaythroughs = favouritePlaythroughs;
        return this;
    }
}
