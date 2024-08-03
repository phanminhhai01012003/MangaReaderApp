package com.manga.mangareaderapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.manga.mangareaderapp.R;
import com.manga.mangareaderapp.model.BannerManga;
import com.manga.mangareaderapp.viewmodel.MangaDetailsAct;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    Context context;
    List<BannerManga> Banners;

    public BannerAdapter(Context context, List<BannerManga> banners) {
        this.context = context;
        Banners = banners;
    }

    @Override
    public int getCount() {
        return Banners.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        ImageView imgBanner = view.findViewById(R.id.img_banner);

        Glide.with(context).load(Banners.get(position).getBackdrop()).into(imgBanner);
        container.addView(view);

        view.setOnClickListener(v -> {
            Intent intent = new Intent(context, MangaDetailsAct.class);
            intent.putExtra("bannerManga", Banners.get(position));
            context.startActivity(intent);

        });

        return view;
    }
}
