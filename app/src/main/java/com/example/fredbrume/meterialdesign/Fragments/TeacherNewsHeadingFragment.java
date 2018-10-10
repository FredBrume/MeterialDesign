package com.example.fredbrume.meterialdesign.Fragments;

import android.annotation.TargetApi;
import android.os.Build;
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
public class TeacherNewsHeadingFragment extends Fragment {


    private static News news;
    @BindView(R.id.news_logo)
    ImageView newsLogo;
    @BindView(R.id.news_date)
    TextView newsDate;
    @BindView(R.id.news_title)
    TextView newsTitle;
    @BindView(R.id.news_type)
    TextView newsType;
    @BindView(R.id.teacher_name)
    TextView teacherName;


    public TeacherNewsHeadingFragment() {

    }

    public static TeacherNewsHeadingFragment getInstance(News news) {

        TeacherNewsHeadingFragment.news = news;
        TeacherNewsHeadingFragment fragment = new TeacherNewsHeadingFragment();

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.teacher_news_heading_layout, container, false);
        ButterKnife.bind(this, view);

        if (news.newsType.equals(Config.EVENT_ALERT_DAILY)) {
            newsTitle.setText(Config.EVENT_ALERT_DAILY_VALUE);
        } else if (news.newsType.equals(Config.EVENT_ALERT_WEEKLY)) {
            newsTitle.setText(Config.EVENT_ALERT_WEEKLY_VALUE);
        } else {
            newsTitle.setText(news.newsTitle);
        }
        teacherName.setText(news.name);
        newsDate.setText(news.newsTime);
        newsType.setText(news.newsType);
        Picasso.with(getActivity()).
                load(Config.buildUserImageUrl(news.imageUrl)).into(newsLogo);

        return view;
    }

}





