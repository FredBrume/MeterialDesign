package com.example.fredbrume.meterialdesign.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.R;

import java.util.ArrayList;

import com.example.fredbrume.meterialdesign.Model.NavDrawerItem;

/**
 * Created by CeeDe on 10/4/2016.
 */
public class NavigationDrawerAdapter extends BaseAdapter {
    Context context;
    TypedArray icons;
    private ArrayList<NavDrawerItem> singleRow;

    public NavigationDrawerAdapter(Context context) {
        this.context = context;
        singleRow = new ArrayList<>();


            singleRow.add(new NavDrawerItem("RESOURCES"));
            singleRow.add(new NavDrawerItem("Home",R.drawable.ic_action_home));
            singleRow.add(new NavDrawerItem("Event", R.drawable.ic_action_event));
            singleRow.add(new NavDrawerItem("Contact", R.drawable.ic_action_contact));
            singleRow.add(new NavDrawerItem("Map", R.drawable.ic_map));
            singleRow.add(new NavDrawerItem("Documents", R.drawable.ic_action_documents));
            singleRow.add(new NavDrawerItem("Gallery", R.drawable.ic_action_pictures));
            singleRow.add(new NavDrawerItem("TOOLS"));
            singleRow.add(new NavDrawerItem("Assignment", R.drawable.ic_action_assignment));
            singleRow.add(new NavDrawerItem("Tip Line", R.drawable.ic_action_tipline));
            singleRow.add(new NavDrawerItem("Share Our App", R.drawable.ic_action_share));
            singleRow.add(new NavDrawerItem("My Alerts", R.drawable.ic_action_alert));
            singleRow.add(new NavDrawerItem("Admin Login", R.drawable.ic_action_login));
            singleRow.add(new NavDrawerItem("About the Developer", R.drawable.ic_action_info));

    }


    @Override
    public int getCount() {
        return singleRow.size();
    }

    @Override
    public Object getItem(int i) {
        return singleRow.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View row=null;

        NavDrawerItem temp = singleRow.get(i);

        if(!singleRow.get(i).isGroupHeader) {
            row = inflater.inflate(R.layout.drawer_list_item, null);


            ImageView imgIcon = (ImageView) row.findViewById(R.id.icon);
            TextView txtTitle = (TextView) row.findViewById(R.id.textAdmin);


            txtTitle.setText(temp.getTitle());
            imgIcon.setImageResource(temp.getIcon());

        }else{
            row = inflater.inflate(R.layout.group_header, null);
            TextView groupTitle = (TextView) row.findViewById(R.id.header);
            groupTitle.setText(temp.getGroupTitles());
        }


        return row;
    }
}
