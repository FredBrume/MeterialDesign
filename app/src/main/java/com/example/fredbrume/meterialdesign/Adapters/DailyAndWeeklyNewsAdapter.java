package com.example.fredbrume.meterialdesign.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fredbrume.meterialdesign.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import com.example.fredbrume.meterialdesign.Model.News;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CeeDe on 12/3/2016.
 */
public class DailyAndWeeklyNewsAdapter extends RecyclerView.Adapter<DailyAndWeeklyNewsAdapter.DailyWeeklyViewHolder> {

    Context context;
    private LinkedList<News> newsList;
    FileDownloadHandler fileDownloadHandler;

    public DailyAndWeeklyNewsAdapter(FileDownloadHandler fileDownloadHandler) {

        this.fileDownloadHandler = fileDownloadHandler;
    }

    @Override
    public DailyWeeklyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        boolean shouldAttachToParentImmediately = false;
        int layoutIdForListItem = R.layout.rowitem_daily_weekly;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new DailyWeeklyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyWeeklyViewHolder holder, int position) {

        News news = newsList.get(position);

        if (news.eventStartTime.equals("") || news.eventEndTime.equals("")) {
            holder.eventTime.setText("TBA");
        } else {
            holder.eventTime.setText(news.eventStartTime + " - " + news.eventEndTime);
        }
        if (news.attachUrl == null) {
            holder.attachmentPanel.setVisibility(View.GONE);
        } else {
            Picasso.with(context)
                    .load(Config.buildUserImageUrl(news.attachUrl)).into(holder.attachment);
        }

        if (!news.newsContext.isEmpty() && news.newsContext.length() > 200) {
            holder.showmore.setVisibility(View.VISIBLE);
        }

        holder.eventDate.setText(news.eventDate);
        holder.subTitle.setText(news.newsTitle);
        holder.newsContext.setText(news.newsContext);

    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }


    public class DailyWeeklyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.subtitle)
        TextView subTitle;
        @BindView(R.id.news_group)
        TextView newsGroup;
        @BindView(R.id.context)
        TextView newsContext;
        @BindView(R.id.event_Date)
        TextView eventDate;
        @BindView(R.id.event_Time)
        TextView eventTime;
        @BindView(R.id.show_more)
        TextView showmore;
        @BindView(R.id.attachment)
        ImageView attachment;
        @BindView(R.id.download)
        ImageView download;
        @BindView(R.id.share)
        ImageView share;
        @BindView(R.id.attachmentPanel)
        LinearLayout attachmentPanel;


        public DailyWeeklyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            download.setOnClickListener(this);
            share.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            News news = newsList.get(getAdapterPosition());

            switch (view.getId()) {

                case R.id.download:

                    Log.d("ONCLICK CLICK", "ON CLICK");

                    String filename = news.attachUrl;

                    if (filename == null) {
                        Toast.makeText(context, "No File to download", Toast.LENGTH_SHORT).show();
                    }
                    fileDownloadHandler.downloadFile(filename);

                    break;

                case R.id.share:

                    fileDownloadHandler.shareFile(news);
                    break;


            }
        }
    }

    public interface FileDownloadHandler {

        void downloadFile(String filename);

        void shareFile(News news);
    }

    public void setLayoutData(LinkedList<News> newsList) {

        this.newsList = newsList;
        notifyDataSetChanged();
    }
}
