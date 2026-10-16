package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.module.ads.ERainAd;
import com.ads.module.ads.wrapper.ApNativeAd;
import com.ads.module.funtion.AdCallback;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Adapter.LanguagesAdapter;
import com.ezcast.screen.mirroring.totv.main.App;
import com.ezcast.screen.mirroring.totv.main.SharePrefs;
import com.ezcast.screen.mirroring.totv.Models.LanguageModelClass;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.LoadAdError;

import java.util.ArrayList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;
import com.ezcast.screen.mirroring.totv.BuildConfig;



public final class LanguageActivity extends ThemeActivity {
    public static final Companion Companion = new Companion(null);
    private static String clickLanguage = "";
    private static int position;
    private ArrayList<LanguageModelClass> arrayList;
    private SharedPreferences languagePreferences;
    private LanguagesAdapter languagesAdapter;
    private ConstraintLayout mainLayout;
    private RecyclerView recyclerView;
    private TextView select_Button;
    private TextView tvSkip;
    private ApNativeAd mApNativeAd;


    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lcom/example/unimote/Activities/LanguageActivity$Companion;", "", "()V", "clickLanguage", "", "getClickLanguage", "()Ljava/lang/String;", "setClickLanguage", "(Ljava/lang/String;)V", "position", "", "getPosition", "()I", "setPosition", "(I)V", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    
    public static final class Companion {
        public  Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }


        public final int getPosition() {
            return LanguageActivity.position;
        }

        public final void setPosition(int i) {
            LanguageActivity.position = i;
        }

        public final String getClickLanguage() {
            return LanguageActivity.clickLanguage;
        }

        public final void setClickLanguage(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            LanguageActivity.clickLanguage = str;
        }
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_language);
        edgeToEdge(this, findViewById(R.id.mainLayout));
        View findViewById = findViewById(R.id.mainLayout);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(R.id.mainLayout)");
        this.mainLayout = (ConstraintLayout) findViewById;
        LanguageActivity languageActivity = this;
        String locale_language = new SharePrefs(languageActivity).getLOCALE_LANGUAGE();
        Intrinsics.checkNotNullExpressionValue(locale_language, "SharePrefs(this@Language…ity).getLOCALE_LANGUAGE()");
        clickLanguage = locale_language;
//        View findViewById2 = findViewById(R.id.tv_skip);
//        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(R.id.tv_skip)");
//        this.tvSkip = (TextView) findViewById2;
//        SharedPreferences sharedPreferences = getSharedPreferences("music_player", 0);
//        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(\"mu…r\", Context.MODE_PRIVATE)");
//        this.languagePreferences = sharedPreferences;


//        ERainAd.getInstance().loadNativeAdResultCallback(this, BuildConfig.native_ad, R.layout.shimmer_native_small, new AdCallback() {
//            @Override
//            public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
//                super.onNativeAdLoaded(nativeAd);
//
//                mApNativeAd = nativeAd;
//            }
//
//            @Override
//            public void onAdFailedToLoad(@Nullable LoadAdError i) {
//                super.onAdFailedToLoad(i);
//
//                mApNativeAd = null;
//            }
//
//            @Override
//            public void onAdFailedToShow(@Nullable AdError adError) {
//                super.onAdFailedToShow(adError);
//
//                mApNativeAd = null;
//            }
//        });



        TextView textView = null;
//        if (sharedPreferences == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("languagePreferences");
//            sharedPreferences = null;
//        }
//        position = sharedPreferences.getInt("language", 0);
        PreferenceManager.getDefaultSharedPreferences(languageActivity).getString("checkMode", DebugKt.DEBUG_PROPERTY_VALUE_ON);
        ArrayList<LanguageModelClass> arrayList = new ArrayList<>();
        this.arrayList = arrayList;
        arrayList.add(new LanguageModelClass(R.drawable.english, "English", "en", false, false));
        ArrayList<LanguageModelClass> arrayList2 = this.arrayList;
        if (arrayList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList2 = null;
        }
        arrayList2.add(new LanguageModelClass(R.drawable.arabic, "Arabic", "ar", false, false));
        ArrayList<LanguageModelClass> arrayList3 = this.arrayList;
        if (arrayList3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList3 = null;
        }
        arrayList3.add(new LanguageModelClass(R.drawable.french, "French", "fr", false, false));
        ArrayList<LanguageModelClass> arrayList4 = this.arrayList;
        if (arrayList4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList4 = null;
        }
        arrayList4.add(new LanguageModelClass(R.drawable.malay, "Malay", "ms", false, false));
        ArrayList<LanguageModelClass> arrayList5 = this.arrayList;
        if (arrayList5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList5 = null;
        }
        arrayList5.add(new LanguageModelClass(R.drawable.germani, "German", "de", false, false));
        ArrayList<LanguageModelClass> arrayList6 = this.arrayList;
        if (arrayList6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList6 = null;
        }
        arrayList6.add(new LanguageModelClass(R.drawable.hindi, "Hindi", "hi", false, false));
        ArrayList<LanguageModelClass> arrayList7 = this.arrayList;
        if (arrayList7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList7 = null;
        }
        arrayList7.add(new LanguageModelClass(R.drawable.portuguese, "Portuguese", "pt", false, false));
        ArrayList<LanguageModelClass> arrayList8 = this.arrayList;
        if (arrayList8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList8 = null;
        }
        arrayList8.add(new LanguageModelClass(R.drawable.spanish, "Spanish", "es", false, false));
        ArrayList<LanguageModelClass> arrayList9 = this.arrayList;
        if (arrayList9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList9 = null;
        }
        arrayList9.add(new LanguageModelClass(R.drawable.japan_flag, "Japanese", "ja", false, false));
        ArrayList<LanguageModelClass> arrayList10 = this.arrayList;
        if (arrayList10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList10 = null;
        }
        arrayList10.add(new LanguageModelClass(R.drawable.turkey, "Turkey", "tr", false, false));
        ArrayList<LanguageModelClass> arrayList11 = this.arrayList;
        if (arrayList11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList11 = null;
        }
        arrayList11.add(new LanguageModelClass(R.drawable.russian, "Russian", "ru", false, false));
