package com.atmira.javatest.service;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.model.NearEarthObjects;
import com.atmira.javatest.util.NasaDummyDataUtil;
import com.atmira.javatest.utils.JavaTestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AsteroidServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(AsteroidServiceTest.class);
    private String planet;
    private RestTemplate restTemplate;
    private AsteroidServiceImpl asteroidService;
    private ResponseEntity<NearEarthObjects> nearEarthObjectsResponseEntity;
    private NasaDummyDataUtil nasaDummyDataUtil;


    @BeforeEach
    public void setup() {
        asteroidService = new AsteroidServiceImpl();
        nasaDummyDataUtil = new NasaDummyDataUtil();
        restTemplate = Mockito.mock(RestTemplate.class);

        asteroidService.setRestTemplate(restTemplate);

        //Preparamos objeto del tipo vacío con OK
        nearEarthObjectsResponseEntity = new ResponseEntity<NearEarthObjects>(HttpStatus.OK);

        LOG.info("@BeforeEach - executes once before each test method in AsteroidServiceTest");
    }

    /**
     * Test método apiCall() de AsteroidServiceImpl
     *
     * @throws Exception
     */
    @Test
    public void whenCallingApiCall_thenShouldReturnCorrectObject() throws Exception {

        //Fechas límite de la petición
        LocalDate dateStart = LocalDate.parse("2020-09-09");
        LocalDate dateEnd = LocalDate.parse("2020-09-16");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(JavaTestConstants.API_REQUEST_ENDPOINT)
                .queryParam(JavaTestConstants.API_PARAMETER_START_DATE, dateStart)
                .queryParam(JavaTestConstants.API_PARAMETER_END_DATE, dateEnd);

        String uriBuilder = builder.build().encode().toUriString();
        nearEarthObjectsResponseEntity = ResponseEntity.ok( (NearEarthObjects) nasaDummyDataUtil.getNasaResponseDummyData("nasa_response_json.json", Optional.of(new NearEarthObjects())).get());

        //Para que el método apiCall tenga datos - simulamos la llamada con los datos del json
        when(restTemplate.getForEntity(uriBuilder, NearEarthObjects.class)).thenReturn(nearEarthObjectsResponseEntity);

        assertThat(asteroidService.apiCall(dateStart, dateEnd)).isEqualTo(nearEarthObjectsResponseEntity.getBody());
    }

    /**
     * Test método findAllAsteroids() de AsteroidServiceImpl
     *
     * Caso fechas y planeta para datos correctos
     *
     * @throws Exception
     */
    @Test
    public void whenCallingFindAllAsteroids_thenShouldReturnCorrectList() throws Exception {
        //Asignamos el planeta de la petición
        planet = "Earth";

        //Fechas límite de la petición
        LocalDate dateStart = LocalDate.parse("2020-09-09");
        LocalDate dateEnd = LocalDate.parse("2020-09-16");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(JavaTestConstants.API_REQUEST_ENDPOINT)
                .queryParam(JavaTestConstants.API_PARAMETER_START_DATE, dateStart)
                .queryParam(JavaTestConstants.API_PARAMETER_END_DATE, dateEnd);

        String uriBuilder = builder.build().encode().toUriString();
        nearEarthObjectsResponseEntity = ResponseEntity.ok( (NearEarthObjects) nasaDummyDataUtil.getNasaResponseDummyData("nasa_response_json.json", Optional.of(new NearEarthObjects())).get());

        //Para que el método apiCall tenga datos - simulamos la llamada con los datos del json
        when(restTemplate.getForEntity(uriBuilder, NearEarthObjects.class)).thenReturn(nearEarthObjectsResponseEntity);

        List<AsteroidDTO> asteroidDTOListExpected = new ArrayList<>();
        asteroidDTOListExpected = (List<AsteroidDTO>) nasaDummyDataUtil.getNasaResponseDummyData("asteroids_test_result.json", Optional.of(asteroidDTOListExpected)).get();

        assertThat(asteroidService.findAllAsteroids(planet, dateStart, dateEnd)).isEqualTo(asteroidDTOListExpected);
    }

}
