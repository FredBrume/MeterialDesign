package com.example.fredbrume.meterialdesign.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import com.example.fredbrume.meterialdesign.Model.News;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by fredbrume on 11/5/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;


    public NewsOnClickHandler newsOnClickHandler;
    private LinkedList<News> newsList;
    private int lastPosition= -1;

    public interface NewsOnClickHandler {

        void onNewsItemClick(News news);
    }

    public NewsAdapter(NewsOnClickHandler newsOnClickHandler) {
        this.newsOnClickHandler = newsOnClickHandler;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int layoutIdForListItem = (viewType == 0) ? R.layout.teacher_news_list_item : R.layout.news_list_item;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);


        return new NewsViewHolder(view);

    }

    @Override
    public int getItemViewType(int position) {

        final String newsType = newsList.get(position).newsType;

        return ((newsType.equals(Config.EVENT_GENERAL) || newsType.equals(Config.EVENT_ALERT_DAILY) ||
                newsType.equals(Config.EVENT_ALERT_WEEKLY)) ? 1 : 0);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        News news = newsList.get(position);

        switch (getItemViewType(position)) {

            case 0:
                holder.teacherName.setText(news.name);
                break;

            case 1:

                if (news.newsType.equals(Config.EVENT_ALERT_DAILY)) {
                    holder.newsTitle.setText(Config.EVENT_ALERT_DAILY_VALUE);

                } else if (news.newsType.equals(Config.EVENT_ALERT_WEEKLY)) {
                    holder.newsTitle.setText(Config.EVENT_ALERT_WEEKLY_VALUE);

                } else {
                    holder.newsTitle.setText(news.newsTitle);
                }

                break;
        }

        if (news.newsStatus.equals(Config.EVENT_NOT_PINNED)) {
            holder.pinIcon.setVisibility(View.GONE);
        }

        holder.newsBody.setText(news.newsContext);
        holder.newsType.setText(news.newsType);
        holder.newsDate.setText(news.newsDate + " " + news.newsTime);

        Picasso.with(context)
                .load(Config.buildUserImageUrl(news.imageUrl)).into(holder.userImage);
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public void onViewDetachedFromWindow(NewsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }


    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.news_title)
        TextView newsTitle;
        @BindView(R.id.news_type)
        TextView newsType;
        @BindView(R.id.news_body)
        TextView newsBody;
        @BindView(R.id.news_date)
        TextView newsDate;
        @Nullable
        @BindView(R.id.teacher_name)
        TextView teacherName;
        @BindView(R.id.news_logo)
        ImageView userImage;
        @BindView(R.id.pinned_icon)
        ImageView pinIcon;


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

                    newsOnClickHandler.onNewsItemClick(newsList.get(getAdapterPosition()));
                }
            }, 500L);
        }
    }

    public void setLayoutData(LinkedList<News> newsList) {

        for (int i = 0; i < newsList.size(); i++) {

            if (newsList.get(i).newsStatus.equals(Config.EVENT_PINNED)) {

                News model = newsList.get(i);
                newsList.remove(newsList.get(i)); //removing the object from the list
                newsList.addFirst(model); //adding the object at the front of the stack

            }
        }

        this.newsList = newsList;
        notifyDataSetChanged();
    }
}
