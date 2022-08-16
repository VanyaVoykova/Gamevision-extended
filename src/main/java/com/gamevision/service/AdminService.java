package com.gamevision.service;

import com.gamevision.model.entity.UserEntity;
import com.gamevision.model.view.UserAdministrationViewModel;
import com.gamevision.model.view.UserViewModel;

public interface AdminService {
    UserAdministrationViewModel promoteUserToAdmin(String username);
    UserAdministrationViewModel demoteUserToUser(String username);

    UserAdministrationViewModel banUser(String username);
    UserAdministrationViewModel unbanUser(String username);


}
