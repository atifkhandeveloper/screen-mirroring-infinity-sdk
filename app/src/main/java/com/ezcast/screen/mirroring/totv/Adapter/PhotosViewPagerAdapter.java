package com.ezcast.screen.mirroring.totv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.MainActivity;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import java.util.ArrayList;


public class PhotosViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<CastingModel> galleryList;

    @Override 
    public int getItemPosition(Object obj) {
        return -2;
    }

    public PhotosViewPagerAdapter(ArrayList<CastingModel> arrayList, Context context) {
        this.galleryList = arrayList;
        this.context = context;
    }

    @Override 
    public int getCount() {
        return this.galleryList.size();
    }

    @Override 
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photos_viewpager_priview_item, viewGroup, false);
        Glide.with(this.context).load(this.galleryList.get(i).getImg()).into(new MyViewHolder(inflate).imageView);
        MainActivity.castingUrl = this.galleryList.get(i).getImg();
        viewGroup.addView(inflate);
        return inflate;
    }

    @Override 
    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }

    @Override 
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.photos_viewPager_row_item_imgView);
        }
    }
}
