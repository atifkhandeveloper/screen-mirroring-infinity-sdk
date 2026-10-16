package com.ezcast.screen.mirroring.totv.Activities

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.*

object BillingRepository : PurchasesUpdatedListener {

    // Replace this placeholder with the subscription product ID created in Play Console.
    // Billing cannot use a universal Google test product ID; the product must belong to your app.
    private const val WEEKLY_PRODUCT_ID = "screen_mirroring_weekly_test"

    private lateinit var billingClient: BillingClient
    private lateinit var appContext: Context

    private var isConnected = false
    private var isProductsReady = false

    private var cachedPlans = emptyList<PlanUiModel>()

    private var premiumCallback: (() -> Unit)? = null
    private var plansCallback: (() -> Unit)? = null

    fun isReady(): Boolean = isProductsReady

    // ---------------- INIT ----------------
    fun init(
        context: Context,
        onReady: (() -> Unit)? = null,
        onPremiumUnlocked: (() -> Unit)? = null
    ) {

        appContext = context.applicationContext
        premiumCallback = onPremiumUnlocked
        plansCallback = onReady

        // Prevent multiple connections
        if (::billingClient.isInitialized && billingClient.isReady) {

            Log.d("BILLING", "Already connected")

            loadAllProducts()
            restorePurchases()

            return
        }

        billingClient = BillingClient.newBuilder(appContext)
            .enablePendingPurchases(
                PendingPurchasesParams.newBuilder()
                    .enableOneTimeProducts()
                    .build()
            )
            .setListener(this)
            .build()

        billingClient.startConnection(object : BillingClientStateListener {

            override fun onBillingSetupFinished(result: BillingResult) {

                if (result.responseCode == BillingClient.BillingResponseCode.OK) {

                    isConnected = true

                    Log.d("BILLING", "Connected")

                    loadAllProducts()

                    restorePurchases()

                } else {

                    Log.e(
                        "BILLING",
                        "Setup failed: ${result.debugMessage}"
                    )
                }
            }

            override fun onBillingServiceDisconnected() {

                isConnected = false

                Log.e("BILLING", "Disconnected")
            }
        })
    }

    // ---------------- LOAD PRODUCTS ----------------
    private fun loadAllProducts() {

        val subs = listOf(

            // IMPORTANT:
            // USE PRODUCT ID NOT BASE PLAN ID
            product(
                WEEKLY_PRODUCT_ID,
                BillingClient.ProductType.SUBS
            )
        )

        queryProducts(subs) { plans ->

            cachedPlans = plans

            isProductsReady = true

            Log.d(
                "BILLING",
                "Products Loaded = ${plans.size}"
            )

            plans.forEach {

                Log.d(
                    "BILLING",
                    "Plan => ${it.id} | ${it.price}"
                )
            }

            plansCallback?.invoke()
        }
    }

