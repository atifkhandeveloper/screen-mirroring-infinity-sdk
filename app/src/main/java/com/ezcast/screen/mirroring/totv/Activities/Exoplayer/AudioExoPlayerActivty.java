package com.ezcast.screen.mirroring.totv.Activities.Exoplayer;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.connectsdk.core.MediaInfo;
import com.connectsdk.device.ConnectableDevice;
import com.connectsdk.device.ConnectableDeviceListener;
import com.connectsdk.device.DevicePicker;
import com.connectsdk.discovery.DiscoveryManager;
import com.connectsdk.discovery.provider.CastDiscoveryProvider;
import com.connectsdk.service.CastService;
import com.connectsdk.service.DeviceService;
import com.connectsdk.service.capability.MediaPlayer;
import com.connectsdk.service.command.ServiceCommandError;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.CastingPlayer.CastPlayerActivity;
import com.ezcast.screen.mirroring.totv.Activities.MainActivity;
import com.ezcast.screen.mirroring.totv.Activities.ThemeActivity;
import com.ezcast.screen.mirroring.totv.Activities.WebService;
import com.ezcast.screen.mirroring.totv.Utils;

import java.util.List;


public class AudioExoPlayerActivty extends ThemeActivity implements ConnectableDeviceListener {
    ImageView back_icon;
    ImageView cast_audio;
    String currentAudioPath;
    ExoPlayer exoPlayer;
    private DiscoveryManager mDiscoveryManager;
    MediaInfo mediaInfo;
    PlayerView playerView;
    int position;
    String description = "Screen Casting";
    String mimeType = "audio/mp3";
    AdapterView.OnItemClickListener selectDevice = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            MainActivity.myDevice = (ConnectableDevice) adapterView.getItemAtPosition(i);
            MainActivity.myDevice.addListener(AudioExoPlayerActivty.this);
            MainActivity.myDevice.connect();
        }
    };

    @Override
    public void onCapabilityUpdated(ConnectableDevice connectableDevice, List<String> list, List<String> list2) {
    }

    @Override
    public void onConnectionFailed(ConnectableDevice connectableDevice, ServiceCommandError serviceCommandError) {
    }

    @Override
    public void onDeviceDisconnected(ConnectableDevice connectableDevice) {
    }

    @Override
    public void onPairingRequired(ConnectableDevice connectableDevice, DeviceService deviceService, DeviceService.PairingType pairingType) {
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_audio_exo_player_activty);
        edgeToEdge(this, findViewById(R.id.mainLayout));

        this.playerView = (PlayerView) findViewById(R.id.audio_exoPlayerVIew);
        this.back_icon = (ImageView) findViewById(R.id.audio_player_back_icon);
        this.cast_audio = (ImageView) findViewById(R.id.audio_cast_icon);
        this.position = getIntent().getIntExtra("audioPosition", 0);
        String valueOf = String.valueOf(Uri.parse(getIntent().getExtras().getString("ExoPlayerKey")));
        this.currentAudioPath = valueOf;
        Uri parse = Uri.parse(valueOf);
        ExoPlayer build = new ExoPlayer.Builder(this).build();
        this.exoPlayer = build;
        this.playerView.setPlayer(build);
        this.exoPlayer.setMediaItem(MediaItem.fromUri(parse));
        this.exoPlayer.prepare();
        DiscoveryManager discoveryManager = DiscoveryManager.getInstance();
        this.mDiscoveryManager = discoveryManager;
        discoveryManager.setPairingLevel(DiscoveryManager.PairingLevel.ON);
        this.mDiscoveryManager.registerDeviceService(CastService.class, CastDiscoveryProvider.class);
        this.mDiscoveryManager.start();
        if (MainActivity.myDevice != null) {
            this.cast_audio.setImageResource(R.drawable.casting_filled_icon);
            showImageOnTv(MainActivity.myDevice, this.currentAudioPath);
        } else {
            this.cast_audio.setImageResource(R.drawable.cast);
        }
        this.cast_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AudioExoPlayerActivty.this.m194x83034c06(view);
            }
        });
        this.back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                AudioExoPlayerActivty.this.m195x1644fe5(view);
            }
        });
    }



    public  void m194x83034c06(View view) {
        if (MainActivity.myDevice != null) {
            PopupMenu popupMenu = new PopupMenu(this, this.cast_audio);
            popupMenu.getMenuInflater().inflate(R.menu.device_disconnect_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() != R.id.disconnectText) {
                        return true;
                    }
                    MainActivity.myDevice.disconnect();
                    MainActivity.myDevice = null;
                    MainActivity.casting_btn.setImageResource(R.drawable.cast);
                    AudioExoPlayerActivty.this.cast_audio.setImageResource(R.drawable.cast);
                    return true;
                }
            });
            popupMenu.show();
            return;
        }
        showDevice();
    }



    public  void m195x1644fe5(View view) {
        onBackPressed();
    }

    public void showImageOnTv(ConnectableDevice connectableDevice, String str) {
        MediaPlayer mediaPlayer = (MediaPlayer) connectableDevice.getCapability(MediaPlayer.class);
        if (mediaPlayer != null) {
            mediaPlayer.playMedia(buildMediaInfo(str), true, new MediaPlayer.LaunchListener() {
                @Override
                public void onError(ServiceCommandError serviceCommandError) {
                }

                @Override
                public void onSuccess(MediaPlayer.MediaLaunchObject mediaLaunchObject) {
                    Intent intent = new Intent(AudioExoPlayerActivty.this, CastPlayerActivity.class);
                    intent.putExtra("mimeType", AudioExoPlayerActivty.this.mimeType);
                    intent.putExtra("position", AudioExoPlayerActivty.this.position);
                    intent.putExtra("boolean_video", false);
                    AudioExoPlayerActivty.this.startActivity(intent);
                    AudioExoPlayerActivty.this.finish();
                }
            });
        }
    }

    private MediaInfo buildMediaInfo(String str) {
        MainActivity.deviceIpAddress = Utils.Companion.findIPAddress(getApplicationContext());
        String[] split = str.split("/0");
        String str2 = MainActivity.deviceIpAddress;
        startService(new Intent(this, WebService.class));
        String str3 = "http://" + str2 + ":4242/" + split[1];
        MediaInfo build = new MediaInfo.Builder(str3, this.mimeType).setTitle("http://" + str2 + ":4242/" + split[1]).setDescription(this.description).setIcon(str3).build();
        this.mediaInfo = build;
        return build;
    }

    private void showDevice() {
        new DevicePicker(this).getPickerDialog(getString(R.string.show_devices), this.selectDevice).show();
    }


    @Override
    public void onDestroy() {
        this.exoPlayer.stop();
        super.onDestroy();
    }

    @Override
    public void onDeviceReady(ConnectableDevice connectableDevice) {
        MainActivity.myDevice = connectableDevice;
        Toast.makeText(this, getString(R.string.connected), 0).show();
        showImageOnTv(MainActivity.myDevice, this.currentAudioPath);
    }
}
