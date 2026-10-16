package com.ezcast.screen.mirroring.totv.Activities;

import static android.app.PendingIntent.getActivity;
import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.connectsdk.device.ConnectableDevice;
import com.connectsdk.device.ConnectableDeviceListener;
import com.connectsdk.device.DevicePicker;
import com.connectsdk.discovery.DiscoveryManager;
import com.connectsdk.discovery.DiscoveryManagerListener;
import com.connectsdk.service.DeviceService;
import com.connectsdk.service.command.ServiceCommandError;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfig;
import com.ezcast.screen.mirroring.totv.infinity.component.main.dialog.ForceUpdateDialog;
import com.ezcast.screen.mirroring.totv.infinity.component.main.dialog.NoInternetDialog;
import com.ezcast.screen.mirroring.totv.infinity.data.event.EventTracking;
import com.ezcast.screen.mirroring.totv.infinity.data.model.ForceUpdateConfig;
import com.ezcast.screen.mirroring.totv.infinity.ui.bases.BannerConfig;
import com.ezcast.screen.mirroring.totv.infinity.ui.bases.ConsentHandler;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.ezcast.screen.mirroring.totv.Adapter.NavViewPagerAdapter;
import com.ezcast.screen.mirroring.totv.Classes.InternetPermission;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.infinity.utils.ITGTrackingHelper;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ThemeActivity implements
        ConnectableDeviceListener,
        DiscoveryManagerListener {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;

    public static String castingUrl;
    public static String deviceIpAddress;
    public static ConnectableDevice myDevice;
    public static ImageView casting_btn;

    private DiscoveryManager mDiscoveryManager;
    private WifiManager.MulticastLock multicastLock;

    private ConstraintLayout casting_custom_toolbar;
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private ImageView setting_btn_casting;
    private NavViewPagerAdapter navViewPagerAdapter;

    // ---- New views from the fragment XML ----
    private TextView searching_textview;
    private AppCompatButton search_by_google_btn;
    private RelativeLayout btn_scan_now;
    private ImageView help_icon_toolbar;


//    protected BannerConfig getBannerConfig() {
//        return new BannerConfig(AdRemoteConfig.banner_home, true);
//    }

    private ConsentHandler consentHandler;

    private final Handler delayHandler = new Handler(Looper.getMainLooper());
    private Runnable delayRunnable;

    private NoInternetDialog noInternetDialog;
    private ForceUpdateDialog forceUpdateDialog;

    private Dialog forceUpdateDialogHandle;
    private ForceUpdateConfig cachedForceUpdateConfig;
//    private TextView tv_connected_status;

    AdapterView.OnItemClickListener selectDevice =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    myDevice = (ConnectableDevice) parent.getItemAtPosition(position);
                    myDevice.addListener(MainActivity.this);
                    myDevice.connect();
                }
            };

    // =========================================================================
    // LIFECYCLE
    // =========================================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        edgeToEdge(this, findViewById(R.id.main));

        showGoogleRateDialog();



        initViews();
        setupViewPager();
        setupClicks();
        showDefaultToolbar();

        checkPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (multicastLock != null && multicastLock.isHeld()) {
            multicastLock.release();
        }
        if (mDiscoveryManager != null) {
            mDiscoveryManager.stop();
        }
    }



    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    // =========================================================================
    // PERMISSIONS
    // =========================================================================
    private void checkPermissions() {
        List<String> permsNeeded = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                permsNeeded.add(Manifest.permission.BLUETOOTH_CONNECT);
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
                permsNeeded.add(Manifest.permission.NEARBY_WIFI_DEVICES);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }

        if (!permsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permsNeeded.toArray(new String[0]),
                    PERMISSION_REQUEST_CODE);
            return;
        }

        startDiscovery();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            startDiscovery();
        }
    }

    // =========================================================================
    // DISCOVERY
    // =========================================================================
    private void startDiscovery() {
        setupMulticast();
        setupDiscovery();
    }

    private void setupMulticast() {
        WifiManager wifi = (WifiManager) getApplicationContext()
                .getSystemService(WIFI_SERVICE);
        multicastLock = wifi.createMulticastLock("connectsdk");
        multicastLock.setReferenceCounted(true);
        multicastLock.acquire();

        Log.d(TAG, "WiFi enabled:       " + wifi.isWifiEnabled());
        Log.d(TAG, "Multicast lock held: " + multicastLock.isHeld());
    }

    private void setupDiscovery() {
        DiscoveryManager.init(getApplicationContext());
        mDiscoveryManager = DiscoveryManager.getInstance();
        mDiscoveryManager.setPairingLevel(DiscoveryManager.PairingLevel.ON);
        mDiscoveryManager.registerDefaultDeviceTypes();
        try {
            Class<? extends DeviceService> castService =
                    Class.forName("com.connectsdk.service.CastService")
                            .asSubclass(DeviceService.class);
            Class<? extends com.connectsdk.discovery.DiscoveryProvider> castProvider =
                    Class.forName("com.connectsdk.discovery.provider.CastDiscoveryProvider")
                            .asSubclass(com.connectsdk.discovery.DiscoveryProvider.class);
            mDiscoveryManager.registerDeviceService(castService, castProvider);
        } catch (ClassNotFoundException | ClassCastException e) {
            Log.w(TAG, "Connect SDK Chromecast module is not available", e);
        }
        mDiscoveryManager.addListener(this);
        mDiscoveryManager.start();
    }

    // =========================================================================
    // VIEWS
    // =========================================================================
    private void initViews() {
        viewPager              = findViewById(R.id.viewPager);
        casting_custom_toolbar = findViewById(R.id.casting_custom_toolbar);
        navigationView         = findViewById(R.id.bottom_nav_view);
        casting_btn            = findViewById(R.id.casting_btn);
        setting_btn_casting    = findViewById(R.id.setting_icon_casting_toolbar);
        help_icon_toolbar      = findViewById(R.id.help_icon_toolbar);

        // New views that live inside the fragment layout
        searching_textview     = findViewById(R.id.searching_textview);
        search_by_google_btn   = findViewById(R.id.search_by_google_btn);
        btn_scan_now           = findViewById(R.id.connect_tv_card);
//        tv_connected_status    = findViewById(R.id.tv_connected_status);
    }

    private void setupViewPager() {
        navViewPagerAdapter = new NavViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(navViewPagerAdapter);

//        navigationView.setOnNavigationItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.remote) {
//                viewPager.setCurrentItem(0);
//            }
//            return true;
//        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int p, float o, int px) {}
            @Override public void onPageSelected(int position) {
                navigationView.getMenu().getItem(position).setChecked(true);
            }
            @Override public void onPageScrollStateChanged(int state) {}
        });
    }

    private void setupClicks() {
        setting_btn_casting.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingActivity.class)));

        casting_btn.setOnClickListener(v -> handleCastButton());

        // "Scan Now" button on the Connect to TV card
