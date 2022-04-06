package com.atmira.javatest.controller;

import static org.assertj.core.api.Assertions.*;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.service.AsteroidServiceI;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AsteroidsControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(AsteroidsControllerTest.class);
    private Resource resource;
    private String planet;
    private static ObjectMapper objectMapper;
    private ResourceLoader resourceLoader;

    @Autowired
    private AsteroidServiceI asteroidService;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        LOG.info("@BeforeAll - executes once before all test methods in AsteroidsControllerTest");
    }

    //getAsteroids() from AsteroidsController
    @Test
    public void givenCorrectPlanetParameter_whenCallingGetAsteroids_thenShouldReturnCorrectResponse() throws Exception {
        //Accedemos a una llamada registrada
        resourceLoader = Mockito.mock(ResourceLoader.class);
        resource = resourceLoader.getResource("src/test/resources/asteroids_test_result.json");

        File file = null;
        List<AsteroidDTO> asteroidsDTOTest = null;

        //Parseamos el objeto del json
        try {
            file = resource.getFile();
            asteroidsDTOTest  = objectMapper.readValue(file, new TypeReference<List<AsteroidDTO>>(){});
        } catch (IOException e) {
            LOG.info("givenCorrectPlanetParameter_whenCallingGetAsteroids_thenShouldReturnCorrectResponse() ERROR - " + e.getMessage());
        }

        //ASSERT

    }

}