    // ---------------- QUERY PRODUCTS ----------------
    private fun queryProducts(
        productList: List<QueryProductDetailsParams.Product>,
        onResult: (List<PlanUiModel>) -> Unit
    ) {

        if (!billingClient.isReady) {

            Log.e("BILLING", "BillingClient not ready")

            onResult(emptyList())

            return
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(params) { result, data ->

            Log.d(
                "BILLING",
                "Query Result Code = ${result.responseCode}"
            )

            Log.d(
                "BILLING",
                "Query Result Message = ${result.debugMessage}"
            )

            if (result.responseCode != BillingClient.BillingResponseCode.OK) {

                Log.e(
                    "BILLING",
                    "Query failed: ${result.debugMessage}"
                )

                onResult(emptyList())

                return@queryProductDetailsAsync
            }

            if (data.productDetailsList.isEmpty()) {

                Log.e(
                    "BILLING",
                    "No products returned from Play Console"
                )
            }

            data.productDetailsList.forEach {

                Log.d(
                    "BILLING",
                    "Found Product = ${it.productId}"
                )
            }

            val mappedPlans = data.productDetailsList.mapNotNull {
                it.toPlan()
            }

            onResult(mappedPlans)
        }
    }

    // ---------------- PRODUCT ----------------
    private fun product(
        id: String,
        type: String
    ): QueryProductDetailsParams.Product {

        return QueryProductDetailsParams.Product.newBuilder()
            .setProductId(id)
            .setProductType(type)
            .build()
    }

    // ---------------- MAP ----------------
    private fun ProductDetails.toPlan(): PlanUiModel {

        val offer = subscriptionOfferDetails?.firstOrNull()

        val hasFreeTrial =
            subscriptionOfferDetails?.any { offerDetails ->

                offerDetails.pricingPhases.pricingPhaseList.any {

                    it.priceAmountMicros == 0L
                }

            } == true

        val price = offer
            ?.pricingPhases
            ?.pricingPhaseList
            ?.lastOrNull()
            ?.formattedPrice ?: "Unavailable"

        return PlanUiModel(

            // IMPORTANT:
            // USE PRODUCT ID
            id = this.productId,

            title = "Weekly Plan",

            price = price,

            hasFreeTrial = hasFreeTrial,

            isBestValue = true,

            product = this
        )
    }

    // ---------------- PURCHASE ----------------
    fun launchPurchase(
        activity: Activity,
        plan: PlanUiModel
    ) {

        if (!billingClient.isReady) {

            Log.e("BILLING", "Billing not ready")

            return
        }

        val product = plan.product

        val offer = product.subscriptionOfferDetails?.firstOrNull()

        if (offer == null) {

            Log.e("BILLING", "No offer found")

            return
        }

        val params = BillingFlowParams.ProductDetailsParams
            .newBuilder()
            .setProductDetails(product)
            .setOfferToken(offer.offerToken)
            .build()

        val billingParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(params))
            .build()

        val result = billingClient.launchBillingFlow(
            activity,
            billingParams
        )

        Log.d(
            "BILLING",
            "Launch Result = ${result.responseCode}"
        )
    }

    // ---------------- CALLBACK ----------------
    override fun onPurchasesUpdated(
        result: BillingResult,
        purchases: MutableList<Purchase>?
    ) {

        Log.d(
            "BILLING",
            "Purchase Update = ${result.responseCode}"
        )

        if (
            result.responseCode ==
            BillingClient.BillingResponseCode.OK &&
            purchases != null
        ) {

            purchases.forEach {
                handlePurchase(it)
            }

        } else if (
            result.responseCode ==
            BillingClient.BillingResponseCode.USER_CANCELED
        ) {

            Log.d("BILLING", "User canceled purchase")

        } else {

            Log.e(
                "BILLING",
                "Purchase failed: ${result.debugMessage}"
            )
        }
    }

    // ---------------- HANDLE PURCHASE ----------------
    private fun handlePurchase(
        purchase: Purchase
    ) {

        if (
            purchase.purchaseState !=
            Purchase.PurchaseState.PURCHASED
        ) {
            return
        }

        if (!purchase.isAcknowledged) {

            val acknowledgeParams =
                AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()

            billingClient.acknowledgePurchase(
                acknowledgeParams
            ) { result ->

                if (
                    result.responseCode ==
                    BillingClient.BillingResponseCode.OK
                ) {

                    Log.d(
                        "BILLING",
                        "Purchase acknowledged"
                    )

                    unlockPremium()

                } else {

                    Log.e(
                        "BILLING",
                        "Acknowledge failed"
                    )
                }
            }

        } else {

            unlockPremium()
        }
    }

    // ---------------- PREMIUM ----------------
    private fun unlockPremium() {

        PremiumManager.setPremium(
            appContext,
            true
        )

        premiumCallback?.invoke()

        Log.d("BILLING", "Premium unlocked")
    }

    // ---------------- RESTORE ----------------
    private fun restorePurchases() {

        if (!billingClient.isReady) return

        billingClient.queryPurchasesAsync(

            QueryPurchasesParams.newBuilder()
                .setProductType(
                    BillingClient.ProductType.SUBS
                )
                .build()

        ) { result, purchases ->

            if (
                result.responseCode ==
                BillingClient.BillingResponseCode.OK
            ) {

                val hasPremium = purchases.any {

                    it.purchaseState ==
                            Purchase.PurchaseState.PURCHASED
                }

                if (hasPremium) {

                    Log.d(
                        "BILLING",
                        "Premium restored"
                    )

                    unlockPremium()
                }
            }
        }
    }

    // ---------------- CACHE ----------------
    fun getCachedPlans(): List<PlanUiModel> {

        return cachedPlans
    }

    // ---------------- DESTROY ----------------
    fun destroy() {

        if (::billingClient.isInitialized) {

            billingClient.endConnection()
        }
    }
}