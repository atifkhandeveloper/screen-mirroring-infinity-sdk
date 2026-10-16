package com.ezcast.screen.mirroring.totv.Provider;

import android.content.Context;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.NotificationOptions;

import java.util.List;

import kotlin.jvm.internal.Intrinsics;



public final class CastOptionsProvider implements OptionsProvider {
    @Override 
    public List<SessionProvider> getAdditionalSessionProviders(Context p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return null;
    }

    @Override 
    public CastOptions getCastOptions(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        NotificationOptions build = new NotificationOptions.Builder().setTargetActivityClassName(ExpandedControlsActivity.class.getName()).build();
        Intrinsics.checkNotNullExpressionValue(build, "Builder()\n            .s…ame)\n            .build()");
        CastMediaOptions build2 = new CastMediaOptions.Builder().setNotificationOptions(build).setExpandedControllerActivityClassName(ExpandedControlsActivity.class.getName()).build();
        Intrinsics.checkNotNullExpressionValue(build2, "Builder()\n            .s…ame)\n            .build()");
        CastOptions build3 = new CastOptions.Builder().setReceiverApplicationId(CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID).setCastMediaOptions(build2).build();
        Intrinsics.checkNotNullExpressionValue(build3, "Builder()\n            .s…ons)\n            .build()");
        return build3;
    }
}
