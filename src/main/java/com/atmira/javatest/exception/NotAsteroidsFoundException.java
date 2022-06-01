package com.atmira.javatest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotAsteroidsFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotAsteroidsFoundException() {
        super("No asteroids found that match the requirements");
        log.warn("No asteroids found that match the requirements");
    }
}
