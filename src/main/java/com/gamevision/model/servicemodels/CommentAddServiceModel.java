package com.gamevision.model.servicemodels;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CommentAddServiceModel {
    //Author name is received from @AuthenticationPrincipal user details in controller

    @NotBlank
    private String authorName;
    //Game id is received from the URL
    private Long gameId;

    @NotBlank
    @Size(min = 10, max = 3000)
    private String text;

    //int likes: added directly in the service
//dateTimeCreated; added in service


    public CommentAddServiceModel() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public CommentAddServiceModel setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public Long getGameId() {
        return gameId;
    }

    public CommentAddServiceModel setGameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }

    public String getText() {
        return text;
    }

    public CommentAddServiceModel setText(String text) {
        this.text = text;
        return this;
    }

}
