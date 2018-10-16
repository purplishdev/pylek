package me.michalwozniak.pylek.model;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class InfluxData {

    private OffsetDateTime time;

    private Double value;
}
