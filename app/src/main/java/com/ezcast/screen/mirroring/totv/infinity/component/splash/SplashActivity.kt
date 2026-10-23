package com.ezcast.screen.mirroring.totv.infinity.component.splash

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

import com.ads.module.admob.AppOpenManager
import com.ads.module.ads.ERainAd
import com.ads.module.funtion.AdCallback
import com.ezcast.screen.mirroring.totv.R

import com.ezcast.screen.mirroring.totv.app.AppConstants
import com.ezcast.screen.mirroring.totv.databinding.ActivitySplashBinding
import com.ezcast.screen.mirroring.totv.infinity.ResumeAdsEntryRule
import com.ezcast.screen.mirroring.totv.infinity.data.event.EventTracking
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfig
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager.loadNativeLanguage
import com.ezcast.screen.mirroring.totv.infinity.ads.RemoteConfigUtils
import com.ezcast.screen.mirroring.totv.infinity.ads.banner_splash
import com.ezcast.screen.mirroring.totv.infinity.ads.banner_splash_uninstall
import com.ezcast.screen.mirroring.totv.infinity.ads.inter_splash
import com.ezcast.screen.mirroring.totv.infinity.ads.inter_splash_uninstall
import com.ezcast.screen.mirroring.totv.infinity.ads.open_resume
import com.ezcast.screen.mirroring.totv.infinity.ui.bases.BaseActivity
import com.ezcast.screen.mirroring.totv.infinity.ui.bases.ConsentHandler
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.goneView
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.isNetwork
import com.ezcast.screen.mirroring.totv.infinity.ui.ext.visibleView
import com.ezcast.screen.mirroring.totv.infinity.utils.ITGTrackingHelper
import com.ezcast.screen.mirroring.totv.infinity.utils.Routes
import com.google.android.gms.ads.LoadAdError
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(), RemoteConfigUtils.Listener {

    private var getConfigSuccess = false
    private var showLanguageNextTime = true
    private var simpleExoPlayer: ExoPlayer? = null
    private lateinit var consentHandler: ConsentHandler
    private fun shouldShowLanguageNextTime() = showLanguageNextTime

    // Guard to prevent moveActivity from being triggered more than once
    private var hasMoved = false

    // Handler used for the safety fallback timeout
    private val mainHandler = Handler(Looper.getMainLooper())

    private val uriVideo = "asset:///video_splash.mp4".toUri()
    private val isHasVideo = true
    private val isFromUninstallShortcut: Boolean
        get() = intent.getStringExtra(AppConstants.FROM_SHORTCUT) == AppConstants.ACTION_OPEN_UNINSTALL

    override fun getLayoutActivity() = R.layout.activity_splash

    override fun onActivityBackPressed() {
        handleDoubleBackToExit()
    }

    override fun initViews() {
        super.initViews()

        initVideoSplash()

        ITGTrackingHelper.logEvent(EventTracking.SPLASH, null)
        RemoteConfigUtils.init(this, this)
        consentHandler = ConsentHandler(
            activity = this,
            appSharedPref = appSharedPref,
            trackingSuffix = 1,
            onConsentFlowCompleted = { loadingRemoteConfig() })
        if (appSharedPref.isConfirmConsent.not() && appSharedPref.isUserGlobal.not() && isNetwork()) {
            consentHandler.requestConsent()
        } else {
            loadingRemoteConfig()
        }
    }

    private fun initVideoSplash() {
        if (isHasVideo) {
//            mBinding.imgSplash.goneView()
//            mBinding.playerView.visibleView()

            simpleExoPlayer = ExoPlayer.Builder(this).build()

            // Bind Media3 PlayerView to Media3 ExoPlayer
            mBinding.playerView.player = simpleExoPlayer

            val mediaItem = MediaItem.fromUri(uriVideo)
            simpleExoPlayer?.setMediaItem(mediaItem)
            simpleExoPlayer?.repeatMode = ExoPlayer.REPEAT_MODE_ALL
            simpleExoPlayer?.volume = 0f
            simpleExoPlayer?.prepare()
            simpleExoPlayer?.playWhenReady = true
        } else {
            mBinding.imgSplash.visibleView()
            mBinding.playerView.goneView()
        }
    }

    private fun loadingRemoteConfig() {
        object : CountDownTimer(AppConstants.DEFAULT_TIME_SPLASH, 100) {
            override fun onTick(millisUntilFinished: Long) {
                if (getConfigSuccess && millisUntilFinished < AppConstants.DEFAULT_LIMIT_TIME_SPLASH) {
                    checkRemoteConfigResult()
                    cancel()
                }
            }

            override fun onFinish() {
                if (!getConfigSuccess) {
                    checkRemoteConfigResult()
                }
            }
        }.start()
    }

    private fun checkRemoteConfigResult() {
        ITGTrackingHelper.logEvent(EventTracking.REQUEST_AD, null)
        AdRemoteConfig.initialize(this, RemoteConfigUtils.getAdRemoteConfig())
        loadSplashBanner()
        if (!isFromUninstallShortcut) {
            loadNativeLanguage(
                this@SplashActivity, appSharedPref.firstLanguage, R.layout.layout_native_language
            )
        }
        val splashInterConfig = if (isFromUninstallShortcut) {
            AdRemoteConfig.inter_splash_uninstall
        } else {
            AdRemoteConfig.inter_splash
        }
        if (splashInterConfig.isEnable && isNetwork(this@SplashActivity)) {
            ERainAd.getInstance().loadSplashInterstitialAds(
                this, splashInterConfig.id, 30000, 5000, object : AdCallback() {
                    override fun onNextAction() {
                        super.onNextAction()
                        ITGTrackingHelper.logEvent(EventTracking.SPLASH_USER_CLOSE_AD, null)
                        moveActivitySafely()
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        ITGTrackingHelper.logEvent(EventTracking.SPLASH_AD_LOAD, null)
                    }

                    override fun onAdFailedToLoad(error: LoadAdError?) {
                        super.onAdFailedToLoad(error)
                        moveActivitySafely()
                    }
                })

            // Safety fallback: if no callback arrives within 8 seconds, move forward anyway
            mainHandler.postDelayed({
                moveActivitySafely()
            }, 8000)
        } else {
            moveActivitySafely()
        }

        if (ResumeAdsEntryRule.shouldEnableOpenResume()) {
            AppOpenManager.getInstance().setAppResumeAdId(AdRemoteConfig.open_resume.id)
            AppOpenManager.getInstance().enableAppResume()
        } else {
            AppOpenManager.getInstance().disableAppResume()
        }
    }

    private fun loadSplashBanner() {
        val bannerConfig = if (isFromUninstallShortcut) {
            AdRemoteConfig.banner_splash_uninstall
        } else {
            AdRemoteConfig.banner_splash
        }
        AdsManager.loadBanner(
            this, bannerConfig, mBinding.frBanner, isCollapse = false
        )
    }

    /**
     * Ensures we only navigate away from the splash once, no matter how many
     * callbacks (onNextAction, onAdFailedToLoad, fallback timer) fire.
     */
    private fun moveActivitySafely() {
        if (hasMoved) return
        hasMoved = true
        mainHandler.removeCallbacksAndMessages(null)
        moveActivity()
    }

    private fun moveActivity() {
        stopVideo()
        when {
            isFromUninstallShortcut -> Routes.startConfirmUninstallActivity(this)
            shouldShowLanguageNextTime() || appSharedPref.firstOnBoarding -> Routes.startLanguageActivity(
                this,
                null
            )

            else -> Routes.startMainActivity(this)
        }
        finish()
    }

    private fun stopVideo() {
        simpleExoPlayer?.let { player ->
            player.pause()
            player.playWhenReady = false
        }
    }

    override fun onPause() {
        super.onPause()
        stopVideo()
    }

    override fun onResume() {
        super.onResume()
        // If the splash has already finished navigating away, skip this ad check
        if (hasMoved) return
        ERainAd.getInstance().onCheckShowSplashWhenFail(this@SplashActivity, object : AdCallback() {
            override fun onNextAction() {
                super.onNextAction()
                moveActivitySafely()
            }
        }, 1000)
    }

    override fun loadSuccess() {
        getConfigSuccess = true
    }

    override fun onDestroy() {
        stopVideo()

        mainHandler.removeCallbacksAndMessages(null)

        mBinding.playerView.player = null
        simpleExoPlayer?.release()
        simpleExoPlayer = null

        if (::consentHandler.isInitialized) {
            consentHandler.clear()
        }

        super.onDestroy()
    }
}