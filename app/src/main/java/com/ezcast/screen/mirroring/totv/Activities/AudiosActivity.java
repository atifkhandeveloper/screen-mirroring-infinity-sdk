package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcast.screen.mirroring.totv.Classes.InternetPermission;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Adapter.AudiosAdpater;
import com.ezcast.screen.mirroring.totv.MediaStore.GetAudios;
import com.ezcast.screen.mirroring.totv.Models.AudioModel;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfig;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfigExtensionsKt;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdUnitConfig;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager;

import java.util.ArrayList;
import java.util.Iterator;


public class AudiosActivity extends ThemeActivity {
    private static final int MY_READ_PERMISSION_CODE = 103;
    public static ArrayList<AudioModel> audioList = new ArrayList<>();

    ConstraintLayout audio_layout;
    AudiosAdpater audiosAdpater;
    ImageView audios_search_icon;
    TextView audios_searching_textview;
    ImageView back_icon;
    String currentAudioPath;
    ArrayList<AudioModel> filteredList = new ArrayList<>();
    ImageView open_search_toolbar_btn;
    RecyclerView recyclerView;
    String searchText;
    ConstraintLayout search_layout;
    boolean searchable;
    private FrameLayout frBanner;
    private boolean bannerLoaded = false;

    private static final String TAG = "AudiosActivity";
    private boolean backInterReady = false;
    private boolean backInterLoading = false;
    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_audios);
        edgeToEdge(this, findViewById(R.id.mainLayout));


        this.frBanner = findViewById(R.id.fr_banner);

        loadMainBanner();
        loadBackInterstitial();


        this.recyclerView = (RecyclerView) findViewById(R.id.audios_recyclerView);
        this.recyclerView = (RecyclerView) findViewById(R.id.audios_recyclerView);
        this.open_search_toolbar_btn = (ImageView) findViewById(R.id.open_search_audio_btnn);
        this.audio_layout = (ConstraintLayout) findViewById(R.id.audio_layout);
        this.search_layout = (ConstraintLayout) findViewById(R.id.audios_search_layout);
        this.back_icon = (ImageView) findViewById(R.id.back_press);
        this.audios_searching_textview = (TextView) findViewById(R.id.audios_searching_textview);
        this.audios_search_icon = (ImageView) findViewById(R.id.audios_search_icon);

        audioList = (ArrayList) GetAudios.getAllAudios(this);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        AudiosAdpater audiosAdpater = new AudiosAdpater(this, audioList);
        this.audiosAdpater = audiosAdpater;
        this.recyclerView.setAdapter(audiosAdpater);
        this.audio_layout.setVisibility(View.VISIBLE);
        final AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.scale);
        this.open_search_toolbar_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AudiosActivity.this.audio_layout.setVisibility(View.GONE);
                AudiosActivity.this.search_layout.setVisibility(View.VISIBLE);
                AudiosActivity.this.searchable = true;
            }
        });
        this.back_icon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                AudiosActivity.this.m174lambda$onCreate$0$comexampleunimoteActivitiesAudiosActivity(animatorSet, view);
            }
        });
        this.audios_searching_textview.addTextChangedListener(new TextWatcher() { 
            @Override 
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override 
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String lowerCase = charSequence.toString().toLowerCase();
                AudiosActivity.this.filteredList = new ArrayList<>();
                Iterator<AudioModel> it = AudiosActivity.audioList.iterator();
                while (it.hasNext()) {
                    AudioModel next = it.next();
                    if (next.getName().toLowerCase().contains(lowerCase)) {
                        AudiosActivity.this.filteredList.add(next);
                    }
                }
                AudiosActivity.this.audiosAdpater.searchItem(AudiosActivity.this.filteredList);
            }

            @Override 
            public void afterTextChanged(Editable editable) {
                if (AudiosActivity.this.filteredList.isEmpty()) {
                    AudiosActivity audiosActivity = AudiosActivity.this;
                    Toast.makeText(audiosActivity, audiosActivity.getString(R.string.not_in_the_list), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    
    
    public  void m174lambda$onCreate$0$comexampleunimoteActivitiesAudiosActivity(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(this.back_icon);
        animatorSet.start();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        // Closing the search panel is not leaving the screen, so no ad here.
        if (this.searchable) {
            this.audio_layout.setVisibility(View.VISIBLE);
            this.search_layout.setVisibility(View.GONE);
            this.searchable = false;
            this.audios_searching_textview.setText("");
            return;
        }
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
}
