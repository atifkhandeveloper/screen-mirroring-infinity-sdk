package com.ezcast.screen.mirroring.totv.Provider;

import android.view.Menu;

import com.ezcast.screen.mirroring.totv.R;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.media.widget.ExpandedControllerActivity;

import kotlin.jvm.internal.Intrinsics;



public final class ExpandedControlsActivity extends ExpandedControllerActivity {
    @Override 
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.expanded_controller, menu);
        Intrinsics.checkNotNull(menu);
        CastButtonFactory.setUpMediaRouteButton(this, menu, R.id.media_route_menu_item);
        return true;
    }
}
