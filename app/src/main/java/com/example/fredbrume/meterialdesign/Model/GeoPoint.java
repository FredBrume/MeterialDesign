package com.example.fredbrume.meterialdesign.Model;

import java.io.Serializable;

/**
 * Created by fredbrume on 12/12/17.
 */

public class GeoPoint implements Serializable {

    private double longitude;
    private double latitude;

    public GeoPoint(double longitude, double latitude){

        this.longitude = longitude;
        this.latitude = latitude;

    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
