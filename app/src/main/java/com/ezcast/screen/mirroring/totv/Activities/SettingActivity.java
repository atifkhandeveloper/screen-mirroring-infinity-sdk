package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;

import com.ezcast.screen.mirroring.totv.R;


public class SettingActivity extends ThemeActivity {

    LinearLayout apps;
    ImageView back_icon;
    LinearLayout how_to_use;
    LinearLayout privacy_policy;
    LinearLayout share_app;
    LinearLayout rate_us;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
//        edgeToEdge(this, findViewById(R.id.mainLayout));
//
//
//
//
//        this.how_to_use = (LinearLayout) findViewById(R.id.how_to_use_layout);
//        this.apps = (LinearLayout) findViewById(R.id.setting_apps_layout);
//        this.share_app = (LinearLayout) findViewById(R.id.share_layout);
//        this.back_icon = (ImageView) findViewById(R.id.back_icon_setting_toolbar);
//        this.privacy_policy = (LinearLayout) findViewById(R.id.privacy_policy_layout);
//        this.rate_us = (LinearLayout) findViewById(R.id.rate_us_layout);


        final AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.scale);
        this.apps.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                SettingActivity.this.startActivity(new Intent(SettingActivity.this, LanguageActivity.class));
            }
        });

        this.how_to_use.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                SettingActivity.this.m211lambda$onCreate$0$comexampleunimoteActivitiesSettingActivity(view);
            }
        });
        this.share_app.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                SettingActivity.this.m212lambda$onCreate$1$comexampleunimoteActivitiesSettingActivity(view);
            }
        });
        this.privacy_policy.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                SettingActivity.this.m213lambda$onCreate$2$comexampleunimoteActivitiesSettingActivity(view);
            }
        });

        this.back_icon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                SettingActivity.this.m215lambda$onCreate$4$comexampleunimoteActivitiesSettingActivity(view);
            }
        });

        this.rate_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

    }

    
    
    public  void m211lambda$onCreate$0$comexampleunimoteActivitiesSettingActivity(View view) {
        startActivity(new Intent(this, HowToConnectActivity.class));
    }

    
    
    public  void m212lambda$onCreate$1$comexampleunimoteActivitiesSettingActivity(View view) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", "Hey check out my app at: https://play.google.com/store/apps/details?id=" + getPackageName());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Share File"));
    }

    
    
    public  void m213lambda$onCreate$2$comexampleunimoteActivitiesSettingActivity(View view) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://nibtokojoinapps.blogspot.com/2026/07/nib-toko-join-apps.html"));
        startActivity(intent);
    }



    
    
    public  void m215lambda$onCreate$4$comexampleunimoteActivitiesSettingActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void openLinkInBrowser(String str) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }

//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//    }
}
