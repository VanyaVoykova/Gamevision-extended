package com.gamevision.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "playthroughs")
public class PlaythroughEntity extends BaseEntity {


    @Size(min = 10, max = 40) //E.g. Sarcastic Dragon Age II run
    @Column(nullable = false)
    private String title;


    @NotBlank
    @Column(name="video_url", nullable = false)
    private String videoUrl;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 10, max = 200) //E.g. Sarcastic Dragon Age II run
    private String description;

    @ManyToOne
    private UserEntity addedBy;

    //TODO: for when comments for playthroughs are implemented
///   @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
/// private Set<CommentEntity> comments;



//   @PositiveOrZero
//   @Column(name = "likes")
//   private Integer likesCounter;





    public PlaythroughEntity() {
    }

    public String getTitle() {
        return title;
    }

    public PlaythroughEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public PlaythroughEntity setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public UserEntity getAddedBy() {
        return addedBy;
    }

    public PlaythroughEntity setAddedBy(UserEntity addedBy) {
        this.addedBy = addedBy;
        return this;
    }

 // public Set<CommentEntity> getComments() {
 //     return comments;
 // }

 // public PlaythroughEntity setComments(Set<CommentEntity> comments) {
 //     this.comments = comments;
 //     return this;
 // }

    public String getDescription() {
        return description;
    }

    public PlaythroughEntity setDescription(String description) {
        this.description = description;
        return this;
    }

 //  public Integer getLikesCounter() {
 //      return likesCounter;
 //  }

 //  public PlaythroughEntity setLikesCounter(Integer likesCounter) {
 //      this.likesCounter = likesCounter;
 //      return this;
 //  }


}
