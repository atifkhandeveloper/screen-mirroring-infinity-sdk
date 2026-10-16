package com.ezcast.screen.mirroring.totv.infinity.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ezcast.screen.mirroring.totv.Activities.MainActivity
import com.ezcast.screen.mirroring.totv.app.AppConstants
import com.ezcast.screen.mirroring.totv.infinity.component.language.LanguageActivity
import com.ezcast.screen.mirroring.totv.infinity.component.onboarding.OnBoardingActivity
import com.ezcast.screen.mirroring.totv.infinity.component.setting.SettingActivity
import com.ezcast.screen.mirroring.totv.infinity.component.splash.SplashActivity
import com.ezcast.screen.mirroring.totv.infinity.component.uninstall.ConfirmUninstallActivity
import com.ezcast.screen.mirroring.totv.infinity.component.uninstall.SurveyActivity
import com.ezcast.screen.mirroring.totv.infinity.component.welcome.WelcomeActivity
import kotlin.jvm.java

object Routes {
    fun startMainActivity(fromActivity: Activity) =
        Intent(fromActivity, MainActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }
    fun startOnBoardingActivity(fromActivity: Activity) =
        Intent(fromActivity, OnBoardingActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)

        }

    fun startLanguageActivity(fromActivity: Activity, bundle: Bundle?) =
        Intent(fromActivity, LanguageActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            bundle?.let { putExtras(it) }
            fromActivity.startActivity(this)

        }

    fun startSplashActivity(fromActivity: Activity) =
        Intent(fromActivity, SplashActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

    fun startConfirmUninstallActivity(fromActivity: Activity) =
        Intent(fromActivity, ConfirmUninstallActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

    fun startSurveyActivity(fromActivity: Activity) =
        Intent(fromActivity, SurveyActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

    fun startSettingActivity(fromActivity: Activity) =
        Intent(fromActivity, SettingActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }


    fun startWelcomeActivity(fromActivity: Activity) =
        Intent(fromActivity, WelcomeActivity::class.java).apply {
            putExtra(AppConstants.KEY_TRACKING_SCREEN_FROM, fromActivity::class.java.simpleName)
            fromActivity.startActivity(this)
        }

    fun addTrackingMoveScreen(fromActivity: String, toActivity: String) {
        ITGTrackingHelper.fromScreenToScreen(fromActivity, toActivity)
    }

}