package com.example.fredbrume.meterialdesign.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import com.example.fredbrume.meterialdesign.Model.Document;
import com.example.fredbrume.meterialdesign.Model.Subject;
import com.example.fredbrume.meterialdesign.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by fredbrume on 11/5/17.
 */

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.NewsViewHolder> {

    private Context context;


    public ClassOnClickHandler classOnClickHandler;
    private List<Subject> subjectList;

    public interface ClassOnClickHandler {

        void onClassItemClick(Subject subject);
        void onClassItemDeleteOnLongClick(Subject subject);
    }

    public ClassAdapter(ClassOnClickHandler classOnClickHandler) {
        this.classOnClickHandler = classOnClickHandler;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int layoutIdForListItem = R.layout.class_list_item;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new NewsViewHolder(view);

    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        int tempHour = 0, tempMinute = 0;

        Subject subject = subjectList.get(position);

        holder.subject.setText(subject.getSubject());
        holder.period.setText(subject.getPeriod());
        holder.day.setText(subject.getDays());

        StringTokenizer token = new StringTokenizer(subject.getTime(), ":");
        while (token.hasMoreElements()) {
            tempHour = Integer.parseInt(token.nextElement().toString());
            tempMinute = Integer.parseInt(token.nextElement().toString());
        }
        holder.time.setText(getTime(tempHour, tempMinute, null));

    }


    @Override
    public int getItemCount() {
        return subjectList == null ? 0 : subjectList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.class_name)
        TextView subject;

        @BindView(R.id.period)
        TextView period;

        @BindView(R.id.days)
        TextView day;

        @BindView(R.id.class_time)
        TextView time;

        public NewsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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

                    classOnClickHandler.onClassItemClick(subjectList.get(getAdapterPosition()));
                }
            }, 500L);
        }

        @Override
        public boolean onLongClick(View v) {
             classOnClickHandler.onClassItemDeleteOnLongClick(subjectList.get(getAdapterPosition()));
            return false;
        }
    }

    public void setLayoutData(List<Subject> subjects) {

        this.subjectList = subjects;
        notifyDataSetChanged();
    }

    private String getTime(int tempoHour, int tempoMinute, String format) {

        if (tempoHour == 0) {
            tempoHour += 12;
            format = "AM";
        } else if (tempoHour == 12) {
            format = "PM";
        } else if (tempoHour > 12) {
            tempoHour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        return "(" +Integer.toString(tempoHour) + ":" + Integer.toString(tempoMinute) + " " + format + ")";
    }
}
