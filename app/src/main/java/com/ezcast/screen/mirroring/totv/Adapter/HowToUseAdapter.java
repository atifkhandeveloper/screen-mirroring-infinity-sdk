package com.ezcast.screen.mirroring.totv.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ezcast.screen.mirroring.totv.HowToFargment.First;
import com.ezcast.screen.mirroring.totv.HowToFargment.Second;
import com.ezcast.screen.mirroring.totv.HowToFargment.Third;


public class HowToUseAdapter extends FragmentPagerAdapter {

    public HowToUseAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new First();
            case 1:
                return new Second();
            case 2:
            default:
                return new Third();
        }
    }
}