package com.gamevision.model.view;

import java.util.List;
import java.util.Set;

public class PlaythroughViewModel {
    private Long id;
  //  private String gameTitle; //shouldn't be necessary we get these directly from the GameEntity's collection
    private String title;
    private String videoUrl;
    private String description;
    private int likesCounter;
    private List<CommentViewModel> comments;

    public PlaythroughViewModel() {
    }

    public Long getId() {
        return id;
    }

    public PlaythroughViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PlaythroughViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public PlaythroughViewModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PlaythroughViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getLikesCounter() {
        return likesCounter;
    }

    public PlaythroughViewModel setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
        return this;
    }

    public List<CommentViewModel> getComments() {
        return comments;
    }

    public PlaythroughViewModel setComments(List<CommentViewModel> comments) {
        this.comments = comments;
        return this;
    }

   //public String getGameTitle() {
   //    return gameTitle;
   //}

   //public PlaythroughViewModel setGameTitle(String gameTitle) {
   //    this.gameTitle = gameTitle;
   //    return this;
   //}
}
