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


public class BaseActivity extends AppCompatActivity {
    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_base);
        setLanguage(new SharePrefs(this).getLOCALE_LANGUAGE());
        edgeToEdge(this, findViewById(R.id.mainLayout));

    }

    private void setLanguage(String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
