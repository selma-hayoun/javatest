package com.atmira.javatest.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsteroidEstimatedDiameter implements Serializable {
    //Dos claves: estimated_diameter_min, estimated_diameter_max
    private Map<String, Float> kilometers;

    @JsonAnyGetter
    public Map<String, Float> getKilometers() {
        return kilometers;
    }
}
