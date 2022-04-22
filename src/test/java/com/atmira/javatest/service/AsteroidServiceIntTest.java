package com.atmira.javatest.service;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.util.NasaDummyDataUtil;
import com.atmira.javatest.utils.JavaTestConstants;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@SpringBootTest
public class AsteroidServiceIntTest {

    private String planet;
    private NasaDummyDataUtil nasaDummyDataUtil;

    @Autowired
    private AsteroidServiceI asteroidService;

    @BeforeEach
    public void setup() {
        nasaDummyDataUtil = new NasaDummyDataUtil();
        log.info("@BeforeEach - executes once before each test method in AsteroidServiceIntegrationTest");
    }

    /**
     * Test del servicio AsteroidServiceI: datos correctos
     *
     * @throws Exception
     */
    @Test
    public void whenCallingFindAllAsteroids_integrated_thenShouldReturnCorrectList() throws Exception {
        //Asignamos el planeta de la petición
        planet = JavaTestConstants.STR_PLANET_EARTH;

        //Fechas límite de la petición
        LocalDate dateStart = LocalDate.parse(JavaTestConstants.TESTS_STR_START_DATE);
        LocalDate dateEnd = LocalDate.parse(JavaTestConstants.TESTS_STR_END_DATE);

        //Datos esperados
        List<AsteroidDTO> filteredAsteroidsDTOExpected = new ArrayList<>();
        filteredAsteroidsDTOExpected = (List<AsteroidDTO>) nasaDummyDataUtil.getNasaResponseDummyData("asteroids_test_result.json", Optional.of(filteredAsteroidsDTOExpected)).get();

        assertThat(asteroidService.findAllAsteroids(planet, dateStart, dateEnd)).isEqualTo(filteredAsteroidsDTOExpected);
    }


}
