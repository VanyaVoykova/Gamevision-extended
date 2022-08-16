package com.gamevision.service.init;

import com.gamevision.model.entity.UserRoleEntity;
import com.gamevision.model.enums.UserRoleEnum;
import com.gamevision.repository.UserRoleRepository;
import com.gamevision.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void initUserRoles() {
        if (userRoleRepository.count() == 0) {
            for (UserRoleEnum roleName : UserRoleEnum.values()) {
                UserRoleEntity role = new UserRoleEntity();
                role.setName(roleName);
                userRoleRepository.save(role);
            }
        }
    }
}
