package me.michalwozniak.pylek;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import me.michalwozniak.pylek.model.InfluxResponse;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void mainActivity_shouldBeCreated() {
        new MainActivity();
    }

    @Test
    public void shouldDeserializeInfluxResponse() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        InfluxResponse response = gson.fromJson("{\"results\":[{\"series\":[{\"name\":\"DEW_POINT\",\"columns\":[\"time\"," +
                              "\"value\"],\"values\":[[\"2018-10-16T19:19:23.663550448Z\",4.82]," +
                              "[\"2018-10-16T19:17:14.373770382Z\",4.84]]}]}]}", InfluxResponse.class);
    }
}
