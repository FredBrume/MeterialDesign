package com.example.fredbrume.meterialdesign.Data.Remote.Api;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import com.example.fredbrume.meterialdesign.Model.GeoPoint;

/**
 * Created by fredbrume on 10/24/17.
 */

public class GeopointDeserializer implements JsonDeserializer {

    private final static String LATITUDE_JSON_KEY = "lat";
    private final static String LONGITUDE_JSON_KEY = "lng";


    @Override
    public GeoPoint deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();

        final GeoPoint geoPoint = new GeoPoint(
                jsonObject.get(LATITUDE_JSON_KEY).getAsDouble(), jsonObject.get(LONGITUDE_JSON_KEY).getAsDouble());

        return geoPoint;
    }
}
