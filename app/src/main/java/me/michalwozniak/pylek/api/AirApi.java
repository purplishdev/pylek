package me.michalwozniak.pylek.api;

import io.reactivex.Single;
import me.michalwozniak.pylek.model.Station;

public interface AirApi {

    Single<Float> getTemperature();

    Single<Float> getHumidity();

    Single<Float> getPressure();

    Single<Float> getPM10(Station station);

    Single<Float> getPM25(Station station);
}
