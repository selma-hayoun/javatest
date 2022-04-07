package com.atmira.javatest.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.model.NearEarthObjects;
import com.atmira.javatest.service.AsteroidServiceI;
import com.atmira.javatest.util.NasaDummyDataUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AsteroidsControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(AsteroidsControllerTest.class);
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
//        filteredAsteroidsDTO = getNasaResponseDummyData("asteroids_test_result.json");
        filteredAsteroidsDTO = (List<AsteroidDTO>) nasaDummyDataUtil.getNasaResponseDummyData("asteroids_test_result.json", Optional.of(filteredAsteroidsDTO)).get();

        //Ahora mockeamos para cuando haga la llamada con el servicio, devuelva lo que queramos
        when(asteroidService.findAllAsteroids(anyString(), any(), any())).thenReturn(filteredAsteroidsDTO);

        LOG.info("@BeforeAll - executes once before all test methods in AsteroidsControllerTest");
    }

    //getAsteroids() from AsteroidsController
    @Test
    public void givenCorrectPlanetParameter_whenCallingGetAsteroids_thenShouldReturnCorrectResponse() throws Exception {
        //Asignamos el planeta de la petición
        planet = "Earth";

        //ASSERT
        assertThat(asteroidsController.getAsteroids(planet)).isEqualTo(ResponseEntity.ok(filteredAsteroidsDTO));
    }


//    //DEPURAR: Los dos métodos en uno
//    private List<AsteroidDTO> getNasaResponseDummyData(String resourceName) {
//
//        ClassLoader classLoader = getClass().getClassLoader();
//
//        File file = null;
//        List<AsteroidDTO> apiDataFiltered = null;
//
//        //Parseamos el objeto del json
//        try {
////            file = resource.getFile();
//            file = new File(classLoader.getResource(resourceName).getFile());
//            apiDataFiltered  = objectMapper.readValue(file, new TypeReference<List<AsteroidDTO>>(){});
//        } catch (IOException e) {
//            LOG.info("whenCallingApiCall_thenShouldReturnCorrectObject() ERROR - " + e.getMessage());
//        }
//
//        return apiDataFiltered;
//    }

}
