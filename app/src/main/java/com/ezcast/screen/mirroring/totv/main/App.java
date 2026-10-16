package com.ezcast.screen.mirroring.totv.main;

import android.app.Application;

import com.ads.module.BuildConfig;
import com.ads.module.admob.Admob;
import com.ads.module.admob.AppOpenManager;
import com.ads.module.ads.ERainAd;
import com.ads.module.application.AdsMultiDexApplication;
import com.ads.module.billing.AppPurchase;
import com.ads.module.config.AdjustConfig;
import com.ads.module.config.ERainAdConfig;
import com.ezcast.screen.mirroring.totv.Activities.HowToUseActivity;
import com.ezcast.screen.mirroring.totv.Activities.LanguageActivity;
import com.ezcast.screen.mirroring.totv.Activities.Splash.SplashActivity;
import com.ezcast.screen.mirroring.totv.R;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;


public class App extends AdsMultiDexApplication {
    private static App app;
    public static boolean isAppPurchase = false;
    private FirebaseAnalytics mFirebaseAnalytics;

    public static App getApp() {
        return app;
    }
    private ERainAdConfig mERainAdConfig;


    @Override 
    public void onCreate() {

        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        app = this;


        // 1. Initialize GMA SDK
        MobileAds.initialize(this);

        // 2. Initialize DevConfig (For QA UI)
//        DevConfig.init(
//                this,
//                BuildConfig.ERAIN_STUDIO_VERSION,
//                BuildConfig.PLAY_SERVICES_ADS_VERSION,
//                BuildConfig.GDPR_MODULE_VERSION
//        );

        // 3. Load Ads configuration from assets
//        AdRemoteConfig.initializeFromAssets(this);

        // 4. Initialize Ads Config & SDK
        initAds();

        // 5. Initialize Billing IAP
        initBilling();

        // 6. Register Welcomeback handling on Resume (if enabled)
//        if (ResumeAdsEntryRule.shouldShowWelcomeOnResume()) {
//            // Register corresponding AppLifecycleObserver
//        }

    }

    private void initAds() {
        int environment;

        if (BuildConfig.DEBUG) {
            environment = Integer.parseInt(ERainAdConfig.ENVIRONMENT_DEVELOP);
        } else {
            environment = Integer.parseInt(ERainAdConfig.ENVIRONMENT_PRODUCTION);
        }

        mERainAdConfig = new ERainAdConfig(this);

        // Configure Adjust
//        mERainAdConfig.setAdjustConfig(new AdjustConfig(
//                true,
//                getResources().getString(R.string.adjust_token)
//        ));

        // Configure Facebook
//        mERainAdConfig.setFacebookClientToken(getResources().getString(R.string.facebook_client_token));

        // Configure TikTok
//        mERainAdConfig.setAdjustTokenTiktok(getResources().getString(R.string.tiktok_token));

        // Minimum interval between two interstitial ads (seconds)
        mERainAdConfig.setIntervalInterstitialAd(35);

        // Open Resume Ad ID
//        mERainAdConfig.setIdAdResume(BuildConfig.open_resume);

        // Initialize ERain Ads SDK
        ERainAd.getInstance().init(this, mERainAdConfig);

        // Additional Resume/Interstitial rules
        Admob.getInstance().setDisableAdResumeWhenClickAds(true);
        Admob.getInstance().setOpenActivityAfterShowInterAds(true);

        // Exclude screens that do not display App Open Ads on Resume
        AppOpenManager.getInstance()
                .disableAppResumeWithActivity(SplashActivity.class);

        AppOpenManager.getInstance()
                .disableAppResumeWithActivity(LanguageActivity.class);

        AppOpenManager.getInstance()
                .disableAppResumeWithActivity(HowToUseActivity.class);
    }




    private void initBilling() {
        ArrayList<String> listIAP = new ArrayList<>();
        listIAP.add("android.test.purchased"); // Test IAP Product ID

        // Add other IAP IDs here

        ArrayList<String> listSub = new ArrayList<>();
        // Add Subscription IDs here

        AppPurchase.getInstance().initBilling(this, listIAP, listSub);
    }

}