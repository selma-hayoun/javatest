package com.atmira.javatest.service;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.util.NasaDummyDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AsteroidServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(AsteroidServiceIntegrationTest.class);
    private String planet;
    private NasaDummyDataUtil nasaDummyDataUtil;

    @Autowired
    private AsteroidServiceI asteroidService;

    @BeforeEach
    public void setup() {
        nasaDummyDataUtil = new NasaDummyDataUtil();
        LOG.info("@BeforeEach - executes once before each test method in AsteroidServiceIntegrationTest");
    }

    @Test
    public void whenCallingFindAllAsteroids_integrated_thenShouldReturnCorrectList() throws Exception {
        //Asignamos el planeta de la petición
        planet = "Earth";

        //Fechas límite de la petición
        LocalDate dateStart = LocalDate.parse("2020-09-09");
        LocalDate dateEnd = LocalDate.parse("2020-09-16");

        //Datos esperados
        List<AsteroidDTO> filteredAsteroidsDTOExpected = new ArrayList<>();
        filteredAsteroidsDTOExpected = (List<AsteroidDTO>) nasaDummyDataUtil.getNasaResponseDummyData("asteroids_test_result.json", Optional.of(filteredAsteroidsDTOExpected)).get();

        assertThat(asteroidService.findAllAsteroids(planet, dateStart, dateEnd)).isEqualTo(filteredAsteroidsDTOExpected);
    }


}
