package com.ezcast.screen.mirroring.totv.MainFragment;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.AppsActivity;


public class RemoteFragment extends Fragment {
    private Activity activity;
    ImageView apps_btn;
    ImageView back_btn;
    ImageView backward_arrow;
    ImageView channel_down;
    ImageView channel_up;
    Context context;
    RelativeLayout dPad_layout;
    TextView eight;
    TextView five;
    ImageView forward_arrow;
    TextView four;
    ImageView home_btn;
    ConsumerIrManager irManager;
    ImageView jump_btn;
    ImageView keyboard_btn;
    ImageView left_arrow;
    ImageView mute_btn;
    TextView nine;
    ImageView numPad_btn;
    ConstraintLayout numeric_layout;
    ImageView ok_btn;
    TextView one;
    ImageView plus_btn;
    RelativeLayout plus_layout;
    ImageView power_btn;
    ImageView refresh_btn;
    ImageView right_arrow;
    TextView seven;
    TextView six;
    TextView three;
    ImageView touchPad_btn;
    RelativeLayout touchPad_layout;
    RelativeLayout touch_area;
    TextView two;
    ImageView vol_down;
    ImageView vol_up;
    TextView zero;

    private void initializeFields() {
    }

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_remote, viewGroup, false);
        initializeFields();
        this.one = (TextView) inflate.findViewById(R.id.numpad_1);
        this.two = (TextView) inflate.findViewById(R.id.numpad_2);
        this.three = (TextView) inflate.findViewById(R.id.numpad_3);
        this.four = (TextView) inflate.findViewById(R.id.numpad_4);
        this.five = (TextView) inflate.findViewById(R.id.numpad_5);
        this.six = (TextView) inflate.findViewById(R.id.numpad_6);
        this.seven = (TextView) inflate.findViewById(R.id.numpad_7);
        this.eight = (TextView) inflate.findViewById(R.id.numpad_8);
        this.nine = (TextView) inflate.findViewById(R.id.numpad_9);
        this.zero = (TextView) inflate.findViewById(R.id.numpad_0);
        this.plus_btn = (ImageView) inflate.findViewById(R.id.plus_btn);
        this.numPad_btn = (ImageView) inflate.findViewById(R.id.numpad_btn);
        this.touchPad_btn = (ImageView) inflate.findViewById(R.id.toucpad_btn);
        this.apps_btn = (ImageView) inflate.findViewById(R.id.apps_btn);
        this.power_btn = (ImageView) inflate.findViewById(R.id.powerOnOff_btn);
        this.vol_up = (ImageView) inflate.findViewById(R.id.vol_up_btn);
        this.vol_down = (ImageView) inflate.findViewById(R.id.vol_down_btn);
        this.home_btn = (ImageView) inflate.findViewById(R.id.home_btn);
        this.refresh_btn = (ImageView) inflate.findViewById(R.id.refresh_btn);
        this.back_btn = (ImageView) inflate.findViewById(R.id.back_btn);
        this.keyboard_btn = (ImageView) inflate.findViewById(R.id.keyBoard_btn);
        this.mute_btn = (ImageView) inflate.findViewById(R.id.mute_btn);
        this.jump_btn = (ImageView) inflate.findViewById(R.id.jump_btn);
        this.channel_up = (ImageView) inflate.findViewById(R.id.channel_up);
        this.channel_down = (ImageView) inflate.findViewById(R.id.channel_down);
        this.left_arrow = (ImageView) inflate.findViewById(R.id.left_d_pad_btn);
        this.right_arrow = (ImageView) inflate.findViewById(R.id.right_d_pad_btn);
        this.forward_arrow = (ImageView) inflate.findViewById(R.id.forward_d_pad_btn);
        this.backward_arrow = (ImageView) inflate.findViewById(R.id.backward_d_pad_btn);
        this.ok_btn = (ImageView) inflate.findViewById(R.id.ok_btn);
        this.dPad_layout = (RelativeLayout) inflate.findViewById(R.id.d_pad_layout);
        this.plus_layout = (RelativeLayout) inflate.findViewById(R.id.plus_layout);
        this.touchPad_layout = (RelativeLayout) inflate.findViewById(R.id.touchpad_layout);
        this.numeric_layout = (ConstraintLayout) inflate.findViewById(R.id.numeric_layout);
        this.touch_area = (RelativeLayout) inflate.findViewById(R.id.touch_area);
        this.plus_layout.setVisibility(View.VISIBLE);
        final AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.scale);
        this.dPad_layout.setVisibility(View.VISIBLE);
        this.irManager = (ConsumerIrManager) getContext().getSystemService(Context.CONSUMER_IR_SERVICE);
        this.plus_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.plus_btn);
                animatorSet.start();
                RemoteFragment.this.dPad_layout.setVisibility(View.VISIBLE);
                RemoteFragment.this.numeric_layout.setVisibility(View.GONE);
                RemoteFragment.this.touch_area.setVisibility(View.GONE);
            }
        });
        this.numPad_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.numPad_btn);
                animatorSet.start();
                RemoteFragment.this.dPad_layout.setVisibility(View.GONE);
                RemoteFragment.this.touch_area.setVisibility(View.GONE);
                RemoteFragment.this.numeric_layout.setVisibility(View.VISIBLE);
            }
        });
        this.touchPad_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.touchPad_btn);
                animatorSet.start();
                RemoteFragment.this.dPad_layout.setVisibility(View.GONE);
                RemoteFragment.this.touch_area.setVisibility(View.VISIBLE);
                RemoteFragment.this.numeric_layout.setVisibility(View.GONE);
            }
        });
        this.apps_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.apps_btn);
                animatorSet.start();
                RemoteFragment.this.startActivity(new Intent(RemoteFragment.this.getActivity(), AppsActivity.class));
            }
        });
        this.power_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.power_btn);
                animatorSet.start();
                if (!RemoteFragment.this.irManager.hasIrEmitter()) {
                    Toast.makeText(RemoteFragment.this.getContext(), "Not Found", 0).show();
                }
                if (RemoteFragment.this.irManager == null || !RemoteFragment.this.irManager.hasIrEmitter()) {
                    return;
                }
                RemoteFragment.this.irManager.transmit(38000, new int[]{9000, 4500, 562, 562, 562, 562, 562, 1687, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 1687, 562, 1687, 562, 562, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 562, 562, 562, 562, 562, 562, 1687, 562, 562, 562, 562, 562, 562, 562, 562, 562, 1687, 562, 1687, 562, 1687, 562, 562, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 41970, 9000, 2250, 562, 98188, 9000, 2250, 562, 98188});
            }
        });
        this.ok_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.ok_btn);
                animatorSet.start();
            }
        });
        this.left_arrow.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.left_arrow);
                animatorSet.start();
            }
        });
        this.right_arrow.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.right_arrow);
                animatorSet.start();
            }
        });
        this.forward_arrow.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.forward_arrow);
                animatorSet.start();
            }
        });
        this.backward_arrow.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.backward_arrow);
                animatorSet.start();
            }
        });
        this.vol_up.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.vol_up);
                animatorSet.start();
                if (!RemoteFragment.this.irManager.hasIrEmitter()) {
                    Toast.makeText(RemoteFragment.this.getContext(), "Not Found", 0).show();
                }
                if (RemoteFragment.this.irManager == null || !RemoteFragment.this.irManager.hasIrEmitter()) {
                    return;
                }
                RemoteFragment.this.irManager.transmit(38000, new int[]{9000, 4500, 562, 562, 562, 562, 562, 1687, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 1687, 562, 1687, 562, 562, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 562, 562, 1687, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 1687, 562, 562, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 41970, 9000, 2250, 562, 98188, 9000, 2250, 562, 98188});
            }
        });
        this.vol_down.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.vol_down);
                animatorSet.start();
                if (!RemoteFragment.this.irManager.hasIrEmitter()) {
                    Toast.makeText(RemoteFragment.this.getContext(), "Not Found", 0).show();
                }
                if (RemoteFragment.this.irManager == null || !RemoteFragment.this.irManager.hasIrEmitter()) {
                    return;
                }
                RemoteFragment.this.irManager.transmit(38000, new int[]{9000, 4500, 562, 562, 562, 562, 562, 1687, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 1687, 562, 1687, 562, 562, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 1687, 562, 41970, 9000, 2250, 562, 98188, 9000, 2250, 562, 98188});
            }
        });
        this.home_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.home_btn);
                animatorSet.start();
            }
        });
        this.refresh_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.refresh_btn);
                animatorSet.start();
            }
        });
        this.back_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.back_btn);
                animatorSet.start();
            }
        });
        this.keyboard_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.keyboard_btn);
                animatorSet.start();
            }
        });
        this.mute_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.mute_btn);
                animatorSet.start();
            }
        });
        this.jump_btn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.jump_btn);
                animatorSet.start();
            }
        });
        this.channel_up.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.channel_up);
                animatorSet.start();
            }
        });
        this.channel_down.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.channel_down);
                animatorSet.start();
            }
        });
        this.one.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.one);
                animatorSet.start();
            }
        });
        this.two.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.two);
                animatorSet.start();
            }
        });
        this.three.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.three);
                animatorSet.start();
            }
        });
        this.four.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.four);
                animatorSet.start();
            }
        });
        this.five.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.five);
                animatorSet.start();
            }
        });
        this.six.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.six);
                animatorSet.start();
            }
        });
        this.seven.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.seven);
                animatorSet.start();
            }
        });
        this.eight.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.eight);
                animatorSet.start();
            }
        });
        this.nine.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.nine);
                animatorSet.start();
            }
        });
        this.zero.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                animatorSet.setTarget(RemoteFragment.this.zero);
                animatorSet.start();
            }
        });
        return inflate;
    }
}
