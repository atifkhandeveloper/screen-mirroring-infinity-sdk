package com.ezcast.screen.mirroring.totv.Models;


public class AudioModel {
    private long duration;
    private long id;
    String mimetype;
    private String name;
    private String path;

    public String getMimetype() {
        return this.mimetype;
    }

    public void setMimetype(String str) {
        this.mimetype = str;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long j) {
        this.duration = j;
    }
}
