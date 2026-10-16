package com.ezcast.screen.mirroring.totv.main;

import android.content.Context;
import android.content.SharedPreferences;


public class SharePrefs {
    private static final String IS_FIRST_TIME = "IS_FIRST_TIME";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String LIFE_TIME_PRICE = "LIFE_TIME_PRICE";
    public static final String MONTHLY_PRICE = "MONTHLY_PRICE";

    public static String RM_MESSAGE_KEY_APPOPEN_IDS = "SM_Open";
    public static String RM_MESSAGE_KEY_COLLAPSIBLE_IDS = "SM_Banner";
    public static String RM_MESSAGE_KEY_INTERSTITIAL_IDS = "SM_Interstitial";
    public static String RM_MESSAGE_KEY_NATIVE_LARGE_IDS = "SM_Native";
    public static final String SELECTED_LANGUAGE = "SELECTED_LANGUAGE";
    private static final String SHARE_PREF_NAME = "MIRROR_PREF";
    public static final String WEEKLY_PRICE = "WEEKLY_PRICE";
    static Context context;
    private static SharedPreferences sharedPref;

    public SharePrefs(Context context2) {
        sharedPref = context2.getSharedPreferences(SHARE_PREF_NAME, 0);
        context = context2;
    }

    public boolean getIsFirstTime() {
        return sharedPref.getBoolean(IS_FIRST_TIME, true);
    }



    public void setIsFirstTime(Boolean bool) {
        sharedPref.edit().putBoolean(IS_FIRST_TIME, bool.booleanValue()).commit();
    }



    public void setRM_MESSAGE_KEY_INTERSTITIAL_IDS(String str) {
        sharedPref.edit().putString(RM_MESSAGE_KEY_INTERSTITIAL_IDS, str).commit();
    }





    public void setRM_MESSAGE_KEY_COLLAPSIBLE_IDS(String str) {
        sharedPref.edit().putString(RM_MESSAGE_KEY_COLLAPSIBLE_IDS, str).commit();
    }



    public void setRM_MESSAGE_KEY_NATIVE_LARGE_IDS(String str) {
        sharedPref.edit().putString(RM_MESSAGE_KEY_NATIVE_LARGE_IDS, str).commit();
    }






    public void setRM_MESSAGE_KEY_APPOPEN_IDS(String str) {
        sharedPref.edit().putString(RM_MESSAGE_KEY_APPOPEN_IDS, str).commit();
    }





    public void setLOCALE_LANGUAGE(String str) {
        sharedPref.edit().putString(LANGUAGE, str).apply();
    }

    public void setSELECTED_LANGUAGE(String str) {
        sharedPref.edit().putString(SELECTED_LANGUAGE, str).apply();
    }

    public String getLOCALE_LANGUAGE() {
        return sharedPref.getString(LANGUAGE, "en");
    }


    public String getMONTHLY_REAL() {
        return sharedPref.getString(WEEKLY_PRICE, "9");
    }

    public String getWEEKLY_REAL() {
        return sharedPref.getString(MONTHLY_PRICE, "19");
    }

    public String getLIFE_TIME_PRICE_REAL() {
        return sharedPref.getString(LIFE_TIME_PRICE, "99");
    }
}
