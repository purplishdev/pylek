package me.michalwozniak.pylek.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

@Entity
@Data
public class AirData implements Comparable<AirData> {

    @PrimaryKey
    private Date date = new Date();

    private float pm10;

    private float pm25;

    private float temperature;

    private float pressure;

    private Station station;

    public AirData(float pm10, float pm25, float temperature, float pressure, Station station) {
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.temperature = temperature;
        this.pressure = pressure;
        this.station = station;
    }

    @Override
    public int compareTo(AirData o) {
        if (o == null) return 1;
        return date.compareTo(o.getDate());
    }
}
