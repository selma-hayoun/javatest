package com.atmira.javatest.service;

import com.atmira.javatest.model.Asteroid;
import com.atmira.javatest.model.NearEarthObjects;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AsteroidServiceImpl implements AsteroidServiceI {

    @Autowired
    private ResourceLoader resourceLoader;

    private static final String API_REQUEST_ENDPOINT = "https://api.nasa.gov/neo/rest/v1/feed?";
    private static final String API_PARAMETER_START_DATE = "start_date";
    private static final String API_PARAMETER_END_DATE = "end_date";

    @Value("${api.key}")
    private String API_KEY;

    @Override
    public List<Asteroid> findAllAsteroids(LocalDate dateStart, LocalDate dateEnd) {

        //Llamada a la api
        String API_RESPONSE = apiCall(dateStart, dateEnd);

        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        //File file = new File("classpath:resources/nasa_response_json.json");

        Resource resource = resourceLoader.getResource("classpath:nasa_response_json.json");
//        Resource resource = resourceLoader.getResource("classpath:nasa_response_json2.json");

        File file = null;
        List<Asteroid> myAsteroids = null;
        Map<String, List<Asteroid>> asteroidsByDate = null;
        NearEarthObjects apiDataFiltered = null;

        try {
            file = resource.getFile();
//            myAsteroids = objectMapper.readValue(file, new TypeReference<List<Asteroid>>() {});
//            asteroidsByDate = objectMapper.readValue(file, new TypeReference<Map<String, List<Asteroid>>>() {});
            apiDataFiltered  = objectMapper.readValue(file, NearEarthObjects.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        apiDataFiltered.getNear_earth_objects().values().stream().forEach(System.out::println);

        return null;
    }

    private String apiCall(LocalDate dateStart, LocalDate dateEnd){
        //URL de la petición
        String API_REQUEST = API_REQUEST_ENDPOINT + "?" + API_PARAMETER_START_DATE + "=" + dateStart
                + "&" + API_PARAMETER_END_DATE + "=" + dateEnd + "&" + API_KEY;
        return null;
    }

}
