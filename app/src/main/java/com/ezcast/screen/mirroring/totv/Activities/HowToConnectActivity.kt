package com.ezcast.screen.mirroring.totv.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.ezcast.screen.mirroring.totv.Classes.InternetPermission
import com.ezcast.screen.mirroring.totv.R
import com.ezcast.screen.mirroring.totv.edgeToEdge
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfig
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager.loadBanner
import com.ezcast.screen.mirroring.totv.infinity.ads.banner_all

class HowToConnectActivity : AppCompatActivity() {

    private lateinit var backIcon: ImageView
    private lateinit var startMirroringBtn: AppCompatButton
    private var frBanner: FrameLayout? = null
    private var bannerLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_how_to_connect)
        this.edgeToEdge(findViewById<View?>(R.id.mainLayout))


        this.frBanner = findViewById<FrameLayout?>(R.id.fr_banner)

        loadMainBanner()

        initViews()
        setupClicks()

        // Optional: show ads if user is not premium
//        if (PremiumManager.shouldShowAds(this)) {
//            val adAdmob = AdAdmob(this)
//            adAdmob.BannerAd(findViewById(R.id.banner), this)
//        }
    }

    private fun initViews() {
        backIcon = findViewById(R.id.back_icon)
        startMirroringBtn = findViewById(R.id.btn_start_mirroring)
    }

    private fun setupClicks() {
        backIcon.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        startMirroringBtn.setOnClickListener {
            // Return to MainActivity and trigger the cast/discovery flow
            try {
                startActivity(Intent("android.settings.CAST_SETTINGS"))
            } catch (e: Exception) {
                Toast.makeText(this, "Cast settings not available", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun loadMainBanner() {
        if (frBanner == null) return

        val bannerConfig =
            AdRemoteConfig.Companion.banner_all

        if (!InternetPermission.isNetworkAvailable(this)) {
            frBanner!!.setVisibility(View.GONE)
            return
        }

        frBanner!!.setVisibility(View.VISIBLE)

        loadBanner(
            this,
            bannerConfig,
            frBanner!!,
            false
        )

        bannerLoaded = true
    }
}