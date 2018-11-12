package me.michalwozniak.pylek.api;

import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import me.michalwozniak.pylek.model.InfluxResponse;
import me.michalwozniak.pylek.model.Station;

@RequiredArgsConstructor
public class AirRepository implements AirApi {

    private final static String VALUE_QUERY = "SELECT mean(\"value\") FROM %s " +
            "WHERE time >= now() - 10m LIMIT 1";

    private final String EPOCH = "ms";

    private final String DATABASE = "smogdb";

    private final InfluxRetrofitApi api;

    @Override
    public Single<Double> getTemperature() {
        return api.query(3, String.format(VALUE_QUERY, "LOCAL_TEMP"), DATABASE, EPOCH)
                .map(InfluxResponse::getValue);
    }

    @Override
    public Single<Double> getHumidity() {
        return api.query(3, String.format(VALUE_QUERY, "LOCAL_HUM"), DATABASE, EPOCH)
                .map(InfluxResponse::getValue);
    }

    @Override
    public Single<Double> getPressure() {
        return api.query(3, String.format(VALUE_QUERY, "LOCAL_PRESS"), DATABASE, EPOCH)
                .map(InfluxResponse::getValue);
    }

    @Override
    public Single<Double> getPM10(Station station) {
        return api.query(station.getNumber(), String.format(VALUE_QUERY, "ppm10sds011"), DATABASE, EPOCH)
                .map(InfluxResponse::getValue);
    }

    @Override
    public Single<Double> getPM25(Station station) {
        return api.query(station.getNumber(), String.format(VALUE_QUERY, "ppm25sds011"), DATABASE, EPOCH)
                .map(InfluxResponse::getValue);
    }
}
