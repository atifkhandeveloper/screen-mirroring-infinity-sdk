package com.ezcast.screen.mirroring.totv.HowToFargment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.ezcast.screen.mirroring.totv.R;


public class First extends Fragment {
    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_first, viewGroup, false);
    }
}
