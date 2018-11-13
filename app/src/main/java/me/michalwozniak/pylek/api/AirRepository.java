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
    public Single<Float> getTemperature() {
        return makeValueCall("LOCAL_TEMP", Station.HRYNIEWICZA);
    }

    @Override
    public Single<Float> getHumidity() {
        return makeValueCall("LOCAL_HUM", Station.HRYNIEWICZA);
    }

    @Override
    public Single<Float> getPressure() {
        return makeValueCall("LOCAL_PRESS", Station.HRYNIEWICZA);
    }

    @Override
    public Single<Float> getPM10(Station station) {
        return makeValueCall("ppm10sds011", station);
    }

    @Override
    public Single<Float> getPM25(Station station) {
        return makeValueCall("ppm25sds011", station);
    }

    private Single<Float> makeValueCall(String path, Station station) {
        String query = String.format(VALUE_QUERY, path);
        return api.query(station.getNumber(), query, DATABASE, EPOCH)
                .map(InfluxResponse::getValue);
    }
}
