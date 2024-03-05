package com.gamevision.model.view;

import com.gamevision.model.entity.GameEntity;
import com.gamevision.model.entity.ProfilePicture;

import java.util.List;
import java.util.TreeSet;

public class UserProfileViewModel {
    private String username;
    private ProfilePicture profilePicture;
    private List<GameCardViewModel> myGames;


    public UserProfileViewModel() {
    }

    public UserProfileViewModel(String username, ProfilePicture profilePicture) {
        this.username = username;
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public UserProfileViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public UserProfileViewModel setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public List<GameCardViewModel> getMyGames() {
        return myGames;
    }

    public UserProfileViewModel setMyGames(List<GameCardViewModel> myGamesList) {
        this.myGames = myGamesList;
        return this;
    }
}
