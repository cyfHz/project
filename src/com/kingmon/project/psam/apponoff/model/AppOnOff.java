package com.kingmon.project.psam.apponoff.model;

public class AppOnOff {
    private String appOnoffId;

    private String code;

    private String onoff;

    public String getAppOnoffId() {
        return appOnoffId;
    }

    public void setAppOnoffId(String appOnoffId) {
        this.appOnoffId = appOnoffId == null ? null : appOnoffId.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getOnoff() {
        return onoff;
    }

    public void setOnoff(String onoff) {
        this.onoff = onoff == null ? null : onoff.trim();
    }
}