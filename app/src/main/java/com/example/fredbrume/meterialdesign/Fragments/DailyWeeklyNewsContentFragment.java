package com.example.fredbrume.meterialdesign.Fragments;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.fredbrume.meterialdesign.Activity.ViewPropertyHandler;
import com.example.fredbrume.meterialdesign.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

import com.example.fredbrume.meterialdesign.Adapters.DailyAndWeeklyNewsAdapter;
import com.example.fredbrume.meterialdesign.Model.News;

import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyJsonStringUtil;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyStringRequest;
import butterknife.BindView;


/**
 * Created by CeeDe on 12/19/2016.
 */
public class DailyWeeklyNewsContentFragment extends BaseFragment implements DailyAndWeeklyNewsAdapter.FileDownloadHandler {

    private static News news;
    @BindView(R.id.news_list)
    RecyclerView newsList;
    private DailyAndWeeklyNewsAdapter adapter;

    public DailyWeeklyNewsContentFragment() {

    }

    public static DailyWeeklyNewsContentFragment getInstance( News news) {

        DailyWeeklyNewsContentFragment.news = news;
        DailyWeeklyNewsContentFragment fragment = new DailyWeeklyNewsContentFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        adapter = new DailyAndWeeklyNewsAdapter(this);
        newsList.setAdapter(adapter);
        getDailyAndWeeklyRequest();
        viewPropertyHandler.setBackgroundMainLogo(0.7f);

        return view;

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_daily_weekly_content;
    }

    private void getDailyAndWeeklyRequest() {

        final VolleyStringRequest volleyStringRequest = new VolleyStringRequest(getContext(), Config.buildDailyWeeklyFeedsUrl());
        volleyStringRequest.addHeader("Content-Type", "application/form-data; charset=utf-8");
        volleyStringRequest.addParams("Sort", news.newsType);
        volleyStringRequest.addParams("WeekDate", news.newsDate);
        volleyStringRequest.addParams("DayTime", news.newsTime);

        volleyStringRequest.executeRequest(Request.Method.POST, new VolleyStringRequest.VolleyCallback() {

                    @Override
                    public void getResponse(String response) {

                        Log.d(this.getClass().getSimpleName(), response);

                        adapter.setLayoutData(VolleyJsonStringUtil.vJsonStringToList(response));

                    }
                }
        );

    }

    @Override
    public void downloadFile(final String filename) {
        if (haveStoragePermission()) {

            if (filename == null) {
                return;
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Config.buildUserImageUrl(filename)));
            request.setTitle(filename);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

            DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }
    }

    private boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error", "You have permission");
                return true;
            } else {
                Log.e("Permission error", "You have asked for permission");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }

        } else {
            Log.e("Permission error", "You already have the permission");
            return true;
        }
    }


    @Override
    public void shareFile(final News news) {

        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, news.newsTitle);
        intent.putExtra(Intent.EXTRA_TEXT, news.newsContext);

        if (!(news == null && news.attachUrl == null)) {

            Picasso.with(getContext()).load(Config.buildUserImageUrl(news.attachUrl)).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        } else {
            intent.setType("text/plain");
        }

        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_using)));
    }


    private Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

}
