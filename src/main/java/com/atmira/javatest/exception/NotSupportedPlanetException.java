package com.atmira.javatest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotSupportedPlanetException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotSupportedPlanetException(String s) {
        super(s.isEmpty()?"Planet name required":"No Data Collected for planet: " + s);
        log.error(s.isEmpty()?"Planet name required":"No Data Collected for planet: " + s);
    }
}
