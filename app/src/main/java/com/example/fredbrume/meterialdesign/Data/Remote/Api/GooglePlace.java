package com.example.fredbrume.meterialdesign.Data.Remote.Api;

import android.content.Context;
import android.location.Geocoder;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import java.util.List;

import com.example.fredbrume.meterialdesign.Model.GeoPoint;
import com.example.fredbrume.meterialdesign.Model.Location;

/**
 * Created by fredbrume on 12/12/17.
 */

public class GooglePlace implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final GoogleApiClient mClient;
    private Context context;
    private String TAG = GooglePlace.class.getSimpleName();

    public GooglePlace(Context context) {
        this.context = context;

        mClient = new GoogleApiClient.Builder(context)

                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    public GeoPoint getLocationFromAddress(Location location) {

        Geocoder coder = new Geocoder(context);
        GeoPoint geoPoint = null;
        List<Address> addressList;

        try {
            addressList = coder.getFromLocationName(location.address, 5);
            if (addressList == null) {
                return null;
            }

            Address address = addressList.get(0);
            geoPoint = new GeoPoint(address.getLongitude(), address.getLatitude());

            return geoPoint;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geoPoint;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "API Client Connection Successful!");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "API Client Connection Failed!");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "API Client Connection Failed!");
    }
}
