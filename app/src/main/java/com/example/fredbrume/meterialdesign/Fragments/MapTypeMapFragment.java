package com.example.fredbrume.meterialdesign.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fredbrume.meterialdesign.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import com.example.fredbrume.meterialdesign.Model.GeoPoint;
import com.example.fredbrume.meterialdesign.Model.Location;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.GooglePlace;

import butterknife.BindView;

/**
 * Created by fredbrume on 12/7/17.
 */

public class MapTypeMapFragment extends BaseFragment implements OnMapReadyCallback {

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.map_coordinator)
    CoordinatorLayout coordinatorLayout;

    static GoogleMap map;
    private static Location[] locations;
    private android.support.v4.app.LoaderManager loaderManager;
    private static final int LOADER_ID = 0;
    private boolean cameraFirstTime = true;

    public MapTypeMapFragment() {

    }

    public static MapTypeMapFragment getInstance(Location[] locations) {

        MapTypeMapFragment.locations = locations;
        MapTypeMapFragment fragment = new MapTypeMapFragment();

        return fragment;
    }

    public void getLocation(Location location, Context context) {

        if (map != null) {
            MarkerOptions markerOptions = new MarkerOptions();
            GooglePlace googlePlace = new GooglePlace(context);

            if (location != null) {
                GeoPoint geoPoint = googlePlace.getLocationFromAddress(location);

                if (geoPoint != null) {
                    map.clear();
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    markerOptions.title(location.name);
                    markerOptions.snippet(location.address);

                    LatLng latlngLocation = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                    markerOptions.position(latlngLocation);

                    map.animateCamera(CameraUpdateFactory.newLatLng(latlngLocation));
                    Marker locationMarker = map.addMarker(markerOptions);
                    locationMarker.showInfoWindow();
                }

            }

        } else {
            Toast.makeText(context, "NULL", Toast.LENGTH_SHORT).show();
        }
    }

    //create a method for adding marker

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mapView != null)
            mapView.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mapView != null)
            mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mapView != null)
            mapView.onStop();

    }

    @Override
    public void onMapReady(GoogleMap map) {

        this.map = map;
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } else {

        }

        loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, new AddMarkerTask());
    }

    public class AddMarkerTask implements LoaderManager.LoaderCallbacks<List<LatLng>> {


        @SuppressLint("StaticFieldLeak")
        @Override
        public Loader<List<LatLng>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<List<LatLng>>(getContext()) {

                List<LatLng> latLngsList;

                @Override
                protected void onStartLoading() {
                    super.onStartLoading();

                    if (latLngsList != null) {
                        deliverResult(latLngsList);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public void deliverResult(List<LatLng> latLngs) {

                    latLngsList = latLngs;
                    super.deliverResult(latLngs);
                }

                @Override
                public List<LatLng> loadInBackground() {

                    GooglePlace googlePlace = new GooglePlace(getContext());
                    List<LatLng> latLngs = new ArrayList<>();

                    for (Location location : locations) {
                        GeoPoint geoPoint = googlePlace.getLocationFromAddress(location);
                        if (geoPoint != null) {

                            LatLng latlngLocation = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                            latLngs.add(latlngLocation);

                        }
                    }
                    return latLngs;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<LatLng>> loader, List<LatLng> data) {

            if (data != null) {
                for (LatLng latLng : data) {
                    MarkerOptions options = new MarkerOptions();
                    options.position(latLng);
                    map.addMarker(options);

                    if (cameraFirstTime) {

                        cameraFirstTime = false;
                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        map.animateCamera(CameraUpdateFactory.zoomTo(13));
                    }
                }
            }

        }

        @Override
        public void onLoaderReset(Loader<List<LatLng>> loader) {

        }

    }


    @Override
    protected int getLayoutResId() {
        return R.layout.map_layout;
    }

}
