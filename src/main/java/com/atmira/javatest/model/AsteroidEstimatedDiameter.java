package com.atmira.javatest.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsteroidEstimatedDiameter implements Serializable {
    //Dos claves: estimated_diameter_min, estimated_diameter_max
    private Map<String, Float> kilometers;

    /**
     * JsonAnyGetter
     * Me permite en la serialización del json devolver un mapa con todos los datos que contenga la etiqueta kilometers
     *
     * @return
     */
    @JsonAnyGetter
    public Map<String, Float> getKilometers() {
        return kilometers;
    }
}
