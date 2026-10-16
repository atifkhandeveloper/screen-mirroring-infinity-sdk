package com.ezcast.screen.mirroring.totv.MainFragment;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.AudiosActivity;
import com.ezcast.screen.mirroring.totv.Activities.BrowserActivity;
import com.ezcast.screen.mirroring.totv.Activities.MainActivity;
import com.ezcast.screen.mirroring.totv.Activities.PhotosActivity;
import com.ezcast.screen.mirroring.totv.Activities.VideosActivity;


public class CastingFragment extends Fragment {

    private static final int MY_AUDIOS_PERMISSION_CODE   = 103;
    private static final int MY_PHOTOS_PERMISSION_CODE   = 101;
    private static final int MY_VIDEOS_PERMISSION_CODE   = 102;
    private static final int PERMISSIONS_REQUEST_13      = 13;
    private static final int REQUEST_PERMISSION_SETTING  = 1001;

    private String[] PERMISSIONS;
    boolean aBoolean;
    String keyVariable;

    private Context context;

    // ---- Views ----
    private RelativeLayout mirroring;        // Mirroring row
    private LinearLayout photos;           // casting_photos
    private LinearLayout videos;           // casting_videos
    private LinearLayout audios;           // casting_audios
    private LinearLayout youtube;
    private LinearLayout google;
    private LinearLayout facebook;
    private LinearLayout twitch;

    private ConstraintLayout browser_layout;
    private RelativeLayout connect_tv_card;

    private ImageView mirroring_icon;
    private ImageView photos_icon;
    private ImageView videos_icon;

    private TextView mirroring_textview;
    private TextView searching_textview;
    private TextView tv_connected_status;

    private AppCompatButton search_by_google_btn;
    private RelativeLayout btn_scan_now;


    public CastingFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_casting, container, false);

        // ---------------- Bind views ----------------
        mirroring            = view.findViewById(R.id.mirroring);
        photos               = view.findViewById(R.id.casting_photos);
        videos               = view.findViewById(R.id.casting_videos);
        audios               = view.findViewById(R.id.casting_audios);
        youtube              = view.findViewById(R.id.youtube_layout);
        google               = view.findViewById(R.id.google_layout);
        facebook             = view.findViewById(R.id.facebook_layout);
        twitch               = view.findViewById(R.id.twitch_layout);

        browser_layout       = view.findViewById(R.id.browser_layout);
        connect_tv_card      = view.findViewById(R.id.connect_tv_card);

        mirroring_icon       = view.findViewById(R.id.mirroring_icon);
        photos_icon          = view.findViewById(R.id.mirroring_icon); // keep reference safe
        videos_icon          = view.findViewById(R.id.mirroring_icon);

        mirroring_textview   = view.findViewById(R.id.mirroring_textview);
        searching_textview   = view.findViewById(R.id.searching_textview);
//        tv_connected_status  = view.findViewById(R.id.tv_connected_status);

        search_by_google_btn = view.findViewById(R.id.search_by_google_btn);
        btn_scan_now         = view.findViewById(R.id.connect_tv_card);


        btn_scan_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent("android.settings.CAST_SETTINGS"));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Cast settings not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ---------------- Animator ----------------
        final AnimatorSet animatorSet = (AnimatorSet)
                AnimatorInflater.loadAnimator(getContext(), R.animator.scale);

        // ---------------- Click listeners ----------------
        mirroring.setOnClickListener(v -> onMirroringClick(animatorSet, v));
        photos.setOnClickListener(v -> onPhotosClick(animatorSet, v));
        videos.setOnClickListener(v -> onVideosClick(animatorSet, v));
        audios.setOnClickListener(v -> onAudiosClick(animatorSet, v));

        youtube.setOnClickListener(v -> onBrowserClick(animatorSet, v, "youtube"));
        google.setOnClickListener(v -> onBrowserClick(animatorSet, v, "google"));
        facebook.setOnClickListener(v -> onBrowserClick(animatorSet, v, "facebook"));
        twitch.setOnClickListener(v -> onBrowserClick(animatorSet, v, "twitch"));

        // Search bar → open browser
        browser_layout.setOnClickListener(v -> openBrowser(null));

        // PASTE button → read clipboard into the search field
        if (search_by_google_btn != null) {
            search_by_google_btn.setOnClickListener(v -> pasteFromClipboard());
        }

        // Search hint tap → open browser
        if (searching_textview != null) {
            searching_textview.setOnClickListener(v -> openBrowser(null));
        }

        // Scan Now → delegate to MainActivity's cast flow
