package com.atmira.javatest.controller;

import com.atmira.javatest.service.AsteroidServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("asteroids")
public class AsteroidsController {

    @Autowired
    private AsteroidServiceI asteroidService;

    @GetMapping()
    public ResponseEntity<?> getAsteroids(@RequestParam(name = "planet") String planet) {

        asteroidService.findAllAsteroids();

        return null;
    }
}
