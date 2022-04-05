package com.atmira.javatest.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class NearEarthObjects implements Serializable {
    private Map<String, List<Asteroid>> near_earth_objects;

    @JsonAnyGetter
    public Map<String, List<Asteroid>> getNear_earth_objects() {
        return near_earth_objects;
    }
}
