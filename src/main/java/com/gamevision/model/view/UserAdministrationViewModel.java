package com.gamevision.model.view;

import java.util.List;

public class UserAdministrationViewModel {

    private Long id;
    private String username;
    private List<String> userRoles;
    private boolean isActive;

    public UserAdministrationViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserAdministrationViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserAdministrationViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public UserAdministrationViewModel setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserAdministrationViewModel setActive(boolean active) {
        isActive = active;
        return this;
    }
}
