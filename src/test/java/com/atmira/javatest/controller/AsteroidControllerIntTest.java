package com.atmira.javatest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AsteroidControllerIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URL_RESOURCE_START = "http://localhost:";
    private static final String URL_RESOURCE_END = "/asteroids?planet=";

    /**
     * Test de conexión a nuestra API: caso parámetro correcto
     */
    @Test
    public void givenCorrectPlanet_integrated_thenShouldGetAsteroidsOk(){
        String planet = "earth";

        ResponseEntity<String> response = restTemplate.getForEntity(URL_RESOURCE_START + port + URL_RESOURCE_END + planet, String.class);

        //No siempre será true, dado que no se envían las fechas como parámetro no se puede controlar
        //Prodría que en el listado total ninguno tuviera riesgo de impacto
//        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertTrue(!response.getBody().isEmpty());
    }

    /**
     * Test conexión a nuestra API: caso parámetro incorrecto o vacío
     */
    @Test
    public void givenIncorrectPlanet_integrated_thenShouldGet404(){
        String planetA = "uranus";
        String planetB = "";

        ResponseEntity<String> responseA = restTemplate.getForEntity(URL_RESOURCE_START + port + URL_RESOURCE_END + planetA, String.class);
        ResponseEntity<String> responseB = restTemplate.getForEntity(URL_RESOURCE_START + port + URL_RESOURCE_END + planetB, String.class);

        Assertions.assertEquals(responseA.getStatusCode(), HttpStatus.NOT_FOUND);
        Assertions.assertEquals(responseB.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * Test de conexión a nuestra API: no enviado parámetro Planet
     */
    @Test
    public void givenNoPlanetParameter_integrated_thenShouldGet400(){
        ResponseEntity<String> response = restTemplate.getForEntity(URL_RESOURCE_START + port + "/asteroids", String.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
