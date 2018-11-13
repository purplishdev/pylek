package me.michalwozniak.pylek.model;

import java.util.Date;

import lombok.Value;

@Value
public class AirData {

    private float pm10;

    private float pm25;

    private float temperature;

    private float pressure;

    private Date date = new Date();
}
