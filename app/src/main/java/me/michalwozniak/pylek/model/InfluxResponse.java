package me.michalwozniak.pylek.model;

import java.util.Date;

import lombok.Value;

@Value
public class InfluxResponse {

    private Date time;

    private Double value;
}
