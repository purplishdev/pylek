package me.michalwozniak.pylek.model;

import java.util.List;

import lombok.Data;

@Data
public class InfluxSeries {

    private String name;

    private String[] columns;

    private List<List<String>> values;
}
