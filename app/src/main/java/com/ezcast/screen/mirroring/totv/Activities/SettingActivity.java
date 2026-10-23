package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;

import com.ezcast.screen.mirroring.totv.Classes.InternetPermission;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.app.AppConstants;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfig;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfigExtensionsKt;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdUnitConfig;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager;
import com.ezcast.screen.mirroring.totv.infinity.component.language.LanguageActivity;


public class SettingActivity extends ThemeActivity {

//    RelativeLayout apps;
    ImageView back_icon;
    RelativeLayout how_to_use;
    RelativeLayout privacy_policy;
    RelativeLayout share_app;
    private FrameLayout frBanner;
    private boolean bannerLoaded = false;
    RelativeLayout rate_us;

    private static final String TAG = "SettingsActivity";
    private boolean backInterReady = false;
    private boolean backInterLoading = false;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        edgeToEdge(this, findViewById(R.id.mainLayout));
//
//
        this.frBanner = findViewById(R.id.fr_banner);

        loadMainBanner();
        loadBackInterstitial();
//
//
        this.back_icon = (ImageView) findViewById(R.id.imv_back);

        this.how_to_use = (RelativeLayout) findViewById(R.id.rlt_language);
//        this.apps = (RelativeLayout) findViewById(R.id.setting_apps_layout);
        this.share_app = (RelativeLayout) findViewById(R.id.rlt_share);
//        this.back_icon = (ImageView) findViewById(R.id.back_icon_setting_toolbar);
        this.privacy_policy = (RelativeLayout) findViewById(R.id.rlt_feedback);
        this.rate_us = (RelativeLayout) findViewById(R.id.rlt_rate);


        final AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.scale);
//        this.apps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingActivity.this.startActivity(new Intent(SettingActivity.this, LanguageActivity.class));
//            }
//        });

        this.how_to_use.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                Intent i = new Intent(SettingActivity.this, LanguageActivity.class);
                i.putExtra(LanguageActivity.EXTRA_FROM_SETTING, true);
                i.putExtra(AppConstants.KEY_SETTING, true);
                startActivity(i);
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
                onBackPressed();
            }
        });

//        this.back_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public final void onClick(View view) {
//                SettingActivity.this.m215lambda$onCreate$4$comexampleunimoteActivitiesSettingActivity(view);
//            }
//        });

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

    private void loadMainBanner() {
        if (frBanner == null) return;

        AdUnitConfig bannerConfig =
                AdRemoteConfigExtensionsKt.getBanner_all(AdRemoteConfig.Companion);

        if (!InternetPermission.isNetworkAvailable(this)) {
            frBanner.setVisibility(View.GONE);
            return;
        }

        frBanner.setVisibility(View.VISIBLE);

        AdsManager.INSTANCE.loadBanner(
                this,
                bannerConfig,
                frBanner,
                false
        );

        bannerLoaded = true;
    }

    @Override
    public void onBackPressed() {
        // Closing the search panel is not leaving the screen, so no ad here.
        showBackInterstitialThen(() -> super.onBackPressed());
    }


    private void loadBackInterstitial() {
        if (!InternetPermission.isNetworkAvailable(this)) return;
        if (backInterLoading) return;

        backInterLoading = true;
        try {
            AdUnitConfig interConfig =
                    AdRemoteConfigExtensionsKt.getInter_home(AdRemoteConfig.Companion);

            if (!interConfig.isEnable()) {
                backInterReady = false;
                return;
            }

            AdsManager.INSTANCE.loadInterHome(this, true);
            backInterReady = true;
        } catch (Throwable t) {
            Log.w(TAG, "Back interstitial load failed", t);
            backInterReady = false;
        } finally {
            backInterLoading = false;
        }
    }

    private void showBackInterstitialThen(Runnable action) {
        if (!backInterReady || !InternetPermission.isNetworkAvailable(this)) {
            action.run();
            return;
        }

        try {
            AdsManager.INSTANCE.showInterBack(this, true, () -> {
                backInterReady = false;
                action.run();
                return kotlin.Unit.INSTANCE;
            });
        } catch (Throwable t) {
            Log.w(TAG, "Back interstitial show failed", t);
            backInterReady = false;
            action.run();
        }
    }
}


//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//    }

