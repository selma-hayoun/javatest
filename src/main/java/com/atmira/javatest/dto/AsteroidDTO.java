package com.atmira.javatest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsteroidDTO implements Serializable {
    private String name;
    private Float diameter;
    private Float velocity;
    private String close_approach_date;
    private String orbiting_body;
}
