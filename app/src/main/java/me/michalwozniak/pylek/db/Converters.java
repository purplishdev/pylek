package me.michalwozniak.pylek.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

import me.michalwozniak.pylek.model.Station;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Station fromKey(Integer value) {
        return value == null ? null : Station.values()[value];
    }

    @TypeConverter
    public static Integer stationToKey(Station station) {
        return station == null ? null : station.ordinal();
    }
}
