package me.michalwozniak.pylek.model;

import java.util.List;

import lombok.Data;

@Data
public class InfluxResponse {

    private List<InfluxResults> results;
}
