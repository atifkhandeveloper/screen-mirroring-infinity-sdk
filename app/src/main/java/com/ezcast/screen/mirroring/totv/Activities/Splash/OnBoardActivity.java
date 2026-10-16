package com.ezcast.screen.mirroring.totv.Activities.Splash;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;

import com.ezcast.screen.mirroring.totv.Activities.PremiumActivity;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.ThemeActivity;


public class OnBoardActivity extends ThemeActivity {
    ImageView button;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);
        edgeToEdge(this, findViewById(R.id.mainLayout));

//        ImageView imageView = (ImageView) findViewById(R.id.on_boarding_button);
//        this.button = imageView;
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public final void onClick(View view) {
//                OnBoardActivity.this.m216x50bc2ff7(view);
//            }
//        });
    }

    
    public  void m216x50bc2ff7(View view) {
        startActivity(new Intent(this, PremiumActivity.class));
    }
}
