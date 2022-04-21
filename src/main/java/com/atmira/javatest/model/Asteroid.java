package com.atmira.javatest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private static final long serialVersionUID = -8743460818704181661L;

    private String name;
    @JsonProperty("estimated_diameter")
    private AsteroidEstimatedDiameter estimatedDiameter;

    @JsonProperty("is_potentially_hazardous_asteroid")
    private boolean isPotentiallyHazardousAsteroid;

    @JsonProperty("close_approach_data")
    private AsteroidCloseApproachData[] closeApproachData;

}
