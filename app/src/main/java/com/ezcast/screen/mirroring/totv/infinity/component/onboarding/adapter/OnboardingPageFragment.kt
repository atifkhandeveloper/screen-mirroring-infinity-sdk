package com.ezcast.screen.mirroring.totv.infinity.component.onboarding.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.ads.module.ads.wrapper.ApNativeAd
import com.bumptech.glide.Glide
import com.ezcast.screen.mirroring.totv.R
import com.ezcast.screen.mirroring.totv.databinding.FragmentOnboardingPageBinding
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager
import com.ezcast.screen.mirroring.totv.infinity.ads.populateNativeAdView
import com.ezcast.screen.mirroring.totv.infinity.component.onboarding.model.FullNativeType
import com.ezcast.screen.mirroring.totv.infinity.component.onboarding.model.OnboardingItem
import com.ezcast.screen.mirroring.totv.infinity.component.onboarding.viewmodel.OnboardingViewModel
import com.ezcast.screen.mirroring.totv.infinity.ui.bases.BaseFragment
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.click
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.goneView
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.invisibleView
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.parcelable
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.visibleView

class OnboardingPageFragment : BaseFragment<FragmentOnboardingPageBinding>() {

    override fun getLayoutFragment(): Int = R.layout.fragment_onboarding_page

    companion object {
        private const val ARG_ONBOARDING_ITEM = "arg_onboarding_item"
        private const val TOTAL_INDICATORS = 3

        fun newInstance(onboardingItem: OnboardingItem) = OnboardingPageFragment().apply {
            arguments = bundleOf(ARG_ONBOARDING_ITEM to onboardingItem)
        }
    }

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    private var onboardingItem: OnboardingItem = OnboardingItem(
        title = R.string.onboarding_title_1,
        description = R.string.onboarding_des_1,
        textButton = R.string.next,
        imageResId = R.drawable.onb_one,
        positionIndicator = 0
    )

    override fun initViews() {
        val bundle = arguments
        if (bundle != null) {
            val item: OnboardingItem? =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(ARG_ONBOARDING_ITEM, OnboardingItem::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    bundle.getParcelable(ARG_ONBOARDING_ITEM)
                }
            if (item != null) {
                onboardingItem = item
            }
        }

        updateLayout()
        if (onboardingItem.isHasNativeFull) {
            mBinding.layoutAdsFull.visibleView()
        } else {
            mBinding.layoutAdsFull.invisibleView()
        }
    }

    override fun observerData() {
        observeAdChannel()
    }

    override fun onClickViews() {
        mBinding.btnNext.click { onboardingViewModel.onNextClicked() }
        mBinding.imgCloseAdsFull.click { onboardingViewModel.onNextClicked() }
    }

    /**
     * Resolves the correct LiveData channel for this page:
     *  - Medium native on Page 1            → nativeOnboarding1AdLive
     *  - Medium native on Page 4            → nativeOnboarding4AdLive
     *  - Full native (FULL1)                → nativeAdOnBoardingFullLive
     *  - Full native (FULL2)                → nativeAdOnBoardingFull2Live   ← NEW
     *  - Otherwise                          → no ad
     */
    private fun observeAdChannel() {
        val liveData: MutableLiveData<ApNativeAd?>? = when {
            onboardingItem.isHasNativeFull && onboardingItem.fullNativeType == FullNativeType.FULL1 ->
                AdsManager.nativeAdOnBoardingFullLive
            onboardingItem.isHasNativeFull && onboardingItem.fullNativeType == FullNativeType.FULL2 ->
                AdsManager.nativeAdOnBoardingFull2Live          // ← NEW channel
            onboardingItem.isHasNativeOnPage1 ->
                AdsManager.nativeOnboarding1AdLive
            onboardingItem.isHasNativeOnPage4 ->
                AdsManager.nativeOnboarding4AdLive
            else -> null
        }

        if (liveData == null) {
            renderNoAd()
            return
        }
        liveData.observe(viewLifecycleOwner) { ad -> renderAd(ad) }
    }

    private fun renderAd(ad: ApNativeAd?) {
        if (onboardingItem.isHasNativeFull) {
            // Both FULL1 and FULL2 share the same full-screen layout; only the
            // LiveData source differs (already resolved in observeAdChannel()).
            if (ad != null) {
                mBinding.layoutAdsFull.visibleView()
                mBinding.imgCloseAdsFull.visibleView()
                mBinding.layoutContent.invisibleView()
                populateNativeAdView(
                    requireActivity(),
                    ad,
                    mBinding.layoutAdsFull,
                    mBinding.shimmerAdsFull.shimmerNativeFull
                )
            } else {
                mBinding.layoutContent.visibleView()
                mBinding.layoutAdsFull.invisibleView()
                mBinding.layoutAds.invisibleView()
                mBinding.imgCloseAdsFull.invisibleView()
            }
        } else if (onboardingItem.isHasNativeOnPage1 || onboardingItem.isHasNativeOnPage4) {
            if (ad != null) {
                mBinding.layoutAds.visibleView()
                populateNativeAdView(
                    requireActivity(),
                    ad,
                    mBinding.layoutAds,
                    mBinding.shimmerAds.shimmerNativeMedium
                )
            } else {
                mBinding.layoutAds.invisibleView()
            }
            mBinding.layoutContent.visibleView()
            mBinding.layoutAdsFull.invisibleView()
            mBinding.imgCloseAdsFull.invisibleView()
        }
    }

    private fun renderNoAd() {
        mBinding.layoutContent.visibleView()
        mBinding.layoutAds.goneView()
        mBinding.layoutAdsFull.invisibleView()
        mBinding.imgCloseAdsFull.invisibleView()
    }

    private fun updateLayout() {
        Glide.with(this).load(onboardingItem.imageResId).into(mBinding.imgOnboarding)
        mBinding.tvTitle.text = getString(onboardingItem.title)
        mBinding.tvDes.text = getString(onboardingItem.description)
        mBinding.btnNext.text = getString(onboardingItem.textButton)

        val indicators = listOf(
            mBinding.imgIndicator0,
            mBinding.imgIndicator1,
            mBinding.imgIndicator2,
            mBinding.imgIndicator3
        )

        indicators.forEachIndexed { index, imageView ->
            if (index < TOTAL_INDICATORS) {
                imageView.visibleView()
                val isSelected = index == onboardingItem.positionIndicator
                imageView.setImageResource(
                    if (isSelected) R.drawable.ic_onboarding_indicator_selected
                    else R.drawable.ic_onboarding_indicator
                )
            } else {
                imageView.goneView()
            }
        }
    }
}