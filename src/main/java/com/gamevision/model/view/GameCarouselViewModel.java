package com.gamevision.model.view;

public class GameCarouselViewModel {
    private Long id;

    private String title;

    private String titleImageUrl;

    private String description;

    private boolean isFirst;

    public GameCarouselViewModel() {
    }

    public Long getId() {
        return id;
    }

    public GameCarouselViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GameCarouselViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public GameCarouselViewModel setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GameCarouselViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public GameCarouselViewModel setFirst(boolean first) {
        isFirst = first;
        return this;
    }
}
