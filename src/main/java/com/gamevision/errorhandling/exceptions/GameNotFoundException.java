package com.gamevision.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Game not found.") //Add to have a decent response and not just 500..
public class GameNotFoundException extends RuntimeException {
private static final String MESSAGE = "Game not found.";

    public GameNotFoundException() {
        super(MESSAGE);
    }

}
