package com.atmira.javatest.service;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.model.Asteroid;
import com.atmira.javatest.model.NearEarthObjects;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AsteroidServiceImpl implements AsteroidServiceI {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_REQUEST_ENDPOINT = "https://api.nasa.gov/neo/rest/v1/feed";
    private static final String API_PARAMETER_START_DATE = "start_date";
    private static final String API_PARAMETER_END_DATE = "end_date";
    private static final String API_PARAMETER_KEY = "api_key";

    @Value("${api.key}")
    private String API_KEY;

    @Override
    public List<AsteroidDTO> findAllAsteroids(String planet, LocalDate dateStart, LocalDate dateEnd) {

//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Resource resource = resourceLoader.getResource("classpath:nasa_response_json.json");
//
//        File file = null;
//        NearEarthObjects apiDataFiltered = null;
//
//        try {
//            file = resource.getFile();
//            apiDataFiltered  = objectMapper.readValue(file, NearEarthObjects.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        apiDataFiltered.getNear_earth_objects().values().stream().forEach(System.out::println);
//
//        //Tratamiento de los datos extraídos del json
//        List<Asteroid> filteredAsteroidsTest = new ArrayList<>();
//        apiDataFiltered.getNear_earth_objects().values().stream().forEach(c -> filteredAsteroidsTest.addAll(c));
//
//        List<AsteroidDTO> filteredAsteroidsDTOTest = filteredAsteroidsTest
//                .stream()
//                .filter(a -> a.is_potentially_hazardous_asteroid())
//                .filter(a -> a.getClose_approach_data()[0].getOrbiting_body().equalsIgnoreCase(planet))
//                .map(a -> new AsteroidDTO(
//                        a.getName(),
//                        (a.getEstimated_diameter().getKilometers().get("estimated_diameter_min") + a.getEstimated_diameter().getKilometers().get("estimated_diameter_max"))/2,
//                        a.getClose_approach_data()[0].getRelative_velocity().get("kilometers_per_hour"),
//                        new SimpleDateFormat("yyyy-MM-dd").format(a.getClose_approach_data()[0].getClose_approach_date()),
//                        a.getClose_approach_data()[0].getOrbiting_body()
//                ))
//                .collect(Collectors.toList());
//
//        //Ordenamos
//        filteredAsteroidsDTOTest.sort(Comparator.comparing(AsteroidDTO::getDiameter).reversed());
//
//        //Enviamos los 3 más grandes de diametro
//        return filteredAsteroidsDTOTest.stream().limit(3).collect(Collectors.toList());

//        System.out.println("********************************************************************");

        //Llamada a la API
        apiCall(dateStart, dateEnd).getNear_earth_objects().values().stream().forEach(System.out::println);

        List<Asteroid> filteredAsteroids = new ArrayList<>();
        apiCall(dateStart, dateEnd).getNear_earth_objects().values().stream().forEach(c -> filteredAsteroids.addAll(c));

        List<AsteroidDTO> filteredAsteroidsDTO = filteredAsteroids
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
        filteredAsteroidsDTO.sort(Comparator.comparing(AsteroidDTO::getDiameter).reversed());

        //Enviamos los 3 más grandes de diametro
        return filteredAsteroidsDTO.stream().limit(3).collect(Collectors.toList());
    }

    protected NearEarthObjects apiCall(LocalDate dateStart, LocalDate dateEnd){
        //URL de la petición
        String API_REQUEST = API_REQUEST_ENDPOINT + "?" + API_PARAMETER_START_DATE + "=" + dateStart
                + "&" + API_PARAMETER_END_DATE + "=" + dateEnd + "&" + API_PARAMETER_KEY + "=" + API_KEY;

//        Map<String, String> params = new HashMap<>();
//        params.put(API_PARAMETER_KEY, API_KEY);
//        params.put(API_PARAMETER_START_DATE, dateStart.toString());
//        params.put(API_PARAMETER_END_DATE, dateEnd.toString());

//        ResponseEntity<NearEarthObjects> responseEntity = restTemplate.getForEntity(API_REQUEST_ENDPOINT, NearEarthObjects.class, params);
        ResponseEntity<NearEarthObjects> responseEntity = restTemplate.getForEntity(API_REQUEST, NearEarthObjects.class);

        return responseEntity.getBody();
    }

}
