package com.ezcast.screen.mirroring.totv.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.ViewPager.VideosViePagerActivity;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import java.util.ArrayList;


public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.viewHolder> {
    Context context;
    ArrayList<CastingModel> videosList;

    public VideosAdapter(ArrayList<CastingModel> arrayList, Context context) {
        this.videosList = arrayList;
        this.context = context;
    }

    @Override 
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.photos_row_item, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(viewHolder viewholder, final int i) {
        Glide.with(this.context).load(this.videosList.get(i).getVideo()).apply((BaseRequestOptions<?>) new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(1))).into(viewholder.imageView);
        viewholder.videoIcon.setVisibility(View.VISIBLE);
        viewholder.imageView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                VideosAdapter.this.m226xa6656b12(i, view);
            }
        });
    }

    
    
    public  void m226xa6656b12(int i, View view) {
        Intent intent = new Intent(this.context, VideosViePagerActivity.class);
        intent.putExtra("videoIndex", i);
        this.context.startActivity(intent);
    }

    @Override 
    public int getItemCount() {
        return this.videosList.size();
    }

    
    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView videoIcon;

        public viewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.rowItem_images);
            this.videoIcon = (ImageView) view.findViewById(R.id.video_Icon);
        }
    }
}
