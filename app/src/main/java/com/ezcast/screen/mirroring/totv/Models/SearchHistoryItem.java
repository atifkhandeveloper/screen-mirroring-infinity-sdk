package com.ezcast.screen.mirroring.totv.Models;

import java.util.Date;


public class SearchHistoryItem {
    private String timestamp;
    private String title;
    private String url;

    public SearchHistoryItem(String str, String str2, String str3) {
        this.title = str;
        this.url = str2;
        this.timestamp = str3;
    }

    public String getURL() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getTime() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = String.valueOf(date);
    }
}
