package com.atmira.javatest.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NearEarthObjects implements Serializable {

    @JsonProperty("near_earth_objects")
    private Map<String, List<Asteroid>> nearEarthObjects;

    /**
     * JsonAnyGetter
     * Me permite en la serialización del json devolver un mapa con todos los datos que contenga la etiqueta near_earth_objects
     *
     * @return
     */
    @JsonAnyGetter
    public Map<String, List<Asteroid>> getNearEarthObjects() {
        return nearEarthObjects;
    }
}
