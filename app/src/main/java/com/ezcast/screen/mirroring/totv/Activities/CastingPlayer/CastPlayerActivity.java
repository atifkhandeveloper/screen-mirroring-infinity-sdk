package com.ezcast.screen.mirroring.totv.Activities.CastingPlayer;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.connectsdk.core.MediaInfo;
import com.connectsdk.device.ConnectableDevice;
import com.connectsdk.discovery.DiscoveryProvider;
import com.connectsdk.service.capability.MediaControl;
import com.connectsdk.service.capability.MediaPlayer;
import com.connectsdk.service.capability.VolumeControl;
import com.connectsdk.service.command.ServiceCommandError;
import com.ezcast.screen.mirroring.totv.R;
import com.ezcast.screen.mirroring.totv.Activities.AudiosActivity;
import com.ezcast.screen.mirroring.totv.Activities.MainActivity;
import com.ezcast.screen.mirroring.totv.Activities.ThemeActivity;
import com.ezcast.screen.mirroring.totv.Activities.VideosActivity;
import com.ezcast.screen.mirroring.totv.Activities.WebService;
import com.ezcast.screen.mirroring.totv.Models.AudioModel;
import com.ezcast.screen.mirroring.totv.Models.CastingModel;
import com.ezcast.screen.mirroring.totv.Utils;
import java.util.ArrayList;
import java.util.Locale;


