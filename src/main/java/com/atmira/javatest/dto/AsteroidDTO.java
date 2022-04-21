package com.atmira.javatest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsteroidDTO implements Serializable {

    @ApiModelProperty(notes = "Asteroid name", example = "Ceres")
    private String name;

    @ApiModelProperty(notes = "Mean Diameter", example = "939.4±0.2")
    private Float diameter;

    @ApiModelProperty(notes = "Asteroid velocity", example = "1.836")
    private Float velocity;

    @ApiModelProperty(notes = "Asteroid close approach date to the planet", example = "2022-12-31")
    @JsonProperty("close_approach_date")
    private String closeApproachDate;

    @ApiModelProperty(notes = "Asteroid orbiting body", example = "Earth")
    @JsonProperty("orbiting_body")
    private String orbitingBody;
}
