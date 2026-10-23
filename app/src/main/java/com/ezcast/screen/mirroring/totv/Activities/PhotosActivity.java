package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcast.screen.mirroring.totv.Classes.InternetPermission;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Adapter.PhotosAdapter;
import com.ezcast.screen.mirroring.totv.MediaStore.GetPhotos;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfig;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfigExtensionsKt;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdUnitConfig;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager;

import java.util.ArrayList;


public class PhotosActivity extends ThemeActivity {
    public static boolean forPermission;
    public static ArrayList<CastingModel> photosList;
    final int MY_READ_PERMISSION_CODE = 101;
    PhotosAdapter adapter;
    
    ImageView back_icon;
    RecyclerView recyclerView;
    private FrameLayout frBanner;
    private boolean bannerLoaded = false;

    private static final String TAG = "PhotosActivity";
    private boolean backInterReady = false;
    private boolean backInterLoading = false;


    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_photos_second);
        edgeToEdge(this, findViewById(R.id.mainLayout));

        this.frBanner = findViewById(R.id.fr_banner);

        loadMainBanner();
        loadBackInterstitial();

        this.recyclerView = (RecyclerView) findViewById(R.id.photos_recyclerView);
        this.back_icon = (ImageView) findViewById(R.id.photos_back_icon);

        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        photosList = GetPhotos.getPhotos(this);
        PhotosAdapter photosAdapter = new PhotosAdapter(photosList, this);
        this.adapter = photosAdapter;
        this.recyclerView.setAdapter(photosAdapter);
        this.back_icon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                PhotosActivity.this.m210lambda$onCreate$0$comexampleunimoteActivitiesPhotosActivity(view);
            }
        });
    }

    
    
    public  void m210lambda$onCreate$0$comexampleunimoteActivitiesPhotosActivity(View view) {
        onBackPressed();
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

    @Override
    public void onBackPressed() {
        // Closing the search panel is not leaving the screen, so no ad here.
        showBackInterstitialThen(() -> super.onBackPressed());
    }

}

