package com.atmira.javatest.service;

import com.atmira.javatest.model.Asteroid;

import java.time.LocalDate;
import java.util.List;

public interface AsteroidServiceI {
    public List<Asteroid> findAllAsteroids(LocalDate dateStart, LocalDate dateEnd);
}
