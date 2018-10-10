package com.example.fredbrume.meterialdesign.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.android.volley.Request;

import com.example.fredbrume.meterialdesign.Adapters.NewsAdapter;
import com.example.fredbrume.meterialdesign.R;

import com.example.fredbrume.meterialdesign.Model.News;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyStringRequest;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyJsonStringUtil;

import butterknife.BindView;

/**
 * Created by fredbrume on 11/7/17.
 */

public class HomeFragment extends BaseFragment implements NewsAdapter.NewsOnClickHandler {

    private NewsAdapter adapter;
    public GetNewsItemOnClick getNewsItemOnClick;

    @BindView(R.id.item_list)
    RecyclerView itemList;

    @BindView(R.id.bottom_toolbar)
    Toolbar bottomToolbar;

    public interface GetNewsItemOnClick {
        void getNewsDetails(News news);

    }

    public HomeFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            getNewsItemOnClick = (GetNewsItemOnClick) context;

        } catch (ClassCastException exception) {

            throw new ClassCastException(context.toString()
                    + " must implement GetNewsItemOnClick");
        }
    }

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        adapter = new NewsAdapter(this);
        itemList.setAdapter(adapter);

        getNewsFeedRequest();

        viewPropertyHandler.setBackgroundMainLogo(0.5f);
        viewPropertyHandler.setToolBar(R.menu.menu_main);

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home_layout;
    }

    private void getNewsFeedRequest() {

        final VolleyStringRequest volleyStringRequest = new VolleyStringRequest(getContext(), Config.buildNewsUrl().toString());
        volleyStringRequest.addHeader("Authorization", "application/json");
        volleyStringRequest.executeRequest(Request.Method.GET, new VolleyStringRequest.VolleyCallback() {

                    @Override
                    public void getResponse(String response) {

                        Log.d(this.getClass().getSimpleName(), response);
                        adapter.setLayoutData(VolleyJsonStringUtil.vJsonStringToList(response));

                    }
                }
        );
    }


    @Override
    public void onNewsItemClick(News news) {

        getNewsItemOnClick.getNewsDetails(news);
    }


}
