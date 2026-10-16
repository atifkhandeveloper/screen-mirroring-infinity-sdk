package com.ezcast.screen.mirroring.totv.Models;

import android.net.Uri;


public class CastingModel {
    Uri VideoUri;
    int audioPath;
    int duration;
    String img;
    Uri imgUri;
    String mimeType;
    String name;
    String video;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Uri getVideoUri() {
        return this.VideoUri;
    }

    public void setVideoUri(Uri uri) {
        this.VideoUri = uri;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public int getAudioPath() {
        return this.audioPath;
    }

    public void setAudioPath(int i) {
        this.audioPath = i;
    }

    public Uri getUri() {
        return this.imgUri;
    }

    public void setUri(Uri uri) {
        this.imgUri = uri;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String str) {
        this.img = str;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String str) {
        this.mimeType = str;
    }

    public String getVideo() {
        return this.video;
    }

    public void setVideo(String str) {
        this.video = str;
    }
}
