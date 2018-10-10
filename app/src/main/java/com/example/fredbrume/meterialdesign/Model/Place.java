package com.example.fredbrume.meterialdesign.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fredbrume on 12/16/17.
 */

public class Place {

    @SerializedName("geometry")
    public List<GeoPoint> geometry;

    @SerializedName("name")
    public String name;
}
