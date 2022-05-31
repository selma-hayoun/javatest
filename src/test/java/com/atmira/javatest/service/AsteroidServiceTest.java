package com.atmira.javatest.service;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.model.NearEarthObjects;
import com.atmira.javatest.util.NasaDummyDataUtil;
import com.atmira.javatest.utils.JavaTestConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Slf4j
public class AsteroidServiceTest {

//    private String planet;
//    private RestTemplate restTemplate;
//    private AsteroidServiceImpl asteroidService;
//    private ResponseEntity<NearEarthObjects> nearEarthObjectsResponseEntity;
//    private NasaDummyDataUtil nasaDummyDataUtil;
//    private String apiNasaEndpoint;
//
//
//    @BeforeEach
//    public void setup() {
//        asteroidService = new AsteroidServiceImpl();
//        nasaDummyDataUtil = new NasaDummyDataUtil();
//        restTemplate = Mockito.mock(RestTemplate.class);
//        apiNasaEndpoint = JavaTestConstants.TESTS_API_REQUEST_ENDPOINT;
//
//        asteroidService.setRestTemplate(restTemplate);
//        asteroidService.setApiNasaEndpoint(apiNasaEndpoint);
//
//        //Preparamos objeto del tipo vacío con OK
//        nearEarthObjectsResponseEntity = new ResponseEntity<NearEarthObjects>(HttpStatus.OK);
//
//        log.info("@BeforeEach - executes once before each test method in AsteroidServiceTest");
//    }

//    /**
//     * Test método apiCall() de AsteroidServiceImpl
//     *
//     * @throws Exception
//     */
//    @Test
//    void whenCallingApiCall_thenShouldReturnCorrectObject() throws Exception {
//
//        //Fechas límite de la petición
//        LocalDate dateStart = LocalDate.parse(JavaTestConstants.TESTS_STR_START_DATE);
//        LocalDate dateEnd = LocalDate.parse(JavaTestConstants.TESTS_STR_END_DATE);
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiNasaEndpoint)
//                .queryParam(JavaTestConstants.API_PARAMETER_START_DATE, dateStart)
//                .queryParam(JavaTestConstants.API_PARAMETER_END_DATE, dateEnd);
//
//        String uriBuilder = builder.build().encode().toUriString();
//        nearEarthObjectsResponseEntity = ResponseEntity.ok( (NearEarthObjects) nasaDummyDataUtil.getNasaResponseDummyData(JavaTestConstants.TESTS_NASA_RESPONSE_JSON, Optional.of(new NearEarthObjects())).get());
//
//        //Para que el método apiCall tenga datos - simulamos la llamada con los datos del json
//        when(restTemplate.getForEntity(uriBuilder, NearEarthObjects.class)).thenReturn(nearEarthObjectsResponseEntity);
//
//        assertThat(asteroidService.apiCall(dateStart, dateEnd)).isEqualTo(nearEarthObjectsResponseEntity.getBody());
//    }

//    /**
//     * Test método findAllAsteroids() de AsteroidServiceImpl
//     *
//     * Caso fechas y planeta para datos correctos
//     *
//     * @throws Exception
//     */
//    @Test
//    void whenCallingFindAllAsteroids_thenShouldReturnCorrectList() throws Exception {
//        //Asignamos el planeta de la petición
//        planet = JavaTestConstants.STR_PLANET_EARTH;
//
//        //Fechas límite de la petición
//        LocalDate dateStart = LocalDate.parse(JavaTestConstants.TESTS_STR_START_DATE);
//        LocalDate dateEnd = LocalDate.parse(JavaTestConstants.TESTS_STR_END_DATE);
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiNasaEndpoint)
//                .queryParam(JavaTestConstants.API_PARAMETER_START_DATE, dateStart)
//                .queryParam(JavaTestConstants.API_PARAMETER_END_DATE, dateEnd);
//
//        String uriBuilder = builder.build().encode().toUriString();
//        nearEarthObjectsResponseEntity = ResponseEntity.ok( (NearEarthObjects) nasaDummyDataUtil.getNasaResponseDummyData(JavaTestConstants.TESTS_NASA_RESPONSE_JSON, Optional.of(new NearEarthObjects())).get());
//
//        //Para que el método apiCall tenga datos - simulamos la llamada con los datos del json
//        when(restTemplate.getForEntity(uriBuilder, NearEarthObjects.class)).thenReturn(nearEarthObjectsResponseEntity);
//
//        List<AsteroidDTO> asteroidDTOListExpected = new ArrayList<>();
//        asteroidDTOListExpected = (List<AsteroidDTO>) nasaDummyDataUtil.getNasaResponseDummyData(JavaTestConstants.TESTS_ASTEROIDS_RESULT, Optional.of(asteroidDTOListExpected)).get();
//
//        assertThat(asteroidService.findAllAsteroids(planet, dateStart, dateEnd)).isEqualTo(asteroidDTOListExpected);
//    }

}
