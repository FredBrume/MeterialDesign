package com.example.fredbrume.meterialdesign.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.fredbrume.meterialdesign.Fragments.MapTypeListFragment;
import com.example.fredbrume.meterialdesign.Fragments.MapTypeMapFragment;
import com.example.fredbrume.meterialdesign.R;

import com.example.fredbrume.meterialdesign.Model.Location;


/**
 * Created by fredbrume on 5/13/17.
 */

public class MapViewPageAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private Location[] locations;

    private static final int[] tabIcons = {R.drawable.ic_earth, R.drawable.ic_action_list};

    public MapViewPageAdapter(Context context, FragmentManager fm, Location[] location) {
        super(fm);

        this.locations = location;
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                return MapTypeMapFragment.getInstance(locations);

            case 1:

                return MapTypeListFragment.getInstance(locations);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public Drawable setupTabIcons(int position) {
        Resources res = context.getResources();
        return res.getDrawable(tabIcons[position]);
    }
}
