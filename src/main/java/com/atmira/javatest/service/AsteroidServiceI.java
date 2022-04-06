package com.atmira.javatest.service;

import com.atmira.javatest.dto.AsteroidDTO;
import com.atmira.javatest.model.Asteroid;
import com.atmira.javatest.model.NearEarthObjects;

import java.time.LocalDate;
import java.util.List;

public interface AsteroidServiceI {
    public List<AsteroidDTO> findAllAsteroids(String planet, LocalDate dateStart, LocalDate dateEnd);
}
