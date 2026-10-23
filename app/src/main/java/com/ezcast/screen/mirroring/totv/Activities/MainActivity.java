package com.ezcast.screen.mirroring.totv.Activities;

import static com.ezcast.screen.mirroring.totv.ExtentionKt.edgeToEdge;

import android.Manifest;
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
import android.widget.FrameLayout;
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
import androidx.lifecycle.LiveData;
import androidx.viewpager.widget.ViewPager;

import com.ads.module.ads.wrapper.ApNativeAd;
import com.connectsdk.device.ConnectableDevice;
import com.connectsdk.device.ConnectableDeviceListener;
import com.connectsdk.device.DevicePicker;
import com.connectsdk.discovery.DiscoveryManager;
import com.connectsdk.discovery.DiscoveryManagerListener;
import com.connectsdk.service.DeviceService;
import com.connectsdk.service.command.ServiceCommandError;

import com.ezcast.screen.mirroring.totv.Adapter.NavViewPagerAdapter;
import com.ezcast.screen.mirroring.totv.Classes.InternetPermission;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfig;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdUnitConfig;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdsManager;
import com.ezcast.screen.mirroring.totv.infinity.ads.AdRemoteConfigExtensionsKt;
import com.ezcast.screen.mirroring.totv.infinity.data.event.EventTracking;
import com.ezcast.screen.mirroring.totv.infinity.utils.ITGTrackingHelper;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ThemeActivity implements
        ConnectableDeviceListener,
        DiscoveryManagerListener {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;

    /** Banner auto-refresh interval: 60 seconds. */
    private static final long BANNER_REFRESH_INTERVAL_MS = 60_000L;

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

    private TextView searching_textview;
    private AppCompatButton search_by_google_btn;
    private RelativeLayout btn_scan_now;
    private ImageView help_icon_toolbar;

    private FrameLayout frBanner;
    private boolean bannerLoaded = false;
    private boolean welcomeAdHandled = false;

    // ── Banner refresh state ─────────────────────────────────
    private final Handler bannerRefreshHandler = new Handler(Looper.getMainLooper());
    private Runnable bannerRefreshRunnable;

    // ── Native ad views / state ──────────────────────────────
    private FrameLayout flNative;
    private ShimmerFrameLayout shimmerNative;
    private boolean nativeRequested = false;

    // ── Click interstitial state ─────────────────────────────
    private boolean clickInterAdReady = false;
    private boolean clickInterAdLoading = false;

    private final Handler delayHandler = new Handler(Looper.getMainLooper());

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

        ITGTrackingHelper.INSTANCE.logEvent(EventTracking.HOME_SCREEN, null);

        showGoogleRateDialog();

        initViews();
        setupViewPager();
        setupClicks();
        showDefaultToolbar();

        observeNativeAd();
        loadNativeHome();

        checkPermissions();

        showWelcomeInterstitialOnce();

        loadClickInterstitial();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        recreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume banner refresh cycle when the screen becomes visible again
        if (bannerLoaded) {
            scheduleBannerRefresh();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause banner refresh while not visible to save battery / data
        cancelBannerRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelBannerRefresh();

        if (multicastLock != null && multicastLock.isHeld()) {
            multicastLock.release();
        }
        if (mDiscoveryManager != null) {
            mDiscoveryManager.stop();
        }
        if (shimmerNative != null) {
            shimmerNative.stopShimmer();
        }
        if (delayHandler != null) {
            delayHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    // =========================================================================
    // BANNER — load + auto-refresh every 60 seconds
    // =========================================================================
    private void loadMainBanner() {
        if (frBanner == null) return;

        AdUnitConfig bannerConfig =
                AdRemoteConfigExtensionsKt.getBanner_home(AdRemoteConfig.Companion);

        if (!InternetPermission.isNetworkAvailable(this)) {
            frBanner.setVisibility(View.GONE);
            cancelBannerRefresh();
            return;
        }

        frBanner.setVisibility(View.VISIBLE);

        AdsManager.INSTANCE.loadBanner(
                this,
                bannerConfig,
                frBanner,
                true
        );

        bannerLoaded = true;

        // Schedule the next refresh
        scheduleBannerRefresh();
    }

    /**
     * Schedules a single banner reload after {@link #BANNER_REFRESH_INTERVAL_MS}.
     * Re-schedules itself after each successful refresh.
     */
    private void scheduleBannerRefresh() {
        cancelBannerRefresh();

        bannerRefreshRunnable = () -> {
            if (isFinishing() || isDestroyed()) return;
            if (frBanner == null) return;

            if (!InternetPermission.isNetworkAvailable(this)) {
                // No network — stop looping until next onResume / manual reload
                Log.d(TAG, "Banner refresh skipped: no network");
                return;
            }

            try {
                AdUnitConfig bannerConfig =
                        AdRemoteConfigExtensionsKt.getBanner_home(AdRemoteConfig.Companion);

                if (bannerConfig.isEnable()) {
                    frBanner.setVisibility(View.VISIBLE);
                    AdsManager.INSTANCE.loadBanner(
                            this,
                            bannerConfig,
                            frBanner,
                            true
                    );
                    Log.d(TAG, "Banner refreshed");
                } else {
                    frBanner.setVisibility(View.GONE);
                    cancelBannerRefresh();
                    return;
                }
            } catch (Throwable t) {
                Log.w(TAG, "Banner refresh failed", t);
            }

            // Re-schedule for the next cycle
            scheduleBannerRefresh();
        };

        bannerRefreshHandler.postDelayed(bannerRefreshRunnable, BANNER_REFRESH_INTERVAL_MS);
    }

    private void cancelBannerRefresh() {
        if (bannerRefreshRunnable != null) {
            bannerRefreshHandler.removeCallbacks(bannerRefreshRunnable);
            bannerRefreshRunnable = null;
        }
    }

    // =========================================================================
    // NATIVE AD
    //
    // Mirrors the sample activity:
    //   load    -> AdsManager.loadNativeLanguageForDashboard(activity, layoutRes)
    //   result  -> AdsManager.nativeDashboardPreviewLive (LiveData<ApNativeAd>)
    //   render  -> populateNativeAdView(activity, ad, container, shimmer)
    //
    // If your AdsManager has dedicated home methods (for example loadNativeHome
    // and nativeHomeLive), swap them in at the three marked lines.
    // =========================================================================
    private void loadNativeHome() {
        if (flNative == null || shimmerNative == null) return;

        if (!InternetPermission.isNetworkAvailable(this)) {
            flNative.setVisibility(View.GONE);
            shimmerNative.setVisibility(View.GONE);
            return;
        }

        try {
            AdUnitConfig nativeConfig =
                    AdRemoteConfigExtensionsKt.getNative_home(AdRemoteConfig.Companion);

            if (!nativeConfig.isEnable()) {
                flNative.setVisibility(View.GONE);
                shimmerNative.setVisibility(View.GONE);
                return;
            }

            nativeRequested = true;
            flNative.setVisibility(View.GONE);
            shimmerNative.setVisibility(View.VISIBLE);
            shimmerNative.startShimmer();

            // <-- CHECK 1: load call
            AdsManager.INSTANCE.loadNativeLanguageForDashboard(
                    this, R.layout.layout_native_ad_small);
        } catch (Throwable t) {
            Log.w(TAG, "Native load failed", t);
            hideNative();
        }
    }

    private void observeNativeAd() {
        // <-- CHECK 2: LiveData getter
        LiveData<ApNativeAd> live = AdsManager.INSTANCE.getNativeDashboardPreviewLive();
        live.observe(this, ad -> {
            if (!nativeRequested) return;

            if (ad != null) {
                showNative(ad);
            } else {
                hideNative();
            }
        });
    }

    private void showNative(ApNativeAd ad) {
        try {
            flNative.setVisibility(View.VISIBLE);
            // <-- CHECK 3: populate helper (a Kotlin top-level function). From Java it is
            // called through its file class, e.g. AdsExtKt.populateNativeAdView(...).
            // Use the file name your project defines it in.
//            AdsManager.populateNativeAdView(
//                    this, ad, flNative, shimmerNative);
        } catch (Throwable t) {
            Log.w(TAG, "Native populate failed", t);
            hideNative();
        }
    }

    private void hideNative() {
        if (shimmerNative != null) {
            shimmerNative.stopShimmer();
            shimmerNative.setVisibility(View.GONE);
        }
        if (flNative != null) {
            flNative.setVisibility(View.GONE);
        }
    }

    // =========================================================================
    // CLICK INTERSTITIAL — uses inter_home from remote config
    // =========================================================================
    private void loadClickInterstitial() {
        if (!InternetPermission.isNetworkAvailable(this)) return;
        if (clickInterAdLoading) return;

        clickInterAdLoading = true;
        try {
            AdUnitConfig interConfig =
                    AdRemoteConfigExtensionsKt.getInter_home(AdRemoteConfig.Companion);

            Log.d(TAG, "inter_home: isEnable=" + interConfig.isEnable()
                    + ", id=" + interConfig.getId());

            if (!interConfig.isEnable()) {
                clickInterAdReady = false;
                return;
            }

            AdsManager.INSTANCE.loadInterHome(this, true);
            clickInterAdReady = true;
            Log.d(TAG, "Click interstitial preloaded via inter_home");
        } catch (Throwable t) {
            Log.w(TAG, "Click interstitial load failed", t);
            clickInterAdReady = false;
        } finally {
            clickInterAdLoading = false;
        }
    }

    private void showClickInterstitialThen(Runnable action) {
        if (!clickInterAdReady || !InternetPermission.isNetworkAvailable(this)) {
            action.run();
            return;
        }

        try {
            AdsManager.INSTANCE.showInterHome(this, true, () -> {
                clickInterAdReady = false;
                loadClickInterstitial();
                action.run();
                return kotlin.Unit.INSTANCE;
            });
        } catch (Throwable t) {
            Log.w(TAG, "Click interstitial show failed", t);
            clickInterAdReady = false;
            action.run();
        }
    }

    // =========================================================================
    // WELCOME INTERSTITIAL
    // =========================================================================
    private void showWelcomeInterstitialOnce() {
        if (welcomeAdHandled) return;
        welcomeAdHandled = true;

        if (!InternetPermission.isNetworkAvailable(this)) return;

        try {
            AdsManager.INSTANCE.loadInterWelcome(this, true);
            AdsManager.INSTANCE.showInterWelcome(this, true, () -> {
                Log.d(TAG, "Inter Welcome shown or skipped");
                return kotlin.Unit.INSTANCE;
            });
        } catch (Throwable t) {
            Log.w(TAG, "Inter Welcome failed", t);
        }
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

        searching_textview     = findViewById(R.id.searching_textview);
        search_by_google_btn   = findViewById(R.id.search_by_google_btn);
        btn_scan_now           = findViewById(R.id.connect_tv_card);

        frBanner               = findViewById(R.id.fr_banner);
        flNative               = findViewById(R.id.fl_native);
        shimmerNative          = findViewById(R.id.shimmer_native);

        loadMainBanner();
    }

    private void setupViewPager() {
        navViewPagerAdapter = new NavViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(navViewPagerAdapter);

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
                showClickInterstitialThen(() ->
                        startActivity(new Intent(MainActivity.this, SettingActivity.class))));

        casting_btn.setOnClickListener(v ->
                showClickInterstitialThen(this::handleCastButton));

        if (searching_textview != null) {
            searching_textview.setOnClickListener(v ->
                    showClickInterstitialThen(this::openSearchUrl));
        }

        if (search_by_google_btn != null) {
            search_by_google_btn.setOnClickListener(v ->
                    showClickInterstitialThen(this::pasteFromClipboard));
        }

        help_icon_toolbar.setOnClickListener(v ->
                showClickInterstitialThen(() ->
                        startActivity(new Intent(MainActivity.this, HowToConnectActivity.class))));

        if (btn_scan_now != null) {
            btn_scan_now.setOnClickListener(v ->
                    showClickInterstitialThen(this::handleCastButton));
        }
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
    // LISTENERS
    // =========================================================================
    @Override public void onDeviceReady(ConnectableDevice device) {
        myDevice = device;
        casting_btn.setImageResource(R.drawable.casting_filled_icon);
        updateConnectedPill(true);
        Toast.makeText(this, getString(R.string.connected), Toast.LENGTH_SHORT).show();
    }

    @Override public void onDeviceDisconnected(ConnectableDevice device) {
        myDevice = null;
        casting_btn.setImageResource(R.drawable.cast);
        updateConnectedPill(false);
        Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
    }

    @Override public void onPairingRequired(ConnectableDevice device,
                                            DeviceService service,
                                            DeviceService.PairingType pairingType) {
        Toast.makeText(this, "Pairing Required", Toast.LENGTH_SHORT).show();
    }

    @Override public void onCapabilityUpdated(ConnectableDevice device,
                                              List<String> added,
                                              List<String> removed) {}

    @Override public void onConnectionFailed(ConnectableDevice device, ServiceCommandError error) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Connection failed: " + error.getMessage());
    }

    @Override public void onDeviceAdded(DiscoveryManager manager, ConnectableDevice device) {
        Log.d(TAG, "Device found: " + device.getFriendlyName());
    }

    @Override public void onDeviceUpdated(DiscoveryManager manager, ConnectableDevice device) {}

    @Override public void onDeviceRemoved(DiscoveryManager manager, ConnectableDevice device) {}

    @Override public void onDiscoveryFailed(DiscoveryManager manager, ServiceCommandError error) {
        Log.e(TAG, "Discovery failed: " + (error != null ? error.getMessage() : "unknown"));
    }

    // =========================================================================
    // MISC
    // =========================================================================
    private void updateConnectedPill(boolean connected) {}

    private void showGoogleRateDialog() {
        ReviewManager manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(reviewTask -> {});
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