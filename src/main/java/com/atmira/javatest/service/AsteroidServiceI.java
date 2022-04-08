package com.atmira.javatest.service;

import com.atmira.javatest.dto.AsteroidDTO;

import java.time.LocalDate;
import java.util.List;

public interface AsteroidServiceI {
    List<AsteroidDTO> findAllAsteroids(String planet, LocalDate dateStart, LocalDate dateEnd);
}
