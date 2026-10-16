package com.ezcast.screen.mirroring.totv.Models;


public class LanguageModelClass {
    int flags;
    boolean isSelected;
    boolean is_primium;
    String languageCode;
    String languageName;

    public boolean isIs_primium() {
        return this.is_primium;
    }

    public void setIs_primium(boolean z) {
        this.is_primium = z;
    }

    public LanguageModelClass(int i, String str, String str2, boolean z, boolean z2) {
        this.flags = i;
        this.languageName = str;
        this.isSelected = z;
        this.is_primium = z2;
        this.languageCode = str2;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public void setLanguageCode(String str) {
        this.languageCode = str;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int i) {
        this.flags = i;
    }

    public String getLanguageName() {
        return this.languageName;
    }

    public void setLanguageName(String str) {
        this.languageName = str;
    }
}
