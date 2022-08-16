package com.gamevision.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A game with that title already exists.")
public class GameTitleExistsException extends RuntimeException { //NonUniqueResultException
  private static final String MESSAGE = "A game with that title already exists.";
    public GameTitleExistsException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage(){return MESSAGE;}
}
