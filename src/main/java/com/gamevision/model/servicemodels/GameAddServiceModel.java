package com.gamevision.model.servicemodels;

//Returned by addGame in GameServiceImpl so Controller and logger get their data
public class GameAddServiceModel {
    private Long id; //needed by controller to compose the URL

    //these two are needed by the Logger to display what title is added by whom

    private String title;

    private String addedBy; //just username here


    public GameAddServiceModel() {
    }


    public Long getId() {
        return id;
    }

    public GameAddServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GameAddServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }


    public String getAddedBy() {
        return addedBy;
    }

    public GameAddServiceModel setAddedBy(String username) {
        this.addedBy = username;
        return this;
    }


}
