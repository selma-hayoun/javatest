package com.atmira.javatest.controller;

import com.atmira.javatest.exception.NotSupportedPlanet;
import com.atmira.javatest.service.AsteroidServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("asteroids")
public class AsteroidsController {

    @Autowired
    private AsteroidServiceI asteroidService;

    @GetMapping()
    public ResponseEntity<?> getAsteroids(@RequestParam(name = "planet") String planet) {
        if(planet.isEmpty() || planet.equalsIgnoreCase("EARTH")){
            throw new NotSupportedPlanet(planet);
        }

        LocalDate dateStart = LocalDate.now();
        LocalDate dateEnd = dateStart.plusDays(7);

        asteroidService.findAllAsteroids(dateStart, dateEnd);

        return null;
    }
}
