package com.gamevision.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Missing genres")
public class MissingGenresException extends RuntimeException{
    public MissingGenresException(String message) {
        super(message);
    }
}
