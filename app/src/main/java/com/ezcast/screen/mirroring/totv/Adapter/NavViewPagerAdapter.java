package com.ezcast.screen.mirroring.totv.Adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.ezcast.screen.mirroring.totv.MainFragment.CastingFragment;
import com.ezcast.screen.mirroring.totv.MainFragment.ChannelFragment;


public class NavViewPagerAdapter extends FragmentPagerAdapter {
    Context context;

    @Override 
    public int getCount() {
        return 1;
    }

    public NavViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override 
    public Fragment getItem(int i) {
        if (i == 0) {
            return new CastingFragment(this.context);
        }
        if (i == 1) {
            return new CastingFragment(this.context);
        }
        return new ChannelFragment();
    }
}
