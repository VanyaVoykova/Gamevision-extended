package com.gamevision.service;

import com.gamevision.model.entity.UserEntity;
import com.gamevision.model.entity.UserRoleEntity;
import com.gamevision.model.user.GamevisionUserDetails;
import com.gamevision.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//No @Service, as it's @Bean-ed by SS
public class GamevisionUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public GamevisionUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsernameIgnoreCase(username)
                .map(this::mapUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));
    }

    //Pass GamevisionUserDetails constructor parameters properly!
    private UserDetails mapUserDetails(UserEntity userEntity) {
        return new GamevisionUserDetails(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.isActive(),
                userEntity.getUserRoles().stream()
                        .map(this::mapGrantedAuthority)
                        .toList()
        );
    }


    private GrantedAuthority mapGrantedAuthority(UserRoleEntity userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole.getName().name()); ///"ROLE_"   syntax is important!
    }


}
