package com.example.fredbrume.meterialdesign.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by fredbrume on 11/5/17.
 */

public class CustomDialogAdapter extends RecyclerView.Adapter<CustomDialogAdapter.NewsViewHolder> {

    private Context context;


    public CustomDialogOnClickHandler customDialogOnClickHandler;
    private String[] data;

    public interface CustomDialogOnClickHandler {

        void onDialogItemClick(String teacher);
    }

    public CustomDialogAdapter(CustomDialogOnClickHandler customDialogOnClickHandler) {
        this.customDialogOnClickHandler = customDialogOnClickHandler;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int layoutIdForListItem = R.layout.custom_dialog_list_item;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new NewsViewHolder(view);

    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        String data1= data[position];
        holder.name.setText(data1);

    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        TextView name;


        public NewsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            customDialogOnClickHandler.onDialogItemClick(data[getAdapterPosition()]);
        }
    }

    public void setLayoutData(String[] data) {

        this.data = data;
        notifyDataSetChanged();
    }

}
