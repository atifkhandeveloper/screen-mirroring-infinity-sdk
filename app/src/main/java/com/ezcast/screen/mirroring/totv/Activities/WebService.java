package com.ezcast.screen.mirroring.totv.Activities;

import android.app.IntentService;
import android.content.Intent;


public final class WebService extends IntentService {
    private final String TAG;

    public WebService() {
        super("blank");
        this.TAG = getClass().getSimpleName();
    }

    @Override 
    public void onStart(Intent intent, int i) {
        super.onStart(intent, i);
    }

    @Override 
    protected void onHandleIntent(Intent intent) {

    }

    @Override 
    public void onDestroy() {
        super.onDestroy();
    }
}