//        if (btn_scan_now != null) {
//            btn_scan_now.setOnClickListener(v -> {
//                if (getActivity() instanceof MainActivity) {
//                    // Reuse MainActivity's casting logic (same as the toolbar cast icon)
//                    ImageView castIcon = getActivity().findViewById(R.id.casting_btn);
//                    if (castIcon != null) castIcon.performClick();
//                }
//            });
//        }

        // Optional: reset the "CONNECTED" pill when fragment resumes
        refreshConnectedPill();

        return view;
    }


    // =========================================================================
    // CLICK HANDLERS
    // =========================================================================

    private void onMirroringClick(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(mirroring);
        animatorSet.start();
        try {
            startActivity(new Intent("android.settings.CAST_SETTINGS"));
        } catch (Exception e) {
            Toast.makeText(getContext(), "Cast settings not available", Toast.LENGTH_SHORT).show();
        }
    }




    private void onPhotosClick(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(photos);
        animatorSet.start();
        if (aBoolean) {
            context.startActivity(new Intent(getContext(), PhotosActivity.class));
        } else if (ContextCompat.checkSelfPermission(requireActivity(),
                "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                    MY_PHOTOS_PERMISSION_CODE);
        } else {
            context.startActivity(new Intent(getContext(), PhotosActivity.class));
        }
    }

    private void onVideosClick(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(videos);
        animatorSet.start();
        if (aBoolean) {
            context.startActivity(new Intent(getContext(), VideosActivity.class));
        } else if (ContextCompat.checkSelfPermission(requireActivity(),
                "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                    MY_VIDEOS_PERMISSION_CODE);
        } else {
            context.startActivity(new Intent(getContext(), VideosActivity.class));
        }
    }

    private void onAudiosClick(AnimatorSet animatorSet, View view) {
        animatorSet.setTarget(audios);
        animatorSet.start();
        if (aBoolean) {
            context.startActivity(new Intent(getContext(), AudiosActivity.class));
        } else if (ActivityCompat.checkSelfPermission(requireActivity(),
                "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                    MY_AUDIOS_PERMISSION_CODE);
        } else {
            context.startActivity(new Intent(getContext(), AudiosActivity.class));
        }
    }

    private void onBrowserClick(AnimatorSet animatorSet, View target, String source) {
        // Pick the right row for the animation
        switch (source) {
            case "youtube":  animatorSet.setTarget(youtube);  break;
            case "google":   animatorSet.setTarget(google);   break;
            case "facebook": animatorSet.setTarget(facebook); break;
            case "twitch":   animatorSet.setTarget(twitch);   break;
        }
        animatorSet.start();

        Intent intent = new Intent(getContext(), BrowserActivity.class);
        intent.putExtra("browserCheck", source);
        startActivity(intent);
    }

    private void openBrowser(String url) {
        Intent intent = new Intent(getContext(), BrowserActivity.class);
        if (url != null) intent.putExtra("url", url);
        startActivity(intent);
    }

    private void pasteFromClipboard() {
        android.content.ClipboardManager cm =
                (android.content.ClipboardManager) requireContext()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null && cm.hasPrimaryClip()
                && cm.getPrimaryClip() != null
                && cm.getPrimaryClip().getItemCount() > 0) {
            CharSequence pasted = cm.getPrimaryClip().getItemAt(0).getText();
            if (pasted != null) {
                searching_textview.setText(pasted);
                openBrowser(pasted.toString());
            }
        } else {
            Toast.makeText(getContext(), "Clipboard is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshConnectedPill() {
        if (tv_connected_status == null) return;
        boolean connected = MainActivity.myDevice != null;
        tv_connected_status.setText(connected ? R.string.connected : R.string.disconnected);
    }


    // =========================================================================
    // PERMISSIONS
    // =========================================================================

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PHOTOS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == 0) {
                aBoolean = true;
                context.startActivity(new Intent(getContext(), PhotosActivity.class));
            } else if (Build.VERSION.SDK_INT >= 33) {
                requestPermissions(new String[]{
                        "android.permission.READ_MEDIA_IMAGES",
                        "android.permission.READ_MEDIA_VIDEO",
                        "android.permission.READ_MEDIA_AUDIO"
                }, PERMISSIONS_REQUEST_13);
                keyVariable = "PHOTOS";
            } else {
                Toast.makeText(context, getString(R.string.permission_not_granted),
                        Toast.LENGTH_LONG).show();
                if (!shouldShowRequestPermissionRationale(
                        "android.permission.READ_EXTERNAL_STORAGE")) {
                    showPermissionDeniedDialog();
                }
            }
        }

        else if (requestCode == PERMISSIONS_REQUEST_13) {
            if (grantResults.length > 0 && grantResults[0] == 0) {
                if ("VIDEOS".equals(keyVariable)) {
                    startActivity(new Intent(getContext(), VideosActivity.class));
                } else if ("AUDIOS".equals(keyVariable)) {
                    startActivity(new Intent(getContext(), AudiosActivity.class));
                } else if ("PHOTOS".equals(keyVariable)) {
                    startActivity(new Intent(getContext(), PhotosActivity.class));
                } else {
                    Toast.makeText(context, getString(R.string.no_folder_found),
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
                if (!shouldShowRequestPermissionRationale(
                        "android.permission.READ_MEDIA_IMAGES")) {
                    showPermissionDeniedDialog();
                }
            }
        }

        else if (requestCode == MY_VIDEOS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == 0) {
                aBoolean = true;
                context.startActivity(new Intent(getContext(), VideosActivity.class));
            } else if (Build.VERSION.SDK_INT >= 33) {
                requestPermissions(new String[]{
                        "android.permission.READ_MEDIA_IMAGES",
                        "android.permission.READ_MEDIA_VIDEO",
                        "android.permission.READ_MEDIA_AUDIO"
                }, PERMISSIONS_REQUEST_13);
                keyVariable = "VIDEOS";
            } else {
                Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
                if (!shouldShowRequestPermissionRationale(
                        "android.permission.READ_EXTERNAL_STORAGE")) {
                    showPermissionDeniedDialog();
                }
            }
        }

        else if (requestCode == MY_AUDIOS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == 0) {
                aBoolean = true;
                context.startActivity(new Intent(getContext(), AudiosActivity.class));
            } else if (Build.VERSION.SDK_INT >= 33) {
                requestPermissions(new String[]{
                        "android.permission.READ_MEDIA_IMAGES",
                        "android.permission.READ_MEDIA_VIDEO",
                        "android.permission.READ_MEDIA_AUDIO"
                }, PERMISSIONS_REQUEST_13);
                keyVariable = "AUDIOS";
            } else {
                Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
                if (!shouldShowRequestPermissionRationale(
                        "android.permission.READ_EXTERNAL_STORAGE")) {
                    showPermissionDeniedDialog();
                }
            }
        }
    }


    // =========================================================================
    // PERMISSION DENIED DIALOG
    // =========================================================================

    private void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Permission Required");
        builder.setMessage("Please grant permission to access external storage.");
        builder.setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAppSettings();
            }

            private void openAppSettings() {
                Intent intent = new Intent(
                        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package",
                        requireContext().getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setCancelable(false);
        builder.show();
    }


    // =========================================================================
    // FRAGMENT LIFECYCLE
    // =========================================================================

    @Override
    public void onResume() {
        super.onResume();
        refreshConnectedPill();
    }
}