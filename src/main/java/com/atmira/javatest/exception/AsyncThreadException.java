package com.atmira.javatest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AsyncThreadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AsyncThreadException() {
        super("Async thread interrupted or aborted.");
    }
}
