package me.michalwozniak.pylek;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import me.michalwozniak.pylek.api.AirApi;
import me.michalwozniak.pylek.json.InfluxRecordDeserializer;
import me.michalwozniak.pylek.model.InfluxResponse;
import me.michalwozniak.pylek.model.Station;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test public void
    shouldReturnInfluxData() {

        Station station = Station.HRYNIEWICZA;

        AirApi service = new InfluxAirApi();

        System.out.println(String.format("Current date: %s\nPM25: %.2f\nPM10: %.2f\nTemperature: %.2f\nHumidity: %.2f\nPressure: %.2f",
                           new Date(), service.getPM25(station), service.getPM10(station),
                           service.getTemperature(station), service.getHumidity(station), service.getPressure(station)));
    }

    @Test public void
    shouldGetResponseFromInfluxDb() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(InfluxResponse.class, new InfluxRecordDeserializer());
        Gson gson = gsonBuilder.create();

        InfluxResponse response = gson.fromJson("{\"results\":[{\"series\":[{\"name\":\"DEW_POINT\",\"columns\":[\"time\"," +
                                                        "\"value\"],\"values\":[[1541526758208,4.82]]}]}]}", InfluxResponse.class);

        Assert.assertNotNull(response);
    }
}
