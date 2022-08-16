package com.gamevision.model.view;

import java.time.LocalDateTime;

public class CommentViewModel {
    private Long id; //commment id
    private String authorUsername; //just author's username, so we can easily display the author's name, i/o just having the author's id
    private String text;
    private int likesCounter;
    private String dateTimeCreated; //to be parsed in comments.js

    public CommentViewModel() {
    }

    public CommentViewModel(Long id, String authorUsername, String text, int likesCounter, String dateTimeCreated) {
        this.id = id;
        this.authorUsername = authorUsername;
        this.text = text;
        this.likesCounter = likesCounter;
        this.dateTimeCreated = dateTimeCreated;
    }

    public Long getId() {
        return id;
    }

    public CommentViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public CommentViewModel setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }


    public String getText() {
        return text;
    }

    public CommentViewModel setText(String text) {
        this.text = text;
        return this;
    }

    public int getLikesCounter() {
        return likesCounter;
    }

    public CommentViewModel setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
        return this;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public CommentViewModel setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
        return this;
    }
}
