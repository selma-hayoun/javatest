package com.atmira.javatest.controller;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.exception.ApiError;
import com.atmira.javatest.exception.NotAsteroidsFound;
import com.atmira.javatest.exception.NotSupportedPlanet;
import com.atmira.javatest.service.AsteroidServiceI;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("asteroids")
public class AsteroidsController {

    @Autowired
    private AsteroidServiceI asteroidService;

    @ApiOperation(value = "getAsteroids"
            ,notes = "Get the 3 bigger potentially hazardous asteroids from NASA API Asteroids - NeoWs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Resources obtained correctly", response = AsteroidDTO.class, responseContainer = "List" ),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Unexpected error") })
    @GetMapping()
    public ResponseEntity<?> getAsteroids(@RequestParam(name = "planet") String planet) {
        if(planet.isEmpty() || !planet.equalsIgnoreCase("EARTH")){
            throw new NotSupportedPlanet(planet);
        }

        LocalDate dateStart = LocalDate.now();
        LocalDate dateEnd = dateStart.plusDays(7);

        List<AsteroidDTO> filteredAsteroidsDTO = asteroidService.findAllAsteroids(planet, dateStart, dateEnd);

        if (filteredAsteroidsDTO.isEmpty()) {
            throw new NotAsteroidsFound();
        } else {
            return ResponseEntity.ok(filteredAsteroidsDTO);
        }
    }
}
