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
import com.ezcast.screen.mirroring.totv.Activities.ViewPager.PhotosViewPAgerActivity;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import java.util.ArrayList;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.viewHolder> {
    Context context;
    ArrayList<CastingModel> photosList;

    public PhotosAdapter(ArrayList<CastingModel> arrayList, Context context) {
        this.photosList = arrayList;
        this.context = context;
    }

    @Override 
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.photos_row_item, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(viewHolder viewholder, final int i) {
        Glide.with(this.context).load(this.photosList.get(i).getImg()).apply((BaseRequestOptions<?>) new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(1))).into(viewholder.imageView);
        viewholder.videoIcon.setVisibility(View.GONE);
        viewholder.imageView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                PhotosAdapter.this.m225xbdc2edc9(i, view);
            }
        });
    }

    
    
    public  void m225xbdc2edc9(int i, View view) {
        Intent intent = new Intent(this.context, PhotosViewPAgerActivity.class);
        intent.putExtra("imageIndex", i);
        this.context.startActivity(intent);
    }

    @Override 
    public int getItemCount() {
        return this.photosList.size();
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
