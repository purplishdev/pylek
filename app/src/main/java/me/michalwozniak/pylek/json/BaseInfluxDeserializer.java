package me.michalwozniak.pylek.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public abstract class BaseInfluxDeserializer<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject resultsObject = json.getAsJsonObject();
            JsonElement results = resultsObject.get("results").getAsJsonArray().get(0);
            JsonObject seriesObject = results.getAsJsonObject();
            JsonElement series = seriesObject.get("series").getAsJsonArray().get(0);

            return tryDeserialize(series, context);
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    abstract protected T tryDeserialize(JsonElement series, JsonDeserializationContext context) throws JsonParseException;
}
