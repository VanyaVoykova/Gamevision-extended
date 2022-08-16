package com.gamevision.service;

import com.gamevision.model.binding.UserRegisterBindingModel;
import com.gamevision.model.entity.UserEntity;
import com.gamevision.model.servicemodels.UserServiceModel;
import com.gamevision.model.view.UserAdministrationViewModel;

import java.util.Optional;

public interface UserService {
    boolean isUserNameFree(String username); //for register

    boolean isEmailFree(String email); //for register

    void registerAndLogin(UserRegisterBindingModel userRegisterBindingModel);

    //  UserServiceModel findByUsernameAndPassword(String username, String password);
    // void loginUser(Long id, String username);

    UserEntity findUserById(Long id);

   UserEntity findUserByUsername(String username); //fix this, use the one below only

    UserServiceModel getUserSmByUsername(String username);

    UserAdministrationViewModel getUserVmByUsername(String username); //ViewModel for Admins (need to see roles and active status)
    //  void updateUser(UserEntity user);

    void initUsers();
}
