package com.gamevision.service;

import com.gamevision.model.binding.UserRegisterBindingModel;
import com.gamevision.model.entity.UserEntity;
import com.gamevision.model.servicemodels.UserServiceModel;
import com.gamevision.model.view.UserAdministrationViewModel;
import com.gamevision.model.view.UserProfileViewModel;
import com.gamevision.model.view.UserViewModel;

public interface UserService {
    boolean isUserNameFree(String username); //for register

    boolean isEmailFree(String email); //for register

    void registerAndLogin(UserRegisterBindingModel userRegisterBindingModel);

    //  UserServiceModel findByUsernameAndPassword(String username, String password);
    // void loginUser(Long id, String username);

    UserEntity findUserById(Long id);

   UserEntity findUserByUsername(String username); //fix this, use the one below only

    UserServiceModel getUserSmByUsername(String username);

    UserProfileViewModel getUserProfileViewModel(String username);

    UserViewModel getUserViewModelByUsername(String username);

    UserAdministrationViewModel getUserAdministrationViewModelByUsername(String username); //ViewModel for Admins (need to see roles and active status)
    //  void updateUser(UserEntity user);

    void initUsers();
}
