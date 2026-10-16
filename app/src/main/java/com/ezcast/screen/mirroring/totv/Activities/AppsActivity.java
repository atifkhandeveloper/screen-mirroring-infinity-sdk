package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;

import com.ezcast.screen.mirroring.totv.R;


public class AppsActivity extends ThemeActivity {
    private Activity activity;
    RelativeLayout amazon;
    ImageView back_icon;
    private Context content;
    RelativeLayout dailymotion;
    RelativeLayout facebook;
    RelativeLayout google;
    RelativeLayout netflix;
    RelativeLayout twitch;
    RelativeLayout vimeo;
    RelativeLayout youtube;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_apps);
        edgeToEdge(this, findViewById(R.id.main));
//
//        if (PremiumManager.INSTANCE.shouldShowAds(this)) {
//            AdAdmob adAdmob = new AdAdmob( this);
//            adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//            adAdmob.FullscreenAd_Counter(this);
//        }



        initializeFields();

        final AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.scale);
        this.youtube.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AppsActivity.this.m165lambda$onCreate$0$comexampleunimoteActivitiesAppsActivity(animatorSet, view);
            }
        });
        this.google.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AppsActivity.this.m166lambda$onCreate$1$comexampleunimoteActivitiesAppsActivity(animatorSet, view);
            }
        });
        this.netflix.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AppsActivity.this.m167lambda$onCreate$2$comexampleunimoteActivitiesAppsActivity(animatorSet, view);
            }
        });
        this.facebook.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AppsActivity.this.m168lambda$onCreate$3$comexampleunimoteActivitiesAppsActivity(animatorSet, view);
            }
        });
        this.vimeo.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AppsActivity.this.m169lambda$onCreate$4$comexampleunimoteActivitiesAppsActivity(animatorSet, view);
            }
        });
        this.amazon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AppsActivity.this.m170lambda$onCreate$5$comexampleunimoteActivitiesAppsActivity(animatorSet, view);
            }
        });
        this.dailymotion.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AppsActivity.this.m171lambda$onCreate$6$comexampleunimoteActivitiesAppsActivity(animatorSet, view);
            }
        });
        this.twitch.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AppsActivity.this.m172lambda$onCreate$7$comexampleunimoteActivitiesAppsActivity(animatorSet, view);
            }
        });
        this.back_icon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AppsActivity.this.m173lambda$onCreate$8$comexampleunimoteActivitiesAppsActivity(view);
            }
        });
    }

    
    
    public  void m165lambda$onCreate$0$comexampleunimoteActivitiesAppsActivity(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(this.youtube);
        animatorSet.start();
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("browserCheck", "youtube");
        startActivity(intent);
    }

    
    
    public  void m166lambda$onCreate$1$comexampleunimoteActivitiesAppsActivity(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(this.google);
        animatorSet.start();
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("browserCheck", "google");
        startActivity(intent);
    }

    
    
    public  void m167lambda$onCreate$2$comexampleunimoteActivitiesAppsActivity(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(this.netflix);
        animatorSet.start();
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("browserCheck", "netflix");
        startActivity(intent);
    }

    
    
    public  void m168lambda$onCreate$3$comexampleunimoteActivitiesAppsActivity(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(this.facebook);
        animatorSet.start();
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("browserCheck", "facebook");
        startActivity(intent);
    }

    
    
    public  void m169lambda$onCreate$4$comexampleunimoteActivitiesAppsActivity(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(this.vimeo);
        animatorSet.start();
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("browserCheck", "vimeo");
        startActivity(intent);
    }

    
    
    public  void m170lambda$onCreate$5$comexampleunimoteActivitiesAppsActivity(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(this.amazon);
        animatorSet.start();
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("browserCheck", "amazon");
        startActivity(intent);
    }

    
    
    public  void m171lambda$onCreate$6$comexampleunimoteActivitiesAppsActivity(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(this.dailymotion);
        animatorSet.start();
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("browserCheck", "dailymotion");
        startActivity(intent);
    }

    
    
    public  void m172lambda$onCreate$7$comexampleunimoteActivitiesAppsActivity(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(this.twitch);
        animatorSet.start();
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("browserCheck", "twitch");
        startActivity(intent);
    }

    
    
    public  void m173lambda$onCreate$8$comexampleunimoteActivitiesAppsActivity(View view) {
        onBackPressed();
    }

    private void initializeFields() {
        this.content = this;
        this.activity = this;
        this.youtube = (RelativeLayout) findViewById(R.id.apps_youtube_layout);
        this.google = (RelativeLayout) findViewById(R.id.apps_google_layout);
        this.netflix = (RelativeLayout) findViewById(R.id.apps_netflix_layout);
        this.facebook = (RelativeLayout) findViewById(R.id.apps_facebook_layout);
        this.vimeo = (RelativeLayout) findViewById(R.id.apps_vimeo_layout);
        this.amazon = (RelativeLayout) findViewById(R.id.apps_amazon_layout);
        this.dailymotion = (RelativeLayout) findViewById(R.id.apps_dm_layout);
        this.back_icon = (ImageView) findViewById(R.id.back_apps_icon);
        this.twitch = (RelativeLayout) findViewById(R.id.apps_twitch_layout);
    }
}
