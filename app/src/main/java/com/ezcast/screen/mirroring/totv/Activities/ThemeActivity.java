package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.main.SharePrefs;
import java.util.Locale;

import kotlin.jvm.internal.Intrinsics;



public class ThemeActivity extends AppCompatActivity {
    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_theme);
        edgeToEdge(this, findViewById(R.id.mainLayout));





        String locale_language = new SharePrefs(this).getLOCALE_LANGUAGE();
        Intrinsics.checkNotNullExpressionValue(locale_language, "SharePrefs(this@ThemeAct…ity).getLOCALE_LANGUAGE()");
        setLanguage(locale_language);
    }

    private final void setLanguage(String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
