package com.ezcast.screen.mirroring.totv.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.Exoplayer.AudioExoPlayerActivty;
import com.ezcast.screen.mirroring.totv.Models.AudioModel;
import java.util.ArrayList;


public class AudiosAdpater extends RecyclerView.Adapter<AudiosAdpater.viewHolder> {
    ArrayList<AudioModel> audiosList;
    Context context;
    int currentPosition = -1;
    MediaPlayer mediaPlayer;

    public AudiosAdpater(Context context, ArrayList<AudioModel> arrayList) {
        new ArrayList();
        this.context = context;
        this.audiosList = arrayList;
        this.mediaPlayer = new MediaPlayer();
    }

    @Override 
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.audios_adapter_row_item, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(viewHolder viewholder, final int i) {
        String valueOf;
        String valueOf2;
        viewholder.audio_name.setText(this.audiosList.get(i).getName());
        viewholder.audiosIcon.setVisibility(View.VISIBLE);
        long duration = this.audiosList.get(i).getDuration() / 1000;
        long j = duration / 60;
        int i2 = (int) (duration % 60);
        if (j <= 9) {
            valueOf = "0" + j;
        } else {
            valueOf = String.valueOf(j);
        }
        if (i2 <= 9) {
            valueOf2 = "0" + i2;
        } else {
            valueOf2 = String.valueOf(i2);
        }
        viewholder.duration.setText(valueOf + ":" + valueOf2);
        viewholder.audio_play.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AudiosAdpater.this.m221x43ebf66b(i, view);
            }
        });
    }

    
    
    public  void m221x43ebf66b(int i, View view) {
        Intent intent = new Intent(this.context, AudioExoPlayerActivty.class);
        intent.putExtra("ExoPlayerKey", this.audiosList.get(i).getPath());
        intent.putExtra("audioPosition", i);
        this.context.startActivity(intent);
    }

    @Override 
    public int getItemCount() {
        return this.audiosList.size();
    }

    public void searchItem(ArrayList<AudioModel> arrayList) {
        this.audiosList = arrayList;
        notifyDataSetChanged();
    }

    
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView audio_name;
        ConstraintLayout audio_play;
        ImageView audiosIcon;
        TextView duration;
        TextView tvPath;

        public viewHolder(View view) {
            super(view);
            this.audiosIcon = (ImageView) view.findViewById(R.id.audio_icon);
            this.audio_play = (ConstraintLayout) view.findViewById(R.id.audio_play_icon);
            this.audio_name = (TextView) view.findViewById(R.id.audio_name_tv);
            this.duration = (TextView) view.findViewById(R.id.audio_duration);
        }
    }
}