//        View findViewById3 = findViewById(R.id.recyclerview);
//        Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(R.id.recyclerview)");
//        this.recyclerView = (RecyclerView) findViewById3;
//        View findViewById4 = findViewById(R.id.select_button);
//        Intrinsics.checkNotNullExpressionValue(findViewById4, "findViewById(R.id.select_button)");
//        this.select_Button = (TextView) findViewById4;
//        ArrayList<LanguageModelClass> arrayList12 = this.arrayList;
//        if (arrayList12 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
//            arrayList12 = null;
//        }
//        this.languagesAdapter = new LanguagesAdapter(languageActivity, arrayList12, this);
//        RecyclerView recyclerView = this.recyclerView;
//        if (recyclerView == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
//            recyclerView = null;
//        }
        recyclerView.setLayoutManager(new LinearLayoutManager(languageActivity, RecyclerView.VERTICAL, false));
        RecyclerView recyclerView2 = this.recyclerView;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView2 = null;
        }
        recyclerView2.setHasFixedSize(false);
        RecyclerView recyclerView3 = this.recyclerView;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView3 = null;
        }
        LanguagesAdapter languagesAdapter = this.languagesAdapter;
        if (languagesAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("languagesAdapter");
            languagesAdapter = null;
        }
        recyclerView3.setAdapter(languagesAdapter);
        TextView textView2 = this.tvSkip;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSkip");
            textView2 = null;
        }
        textView2.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                LanguageActivity.onCreate$lambda$0(LanguageActivity.this, view);
            }
        });
        TextView textView3 = this.select_Button;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("select_Button");
        } else {
            textView = textView3;
        }
        textView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                LanguageActivity.onCreate$lambda$1(LanguageActivity.this, view);
            }
        });
    }

    
    public static final void onCreate$lambda$0(LanguageActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(view.getContext(), HowToUseActivity.class));
    }

    
    public static final void onCreate$lambda$1(LanguageActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.setLanguag(clickLanguage);
    }

    
    @Override 
    public void onResume() {
        super.onResume();
    }

    @Override 
    public void onBackPressed() {
        startActivity(new Intent(this, HowToUseActivity.class));
    }

    private final void setLanguag(String str) {
        SharedPreferences sharedPreferences = null;
        LanguagesAdapter languagesAdapter = null;
        LanguagesAdapter languagesAdapter2 = null;
        LanguagesAdapter languagesAdapter3 = null;
        LanguagesAdapter languagesAdapter4 = null;
        LanguagesAdapter languagesAdapter5 = null;
        SharedPreferences sharedPreferences2 = null;
        if (!App.isAppPurchase) {
            if (str.equals("fr")) {
                SharedPreferences sharedPreferences3 = this.languagePreferences;
                if (sharedPreferences3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagePreferences");
                    sharedPreferences3 = null;
                }
                position = sharedPreferences3.getInt("language", 0);
                LanguagesAdapter languagesAdapter6 = this.languagesAdapter;
                if (languagesAdapter6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagesAdapter");
                } else {
                    languagesAdapter = languagesAdapter6;
                }
                languagesAdapter.notifyDataSetChanged();
                return;
            } else if (str.equals("hi")) {
                SharedPreferences sharedPreferences4 = this.languagePreferences;
                if (sharedPreferences4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagePreferences");
                    sharedPreferences4 = null;
                }
                position = sharedPreferences4.getInt("language", 0);
                LanguagesAdapter languagesAdapter7 = this.languagesAdapter;
                if (languagesAdapter7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagesAdapter");
                } else {
                    languagesAdapter2 = languagesAdapter7;
                }
                languagesAdapter2.notifyDataSetChanged();
                return;
            } else if (str.equals("es")) {
                SharedPreferences sharedPreferences5 = this.languagePreferences;
                if (sharedPreferences5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagePreferences");
                    sharedPreferences5 = null;
                }
                position = sharedPreferences5.getInt("language", 0);
                LanguagesAdapter languagesAdapter8 = this.languagesAdapter;
                if (languagesAdapter8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagesAdapter");
                } else {
                    languagesAdapter3 = languagesAdapter8;
                }
                languagesAdapter3.notifyDataSetChanged();
                return;
            } else if (str.equals("ar")) {
                SharedPreferences sharedPreferences6 = this.languagePreferences;
                if (sharedPreferences6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagePreferences");
                    sharedPreferences6 = null;
                }
                position = sharedPreferences6.getInt("language", 0);
                LanguagesAdapter languagesAdapter9 = this.languagesAdapter;
                if (languagesAdapter9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagesAdapter");
                } else {
                    languagesAdapter4 = languagesAdapter9;
                }
                languagesAdapter4.notifyDataSetChanged();
                return;
            } else if (str.equals("ja")) {
                SharedPreferences sharedPreferences7 = this.languagePreferences;
                if (sharedPreferences7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagePreferences");
                    sharedPreferences7 = null;
                }
                position = sharedPreferences7.getInt("language", 0);
                LanguagesAdapter languagesAdapter10 = this.languagesAdapter;
                if (languagesAdapter10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagesAdapter");
                } else {
                    languagesAdapter5 = languagesAdapter10;
                }
                languagesAdapter5.notifyDataSetChanged();
                return;
            } else {
                LanguageActivity languageActivity = this;
                SharePrefs sharePrefs = new SharePrefs(languageActivity);
                ArrayList<LanguageModelClass> arrayList = this.arrayList;
                if (arrayList == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("arrayList");
                    arrayList = null;
                }
                sharePrefs.setLOCALE_LANGUAGE(arrayList.get(position).getLanguageCode());
                SharePrefs sharePrefs2 = new SharePrefs(languageActivity);
                ArrayList<LanguageModelClass> arrayList2 = this.arrayList;
                if (arrayList2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("arrayList");
                    arrayList2 = null;
                }
                sharePrefs2.setSELECTED_LANGUAGE(arrayList2.get(position).getLanguageName());
                String locale_language = new SharePrefs(languageActivity).getLOCALE_LANGUAGE();
                Intrinsics.checkNotNullExpressionValue(locale_language, "SharePrefs(this@Language…ity).getLOCALE_LANGUAGE()");
                SharedPreferences sharedPreferences8 = this.languagePreferences;
                if (sharedPreferences8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("languagePreferences");
                } else {
                    sharedPreferences2 = sharedPreferences8;
                }
                SharedPreferences.Editor edit = sharedPreferences2.edit();
                edit.putInt("language", position);
                edit.apply();
                Locale locale = new Locale(locale_language);
                Locale.setDefault(locale);
                Resources resources = getResources();
                Configuration configuration = resources.getConfiguration();
                configuration.setLocale(locale);
                resources.updateConfiguration(configuration, resources.getDisplayMetrics());
                startActivity(new Intent(languageActivity, HowToUseActivity.class));
                return;
            }
        }
        LanguageActivity languageActivity2 = this;
        SharePrefs sharePrefs3 = new SharePrefs(languageActivity2);
        ArrayList<LanguageModelClass> arrayList3 = this.arrayList;
        if (arrayList3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList3 = null;
        }
        sharePrefs3.setLOCALE_LANGUAGE(arrayList3.get(position).getLanguageCode());
        SharePrefs sharePrefs4 = new SharePrefs(languageActivity2);
        ArrayList<LanguageModelClass> arrayList4 = this.arrayList;
        if (arrayList4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrayList");
            arrayList4 = null;
        }
        sharePrefs4.setSELECTED_LANGUAGE(arrayList4.get(position).getLanguageName());
        String locale_language2 = new SharePrefs(languageActivity2).getLOCALE_LANGUAGE();
        Intrinsics.checkNotNullExpressionValue(locale_language2, "SharePrefs(this@Language…ity).getLOCALE_LANGUAGE()");
        SharedPreferences sharedPreferences9 = this.languagePreferences;
        if (sharedPreferences9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("languagePreferences");
        } else {
            sharedPreferences = sharedPreferences9;
        }
        SharedPreferences.Editor edit2 = sharedPreferences.edit();
        edit2.putInt("language", position);
        edit2.apply();
        Locale locale2 = new Locale(locale_language2);
        Locale.setDefault(locale2);
        Resources resources2 = getResources();
        Configuration configuration2 = resources2.getConfiguration();
        configuration2.setLocale(locale2);
        resources2.updateConfiguration(configuration2, resources2.getDisplayMetrics());
        recreate();
        startActivity(new Intent(languageActivity2, HowToUseActivity.class));
    }

    public final void notifyAdapter() {
        LanguagesAdapter languagesAdapter = this.languagesAdapter;
        if (languagesAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("languagesAdapter");
            languagesAdapter = null;
        }
        languagesAdapter.notifyDataSetChanged();
    }
}
