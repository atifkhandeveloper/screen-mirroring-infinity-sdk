package com.ezcast.screen.mirroring.totv;

import android.content.Context;
import android.net.wifi.WifiManager;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;



public final class Utils {
    public static final Companion Companion = new Companion(null);

    public static final class Companion {
        public  Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String findIPAddress(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Object systemService = context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.wifi.WifiManager");
            WifiManager wifiManager = (WifiManager) systemService;
            try {
                if (wifiManager.getConnectionInfo() != null) {
                    return InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(wifiManager.getConnectionInfo().getIpAddress()).array()).getHostAddress();
                }
                return null;
            } catch (Exception unused) {
                return null;
            }
        }
    }
}
