package com.example.fredbrume.meterialdesign.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.R;
import com.squareup.picasso.Picasso;

import com.example.fredbrume.meterialdesign.Model.News;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CeeDe on 12/6/2016.
 */
public class NewsHeadingFragment extends Fragment {

    @BindView(R.id.news_logo)
    ImageView newsLogo;
    @BindView(R.id.news_date)
    TextView newsDate;
    @BindView(R.id.news_title)
    TextView newsTitle;
    @BindView(R.id.news_type)
    TextView newsType;

    private static News news;

    public NewsHeadingFragment() {

    }

    public static NewsHeadingFragment getInstance( News news) {

        NewsHeadingFragment.news = news;
        NewsHeadingFragment fragment = new NewsHeadingFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_heading_layout, container, false);
        ButterKnife.bind(this, view);

        assert (news != null);

        if (news.newsType.equals(Config.EVENT_ALERT_DAILY)) {
            newsTitle.setText(Config.EVENT_ALERT_DAILY_VALUE);
        } else if (news.newsType.equals(Config.EVENT_ALERT_WEEKLY)) {
            newsTitle.setText(Config.EVENT_ALERT_WEEKLY_VALUE);
        } else {
            newsTitle.setText(news.newsTitle);
        }


        newsDate.setText(news.newsDate + " " + news.newsTime);
        newsType.setText(news.newsType);
        Picasso.with(getActivity()).
                load(Config.buildUserImageUrl(news.imageUrl)).into(newsLogo);

        return view;
    }
}
