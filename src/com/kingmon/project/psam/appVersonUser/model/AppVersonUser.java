package com.kingmon.project.psam.appVersonUser.model;

public class AppVersonUser {
    private String id;

    private String userid;

    private String versoninfo;

    private String isvalid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getVersoninfo() {
        return versoninfo;
    }

    public void setVersoninfo(String versoninfo) {
        this.versoninfo = versoninfo == null ? null : versoninfo.trim();
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid == null ? null : isvalid.trim();
    }
}