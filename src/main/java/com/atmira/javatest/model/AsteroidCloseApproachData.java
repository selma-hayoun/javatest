package com.atmira.javatest.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("close_approach_date")
    private Date closeApproachDate;

    @JsonProperty("relative_velocity")
    private Map<String, Float> relativeVelocity;

    @JsonProperty("orbiting_body")
    private String orbitingBody;

    /**
     * JsonAnyGetter
     * Me permite en la serialización del json devolver un mapa con todos los datos que contenga la etiqueta relative_velocity
     *
     * @return
     */
    @JsonAnyGetter
    public Map<String, Float> getRelativeVelocity() {
        return relativeVelocity;
    }
}
