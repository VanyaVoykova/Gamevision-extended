package com.gamevision.model.binding;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class GameAddBindingModel {

    @NotBlank
    @Size(min = 2, max = 50)
    private String title;

    @NotBlank
    private String titleImageUrl;

    @NotBlank
    @Size(min = 20)
    private String description;


    //check boxes -> List/Set of Strings
    // @NotEmpty the .isEmpty() check is with the bindingResult check
    private List<String> genres;


    //TODO get Principal separately, allow it to be null here for the initial binding
    // @NotNull //the admin who added the game - get by Principal id
    private String addedBy; //username


//TODO check if MM will add an empty Set<CommentEntity>
    //Comments are added in game view


//PLAYTHROUGH
//KISS, add only one playthrough for now
//Playthroughs are added in game view to keep add game simple and avoid dumping a lot of data all at once
//TODO: check MM will add an empty Set<PlaythroughEntity> when mapping to GameEntity in @Service

    //todo modify: remove adding a playthrough when adding a game, SRP

//  @Size(min = 10, max = 40) //E.g. Sarcastic Dragon Age II run
//  @NotBlank //just @NotBlank, it covers @NotNull
//  private String playthroughTitle;

//  @NotBlank
//  private String playthroughVideoUrl; //URL

//  @NotBlank
//  @Size(min = 10, max = 200) //E.g. Sarcastic Dragon Age II run
//  private String playthroughDescription;

    public GameAddBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public GameAddBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public GameAddBindingModel setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GameAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getGenres() {
        return genres;
    }

    public GameAddBindingModel setGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public GameAddBindingModel setAddedBy(String username) {
        this.addedBy = username;
        return this;
    }

    //  public String getPlaythroughTitle() {
    //      return playthroughTitle;
    //  }
//
    //  public GameAddBindingModel setPlaythroughTitle(String playthroughTitle) {
    //      this.playthroughTitle = playthroughTitle;
    //      return this;
    //  }
//
    //  public String getPlaythroughVideoUrl() {
    //      return playthroughVideoUrl;
    //  }
//
    //  public GameAddBindingModel setPlaythroughVideoUrl(String playthroughVideoUrl) {
    //      this.playthroughVideoUrl = playthroughVideoUrl;
    //      return this;
    //  }
//
    //  public String getPlaythroughDescription() {
    //      return playthroughDescription;
    //  }
//
    //  public GameAddBindingModel setPlaythroughDescription(String playthroughDescription) {
    //      this.playthroughDescription = playthroughDescription;
    //      return this;
    //  }
}
