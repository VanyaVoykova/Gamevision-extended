package com.gamevision.model.view;


import java.util.List;


public class GameViewModel {
   private Long id; //we have it from the controller/url

    private String title;

    private String titleImageUrl;

    private String description;

    private List<String> genres;

    private List<PlaythroughViewModel> playthroughs;

    private List<CommentViewModel> comments;

    public GameViewModel() {
    }

    public String getTitle() {
        return title;
    }

    public GameViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public GameViewModel setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GameViewModel setDescription(String description) {
        this.description = description;
        return this;
    }


    public Long getId() {
        return id;
    }

    public GameViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public List<String> getGenres() {
        return genres;
    }

    public GameViewModel setGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    public List<PlaythroughViewModel> getPlaythroughs() {
        return playthroughs;
    }

    public GameViewModel setPlaythroughs(List<PlaythroughViewModel> playthroughs) {
        this.playthroughs = playthroughs;
        return this;
    }

    public List<CommentViewModel> getComments() {
        return comments;
    }

    public GameViewModel setComments(List<CommentViewModel> comments) {
        this.comments = comments;
        return this;
    }
}
