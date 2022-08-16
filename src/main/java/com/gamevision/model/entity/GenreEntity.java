package com.gamevision.model.entity;

import com.gamevision.model.enums.GenreNameEnum;

import javax.persistence.*;

@Entity
@Table(name = "genres")
public class GenreEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenreNameEnum name;

    public GenreEntity() {
    }

    public GenreNameEnum getName() {
        return name;
    }

    public GenreEntity setName(GenreNameEnum name) {
        this.name = name;
        return this;
    }
}
