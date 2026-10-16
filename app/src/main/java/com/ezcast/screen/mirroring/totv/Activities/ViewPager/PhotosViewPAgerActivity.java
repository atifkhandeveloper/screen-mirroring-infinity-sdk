package com.ezcast.screen.mirroring.totv.Activities.ViewPager;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.media3.common.MimeTypes;
import androidx.viewpager.widget.ViewPager;
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
import com.ezcast.screen.mirroring.totv.Activities.MainActivity;
import com.ezcast.screen.mirroring.totv.Activities.PhotosActivity;
import com.ezcast.screen.mirroring.totv.Activities.ThemeActivity;
import com.ezcast.screen.mirroring.totv.Activities.WebService;
import com.ezcast.screen.mirroring.totv.Adapter.PhotosViewPagerAdapter;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import com.ezcast.screen.mirroring.totv.Utils;
import java.util.ArrayList;
import java.util.List;


public class PhotosViewPAgerActivity extends ThemeActivity implements ConnectableDeviceListener {
    ArrayList<CastingModel> allImages;
    ImageView back_icon;
    ImageView cast_photos;
    String getUrl;
    private DiscoveryManager mDiscoveryManager;
    MediaInfo mediaInfo;
    String nextItemPath;
    ViewPager viewPager;
    String description = "Screen Casting";
    String mimeType = MimeTypes.IMAGE_JPEG;
    AdapterView.OnItemClickListener selectDevice = new AdapterView.OnItemClickListener() { 
        @Override 
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            MainActivity.myDevice = (ConnectableDevice) adapterView.getItemAtPosition(i);
            MainActivity.myDevice.addListener(PhotosViewPAgerActivity.this);
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
        setContentView(R.layout.activity_photos_view_pager);
        edgeToEdge(this, findViewById(R.id.mainLayout));



        this.viewPager = (ViewPager) findViewById(R.id.photos_ViewPAger);
        this.back_icon = (ImageView) findViewById(R.id.photos_viewpager_back_icon);
        this.cast_photos = (ImageView) findViewById(R.id.photos_cast_icon);
        int i = getIntent().getExtras().getInt("imageIndex");
        this.allImages = PhotosActivity.photosList;
        DiscoveryManager discoveryManager = DiscoveryManager.getInstance();
        this.mDiscoveryManager = discoveryManager;
        discoveryManager.setPairingLevel(DiscoveryManager.PairingLevel.ON);
        this.mDiscoveryManager.registerDeviceService(CastService.class, CastDiscoveryProvider.class);
        this.mDiscoveryManager.start();
        this.viewPager.setAdapter(new PhotosViewPagerAdapter(this.allImages, this));
        this.viewPager.setCurrentItem(i);
        this.getUrl = this.allImages.get(i).getImg();
        if (MainActivity.myDevice != null) {
            this.cast_photos.setImageResource(R.drawable.casting_filled_icon);
            showImageOnTv(MainActivity.myDevice, this.getUrl);
        } else {
            this.cast_photos.setImageResource(R.drawable.cast);
        }
        this.cast_photos.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                PhotosViewPAgerActivity.this.m218x9feda8b6(view);
            }
        });
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { 
            @Override 
            public void onPageScrollStateChanged(int i2) {
            }

            @Override 
            public void onPageScrolled(int i2, float f, int i3) {
            }

            @Override 
            public void onPageSelected(int i2) {
                if (MainActivity.myDevice != null) {
                    PhotosViewPAgerActivity photosViewPAgerActivity = PhotosViewPAgerActivity.this;
                    photosViewPAgerActivity.nextItemPath = photosViewPAgerActivity.allImages.get(i2).getImg();
                    PhotosViewPAgerActivity.this.showImageOnTv(MainActivity.myDevice, PhotosViewPAgerActivity.this.nextItemPath);
                }
            }
        });
        this.back_icon.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                PhotosViewPAgerActivity.this.m219xa1d30d5(view);
            }
        });
    }

    
    
    public  void m218x9feda8b6(View view) {
        if (MainActivity.myDevice != null) {
            PopupMenu popupMenu = new PopupMenu(this, this.cast_photos);
            popupMenu.getMenuInflater().inflate(R.menu.device_disconnect_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { 
                @Override 
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() != R.id.disconnectText) {
                        return true;
                    }
                    MainActivity.myDevice.disconnect();
                    PhotosViewPAgerActivity.this.cast_photos.setImageResource(R.drawable.cast);
                    MainActivity.myDevice = null;
                    MainActivity.casting_btn.setImageResource(R.drawable.cast);
                    PhotosViewPAgerActivity photosViewPAgerActivity = PhotosViewPAgerActivity.this;
                    Toast.makeText(photosViewPAgerActivity, photosViewPAgerActivity.getString(R.string.disconnected), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            popupMenu.show();
            return;
        }
        showDevice();
    }

    
    
    public  void m219xa1d30d5(View view) {
        onBackPressed();
    }

    
    @Override 
    public void onDestroy() {
        super.onDestroy();
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
    public void onDeviceReady(ConnectableDevice connectableDevice) {
        MainActivity.myDevice = connectableDevice;
        this.cast_photos.setImageResource(R.drawable.casting_filled_icon);
        MainActivity.casting_btn.setImageResource(R.drawable.casting_filled_icon);
        showImageOnTv(MainActivity.myDevice, this.getUrl);
    }

    
    @Override 
    public void onResume() {
        super.onResume();
    }
}
