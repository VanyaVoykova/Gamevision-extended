package com.gamevision.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class GameEditBindingModel {
    @NotBlank
    @Size(min = 2, max = 50)
    private String title;

    @NotBlank
    private String titleImageUrl;

    @NotBlank
    @Size(min = 20)
    private String description;


    //check boxes -> List/Set of Strings
    @NotEmpty //checked first with.isEmpty() with the bindingResult check
    private List<String> genres;

    //No addedBy (user), no playthrough info


    public GameEditBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public GameEditBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public GameEditBindingModel setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GameEditBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getGenres() {
        return genres;
    }

    public GameEditBindingModel setGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }
}
