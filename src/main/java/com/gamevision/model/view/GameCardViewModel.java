package com.gamevision.model.view;

import com.gamevision.model.entity.GenreEntity;

import java.util.List;
import java.util.Set;

public class GameCardViewModel {

    private Long id;

    private String title;

    private String titleImageUrl;

    private String description;

    private List<String> genres;

    public GameCardViewModel() {
    }

    public Long getId() {
        return id;
    }

    public GameCardViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GameCardViewModel setTitle(String title) {
        this.title = title;
        return this;
    }


    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public GameCardViewModel setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GameCardViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getGenres() {
        return genres;
    }

    public GameCardViewModel setGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }
}
