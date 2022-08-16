package com.gamevision.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "profile_pictures")
public class ProfilePicture extends BaseEntity {

    private String url; //TODO: size restrictions?
    @Column(name = "is_default") //if url is null/blank
    private boolean isDefault = true; //plonk default profile pic if user hasn't chosen one
    //private String publicId; //Security? TODO: check it out, add getters and setters if necessary


    public ProfilePicture() {
    }

    public String getUrl() {
        return url;
    }

    public ProfilePicture setUrl(String url) {
        this.url = url;
        return this;
    }

    //TODO: check if necessary
    public boolean isDefault() {
        return isDefault;
    }

    public ProfilePicture setDefault(boolean aDefault) {
        isDefault = aDefault;
        return this;
    }


}
