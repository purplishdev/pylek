package me.michalwozniak.pylek.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.util.Date;

import me.michalwozniak.pylek.model.InfluxResponse;

public class InfluxRecordDeserializer extends BaseInfluxDeserializer<InfluxResponse> {

    @Override
    protected InfluxResponse tryDeserialize(JsonElement series, JsonDeserializationContext context) throws JsonParseException {

        try {
            JsonArray values = series.getAsJsonObject().get("values").getAsJsonArray();

            int recordCount = values.size();
            if (recordCount > 1) {
                throw new JsonParseException("Expected 1 record count, got " + recordCount);
            }

            JsonArray record = values.get(0).getAsJsonArray();
            Date time = new Date(record.get(0).getAsLong());
            double value = record.get(1).getAsDouble();

            return new InfluxResponse(time, value);
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }
}
