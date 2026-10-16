package com.ezcast.screen.mirroring.totv.infinity.component.uninstall

import android.widget.FrameLayout
import com.ads.module.ads.wrapper.ApNativeAd
import com.facebook.shimmer.ShimmerFrameLayout
import com.ezcast.screen.mirroring.totv.R

import com.ezcast.screen.mirroring.totv.databinding.ActivityConfirmUninstallBinding
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager
import com.ezcast.screen.mirroring.totv.infinity.ads.populateNativeAdView
import com.ezcast.screen.mirroring.totv.infinity.ui.bases.BaseActivity
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.click
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.goneView
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.visibleView
import com.ezcast.screen.mirroring.totv.infinity.utils.Routes

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmUninstallActivity : BaseActivity<ActivityConfirmUninstallBinding>() {

    override fun getLayoutActivity() = R.layout.activity_confirm_uninstall

    override fun initViews() {
        super.initViews()
        AdsManager.loadNativeConfirmUninstall(this, R.layout.layout_native_ad_medium)
    }

    override fun observerData() {
        super.observerData()
        AdsManager.nativeConfirmUninstallAdLive.observe(this) { ad ->
            renderConfirmUninstallAd(ad)
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        mBinding.imgBack.click {
            whenBack()
        }

        mBinding.btnTryAgain.click {
            whenBack()
        }

        mBinding.btnStillUninstall.click {
            Routes.startSurveyActivity(this)
            finish()
        }

    }


    private fun whenBack() {
        Routes.startMainActivity(this)
        finish()
    }

    private fun renderConfirmUninstallAd(ad: ApNativeAd?) {
        val frAds = mBinding.root.findViewById<FrameLayout>(R.id.fr_ads) ?: return
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

    override fun onBackPressed() {
        Routes.startMainActivity(this)
    }
}
