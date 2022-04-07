package com.atmira.javatest.controller;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.exception.NotAsteroidsFoundException;
import com.atmira.javatest.exception.NotSupportedPlanetException;
import com.atmira.javatest.service.AsteroidServiceI;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("")
public class AsteroidsController {

    @Autowired
    @Setter
    private AsteroidServiceI asteroidService;

    @ApiOperation(value = "getAsteroids",
            notes = "Get the 3 bigger potentially hazardous asteroids from NASA API Asteroids - NeoWs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Resources obtained correctly", response = AsteroidDTO.class, responseContainer = "List" ),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not Found: no asteroids found that match the requirements or no data collected for the given planet"),
            @ApiResponse(code = 500, message = "Unexpected error") })
    @RequestMapping(value = "asteroids",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<?> getAsteroids(@RequestParam(name = "planet") @ApiParam(name = "planet", value = "Planet name", example = "Earth") String planet) {
        if(planet.isEmpty() || !planet.equalsIgnoreCase("EARTH")){
            throw new NotSupportedPlanetException(planet);
        }

        LocalDate dateStart = LocalDate.now();
        LocalDate dateEnd = dateStart.plusDays(7);

        List<AsteroidDTO> filteredAsteroidsDTO = asteroidService.findAllAsteroids(planet, dateStart, dateEnd);

        if (filteredAsteroidsDTO.isEmpty()) {
            throw new NotAsteroidsFoundException();
        } else {
            return ResponseEntity.ok(filteredAsteroidsDTO);
        }
    }
}
