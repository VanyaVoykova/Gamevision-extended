package com.gamevision.service.impl;

import com.gamevision.errorhandling.exceptions.UserIsAlreadyAdminException;
import com.gamevision.errorhandling.exceptions.UserIsAlreadyOnlyUserException;
import com.gamevision.errorhandling.exceptions.UserNotFoundException;
import com.gamevision.model.entity.UserEntity;
import com.gamevision.model.entity.UserRoleEntity;
import com.gamevision.model.enums.UserRoleEnum;
import com.gamevision.model.view.UserAdministrationViewModel;
import com.gamevision.model.view.UserViewModel;
import com.gamevision.repository.UserRepository;
import com.gamevision.repository.UserRoleRepository;
import com.gamevision.service.AdminService;
import com.gamevision.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AdminServiceImpl implements AdminService {
    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    UserService userService;

    public AdminServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    @Override
    public UserAdministrationViewModel promoteUserToAdmin(String username) {
        UserEntity userToPromote = userRepository.findByUsernameIgnoreCase(username).orElseThrow(UserNotFoundException::new);
        UserRoleEntity adminRole = userRoleRepository.findByName(UserRoleEnum.ADMIN).orElse(null); //should never be null

        userToPromote.getUserRoles().add(adminRole);

        userRepository.save(userToPromote);

        //TODO: UX check if user already is admin

        return userService.getUserVmByUsername(username);
    }

    @Override
    public UserAdministrationViewModel demoteUserToUser(String username) {
        UserEntity userToDemoteToUser = userRepository.findByUsernameIgnoreCase(username).orElseThrow(UserNotFoundException::new);
        UserRoleEntity userRole = userRoleRepository.findByName(UserRoleEnum.USER).orElse(null); //should never be null

        //TODO: UX check if user cannot be demoted further

        userToDemoteToUser.getUserRoles().clear();
        userToDemoteToUser.getUserRoles().add(userRole);

        userRepository.save(userToDemoteToUser);

        return userService.getUserVmByUsername(username);
    }

    //TODO: implement MODERATOR role

    @Override
    public UserAdministrationViewModel banUser(String username) {

        UserEntity userToBan = userRepository.findByUsernameIgnoreCase(username).orElseThrow(UserNotFoundException::new);
        userToBan.setActive(false);
        userRepository.save(userToBan);

        return userService.getUserVmByUsername(username);
    }

    @Override
    public UserAdministrationViewModel unbanUser(String username) {
        UserEntity userToUnban = userRepository.findByUsernameIgnoreCase(username).orElseThrow(UserNotFoundException::new);
        userToUnban.setActive(true);
        userRepository.save(userToUnban);

        return userService.getUserVmByUsername(username);
    }
}
