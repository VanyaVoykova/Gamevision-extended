package com.gamevision.model.view;

public class GenreViewModel {
    private String name;

    public GenreViewModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public GenreViewModel setName(String name) {
        this.name = name;
        return this;
    }
}