//        if (btn_scan_now != null) {
//            btn_scan_now.setOnClickListener(v -> handleCastButton());
//        }

        // Search bar: tapping it opens the URL input / browser flow
        if (searching_textview != null) {
            searching_textview.setOnClickListener(v -> openSearchUrl());
        }

        // PASTE button on the search bar
        if (search_by_google_btn != null) {
            search_by_google_btn.setOnClickListener(v -> pasteFromClipboard());
        }

        help_icon_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HowToConnectActivity.class));
            }
        });
    }

    private void handleCastButton() {
        if (!InternetPermission.isNetworkAvailable(MainActivity.this)) {
            Toast.makeText(this,
                    getString(R.string.network_connection_not_found),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (myDevice != null) {
            showDisconnectPopup();
        } else {
            showDevicePicker();
        }
    }

    private void openSearchUrl() {
        // TODO: replace with the actual URL entry / browser activity
        // For now, focus an input or start a browser-like screen.
        Toast.makeText(this, R.string.search_by_google_or_enter_your_url,
                Toast.LENGTH_SHORT).show();
    }

    private void pasteFromClipboard() {
        android.content.ClipboardManager cm =
                (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (cm != null && cm.hasPrimaryClip()
                && cm.getPrimaryClip().getItemCount() > 0) {
            CharSequence pasted = cm.getPrimaryClip().getItemAt(0).getText();
            if (pasted != null && searching_textview != null) {
                searching_textview.setText(pasted);
                castingUrl = pasted.toString();
            }
        } else {
            Toast.makeText(this, "Clipboard empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDefaultToolbar() {
        casting_custom_toolbar.setVisibility(View.VISIBLE);
    }

    // =========================================================================
    // DEVICE PICKER
    // =========================================================================
    private void showDevicePicker() {
        new DevicePicker(this)
                .getPickerDialog(getString(R.string.show_devices), selectDevice)
                .show();
    }

    private void showDisconnectPopup() {
        PopupMenu popupMenu = new PopupMenu(this, casting_btn);
        popupMenu.getMenuInflater().inflate(R.menu.device_disconnect_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.disconnectText && myDevice != null) {
                myDevice.disconnect();
                myDevice = null;
                casting_btn.setImageResource(R.drawable.cast);
                updateConnectedPill(false);
                Toast.makeText(this, getString(R.string.disconnected), Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        popupMenu.show();
    }

    // =========================================================================
    // ConnectableDeviceListener
    // =========================================================================
    @Override
    public void onDeviceReady(ConnectableDevice device) {
        myDevice = device;
        casting_btn.setImageResource(R.drawable.casting_filled_icon);
        updateConnectedPill(true);
        Toast.makeText(this, getString(R.string.connected), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Device ready: " + device.getFriendlyName());
    }

    @Override
    public void onDeviceDisconnected(ConnectableDevice device) {
        myDevice = null;
        casting_btn.setImageResource(R.drawable.cast);
        updateConnectedPill(false);
        Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPairingRequired(ConnectableDevice device,
                                  DeviceService service,
                                  DeviceService.PairingType pairingType) {
        Toast.makeText(this, "Pairing Required", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCapabilityUpdated(ConnectableDevice device,
                                    List<String> added,
                                    List<String> removed) {}

    @Override
    public void onConnectionFailed(ConnectableDevice device, ServiceCommandError error) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Connection failed: " + error.getMessage());
    }

    // =========================================================================
    // DiscoveryManagerListener
    // =========================================================================
    @Override
    public void onDeviceAdded(DiscoveryManager manager, ConnectableDevice device) {
        Log.d(TAG, "Device found: " + device.getFriendlyName()
                + " | IP: " + device.getIpAddress()
                + " | Services: " + device.getServices());
        Toast.makeText(this, "Found: " + device.getFriendlyName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeviceUpdated(DiscoveryManager manager, ConnectableDevice device) {
        Log.d(TAG, "Device updated: " + device.getFriendlyName());
    }

    @Override
    public void onDeviceRemoved(DiscoveryManager manager, ConnectableDevice device) {
        Log.d(TAG, "Device removed: " + device.getFriendlyName());
    }

    @Override
    public void onDiscoveryFailed(DiscoveryManager manager, ServiceCommandError error) {
        Log.e(TAG, "Discovery failed: " + (error != null ? error.getMessage() : "unknown"));
        Toast.makeText(this, "Discovery Failed", Toast.LENGTH_SHORT).show();
    }

    // =========================================================================
    // MISC
    // =========================================================================
    private void updateConnectedPill(boolean connected) {
//        if (tv_connected_status == null) return;
//        tv_connected_status.setText(connected ? R.string.connected : R.string.disconnected);
        // Optional: change dot / pill color here
    }

    private void showGoogleRateDialog() {
        ReviewManager manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(reviewTask -> {
                    // Review flow finished
                });
            }
        });
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}