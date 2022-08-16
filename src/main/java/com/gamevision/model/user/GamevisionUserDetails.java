package com.gamevision.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class GamevisionUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final String email;
    private final boolean isActive;
    private final Collection<GrantedAuthority> authorities;

    public GamevisionUserDetails(String username, String password, String email, boolean isActive, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }


    @Override
    public boolean isEnabled() {
        return this.isActive;
    }


    @Override
    public String toString() {
        return "AppUser{" +
                "password='" + password + '\'' +
                ", username ='" + username + '\'' +
                ", authorities=" + authorities +
                '}';
    }


}
