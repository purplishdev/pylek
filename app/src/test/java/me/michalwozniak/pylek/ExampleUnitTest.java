package me.michalwozniak.pylek;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Assert;
import org.junit.Test;

import me.michalwozniak.pylek.json.InfluxResponseDeserializer;
import me.michalwozniak.pylek.model.InfluxResponse;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test public void
    shouldGetResponseFromInfluxDb() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(InfluxResponse.class, new InfluxResponseDeserializer());
        Gson gson = gsonBuilder.create();

        InfluxResponse response = gson.fromJson("{\"results\":[{\"series\":[{\"name\":\"DEW_POINT\",\"columns\":[\"time\"," +
                                                        "\"value\"],\"values\":[[1541526758208,4.82]]}]}]}", InfluxResponse.class);

        Assert.assertNotNull(response);
    }
}
