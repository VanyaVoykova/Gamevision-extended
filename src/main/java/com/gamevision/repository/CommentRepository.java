package com.gamevision.repository;

import com.gamevision.model.entity.CommentEntity;
import com.gamevision.model.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
   // List<CommentEntity> findAllByGame(GameEntity game); <- error - after refactoring CommentEntity no longer holds a reference to game
}