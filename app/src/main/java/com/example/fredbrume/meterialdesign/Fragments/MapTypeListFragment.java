package com.example.fredbrume.meterialdesign.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fredbrume.meterialdesign.Activity.ViewPropertyHandler;
import com.example.fredbrume.meterialdesign.Adapters.MapListAdapter;
import com.example.fredbrume.meterialdesign.R;

import com.example.fredbrume.meterialdesign.Model.Location;
import butterknife.BindView;

/**
 * Created by fredbrume on 12/7/17.
 */

public class MapTypeListFragment extends BaseFragment implements MapListAdapter.LocationOnClickHandler {

    private static Location[] locations;
    @BindView(R.id.location_list)
    RecyclerView locationList;

    private SendLocationHandler sendLocationHandler;


    public MapTypeListFragment() {

    }

    public interface SendLocationHandler{

        void sendLocation(Location location);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            sendLocationHandler = (SendLocationHandler) context;

        } catch (ClassCastException exception) {

            throw new ClassCastException(context.toString()
                    + " must implement SendLocationHandler");
        }
    }

    public static MapTypeListFragment getInstance(Location[] locations) {

        MapTypeListFragment.locations = locations;
        MapTypeListFragment fragment = new MapTypeListFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        MapListAdapter mapListAdapter = new MapListAdapter(this);
        locationList.setAdapter(mapListAdapter);
        mapListAdapter.setLayoutData(locations);
        viewPropertyHandler.setBackgroundMainLogo(0.2f);


        return view;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.map_list_layout;
    }


    @Override
    public void onLocationItemClick(Location location) {

        sendLocationHandler.sendLocation(location);
    }
}
