package com.example.fredbrume.meterialdesign.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fredbrume.meterialdesign.R;
import com.squareup.picasso.Picasso;

import com.example.fredbrume.meterialdesign.Model.Banner;
import com.example.fredbrume.meterialdesign.Data.Remote.Api.Config;
import butterknife.BindView;

/**
 * Created by SONU on 29/08/15.
 */
public class SlidingImageAdapter extends PagerAdapter {


    private Banner[] IMAGES;
    private LayoutInflater inflater;
    private Context context;
    @BindView(R.id.bannerImage)
    ImageView bannerImage;


    public SlidingImageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES == null ? 0 : IMAGES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View imageLayout = inflater.inflate(R.layout.backdrop_item_layout, view, false);

        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.bannerImage);

//        ButterKnife.bind(imageLayout);

        assert imageLayout != null;

        Banner banner = IMAGES[position];

        Picasso.with(context).load(Config.buildBanneImagerUrl(banner.Banner_IMG)).into(imageView);
        view.addView(imageLayout);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    public void setSliderImages(Banner[] images) {
        IMAGES = images;
        notifyDataSetChanged();
    }


}
