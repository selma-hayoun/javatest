package com.atmira.javatest.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AsteroidCloseApproachData implements Serializable {
    private Date close_approach_date;
    private Map<String, Float> relative_velocity;
    private String orbiting_body;

    /**
     * JsonAnyGetter
     * Me permite en la serialización del json devolver un mapa con todos los datos que contenga la etiqueta relative_velocity
     *
     * @return
     */
    @JsonAnyGetter
    public Map<String, Float> getRelative_velocity() {
        return relative_velocity;
    }
}
