package com.gamevision.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This user is already admin.")
public class UserIsAlreadyAdminException extends RuntimeException{
    private static final String MESSAGE = "This user is already admin.";
    public UserIsAlreadyAdminException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage(){return MESSAGE;}
}
