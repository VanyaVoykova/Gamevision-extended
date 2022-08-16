package com.gamevision.model.entity;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @ManyToOne
    private UserEntity author;

  // @Size(min = 3, max = 30)
  // private String title; //optional

    @Column(nullable = false)
    @Size(min = 10, max = 3000)
    private String text;

    @PositiveOrZero
    private int likesCounter;

    @Column(name = "created", nullable = false)
    private LocalDateTime dateTimeCreated;

    public CommentEntity() {
    }

    public UserEntity getAuthor() {
        return author;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

   // public String getTitle() {
   //     return title;
   // }
//
   // public CommentEntity setTitle(String title) {
   //     this.title = title;
   //     return this;
   // }

    public String getText() {
        return text;
    }

    public CommentEntity setText(String text) {
        this.text = text;
        return this;
    }

    public int getLikesCounter() {
        return likesCounter;
    }

    public CommentEntity setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
        return this;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public CommentEntity setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
        return this;
    }
}

