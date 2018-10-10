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

import com.example.fredbrume.meterialdesign.Model.Assignment;
import com.example.fredbrume.meterialdesign.Model.Subject;
import com.example.fredbrume.meterialdesign.R;

import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by fredbrume on 11/5/17.
 */

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.NewsViewHolder> {

    private Context context;


    public AssignmentOnClickHandler assignmentOnClickHandler;
    private List<Assignment> assignmentList;

    public interface AssignmentOnClickHandler {

        void onAssignmentItemClick(Assignment assignment);

    }

    public AssignmentAdapter(AssignmentOnClickHandler assignmentOnClickHandler) {
        this.assignmentOnClickHandler = assignmentOnClickHandler;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int layoutIdForListItem = R.layout.assignment_list_item;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new NewsViewHolder(view);

    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        Assignment assignment = assignmentList.get(position);

        holder.title.setText(assignment.getAssignmentName());
        holder.date.setText(assignment.getDate());
        holder.id.setText(String.valueOf(assignment.getType().charAt(0)));
        holder.reminder.setVisibility((assignment.getReminder().equals("true"))? View.VISIBLE : View.INVISIBLE);

    }


    @Override
    public int getItemCount() {
        return assignmentList == null ? 0 : assignmentList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.id)
        TextView id;

        @BindView(R.id.reminder)
        ImageView reminder;

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

                    assignmentOnClickHandler.onAssignmentItemClick(assignmentList.get(getAdapterPosition()));
                }
            }, 500L);
        }

    }

    public void setLayoutData(List<Assignment> assignments) {

        this.assignmentList = assignments;
        notifyDataSetChanged();
    }
}
