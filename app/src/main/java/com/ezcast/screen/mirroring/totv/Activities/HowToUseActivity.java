package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.ads.module.ads.ERainAd;
import com.ads.module.ads.wrapper.ApNativeAd;
import com.ads.module.funtion.AdCallback;
import com.ezcast.screen.mirroring.totv.BuildConfig;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Adapter.HowToUseAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HowToUseActivity extends ThemeActivity {
    boolean aBoolean;
    boolean abBoolean;
    HowToUseAdapter adapter;
    
    BottomNavigationView navigationView;
    View skip_Tv;
    LinearLayout swipe_btn;
    ViewPager viewPager;
    private ApNativeAd mApNativeAd;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_how_to_use1);
        edgeToEdge(this, findViewById(R.id.mainLayout));

//        ERainAd.getInstance().loadNativeAdResultCallback(this, BuildConfig.native_ad, R.layout.shimmer_native_small, new AdCallback() {
//            @Override
//            public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
//                super.onNativeAdLoaded(nativeAd);
//
//                mApNativeAd = nativeAd;
//            }
//
//            @Override
//            public void onAdFailedToLoad(@Nullable LoadAdError i) {
//                super.onAdFailedToLoad(i);
//
//                mApNativeAd = null;
//            }
//
//            @Override
//            public void onAdFailedToShow(@Nullable AdError adError) {
//                super.onAdFailedToShow(adError);
//
//                mApNativeAd = null;
//            }
//        });



        this.navigationView = (BottomNavigationView) findViewById(R.id.how_to_use_bottom_nav_view);
        this.viewPager = (ViewPager) findViewById(R.id.how_to_use_viewPager);
        this.swipe_btn = (LinearLayout) findViewById(R.id.how_to_use_swipe_btn);
        this.skip_Tv = (TextView) findViewById(R.id.skip);

        HowToUseAdapter howToUseAdapter = new HowToUseAdapter(getSupportFragmentManager());
        this.adapter = howToUseAdapter;
        this.viewPager.setAdapter(howToUseAdapter);
        this.swipe_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                HowToUseActivity.this.m198xab04ba12(view);
            }
        });
        this.navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { 
            @Override 
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.first) {
                    HowToUseActivity.this.viewPager.setCurrentItem(0);
                    HowToUseActivity.this.aBoolean = false;
                    HowToUseActivity.this.swipe_btn.setClickable(true);
                    return true;
                } else if (itemId != R.id.second) {
                    return true;
                } else if (itemId != R.id.third) {
                    return true;
                } else {
                    HowToUseActivity.this.viewPager.setCurrentItem(1);
                    HowToUseActivity.this.aBoolean = false;
                    return true;
                }
            }
        });
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { 
            @Override 
            public void onPageScrollStateChanged(int i) {
            }

            @Override 
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override 
            public void onPageSelected(int i) {
                HowToUseActivity.this.navigationView.getMenu().getItem(i).setChecked(true);
                if (i == 0) {
                    HowToUseActivity.this.swipe_btn.setClickable(true);
                    HowToUseActivity.this.aBoolean = false;
                    HowToUseActivity.this.abBoolean = false;
                } else if (i == 1) {
                    HowToUseActivity.this.swipe_btn.setClickable(true);
                    HowToUseActivity.this.aBoolean = true;
                    HowToUseActivity.this.abBoolean = false;
                } else {
                    HowToUseActivity.this.abBoolean = true;
                    HowToUseActivity.this.aBoolean = true;
                }
            }
        });
        this.skip_Tv.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                HowToUseActivity.this.m199xaa8e5413(view);
            }
        });
    }

    
    
    public  void m198xab04ba12(View view) {
        if (!this.aBoolean) {
            this.viewPager.setCurrentItem(1);
            this.aBoolean = true;
        } else if (!this.abBoolean) {
            this.viewPager.setCurrentItem(2);
            this.abBoolean = true;
        } else if (!this.abBoolean) {
            this.viewPager.setCurrentItem(3);
            this.abBoolean = true;
        } else {
            startActivity(new Intent(HowToUseActivity.this, MainActivity.class));
            finish();
        }
    }

    
    
    public  void m199xaa8e5413(View view) {
        startActivity(new Intent(HowToUseActivity.this, MainActivity.class));
        finish();
    }
}
