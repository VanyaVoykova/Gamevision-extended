package com.gamevision.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "games")
public class GameEntity extends BaseEntity {

    @NotBlank
    @Size(min = 2, max = 50)
    private String title;

    @NotBlank
    @Column(name = "title_image_url")
    private String titleImageUrl;

    @Column(nullable = false)
    @Lob
    @Size(min = 20)
    private String description;

    //Genres are not that many, so we can go with EAGER
    @ManyToMany(fetch = FetchType.EAGER)    //a game can belong to many genres
    @Column(nullable = false)
    private Set<GenreEntity> genres;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    //so we can easily view all playthroughs available for a game; leave it lazy for view purposes, draw the specific walkthrough when user clicks on it
    private Set<PlaythroughEntity> playthroughs;

    @ManyToOne
    @NotNull
    private UserEntity addedBy;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    //One game can have many comments
    private Set<CommentEntity> comments; //got to use GameCommentEntity, no polymorphism possible with JPA, got to have separate entities

    //todo: rating (with stars)


    public GameEntity() {
    }


    public String getDescription() {
        return description;
    }

    public GameEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GameEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public GameEntity setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
        return this;
    }

    public Set<GenreEntity> getGenres() {
        return genres;
    }

    public GameEntity setGenres(Set<GenreEntity> genres) {
        this.genres = genres;
        return this;
    }

    public Set<PlaythroughEntity> getPlaythroughs() {
        return playthroughs;
    }

    public GameEntity setPlaythroughs(Set<PlaythroughEntity> playthroughs) {
        this.playthroughs = playthroughs;
        return this;
    }

    public UserEntity getAddedBy() {
        return addedBy;
    }

    public GameEntity setAddedBy(UserEntity addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public GameEntity setComments(Set<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }
}
