package com.ezcast.screen.mirroring.totv.Activities.ViewPager;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.viewpager.widget.ViewPager;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.ThemeActivity;
import com.ezcast.screen.mirroring.totv.Activities.VideosActivity;
import com.ezcast.screen.mirroring.totv.Adapter.VideosViewpagerAdapter;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import java.util.ArrayList;


public class VideosViePagerActivity extends ThemeActivity {
    public static String currentVideoPath;
    public static int videoIndex;
    ArrayList<CastingModel> allVideos;
    ImageView back_icon;
    ImageView cast_video;
    ViewPager viewPager;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_videos_vie_pager);
        edgeToEdge(this, findViewById(R.id.mainLayout));



        this.viewPager = (ViewPager) findViewById(R.id.videos_ViewPAger);
        this.back_icon = (ImageView) findViewById(R.id.videos_viewpager_toolbar_back_icon);
        this.allVideos = VideosActivity.videosList;
        int i = getIntent().getExtras().getInt("videoIndex");
        videoIndex = i;
        currentVideoPath = this.allVideos.get(i).getVideo();
        this.viewPager.setAdapter(new VideosViewpagerAdapter(this.allVideos, this));
        this.viewPager.setCurrentItem(videoIndex);
        this.back_icon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                VideosViePagerActivity.this.m220xf32fe96e(view);
            }
        });
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { 
            @Override 
            public void onPageScrollStateChanged(int i2) {
            }

            @Override 
            public void onPageScrolled(int i2, float f, int i3) {
            }

            @Override 
            public void onPageSelected(int i2) {
                VideosViePagerActivity.currentVideoPath = null;
                VideosViePagerActivity.currentVideoPath = VideosViePagerActivity.this.allVideos.get(i2).getVideo();
            }
        });
    }

    
    
    public  void m220xf32fe96e(View view) {
        onBackPressed();
    }
}
