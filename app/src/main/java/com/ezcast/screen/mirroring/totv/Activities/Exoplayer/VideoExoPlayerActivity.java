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

import androidx.activity.EdgeToEdge;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
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
import com.ezcast.screen.mirroring.totv.Activities.ViewPager.VideosViePagerActivity;
import com.ezcast.screen.mirroring.totv.Activities.WebService;
import com.ezcast.screen.mirroring.totv.Utils;

import java.util.List;


public class VideoExoPlayerActivity extends ThemeActivity implements ConnectableDeviceListener {
    ImageView back_icon;
    ImageView cast_video;
    ExoPlayer exoPlayer;
    private DiscoveryManager mDiscoveryManager;
    MediaInfo mediaInfo;
    PlayerView playerView;
    int position;
    String description = "Screen Casting";
    String mimeType = MimeTypes.VIDEO_MP4;
    AdapterView.OnItemClickListener selectDevice = new AdapterView.OnItemClickListener() { 
        @Override 
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            MainActivity.myDevice = (ConnectableDevice) adapterView.getItemAtPosition(i);
            MainActivity.myDevice.addListener(VideoExoPlayerActivity.this);
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

        setContentView(R.layout.activity_exo_player);
        edgeToEdge(this, findViewById(R.id.mainLayout));

        this.playerView = (PlayerView) findViewById(R.id.videos_exoPlayerVIew);
        this.back_icon = (ImageView) findViewById(R.id.videos_exoplayer_toolbar_back_icon);
        this.cast_video = (ImageView) findViewById(R.id.exo_video_cast_icon);
        this.position = getIntent().getIntExtra("position", 0);
        Uri parse = Uri.parse(String.valueOf(Uri.parse(getIntent().getExtras().getString("ExoPlayerKey"))));
        exoPlayer = new ExoPlayer.Builder(this).build();
        this.playerView.setPlayer(exoPlayer);
        this.exoPlayer.setMediaItem(MediaItem.fromUri(parse));
        this.exoPlayer.prepare();
        DiscoveryManager discoveryManager = DiscoveryManager.getInstance();
        this.mDiscoveryManager = discoveryManager;
        discoveryManager.setPairingLevel(DiscoveryManager.PairingLevel.ON);
        this.mDiscoveryManager.registerDeviceService(CastService.class, CastDiscoveryProvider.class);
        this.mDiscoveryManager.start();
        if (MainActivity.myDevice != null) {
            this.cast_video.setImageResource(R.drawable.casting_filled_icon);
            showImageOnTv(MainActivity.myDevice, VideosViePagerActivity.currentVideoPath);
        } else {
            this.cast_video.setImageResource(R.drawable.cast);
        }
        this.cast_video.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                VideoExoPlayerActivity.this.m196x18895148(view);
            }
        });
        this.back_icon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                VideoExoPlayerActivity.this.m197x6648c949(view);
            }
        });
    }

    public  void m196x18895148(View view) {
        if (MainActivity.myDevice != null) {
            PopupMenu popupMenu = new PopupMenu(this, this.cast_video);
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
                    VideoExoPlayerActivity.this.cast_video.setImageResource(R.drawable.cast);
                    return true;
                }
            });
            popupMenu.show();
            return;
        }
        showDevice();
    }

    
    
    public  void m197x6648c949(View view) {
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
                    Intent intent = new Intent(VideoExoPlayerActivity.this, CastPlayerActivity.class);
                    intent.putExtra("mimeType", VideoExoPlayerActivity.this.mimeType);
                    intent.putExtra("position", VideoExoPlayerActivity.this.position);
                    intent.putExtra("boolean_video", true);
                    VideoExoPlayerActivity.this.startActivity(intent);
                    VideoExoPlayerActivity.this.finish();
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
    public void onPause() {
        super.onPause();
        this.exoPlayer.pause();
    }

    @Override 
    public void onDeviceReady(ConnectableDevice connectableDevice) {
        MainActivity.myDevice = connectableDevice;
        MainActivity.casting_btn.setImageResource(R.drawable.casting_filled_icon);
        this.cast_video.setImageResource(R.drawable.casting_filled_icon);
        showImageOnTv(MainActivity.myDevice, VideosViePagerActivity.currentVideoPath);
    }
}
