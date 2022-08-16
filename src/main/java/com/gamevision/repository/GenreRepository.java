package com.gamevision.repository;

import com.gamevision.model.entity.GenreEntity;
import com.gamevision.model.enums.GenreNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    GenreEntity findByName(GenreNameEnum genreName);
}
