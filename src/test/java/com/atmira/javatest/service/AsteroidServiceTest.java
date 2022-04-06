package com.atmira.javatest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.model.Asteroid;
import com.atmira.javatest.model.NearEarthObjects;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AsteroidServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(AsteroidServiceTest.class);
    private String planet;
    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    private AsteroidServiceImpl asteroidService;

    private ResponseEntity<NearEarthObjects> nearEarthObjectsResponseEntity;

    @BeforeEach
    public void setup() {
        asteroidService = new AsteroidServiceImpl();

        objectMapper = new ObjectMapper();
        restTemplate = Mockito.mock(RestTemplate.class);

        asteroidService.setRestTemplate(restTemplate);

        //Preparamos objeto del tipo vacío con OK
        nearEarthObjectsResponseEntity=new ResponseEntity<NearEarthObjects>(HttpStatus.OK);

        LOG.info("@BeforeAll - executes once before all test methods in AsteroidServiceITest");
    }

    //apiCall method from AsteroidServiceImpl
    @Test
    public void whenCallingApiCall_thenShouldReturnCorrectObject() throws Exception {

        //Fechas límite de la petición
        LocalDate dateStart = LocalDate.parse("2020-09-09");
        LocalDate dateEnd = LocalDate.parse("2020-09-16");

        String API_REQUEST = asteroidService.API_REQUEST_ENDPOINT + "?" + asteroidService.API_PARAMETER_START_DATE + "=" + dateStart
                + "&" + asteroidService.API_PARAMETER_END_DATE + "=" + dateEnd + "&" + asteroidService.API_PARAMETER_KEY + "=" + asteroidService.API_KEY;

        getNasaResponseDummyData();

        when(restTemplate.getForEntity(API_REQUEST, NearEarthObjects.class)).thenReturn(nearEarthObjectsResponseEntity);

        assertThat(asteroidService.apiCall(dateStart, dateEnd)).isEqualTo(nearEarthObjectsResponseEntity.getBody());
    }


    private void getNasaResponseDummyData() {
        //Accedemos a una llamada registrada
//        resource = resourceLoader.getResource("src/test/data/nasa_response_json.json");

        String resourceName = "nasa_response_json.json";

        ClassLoader classLoader = getClass().getClassLoader();

        File file = null;
        NearEarthObjects apiDataFiltered = null;

        //Parseamos el objeto del json
        try {
//            file = resource.getFile();
            file = new File(classLoader.getResource(resourceName).getFile());
            apiDataFiltered  = objectMapper.readValue(file, NearEarthObjects.class);
        } catch (IOException e) {
            LOG.info("whenCallingApiCall_thenShouldReturnCorrectObject() ERROR - " + e.getMessage());
        }

        //Seteamos el valor esperado de petición a la api de la nasa con el contenido de nuestro json para pruebas
        nearEarthObjectsResponseEntity = ResponseEntity.ok(apiDataFiltered);
    }


    //DEPURAR: Los dos métodos en uno
    private List<AsteroidDTO> getNasaResponseDummyData(String resourceName) {

        ClassLoader classLoader = getClass().getClassLoader();

        File file = null;
        List<AsteroidDTO> apiDataFiltered = null;

        //Parseamos el objeto del json
        try {
//            file = resource.getFile();
            file = new File(classLoader.getResource(resourceName).getFile());
            apiDataFiltered  = objectMapper.readValue(file, new TypeReference<List<AsteroidDTO>>(){});
        } catch (IOException e) {
            LOG.info("whenCallingApiCall_thenShouldReturnCorrectObject() ERROR - " + e.getMessage());
        }

        return apiDataFiltered;
    }


    //findAllAsteroids method from AsteroidServiceImpl
    @Test
    public void whenCallingFindAllAsteroids_thenShouldReturnCorrectList() throws Exception {
        //Asignamos el planeta de la petición
        planet = "Earth";

        //Fechas límite de la petición
        LocalDate dateStart = LocalDate.parse("2020-09-09");
        LocalDate dateEnd = LocalDate.parse("2020-09-16");

        String API_REQUEST = asteroidService.API_REQUEST_ENDPOINT + "?" + asteroidService.API_PARAMETER_START_DATE + "=" + dateStart
                + "&" + asteroidService.API_PARAMETER_END_DATE + "=" + dateEnd + "&" + asteroidService.API_PARAMETER_KEY + "=" + asteroidService.API_KEY;

        getNasaResponseDummyData();

        //Para que el método apiCall tenga datos
        when(restTemplate.getForEntity(API_REQUEST, NearEarthObjects.class)).thenReturn(nearEarthObjectsResponseEntity);

        assertThat(asteroidService.findAllAsteroids(planet, dateStart, dateEnd)).isEqualTo(getNasaResponseDummyData("asteroids_test_result.json"));
    }

}
