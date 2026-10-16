package com.ezcast.screen.mirroring.totv.infinity

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.ads.module.admob.AppOpenManager
import com.ads.module.ads.ERainAd
import com.ads.module.billing.AppPurchase
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfig
import com.ezcast.screen.mirroring.totv.infinity.ads.inter_welcome
import com.ezcast.screen.mirroring.totv.infinity.component.language.LanguageActivity
import com.ezcast.screen.mirroring.totv.infinity.component.onboarding.OnBoardingActivity
import com.ezcast.screen.mirroring.totv.infinity.component.splash.SplashActivity
import com.ezcast.screen.mirroring.totv.infinity.component.uninstall.SurveyActivity
import com.ezcast.screen.mirroring.totv.infinity.component.welcome.WelcomeActivity
import com.ezcast.screen.mirroring.totv.infinity.utils.Routes

class AppLifecycleObserver : DefaultLifecycleObserver {

    private val listActivityDisableResume = arrayListOf(
        SplashActivity::class.java,
        LanguageActivity::class.java,
        OnBoardingActivity::class.java,
        WelcomeActivity::class.java,
        SurveyActivity::class.java,
    )

    override fun onStart(owner: LifecycleOwner) {
        val currentActivity = GlobalApp.currentActivity
        if (currentActivity != null) {
            val isDisable = listActivityDisableResume.any { clazz ->
                clazz.isInstance(currentActivity)
            }
            if (!isDisable && ResumeAdsEntryRule.shouldShowWelcomeOnResume()
                && !AppOpenManager.getInstance().isInterstitialShowing
                && !AppPurchase.getInstance().isPurchased(currentActivity.applicationContext)
                && ERainAd.getInstance().getShouldDisplayInterWelcomeBack(AdRemoteConfig.inter_welcome.enableUaCheck)
            ) {
//                if (AppOpenManager.getInstance().isDisableAdResumeByClickAction)
//                    AppOpenManager.getInstance().isDisableAdResumeByClickAction = false
//                else
                    Routes.startWelcomeActivity(currentActivity)
            }
        }
    }

    override fun onStop(owner: LifecycleOwner) {}
}
