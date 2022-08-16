package com.gamevision.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CommentBindingModel {

    //Author name is received from @AuthenticationPrincipal user details in controller
    //Game id is received from the URL

    @NotBlank
    @Size(min = 10, max = 3000)
    private String text;

    //add int likesCounter to 0 for the entity


    public CommentBindingModel() {
    }

    public CommentBindingModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public CommentBindingModel setText(String text) {
        this.text = text;
        return this;
    }

}
