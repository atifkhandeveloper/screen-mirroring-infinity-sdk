package com.ezcast.screen.mirroring.totv.infinity.component.language

import android.os.Handler
import android.os.Looper
import android.view.View
import com.ads.module.ads.wrapper.ApNativeAd
import com.ezcast.screen.mirroring.totv.R

import com.ezcast.screen.mirroring.totv.app.AppConstants
import com.ezcast.screen.mirroring.totv.app.AppConstants.DEFAULT_TIME_DELAY_SHOW_LANGUAGE_DONE_BUTTON
import com.ezcast.screen.mirroring.totv.databinding.ActivityLanguageBinding
import com.ezcast.screen.mirroring.totv.infinity.data.event.EventTracking
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager.loadNativeLanguageClick
import com.ezcast.screen.mirroring.totv.infinity.ads.RemoteConfigUtils
import com.ezcast.screen.mirroring.totv.infinity.ads.populateNativeAdView

import com.ezcast.screen.mirroring.totv.infinity.component.language.adapter.LanguageAdapter
import com.ezcast.screen.mirroring.totv.infinity.component.language.data.LanguageData
import com.ezcast.screen.mirroring.totv.infinity.ui.bases.BaseActivity
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.click
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.goneView
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.isNetwork
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.visibleView
import com.ezcast.screen.mirroring.totv.infinity.utils.ITGTrackingHelper
import com.ezcast.screen.mirroring.totv.infinity.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import com.itg.devconfig.utils.setOnAdminAdToggleListener



@AndroidEntryPoint
class LanguageActivity : BaseActivity<ActivityLanguageBinding>() {
    private var timeDelayDoneButton = DEFAULT_TIME_DELAY_SHOW_LANGUAGE_DONE_BUTTON
    private var isFromSetting = false
    override val shouldShowNavigationBars = RemoteConfigUtils.getOnShowNavigationButton()

    companion object {
        const val EXTRA_FROM_SETTING = "extra_from_setting"
    }

    override fun getLayoutActivity() = R.layout.activity_language

    override fun onActivityBackPressed() {
        if (isFromSetting) {
            super.onActivityBackPressed()
        } else {
            handleDoubleBackToExit()
        }
    }

    private val fromSetting
        get() = intent.getBooleanExtra(AppConstants.KEY_SETTING, false)

    private var shouldDelayDoneButton = true
    private var selectedIso = LanguageData.defaultLanguage.iso

    private val languageAdapter: LanguageAdapter by lazy {
        LanguageAdapter(
            onItemLanguageClick = {
                ITGTrackingHelper.logEvent(EventTracking.LANGUAGE_CLICK,null)
                listenLanguageClickAd()
                delayShowDoneButton()
                selectedIso = it.iso
                LanguageData.selectLanguage(it.iso)
                resubmitLanguageData()
            }
        )
    }

    override fun initViews() {
        ITGTrackingHelper.logEvent(EventTracking.LANGUAGE_FIRST,null)
        isFromSetting = intent.getBooleanExtra(EXTRA_FROM_SETTING, false)
        mBinding.tvTitle.setOnAdminAdToggleListener(){
            Routes.startSplashActivity(this@LanguageActivity)
            finish()
        }
        shouldDelayDoneButton = RemoteConfigUtils.shouldDelayLanguageDoneButton()
        timeDelayDoneButton = RemoteConfigUtils.getTimeDelayButtonDoneLanguage()
        initAdapter()
        initLayout()

        mBinding.root.postDelayed({
            loadNativeLanguageClick(this, appSharedPref.firstLanguage, R.layout.layout_native_language_click)
            initAds()
        }, 100L)
    }

    override fun observerData() {
        listenLanguageAd()
    }

    override fun onClickViews() {
        mBinding.ivDone.click {
            val iso = selectedIso
            appSharedPref.languageCode = iso
            startNextActivity()
        }
        mBinding.tvDone.click {
            mBinding.ivDone.performClick()
        }
    }

    private fun initAdapter() {
        mBinding.recyclerView.adapter = languageAdapter
        resubmitLanguageData()
    }

    private fun initLayout() {
        if (isFromSetting) {
            mBinding.tvDone.visibleView()
            mBinding.ivDone.visibleView()
            timeDelayDoneButton = 0
        } else {
            if (shouldDelayDoneButton) {
                mBinding.ivDone.goneView()
                mBinding.tvDone.goneView()
            } else {
                mBinding.ivDone.visibleView()
                mBinding.tvDone.visibleView()
            }
        }
    }

    private fun initAds() {
        if (fromSetting) {
            mBinding.flAds.goneView()
        } else {
            AdsManager.loadNativeOnboarding1(
                this,
                appSharedPref.firstOnBoarding,
                R.layout.layout_native_onboarding
            )
        }
    }

    private fun listenLanguageAd() {
        AdsManager.nativeLanguageClickAdLive.removeObservers(this)
        AdsManager.nativeLanguageAdLive.observe(this) { ad ->
            if (ad != null) showNativeLanguage(ad) else mBinding.flAds.goneView()
        }
    }

    private fun listenLanguageClickAd() {
        AdsManager.nativeLanguageAdLive.removeObservers(this)
        AdsManager.nativeLanguageClickAdLive.observe(this) { ad ->
            if (ad != null) showNativeLanguage(ad) else mBinding.flAds.goneView()
        }
    }

    private fun showNativeLanguage(ad: ApNativeAd) {
        if (!isNetwork()) {
            mBinding.flAds.goneView()
            return
        }
        mBinding.flAds.visibleView()
        populateNativeAdView(
            this,
            ad,
            mBinding.flAds,
            mBinding.shimmerAds.shimmerNativeSmall
        )
    }

    private fun delayShowDoneButton() {
        if (mBinding.tvDone.visibility == View.VISIBLE) return

        if (shouldDelayDoneButton) {
            Handler(Looper.getMainLooper()).postDelayed({
                mBinding.ivDone.visibleView()
                mBinding.tvDone.visibleView()
            }, timeDelayDoneButton)
        } else {
            mBinding.ivDone.visibleView()
            mBinding.tvDone.visibleView()
        }
    }

    private fun resubmitLanguageData() {
        languageAdapter.submitList(LanguageData.languages)
    }

    private fun startNextActivity() {
        if (fromSetting) {
            Routes.startMainActivity(this)
        } else {
            appSharedPref.firstLanguage = false
            Routes.startOnBoardingActivity(this)
        }
        finish()
    }
}
