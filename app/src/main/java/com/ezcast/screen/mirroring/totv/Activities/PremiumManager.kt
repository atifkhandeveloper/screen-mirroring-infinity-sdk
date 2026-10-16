package com.ezcast.screen.mirroring.totv.Activities

import android.content.Context

object PremiumManager {

    private const val PREF = "premium_prefs"
    private const val KEY = "is_premium"

    // ---------------- SAVE PREMIUM ----------------
//    fun setPremium(context: Context, value: Boolean) {
//
//        val prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
//
//        // 🔥 write immediately (important for billing)
//        prefs.edit().putBoolean(KEY, value).commit()
//
//        // backup apply (safe sync for all devices)
//        prefs.edit().apply()
//    }

    fun setPremium(context: Context, value: Boolean) {
        val prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

        prefs.edit().putBoolean(KEY, value).commit() // 🔥 IMPORTANT (instant write)
    }

    // ---------------- CHECK PREMIUM ----------------
    fun isPremium(context: Context): Boolean {
        return context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .getBoolean(KEY, false)
    }

    // ---------------- ADS HELPER ----------------
    fun shouldShowAds(context: Context): Boolean {
        return !isPremium(context)
    }

    // ---------------- RESET (TEST ONLY) ----------------
    fun reset(context: Context) {
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }

    fun refreshPremium(context: Context): Boolean {
        val value = isPremium(context)

        if (value) {
            // ensures memory sync across app
            context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY, true)
                .apply()
        }

        return value
    }
}