package com.gamevision.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This user is only a user already and cannot be demoted further.")
public class UserIsAlreadyOnlyUserException extends RuntimeException{
    private static final String MESSAGE = "This user is only a user already and cannot be demoted further.";
    public UserIsAlreadyOnlyUserException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage(){return MESSAGE;}
}
