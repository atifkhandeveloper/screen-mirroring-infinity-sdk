package com.ezcast.screen.mirroring.totv.main;

import com.ads.module.admob.Admob;
import com.ads.module.admob.AppOpenManager;
import com.ads.module.ads.ERainAd;
import com.ads.module.application.AdsMultiDexApplication;
import com.ads.module.billing.AppPurchase;
import com.ads.module.config.AdjustConfig;
import com.ads.module.config.ERainAdConfig;
import com.ezcast.screen.mirroring.totv.Activities.MainActivity;
import com.ezcast.screen.mirroring.totv.BuildConfig;
import com.ezcast.screen.mirroring.totv.R;

import java.util.ArrayList;
import java.util.List;

public class Appp extends AdsMultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        initAds();
        initBilling();
    }

//    private void initAds() {
//        String environment = BuildConfig.DEBUG ? ERainAdConfig.ENVIRONMENT_DEVELOP : ERainAdConfig.ENVIRONMENT_PRODUCTION;
//        mERainAdConfig = new ERainAdConfig(this, environment);
//
//        AdjustConfig adjustConfig = new AdjustConfig(true,getString(R.string.adjust_token));
//        mERainAdConfig.setAdjustConfig(adjustConfig);
////        mERainAdConfig.setFacebookClientToken(getString(R.string.facebook_client_token));
////        mERainAdConfig.setAdjustTokenTiktok(getString(R.string.tiktok_token));
//
//        mERainAdConfig.setIdAdResume(BuildConfig.open_resume);
//
//        ERainAd.getInstance().init(this, mERainAdConfig);
//        Admob.getInstance().setDisableAdResumeWhenClickAds(true);
//        Admob.getInstance().setOpenActivityAfterShowInterAds(true);
//        AppOpenManager.getInstance().disableAppResumeWithActivity(MainActivity.class);
//    }

    private void initBilling(){
        List<String> listIAP = new ArrayList<>();
        listIAP.add("android.test.purchased");
        List<String> listSub = new ArrayList<>();
        AppPurchase.getInstance().initBilling(this, listIAP, listSub);
    }
}