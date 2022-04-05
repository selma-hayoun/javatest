package com.atmira.javatest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotSupportedPlanet extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotSupportedPlanet(String s) {
        super(s.isEmpty()?"Planet name required":"No Data Collected for planet: " + s);
    }
}
