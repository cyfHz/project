package com.kingmon.project.psam.userAppInfo.model;

import java.util.Date;

public class UserAppInfo {
    private String id;

    private String userid;

    private String versoninfo;

    private Date updatetime;

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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}