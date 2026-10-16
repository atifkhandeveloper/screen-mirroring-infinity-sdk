package com.ezcast.screen.mirroring.totv.infinity.component.uninstall

import android.content.Intent
import android.provider.Settings
import android.widget.FrameLayout
import androidx.core.net.toUri
import com.ads.module.ads.wrapper.ApNativeAd
import com.ezcast.screen.mirroring.totv.R

import com.ezcast.screen.mirroring.totv.databinding.ActivitySurveyBinding
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager
import com.ezcast.screen.mirroring.totv.infinity.ads.populateNativeAdView
import com.ezcast.screen.mirroring.totv.infinity.ui.bases.BaseActivity
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.click
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.goneView
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.visibleView
import com.ezcast.screen.mirroring.totv.infinity.utils.Routes

import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyActivity : BaseActivity<ActivitySurveyBinding>() {

    override fun getLayoutActivity() = R.layout.activity_survey

    override fun initViews() {
        super.initViews()
        AdsManager.loadNativeSurvey(this, R.layout.layout_native_ad_medium)
    }

    override fun observerData() {
        super.observerData()
        AdsManager.nativeSurveyAdLive.observe(this) { ad -> renderSurveyAd(ad) }
    }

    private fun renderSurveyAd(ad: ApNativeAd?) {
        val frAds = mBinding.root.findViewById<FrameLayout>(R.id.fr_ads)
            ?: return
        if (ad == null) {
            frAds.goneView()
            return
        }
        frAds.visibleView()
        val shimmer = mBinding.root.findViewById<ShimmerFrameLayout>(R.id.shimmer_ads)
        if (shimmer != null) {
            populateNativeAdView(this, ad, frAds, shimmer)
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        mBinding.btnCancel.click {
            whenBack()
        }

        mBinding.imgBack.click {
            whenBack()
        }

        mBinding.btnUninstall.click {
            try {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    "package:${packageName}".toUri()
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } catch (_: Exception) {
            }
            finish()
        }
    }

    private fun whenBack() {
        Routes.startMainActivity(this)
        finish()
    }
}
