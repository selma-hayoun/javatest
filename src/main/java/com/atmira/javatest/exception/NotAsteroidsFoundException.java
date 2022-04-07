package com.atmira.javatest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotAsteroidsFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotAsteroidsFoundException() {
        super("No asteroids found that match the requirements");
    }
}
