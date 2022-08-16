package com.gamevision.model.servicemodels;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class GameEditServiceModel {

    @NotBlank
    private String title;

    @NotBlank
    private String titleImageUrl;

    @NotBlank
    private String description;

    //private Set<GenreEntity> genres; //SM here as well?
    //check boxes -> List/Set of Strings
    @NotEmpty //checked first with.isEmpty() with the bindingResult check
    private List<String> genres;


    public GameEditServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public GameEditServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public GameEditServiceModel setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GameEditServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getGenres() {
        return genres;
    }

    public GameEditServiceModel setGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }
}
