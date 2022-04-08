package com.atmira.javatest.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private Map<String, List<Asteroid>> near_earth_objects;

    /**
     * JsonAnyGetter
     * Me permite en la serialización del json devolver un mapa con todos los datos que contenga la etiqueta near_earth_objects
     *
     * @return
     */
    @JsonAnyGetter
    public Map<String, List<Asteroid>> getNear_earth_objects() {
        return near_earth_objects;
    }
}
