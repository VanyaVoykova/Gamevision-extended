package com.gamevision.model.binding;

import java.util.List;

public class GenresFormDTO {
    private List<String> genres;

    public GenresFormDTO() {
    }

    public List<String> getGenres() {
        return genres;
    }

    public GenresFormDTO setGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }
}
