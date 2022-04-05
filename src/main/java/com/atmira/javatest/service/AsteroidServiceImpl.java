package com.atmira.javatest.service;

import com.atmira.javatest.model.Asteroid;
import com.atmira.javatest.model.NearEarthObjects;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class AsteroidServiceImpl implements AsteroidServiceI {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public List<Asteroid> findAllAsteroids() {
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

}
