package com.mendix.recipe.fetch.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RecipeValidationException extends Exception {

    public RecipeValidationException(String message) {
        super(message);
    }
}
