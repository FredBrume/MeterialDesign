package com.example.fredbrume.meterialdesign.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.example.fredbrume.meterialdesign.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

import java.util.ArrayList;
import java.util.List;

import com.example.fredbrume.meterialdesign.Model.Gallery;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyJsonStringUtil;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.VolleyStringRequest;
import butterknife.BindView;

/**
 * Created by fredbrume on 12/7/17.
 */

public class PicturesFragment extends BaseFragment {

    private int gallerySize;
    private Gallery[] gallery;

    @BindView(R.id.scroll_gallery_view)
    ScrollGalleryView scrollGalleryView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getPicturesRequest();

        return view;
    }

    private void getPicturesRequest() {
        final VolleyStringRequest volleyStringRequest = new VolleyStringRequest(getContext(), Config.buildPicturesUrl().toString());
        volleyStringRequest.addHeader("Authorization", "application/json");
        volleyStringRequest.executeRequest(Request.Method.GET, new VolleyStringRequest.VolleyCallback() {

                    @Override
                    public void getResponse(String response) {

                        Log.d(this.getClass().getSimpleName(), response);

                        gallerySize = VolleyJsonStringUtil.vJsonPicturesList(response).length;

                        gallery = VolleyJsonStringUtil.vJsonPicturesList(response);

                        List<MediaInfo> infos = new ArrayList<>(gallerySize);

                        for(int i =0; i < gallerySize; i++)
                        {
                            final int finalI = i;
                            infos.add(MediaInfo.mediaLoader(new MediaLoader() {

                                @Override
                                public boolean isImage() {
                                    return true;
                                }

                                @Override
                                public void loadMedia(Context context, ImageView imageView, final SuccessCallback callback) {

                                    Picasso.with(context).load(Config.buildGalleryUrl(gallery[finalI].images)).into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            callback.onSuccess();
                                        }

                                        @Override
                                        public void onError() {


                                            Log.e("ERROR:", "Loading wasnt successful");
                                        }
                                    });
                                }

                                @Override
                                public void loadThumbnail(Context context, ImageView thumbnailView, final SuccessCallback callback) {

                                    Picasso.with(context).load(Config.buildGalleryUrl(gallery[finalI].images)).into(thumbnailView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            callback.onSuccess();
                                        }

                                        @Override
                                        public void onError() {


                                            Log.e("ERROR:", "Loading wasnt successful");
                                        }
                                    });

                                }
                            }));
                        }

                        scrollGalleryView
                                .setThumbnailSize(100)
                                .setZoom(true)
                                .setFragmentManager(getFragmentManager())
                                .addMedia(infos);

                    }
                }
        );
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_pictures;
    }
}
