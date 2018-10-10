package com.example.fredbrume.meterialdesign.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.Model.News;
import com.example.fredbrume.meterialdesign.R;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CeeDe on 12/6/2016.
 */
public class NewsContentFragment extends Fragment {

    private static News news;
    @BindView(R.id.news_context)
    TextView newsContext;
    @BindView(R.id.news_attachment)
    ImageView newsAttachment;

    public NewsContentFragment() {

    }

    public static NewsContentFragment getInstance(News news) {

        NewsContentFragment.news = news;
        NewsContentFragment fragment = new NewsContentFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_content_layout, container, false);
        ButterKnife.bind(this, view);

        if (news != null) {
            newsContext.setText(news.newsContext);

            if ((!news.attachUrl.isEmpty()) || (news.attachUrl != null)) {
                Picasso.with(getContext().getApplicationContext()).
                        load(Config.buildNewsAttachmentUrl(news.attachUrl)).into(newsAttachment);
            }
        }
        return view;
    }


}
