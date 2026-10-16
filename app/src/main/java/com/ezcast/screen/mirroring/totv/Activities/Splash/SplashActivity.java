package com.ezcast.screen.mirroring.totv.Activities.Splash;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;

import com.ads.module.ads.ERainAd;
import com.ads.module.funtion.AdCallback;
import com.ezcast.screen.mirroring.totv.Activities.LanguageActivity;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.ThemeActivity;
import com.ezcast.screen.mirroring.totv.main.SharePrefs;
import com.ezcast.screen.mirroring.totv.BuildConfig;

public class SplashActivity extends ThemeActivity {

    // Hard fallback timeout (ms) – guarantees navigation even if the ad SDK never calls back
    private static final long FALLBACK_TIMEOUT_MS = 15000L;

    SharePrefs prefs;
    private ProgressBar splashProgress;

    private boolean isNavigated = false;
    private Handler fallbackHandler;
    private Runnable fallbackRunnable;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        this.prefs = new SharePrefs(this);
        edgeToEdge(this, findViewById(R.id.mainLayout));

//        splashProgress = findViewById(R.id.splash_progress);
        showProgress(true);

        // Start the fallback timer FIRST – this guarantees we always move on
        startFallbackTimer();

        // Then attempt to load the splash ad
        loadSplashAd();
    }

    /**
     * Guarantees navigation to the next screen after FALLBACK_TIMEOUT_MS,
     * even if the ad SDK never calls onNextAction().
     */
    private void startFallbackTimer() {
        fallbackHandler = new Handler(Looper.getMainLooper());
        fallbackRunnable = new Runnable() {
            @Override
            public void run() {
                goToNextScreen();
            }
        };
        fallbackHandler.postDelayed(fallbackRunnable, FALLBACK_TIMEOUT_MS);
    }

    private void cancelFallbackTimer() {
        if (fallbackHandler != null && fallbackRunnable != null) {
            fallbackHandler.removeCallbacks(fallbackRunnable);
        }
    }

    private void loadSplashAd() {
//        try {
//            ERainAd.getInstance().loadSplashInterstitialAds(
//                    this,
//                    BuildConfig.inter,
//                    25000,
//                    5000,
//                    new AdCallback() {
//                        @Override
//                        public void onNextAction() {
//                            super.onNextAction();
//                            goToNextScreen();
//                        }
//                    }
//            );
//        } catch (Exception e) {
//            // If anything goes wrong while loading, just skip the ad
//            e.printStackTrace();
//            goToNextScreen();
//        }
    }

    /**
     * Navigate to the next screen exactly once.
     */
    private void goToNextScreen() {
        if (isNavigated) {
            return;
        }
        isNavigated = true;

        cancelFallbackTimer();

        startActivity(new Intent(SplashActivity.this, LanguageActivity.class));
        finish();
    }

    private void showProgress(boolean show) {
        if (splashProgress != null) {
            splashProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        cancelFallbackTimer();
        super.onDestroy();
    }
}