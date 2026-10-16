package com.ezcast.screen.mirroring.totv.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.Exoplayer.VideoExoPlayerActivity;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import java.util.ArrayList;


public class VideosViewpagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<CastingModel> galleryList;

    @Override 
    public int getItemPosition(Object obj) {
        return -2;
    }

    public VideosViewpagerAdapter(ArrayList<CastingModel> arrayList, Context context) {
        this.galleryList = arrayList;
        this.context = context;
    }

    @Override 
    public int getCount() {
        return this.galleryList.size();
    }

    @Override 
    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videos_viewpager_preview_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        if (this.galleryList.get(i).getMimeType().contains("video")) {
            Glide.with(this.context).load(this.galleryList.get(i).getVideo()).into(myViewHolder.view);
        }
        myViewHolder.videoIcon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(VideosViewpagerAdapter.this.context, VideoExoPlayerActivity.class);
                intent.putExtra("ExoPlayerKey", ((CastingModel) VideosViewpagerAdapter.this.galleryList.get(i)).getVideo());
                intent.putExtra("position", i);
                VideosViewpagerAdapter.this.context.startActivity(intent);
            }
        });
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
        ImageView videoIcon;
        ImageView view;

        public MyViewHolder(View view) {
            super(view);
            this.view = (ImageView) view.findViewById(R.id.videos_viewpager_row_item_imgview);
            this.videoIcon = (ImageView) view.findViewById(R.id.ViewPagerVideo_Icon);
        }
    }
}
