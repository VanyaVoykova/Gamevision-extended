package com.gamevision.repository;

import com.gamevision.model.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    //no need to add findAll explicitly, can be used right away

    Optional<GameEntity> findByTitle(String title);

    @Modifying
    void deleteById(Long id);
 }
