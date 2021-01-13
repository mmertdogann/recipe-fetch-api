package com.mendix.recipe.fetch.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CategoryIsNotExistException extends Exception {

    public CategoryIsNotExistException(String message) {
        super(message);
    }
}
