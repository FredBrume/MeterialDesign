package com.example.fredbrume.meterialdesign.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;

import com.example.fredbrume.meterialdesign.Adapters.MapViewPageAdapter;
import com.example.fredbrume.meterialdesign.R;

import com.example.fredbrume.meterialdesign.Model.Location;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyJsonStringUtil;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyStringRequest;
import butterknife.BindView;

/**
 * Created by fredbrume on 12/7/17.
 */

public class MapFragment extends BaseFragment {

    @BindView(R.id.map_viewpager)
    ViewPager viewPager;

    @BindView(R.id.materialTabHost)
    TabLayout tabHost;

    private MapViewPageAdapter adapter;
    private static Location[] locations;
    private static Location location;

    public MapFragment()
    {

    }

    public void getLocation(Location location, Context context)
    {
        viewPager.setCurrentItem(0,true);
        MapTypeMapFragment fragment = (MapTypeMapFragment) adapter.getItem(0);
        fragment.getLocation(location, context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getLocationRequest();

        return view;
    }

    private void getLocationRequest() {
        final VolleyStringRequest volleyStringRequest = new VolleyStringRequest(getContext(), Config.buildLocationUrl().toString());
        volleyStringRequest.addHeader("Authorization", "application/json");
        volleyStringRequest.executeRequest(Request.Method.GET, new VolleyStringRequest.VolleyCallback() {

            @Override
            public void getResponse(String response) {

                locations = VolleyJsonStringUtil.vJsonLocationList(response);

                adapter = new MapViewPageAdapter(getContext(), getFragmentManager(), locations);
                viewPager.setAdapter(adapter);

                tabHost.setupWithViewPager(viewPager);

                tabHost.getTabAt(0).setCustomView(R.layout.customtablayout);
                tabHost.getTabAt(1).setCustomView(R.layout.customtablayout);
                tabHost.getTabAt(0).setIcon(adapter.setupTabIcons(0));
                tabHost.getTabAt(1).setIcon(adapter.setupTabIcons(1));



                Log.d("Location", response);
            }
        });


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_map;
    }
}
