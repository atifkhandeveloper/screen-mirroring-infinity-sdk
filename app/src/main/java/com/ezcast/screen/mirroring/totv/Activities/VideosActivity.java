package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Adapter.VideosAdapter;
import com.ezcast.screen.mirroring.totv.MediaStore.GetVideos;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import java.util.ArrayList;


public class VideosActivity extends ThemeActivity {
    public static ArrayList<CastingModel> videosList;
    final int MY_READ_PERMISSION_CODE = 102;
    VideosAdapter adapter;
    
    ImageView back_icon;
    RecyclerView recyclerView;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_videos_second);
        edgeToEdge(this, findViewById(R.id.mainLayout));





        this.recyclerView = (RecyclerView) findViewById(R.id.videos_recyclerView);

        this.back_icon = (ImageView) findViewById(R.id.videos_toolbar_back_icon);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        videosList = GetVideos.ascendingDialog(this);
        VideosAdapter videosAdapter = new VideosAdapter(videosList, this);
        this.adapter = videosAdapter;
        this.recyclerView.setAdapter(videosAdapter);
        this.back_icon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                VideosActivity.this.m217lambda$onCreate$0$comexampleunimoteActivitiesVideosActivity(view);
            }
        });
    }

    
    
    public  void m217lambda$onCreate$0$comexampleunimoteActivitiesVideosActivity(View view) {
        onBackPressed();
    }

    @Override 
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 102) {
            if (iArr.length > 0 && iArr[0] == 0) {
                this.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                videosList = GetVideos.ascendingDialog(this);
                VideosAdapter videosAdapter = new VideosAdapter(videosList, this);
                this.adapter = videosAdapter;
                this.recyclerView.setAdapter(videosAdapter);
                return;
            }
            Toast.makeText(this, getString(R.string.permission_not_granted), Toast.LENGTH_SHORT).show();
        }
    }
}
