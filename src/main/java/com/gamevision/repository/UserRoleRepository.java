package com.gamevision.repository;

import com.gamevision.model.entity.UserRoleEntity;
import com.gamevision.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByName(UserRoleEnum nameEnum);
    List<UserRoleEntity> findAll();
}
