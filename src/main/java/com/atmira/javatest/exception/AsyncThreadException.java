package com.atmira.javatest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AsyncThreadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AsyncThreadException() {
        super("Async thread interrupted or aborted.");
        log.error("Async thread interrupted or aborted.");
    }
}
