package com.gamevision.repository;

import com.gamevision.model.entity.PlaythroughEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaythroughRepository extends JpaRepository<PlaythroughEntity, Long> {
    @Modifying
    void deleteById(Long id);
}
