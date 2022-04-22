package com.atmira.javatest.controller;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.exception.NotAsteroidsFoundException;
import com.atmira.javatest.exception.NotSupportedPlanetException;
import com.atmira.javatest.service.AsteroidServiceI;
import com.atmira.javatest.util.NasaDummyDataUtil;
import com.atmira.javatest.utils.JavaTestConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
class AsteroidsControllerTest {

    private String planet;
    private AsteroidServiceI asteroidService;
    private AsteroidsController asteroidsController;
    private List<AsteroidDTO> filteredAsteroidsDTO;
    private NasaDummyDataUtil nasaDummyDataUtil;

    @BeforeEach
    public void setup() {
        nasaDummyDataUtil = new NasaDummyDataUtil();
        filteredAsteroidsDTO = new ArrayList<>();

        //Mockeamos el servicio
        asteroidService = Mockito.mock(AsteroidServiceI.class);

        //Seteamos el servicio mockeado a utilizar
        asteroidsController = new AsteroidsController();
        asteroidsController.setAsteroidService(asteroidService);

        //Datos del servicio mockeado
        filteredAsteroidsDTO = (List<AsteroidDTO>) nasaDummyDataUtil.getNasaResponseDummyData("asteroids_test_result.json", Optional.of(filteredAsteroidsDTO)).get();

        //Ahora mockeamos para cuando haga la llamada con el servicio, devuelva lo que queramos
        when(asteroidService.findAllAsteroids(anyString(), any(), any())).thenReturn(filteredAsteroidsDTO);

        log.info("@BeforeEach - executes once before each test method in AsteroidsControllerTest");
    }

    /**
     * Test método getAsteroids() del controlador dado planeta correcto en fechas correctas
     *
     * @throws Exception
     */
    @Test
    void givenCorrectPlanetParameter_whenCallingGetAsteroids_thenShouldReturnCorrectResponse() throws Exception {
        //Asignamos el planeta de la petición
        planet = JavaTestConstants.STR_PLANET_EARTH;

        assertThat(asteroidsController.getAsteroids(planet)).isEqualTo(ResponseEntity.ok(filteredAsteroidsDTO));
    }


    /**
     * Test método getAsteroids() del controlador: caso de lanzamiento de excepción NotSupportedPlanetException
     *
     * Planeta suministrado no es Earth, único del cual suministra datos la API consultada
     *
     * @throws Exception
     */
    @Test
    void givenNotSupportedPlanet_whenCallingGetAsteroids_thenExpectedNotSupportedPlanetException() throws Exception {
        planet = "Uranus";

        NotSupportedPlanetException thrown = Assertions.assertThrows(NotSupportedPlanetException.class, () -> {
            asteroidsController.getAsteroids(planet);
        }, "NotSupportedPlanetException was expected");

        Assertions.assertEquals("No Data Collected for planet: ".concat(planet), thrown.getMessage());
    }

    /**
     * Test método getAsteroids() del controlador: caso de lanzamiento de excepción NotSupportedPlanetException
     *
     * Suministrado parámetro vacío
     *
     * @throws Exception
     */
    @Test
    void givenEmptyPlanet_whenCallingGetAsteroids_thenExpectedNotSupportedPlanetException() throws Exception {
        planet = JavaTestConstants.STR_EMPTY;

        NotSupportedPlanetException thrown = Assertions.assertThrows(NotSupportedPlanetException.class, () -> {
            asteroidsController.getAsteroids(planet);
        }, "NotSupportedPlanetException was expected");

        Assertions.assertEquals("Planet name required", thrown.getMessage());
    }

    /**
     * Test método getAsteroids() del controlador: caso de lanzamiento de excepción NotAsteroidsFoundException
     *
     *
     * @throws Exception
     */
    @Test
    void givenPlanetWithNoHazardousAsteroids_whenCallingGetAsteroids_thenExpectedNotAsteroidsFoundException() throws Exception {
        planet = JavaTestConstants.STR_PLANET_EARTH;
        List<AsteroidDTO> emptyList = new ArrayList<>();

        //Ahora mockeamos para cuando haga la llamada con el servicio, devuelva lo que queramos
        when(asteroidService.findAllAsteroids(anyString(), any(), any())).thenReturn(emptyList);

        NotAsteroidsFoundException thrown = Assertions.assertThrows(NotAsteroidsFoundException.class, () -> {
            asteroidsController.getAsteroids(planet);
        }, "NotAsteroidsFoundException was expected");

        Assertions.assertEquals("No asteroids found that match the requirements", thrown.getMessage());
    }


}