public class CastPlayerActivity extends ThemeActivity {
    private ImageView backwardMedia;
    private ImageView btnBackPress;
    boolean casting_boolean;
    private Context context;
    private TextView durationGo;
    private ImageView endVideo;
    private ImageView forwardMedia;
    private MediaControl mediaControl;
    private MediaInfo mediaInfo;
    private SeekBar mediaSeekBar;
    private String mimeType;
    private ImageView muteMedia;
    private ImageView play_pause;
    private int position;
    private ImageView startVideo;
    private TextView totalDuration;
    private VolumeControl volumeControl;
    private ImageView volumeDown;
    private ImageView volumeUp;
    private ArrayList<CastingModel> castingVideoList = new ArrayList<>();
    private ArrayList<AudioModel> castingAudioList = new ArrayList<>();
    private boolean isPlaying = true;
    private boolean isMute = false;
    private long totalTime = 0;
    private String description = "Screen Casting";


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cast_player);




        final AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.scale);
        this.mimeType = getIntent().getStringExtra("mimeType");
        this.position = getIntent().getIntExtra("position", 0);
        this.casting_boolean = getIntent().getBooleanExtra("boolean_video", false);
        initializeViews();
        this.mediaControl = (MediaControl) MainActivity.myDevice.getCapability(MediaControl.class);
        this.volumeControl = (VolumeControl) MainActivity.myDevice.getCapability(VolumeControl.class);
        handlerTime();
        totalTimeSet();
        this.castingAudioList = AudiosActivity.audioList;
        this.castingVideoList = VideosActivity.videosList;
        this.play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorSet.setTarget(CastPlayerActivity.this.play_pause);
                animatorSet.start();
                if (CastPlayerActivity.this.isPlaying) {
                    if (CastPlayerActivity.this.mediaControl != null) {
                        CastPlayerActivity.this.mediaControl.pause(null);
                        CastPlayerActivity.this.play_pause.setImageResource(R.drawable.pause_icon);
                        CastPlayerActivity.this.isPlaying = false;
                    }
                } else if (CastPlayerActivity.this.mediaControl != null) {
                    CastPlayerActivity.this.mediaControl.play(null);
                    CastPlayerActivity.this.play_pause.setImageResource(R.drawable.exo_video_play_icon);
                    CastPlayerActivity.this.isPlaying = true;
                }
            }
        });
        this.backwardMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorSet.setTarget(CastPlayerActivity.this.backwardMedia);
                animatorSet.start();
                CastPlayerActivity.this.mediaControl.getPosition(new MediaControl.PositionListener() {
                    @Override
                    public void onError(ServiceCommandError serviceCommandError) {
                    }

                    @Override
                    public void onSuccess(Long l) {
                        CastPlayerActivity.this.mediaControl.seek(l.longValue() - 5000, null);
                    }
                });
            }
        });
        this.forwardMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorSet.setTarget(CastPlayerActivity.this.forwardMedia);
                animatorSet.start();
                CastPlayerActivity.this.mediaControl.getPosition(new MediaControl.PositionListener() {
                    @Override
                    public void onError(ServiceCommandError serviceCommandError) {
                    }

                    @Override
                    public void onSuccess(Long l) {
                        CastPlayerActivity.this.mediaControl.seek(l.longValue() + 5000, null);
                    }
                });
            }
        });
        this.volumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorSet.setTarget(CastPlayerActivity.this.volumeUp);
                animatorSet.start();
                if (CastPlayerActivity.this.volumeControl != null) {
                    CastPlayerActivity.this.volumeControl.volumeUp(null);
                }
            }
        });
        this.volumeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorSet.setTarget(CastPlayerActivity.this.volumeDown);
                animatorSet.start();
                if (CastPlayerActivity.this.volumeControl != null) {
                    CastPlayerActivity.this.volumeControl.volumeDown(null);
                }
            }
        });
        this.muteMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorSet.setTarget(CastPlayerActivity.this.muteMedia);
                animatorSet.start();
                if (!CastPlayerActivity.this.isMute) {
                    CastPlayerActivity.this.volumeControl.setMute(true, null);
                    CastPlayerActivity.this.muteMedia.setImageResource(R.drawable.mute);
                    CastPlayerActivity.this.isMute = true;
                    return;
                }
                CastPlayerActivity.this.volumeControl.setMute(false, null);
                CastPlayerActivity.this.muteMedia.setImageResource(R.drawable.mute);
                CastPlayerActivity.this.isMute = false;
            }
        });
        this.btnBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                CastPlayerActivity.this.m192x5911a3ff(view);
            }
        });
        this.endVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                CastPlayerActivity.this.m193xa6d11c00(view);
            }
        });
        this.startVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CastPlayerActivity.this.casting_boolean) {
                    if (CastPlayerActivity.this.position > 0) {
                        String video = ((CastingModel) CastPlayerActivity.this.castingVideoList.get(CastPlayerActivity.this.position - 1)).getVideo();
                        CastPlayerActivity.this.position--;
                        CastPlayerActivity.this.showImageOnTv(MainActivity.myDevice, video);
                        CastPlayerActivity.this.play_pause.setImageResource(R.drawable.pause_icon);
                        CastPlayerActivity.this.isPlaying = true;
                    }
                } else if (!CastPlayerActivity.this.casting_boolean) {
                    if (CastPlayerActivity.this.position > 0) {
                        String path = ((AudioModel) CastPlayerActivity.this.castingAudioList.get(CastPlayerActivity.this.position - 1)).getPath();
                        CastPlayerActivity.this.position--;
                        CastPlayerActivity.this.showImageOnTv(MainActivity.myDevice, path);
                        CastPlayerActivity.this.play_pause.setImageResource(R.drawable.pause_icon);
                        CastPlayerActivity.this.isPlaying = true;
                    }
                } else {
                    Toast.makeText(CastPlayerActivity.this.context, CastPlayerActivity.this.getString(R.string.no_item_found), 0).show();
                }
            }
        });
    }



    public  void m192x5911a3ff(View view) {
        onBackPressed();
    }



    public  void m193xa6d11c00(View view) {
        boolean z = this.casting_boolean;
        if (z) {
            if (this.position < this.castingVideoList.size() - 1) {
                String video = this.castingVideoList.get(this.position + 1).getVideo();
                this.position++;
                showImageOnTv(MainActivity.myDevice, video);
                this.play_pause.setImageResource(R.drawable.pause_icon);
                this.isPlaying = true;
            }
        } else if (z || this.position >= this.castingAudioList.size() - 1) {
        } else {
            String path = this.castingAudioList.get(this.position + 1).getPath();
            this.position++;
            showImageOnTv(MainActivity.myDevice, path);
            totalTimeSet();
            this.play_pause.setImageResource(R.drawable.pause_icon);
            this.isPlaying = true;
        }
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
                    CastPlayerActivity.this.totalTimeSet();
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


    public void totalTimeSet() {
        this.mediaControl.getDuration(new MediaControl.DurationListener() {
            @Override
            public void onError(ServiceCommandError serviceCommandError) {
            }

            @Override
            public void onSuccess(Long l) {
                CastPlayerActivity.this.totalTime = l.longValue();
                String formatTime = CastPlayerActivity.this.formatTime(Integer.valueOf(Math.toIntExact(l.longValue())));
                CastPlayerActivity.this.mediaSeekBar.setMax(Math.toIntExact(l.longValue()));
                CastPlayerActivity.this.totalDuration.setText(formatTime);
            }
        });
        this.mediaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    CastPlayerActivity.this.mediaControl.seek((i * CastPlayerActivity.this.totalTime) / seekBar.getMax(), null);
                }
            }
        });
    }

    private void handlerTime() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                CastPlayerActivity.this.mediaControl.getPosition(new MediaControl.PositionListener() {
                    @Override
                    public void onError(ServiceCommandError serviceCommandError) {
                    }

                    @Override
                    public void onSuccess(Long l) {
                        CastPlayerActivity.this.durationGo.setText(CastPlayerActivity.this.formatTime(Integer.valueOf(Math.toIntExact(l.longValue()))));
                        CastPlayerActivity.this.mediaSeekBar.setProgress(Integer.parseInt(l.toString()));
                    }
                });
                handler.postDelayed(this, 1000L);
            }
        });
    }


    public String formatTime(Integer num) {
        long intValue = (num.intValue() / 1000) % 60;
        long intValue2 = (num.intValue() / DiscoveryProvider.TIMEOUT) % 60;
        long intValue3 = (num.intValue() / 3600000) % 24;
        return intValue3 > 0 ? String.format(Locale.getDefault(), "%02d:%02d:%02d", Long.valueOf(intValue3), Long.valueOf(intValue2), Long.valueOf(intValue)) : String.format(Locale.getDefault(), "%02d:%02d", Long.valueOf(intValue2), Long.valueOf(intValue));
    }

    private void initializeViews() {
        this.context = this;
        this.btnBackPress = (ImageView) findViewById(R.id.btnBackPress);
        this.volumeUp = (ImageView) findViewById(R.id.volumeUp);
        this.backwardMedia = (ImageView) findViewById(R.id.backwardMedia);
        this.play_pause = (ImageView) findViewById(R.id.play_pause);
        this.forwardMedia = (ImageView) findViewById(R.id.forwardMedia);
        this.volumeDown = (ImageView) findViewById(R.id.volumeDown);
        this.startVideo = (ImageView) findViewById(R.id.startVideo);
        this.muteMedia = (ImageView) findViewById(R.id.muteMedia);
        this.endVideo = (ImageView) findViewById(R.id.endVideo);
        this.durationGo = (TextView) findViewById(R.id.durationGo);
        this.totalDuration = (TextView) findViewById(R.id.totalDuration);
        this.mediaSeekBar = (SeekBar) findViewById(R.id.mediaSeekBar);
    }
}
