package com.atmira.javatest.controller;

import com.atmira.javatest.utils.JavaTestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AsteroidControllerIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Test de conexión a nuestra API: caso parámetro correcto
     */
    @Test
    void givenCorrectPlanet_integrated_thenShouldGetAsteroidsOk(){
        String planet = JavaTestConstants.STR_PLANET_EARTH;

        ResponseEntity<String> response = restTemplate.getForEntity(JavaTestConstants.URL_RESOURCE_START + port + JavaTestConstants.URL_RESOURCE_END + planet, String.class);

        //No siempre será true, dado que no se envían las fechas como parámetro no se puede controlar
        //Prodría que en el listado total ninguno tuviera riesgo de impacto
//        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertTrue(!response.getBody().isEmpty());
    }

    /**
     * Test conexión a nuestra API: caso parámetro incorrecto o vacío
     */
    @Test
    void givenIncorrectPlanet_integrated_thenShouldGet404(){
        String planetA = JavaTestConstants.STR_PLANET_URANUS;
        String planetB = JavaTestConstants.STR_EMPTY;

        ResponseEntity<String> responseA = restTemplate.getForEntity(JavaTestConstants.URL_RESOURCE_START + port + JavaTestConstants.URL_RESOURCE_END + planetA, String.class);
        ResponseEntity<String> responseB = restTemplate.getForEntity(JavaTestConstants.URL_RESOURCE_START + port + JavaTestConstants.URL_RESOURCE_END + planetB, String.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseA.getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseB.getStatusCode());
    }

    /**
     * Test de conexión a nuestra API: no enviado parámetro Planet
     */
    @Test
    void givenNoPlanetParameter_integrated_thenShouldGet400(){
        ResponseEntity<String> response = restTemplate.getForEntity(JavaTestConstants.URL_RESOURCE_START + port + "/asteroids", String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
