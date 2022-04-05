package com.atmira.javatest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Asteroid implements Serializable {
    private String name;
    private AsteroidEstimatedDiameter estimated_diameter;
    private boolean is_potentially_hazardous_asteroid;
    private AsteroidCloseApproachData[] close_approach_data;

}
