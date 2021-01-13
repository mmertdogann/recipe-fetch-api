package com.mendix.recipe.fetch.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class TitleAlreadyExistsException extends Exception {

    public TitleAlreadyExistsException(String message) {
        super(message);
    }
}
