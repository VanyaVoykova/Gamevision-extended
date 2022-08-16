package com.gamevision.repository;

import com.gamevision.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
   // Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameIgnoreCase(String username); //to prevent users registering with a duplicate name
    Optional<UserEntity> findByEmailIgnoreCase(String email); //to prevent users registering with a duplicate email

    Optional<UserEntity> findById(Long id);
}
