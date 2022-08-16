package com.gamevision.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class PlaythroughAddBindingModel {

    @Size(min = 10, max = 40) //E.g. Sarcastic Dragon Age II run
    @NotBlank
    private String title;

    @NotBlank
    private String videoUrl;

    @NotBlank
    @Size(min = 10, max = 200) //E.g. Sarcastic Dragon Age II run
    private String description;


    public PlaythroughAddBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public PlaythroughAddBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public PlaythroughAddBindingModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PlaythroughAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }


}
