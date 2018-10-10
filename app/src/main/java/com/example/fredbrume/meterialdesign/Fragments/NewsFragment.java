package com.example.fredbrume.meterialdesign.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fredbrume.meterialdesign.R;

import com.example.fredbrume.meterialdesign.Model.News;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;


/**
 * Created by fredbrume on 11/7/17.
 */

public class NewsFragment extends Fragment {

    public final static String BUNDLE_TAG = NewsFragment.class.getSimpleName();
    private static News news;


    public NewsFragment() {

    }

    public static NewsFragment getInstance(News news) {

        NewsFragment.news = news;
        NewsFragment fragment = new NewsFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);


        setHeadingAndContentLayout();

        return view;
    }

    private void setHeadingAndContentLayout() {

        if (news.newsType.equals(Config.EVENT_GENERAL)) {
            sendGeneralNewsFeed(news);

        } else if (news.newsType.equals(Config.EVENT_ALERT_DAILY)) {

            sendDailyAndWeeklyNewsFeed(news);

        } else if (news.newsType.equals(Config.EVENT_ALERT_WEEKLY)) {

            sendDailyAndWeeklyNewsFeed(news);

        } else {
            sendTeacherNewsFeed(news);
        }
    }

    private void sendGeneralNewsFeed(News news) {

        openChildFragment(NewsHeadingFragment.getInstance(news), R.id.news_heading, news, NewsHeadingFragment.class.getSimpleName());
        openChildFragment(NewsContentFragment.getInstance(news), R.id.news_content, news, NewsContentFragment.class.getSimpleName());

    }

    private void sendDailyAndWeeklyNewsFeed(News news) {
        openChildFragment(NewsHeadingFragment.getInstance(news), R.id.news_heading, news, NewsHeadingFragment.class.getSimpleName());
        openChildFragment(DailyWeeklyNewsContentFragment.getInstance(news), R.id.news_content, news, DailyWeeklyNewsContentFragment.class.getSimpleName());
    }

    private void sendTeacherNewsFeed(News news) {

        openChildFragment(TeacherNewsHeadingFragment.getInstance(news), R.id.news_heading, news, TeacherNewsHeadingFragment.class.getSimpleName());
        openChildFragment(DailyWeeklyNewsContentFragment.getInstance(news), R.id.news_content, news, DailyWeeklyNewsContentFragment.class.getSimpleName());

    }

    private void openChildFragment(Fragment fragment, int fragmentLayout, News news, String stackTag) {
        if (fragment != null && news != null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(fragmentLayout, fragment, stackTag). addToBackStack(null).commit();
        }
    }

}
