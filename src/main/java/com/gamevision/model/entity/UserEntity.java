package com.gamevision.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;

  //No max max size validation here! It gets FAT with encoding!
    @Column(nullable = false)
    @Size(min = 3)
    private String password;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    // @OneToMany //Not necessary to keep track of comments in UserEntity
    // private Set<CommentEntity> comments;
//
    @OneToOne
    private ProfilePicture profilePicture;

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private Set<UserRoleEntity> userRoles;

    @Column(nullable = false, name = "is_active")
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER) //TODO check if EAGER is OK; check if @ManyToMany is OK? @OneToMany?
    private Set<GameEntity> games; //One user may have many games and one game can be saved by many users to their gamelist.

    @OneToMany //TODO: check fetch type
    @Column(nullable = false, name="favourite_playthroughs") //a bit fatter DB but avoids NPE
    private Set<PlaythroughEntity> favouritePlaythroughs;




    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }


    public Set<GameEntity> getGames() {
        return games;
    }

    public UserEntity setGames(Set<GameEntity> games) {
        this.games = games;
        return this;
    }

    public Set<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public UserEntity setUserRoles(Set<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    //  public Set<CommentEntity> getComments() {
    //      return comments;
    //  }
//
    //  public UserEntity setComments(Set<CommentEntity> comments) {
    //      this.comments = comments;
    //      return this;
    //  }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public UserEntity setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public Set<PlaythroughEntity> getFavouritePlaythroughs() {
        return favouritePlaythroughs;
    }

    public UserEntity setFavouritePlaythroughs(Set<PlaythroughEntity> favouritePlaythroughs) {
        this.favouritePlaythroughs = favouritePlaythroughs;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserEntity setActive(boolean active) {
        isActive = active;
        return this;
    }
}
