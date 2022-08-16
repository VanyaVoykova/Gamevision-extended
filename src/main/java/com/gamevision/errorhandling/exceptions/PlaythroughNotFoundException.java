package com.gamevision.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Playthrough not found.") //Add to have a decent response and not just 500..
public class PlaythroughNotFoundException extends RuntimeException{
    private static final String MESSAGE = "Playthrough not found.";

    public PlaythroughNotFoundException() {
        super(MESSAGE);
    }
}
