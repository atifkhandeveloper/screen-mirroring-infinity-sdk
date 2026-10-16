package com.ezcast.screen.mirroring.totv.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatButton

import com.ezcast.screen.mirroring.totv.R
import com.ezcast.screen.mirroring.totv.databinding.ActivityPaywallBinding
import com.ezcast.screen.mirroring.totv.edgeToEdge

class PremiumActivity : ThemeActivity() {

    private lateinit var binding: ActivityPaywallBinding

    private lateinit var btnSubscribe: AppCompatButton
    private lateinit var btnRestore: TextView
    private lateinit var btnClose: ImageButton
    private lateinit var btnTerms: TextView
    private lateinit var btnPrivacy: TextView

//    private var interstitialAd: InterstitialAd? = null

    private var plans = emptyList<PlanUiModel>()

    private val handler = Handler(Looper.getMainLooper())

    private var isAdShownOnClose = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()

        binding = ActivityPaywallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.edgeToEdge(findViewById<View?>(R.id.mainLayout))

        // Already premium
        if (PremiumManager.isPremium(this)) {

            startActivity(
                Intent(this, MainActivity::class.java)
            )

            finish()

            return
        }

        initViews()

        setupClicks()

        // Initial state
        bindPlans()

        // Load cached plans if available
        if (BillingRepository.isReady()) {

            plans = BillingRepository.getCachedPlans()

            bindPlans()
        }

        // Init Billing
        BillingRepository.init(

            context = this,

            onReady = {

                plans = BillingRepository.getCachedPlans()

                Log.d(
                    "PremiumActivity",
                    "Plans loaded = ${plans.size}"
                )

                bindPlans()

                logPlansStatus()
            },

            onPremiumUnlocked = {

                setResult(RESULT_OK)

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )

                finish()
            }
        )

        // Load Ads if needed
        if (PremiumManager.shouldShowAds(this)) {

            // loadInterstitial()
        }

        showCloseButtonAfterDelay()
    }

    // ---------------- INIT ----------------

    private fun initViews() {

        btnSubscribe = findViewById(R.id.btn_continue)

        btnRestore = findViewById(R.id.tv_restore)

        btnClose = findViewById(R.id.btn_close)

        btnTerms = findViewById(R.id.tv_terms)

        btnPrivacy = findViewById(R.id.tv_privacy)

        btnClose.visibility = View.INVISIBLE

        btnSubscribe.text = "Loading..."

        findViewById<TextView>(
            R.id.tv_subtitle
        )?.text = "Loading price..."
    }

    // ---------------- CLOSE BUTTON ----------------

    private fun showCloseButtonAfterDelay() {

        handler.postDelayed({

            btnClose.visibility = View.VISIBLE

        }, 3000)
    }

    // ---------------- BIND PLANS ----------------

    private fun bindPlans() {

        Log.d(
            "PremiumActivity",
            "Binding plans size = ${plans.size}"
        )

        if (plans.isEmpty()) {

            btnSubscribe.text = "Loading..."

            findViewById<TextView>(
                R.id.tv_subtitle
            )?.text = "Loading price..."

            return
        }

        // IMPORTANT:
        // USE PRODUCT ID
        val weeklyPlan = plans.find {
            it.id == "screen_mirroring_weekly_test"
        }

        if (weeklyPlan == null) {

            btnSubscribe.text = "Not Available"

            findViewById<TextView>(
                R.id.tv_subtitle
            )?.text = "Plan not found"

            Log.e(
                "PremiumActivity",
                "screen_mirroring_weekly_test not found"
            )

            return
        }

        btnSubscribe.text = if (
            weeklyPlan.hasFreeTrial
        ) {
            "START FREE TRIAL"
        } else {
            "SUBSCRIBE NOW"
        }

        val priceText = if (
            weeklyPlan.hasFreeTrial
        ) {

            "Enjoy Ads free experience of Screen Mirroring at ${weeklyPlan.price+"/Week only Cancel Anytime"}"

        } else {

            weeklyPlan.price
        }

        findViewById<TextView>(
            R.id.tv_subtitle
        )?.apply {

            text = priceText

            post {

                requestLayout()

                invalidate()
            }
        }

        Log.d(
            "PremiumActivity",
            "UI Updated = ${weeklyPlan.price}"
        )
    }

    // ---------------- LOGS ----------------

    private fun logPlansStatus() {

        if (plans.isEmpty()) {

            Log.e(
                "PremiumActivity",
                "No plans available"
            )
        }

        plans.forEach {

            Log.d(
                "PremiumActivity",
                "Plan => ${it.id} | ${it.price} | Trial=${it.hasFreeTrial}"
            )
        }
    }

    // ---------------- CLICKS ----------------

    private fun setupClicks() {

        btnSubscribe.setOnClickListener {

            val weeklyPlan = plans.find {
                it.id == "screen_mirroring_weekly_test"
            }

            if (weeklyPlan != null) {

                BillingRepository.launchPurchase(
                    this,
                    weeklyPlan
                )

            } else {

                Log.e(
                    "PremiumActivity",
                    "Plan missing"
                )

                bindPlans()
            }
        }

        btnRestore.setOnClickListener {

            BillingRepository.init(

                context = this,

                onReady = {

                    plans =
                        BillingRepository.getCachedPlans()

                    bindPlans()
                },

                onPremiumUnlocked = {

                    setResult(RESULT_OK)

                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        )
                    )

                    finish()
                }
            )

            Log.d(
                "PremiumActivity",
                "Restore clicked"
            )
        }

        btnTerms.setOnClickListener {

            startActivity(

                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://your-terms-url.com"
                    )
                )
            )
        }

        btnPrivacy.setOnClickListener {

            startActivity(

                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://your-privacy-url.com"
                    )
                )
            )
        }

        btnClose.setOnClickListener {


                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )

                finish()
            }
        }

    // ---------------- ADS ----------------

//    private fun showAdOnClose() {
//
//        if (
//            !PremiumManager.shouldShowAds(this)
//            || interstitialAd == null
//        ) {
//
//            startActivity(
//                Intent(
//                    this,
//                    MainActivity::class.java
//                )
//            )
//
//            finish()
//
//            return
//        }
//
//        interstitialAd?.fullScreenContentCallback =
//            object : FullScreenContentCallback() {
//
//                override fun onAdDismissedFullScreenContent() {
//
//                    startActivity(
//                        Intent(
//                            this@PremiumActivity,
//                            MainActivity::class.java
//                        )
//                    )
//
//                    finish()
//                }
//
//                override fun onAdFailedToShowFullScreenContent(
//                    adError: AdError
//                ) {
//
//                    startActivity(
//                        Intent(
//                            this@PremiumActivity,
//                            MainActivity::class.java
//                        )
//                    )
//
//                    finish()
//                }
//            }
//
//        interstitialAd?.show(this)
//    }

    // ---------------- DESTROY ----------------

    override fun onDestroy() {

        super.onDestroy()

        handler.removeCallbacksAndMessages(null)
    }

    // ---------------- BACK ----------------

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        // Disabled
    }
}