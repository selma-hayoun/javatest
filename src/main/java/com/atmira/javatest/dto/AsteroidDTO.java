package com.atmira.javatest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsteroidDTO implements Serializable {
    @ApiModelProperty(notes = "Asteroid name", example = "Ceres", required = false)
    private String name;
    @ApiModelProperty(notes = "Mean Diameter", example = "939.4±0.2", required = false)
    private Float diameter;
    @ApiModelProperty(notes = "Asteroid velocity", example = "1.836", required = false)
    private Float velocity;
    @ApiModelProperty(notes = "Asteroid close approach date to the planet", example = "2022-12-31", required = false)
    private String close_approach_date;
    @ApiModelProperty(notes = "Asteroid orbiting body", example = "Earth", required = false)
    private String orbiting_body;
}
