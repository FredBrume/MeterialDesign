package com.example.fredbrume.meterialdesign.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.R;

import com.example.fredbrume.meterialdesign.Model.Location;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by fredbrume on 11/5/17.
 */

public class MapListAdapter extends RecyclerView.Adapter<MapListAdapter.NewsViewHolder> {

    private Context context;


    public LocationOnClickHandler locationsOnClickHandler;
    private Location[] locations;

    public interface LocationOnClickHandler {

        void onLocationItemClick(Location location);
    }

    public MapListAdapter(LocationOnClickHandler locationsOnClickHandler) {
        this.locationsOnClickHandler = locationsOnClickHandler;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int layoutIdForListItem = R.layout.map_list_listitem;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new NewsViewHolder(view);

    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        Location location = locations[position];
        holder.district_name.setText(location.name);

    }


    @Override
    public int getItemCount() {
        return locations == null ? 0 : locations.length;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.district_name)
        TextView district_name;


        public NewsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Animation animatioPinch = AnimationUtils.loadAnimation(context, R.anim.pinch);
            animatioPinch.setDuration(500);
            view.startAnimation(animatioPinch);

            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    locationsOnClickHandler.onLocationItemClick(locations[getAdapterPosition()]);
                }
            }, 500L);
        }
    }

    public void setLayoutData(Location[] locations) {

        this.locations = locations;
        notifyDataSetChanged();
    }
}
