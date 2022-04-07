package com.atmira.javatest.util;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.model.NearEarthObjects;
import com.atmira.javatest.service.AsteroidServiceTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class NasaDummyDataUtil {

    private static final Logger LOG = LoggerFactory.getLogger(AsteroidServiceTest.class);
    private ObjectMapper objectMapper;
    private ResponseEntity<NearEarthObjects> nearEarthObjectsResponseEntity;

    public NasaDummyDataUtil() {
        this.objectMapper = new ObjectMapper();
    }

    public Optional<?> getNasaResponseDummyData(String resourceName, Optional<?> objectType) {

        ClassLoader classLoader = getClass().getClassLoader();
        File file;
        Optional<?> result = null;

        //Parseamos el objeto del json
        try {
            file = new File(classLoader.getResource(resourceName).getFile());

            if(objectType.get() instanceof NearEarthObjects){
                result = Optional.of(objectMapper.readValue(file, NearEarthObjects.class));
            } else {
                result = Optional.of(objectMapper.readValue(file, new TypeReference<List<AsteroidDTO>>(){}));
            }

        } catch (IOException e) {
            LOG.info("getNasaResponseDummyData() parsing json file ERROR - " + e.getMessage());
        }
        return result;
    }
}
