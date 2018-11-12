package me.michalwozniak.pylek.api;

import io.reactivex.Single;
import me.michalwozniak.pylek.model.Station;

public interface AirApi {

    Single<Double> getTemperature();

    Single<Double> getHumidity();

    Single<Double> getPressure();

    Single<Double> getPM10(Station station);

    Single<Double> getPM25(Station station);
}
