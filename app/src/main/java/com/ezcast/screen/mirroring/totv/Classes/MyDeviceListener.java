package com.ezcast.screen.mirroring.totv.Classes;

import android.widget.Toast;
import com.connectsdk.service.DeviceService;
import com.ezcast.screen.mirroring.totv.Activities.ViewPager.PhotosViewPAgerActivity;
import java.util.Map;


public class MyDeviceListener {
    public boolean isConnecting = false;
    Map<String, DeviceService> services;

    public void onDeviceReady(PhotosViewPAgerActivity photosViewPAgerActivity) {
        Toast.makeText(photosViewPAgerActivity, "hello", 0).show();
    }

    public void connect() {
        new Thread(new Runnable() { 
            @Override 
            public void run() {
                MyDeviceListener.this.isConnecting = true;
                for (DeviceService deviceService : MyDeviceListener.this.services.values()) {
                    if (!deviceService.isConnected()) {
                        deviceService.connect();
                    }
                }
                MyDeviceListener.this.isConnecting = false;
            }
        }).start();
    }
}
