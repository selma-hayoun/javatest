package com.atmira.javatest.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.model.Asteroid;
import com.atmira.javatest.model.NearEarthObjects;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AsteroidServiceITest {

    private static final Logger LOG = LoggerFactory.getLogger(AsteroidServiceITest.class);
    private Resource resource;
    private String planet;
    private static ObjectMapper objectMapper;
    private ResourceLoader resourceLoader;

    @Autowired
    private AsteroidServiceImpl asteroidService;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        LOG.info("@BeforeAll - executes once before all test methods in AsteroidServiceITest");
    }

    //apiCall method from AsteroidServiceImpl
    @Test
    public void whenCallingApiCall_thenShouldReturnCorrectObject() throws Exception {
        //Accedemos a una llamada registrada
        resourceLoader = Mockito.mock(ResourceLoader.class);
        resource = resourceLoader.getResource("src/test/data/nasa_response_json.json");

        String resourceName = "src/test/resources/nasa_response_json.json";

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

        //Fechas límite de la petición
        LocalDate dateStart = LocalDate.parse("2020-09-09");
        LocalDate dateEnd = LocalDate.parse("2020-09-16");

        assertThat(asteroidService.apiCall(dateStart, dateEnd)).isEqualTo(apiDataFiltered);
    }

    //findAllAsteroids method from AsteroidServiceImpl
    @Test
    public void whenCallingFindAllAsteroids_thenShouldReturnCorrectList() throws Exception {
        //Asignamos el planeta de la petición
        planet = "Earth";
        //Accedemos a una llamada registrada
        resourceLoader = Mockito.mock(ResourceLoader.class);
        resource = resourceLoader.getResource("classpath:nasa_response_json.json");

        File file = null;
        NearEarthObjects apiDataFiltered = null;

        //Parseamos el objeto del json
        try {
            file = resource.getFile();
            apiDataFiltered  = objectMapper.readValue(file, NearEarthObjects.class);
        } catch (IOException e) {
            LOG.info("whenCallingFindAllAsteroids_thenShouldReturnCorrectList() ERROR - " + e.getMessage());
        }

        //Tratamiento de los datos extraídos del json
        List<Asteroid> filteredAsteroidsTest = new ArrayList<>();
        apiDataFiltered.getNear_earth_objects().values().stream().forEach(c -> filteredAsteroidsTest.addAll(c));

        List<AsteroidDTO> filteredAsteroidsDTOTest = filteredAsteroidsTest
                .stream()
                .filter(a -> a.is_potentially_hazardous_asteroid())
                .filter(a -> a.getClose_approach_data()[0].getOrbiting_body().equalsIgnoreCase(planet))
                .map(a -> new AsteroidDTO(
                        a.getName(),
                        (a.getEstimated_diameter().getKilometers().get("estimated_diameter_min") + a.getEstimated_diameter().getKilometers().get("estimated_diameter_max"))/2,
                        a.getClose_approach_data()[0].getRelative_velocity().get("kilometers_per_hour"),
                        new SimpleDateFormat("yyyy-MM-dd").format(a.getClose_approach_data()[0].getClose_approach_date()),
                        a.getClose_approach_data()[0].getOrbiting_body()
                ))
                .collect(Collectors.toList());

        //Ordenamos
        filteredAsteroidsDTOTest.sort(Comparator.comparing(AsteroidDTO::getDiameter).reversed());

        //Limitamos los 3 más grandes de diametro
        List<AsteroidDTO> filteredAsteroidsDTOExpected = filteredAsteroidsDTOTest.stream().limit(3).collect(Collectors.toList());

        //Fechas límite de la petición
        LocalDate dateStart = LocalDate.parse("2020-09-09");
        LocalDate dateEnd = LocalDate.parse("2020-09-16");

        assertThat(asteroidService.findAllAsteroids(planet, dateStart, dateEnd)).isEqualTo(filteredAsteroidsDTOExpected);
    }

}
