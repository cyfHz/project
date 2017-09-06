package com.kingmon.project.webservice.common.model;

import java.io.Serializable;
import java.util.Date;

public class ServiceBzdzUserjs implements Serializable{
    private String jsid;

    private String jsr;

    private Date jssj;

    private String jssm;

    private String jsuserid;

    public String getJsid() {
        return jsid;
    }

    public void setJsid(String jsid) {
        this.jsid = jsid == null ? null : jsid.trim();
    }

    public String getJsr() {
        return jsr;
    }

    public void setJsr(String jsr) {
        this.jsr = jsr == null ? null : jsr.trim();
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getJssm() {
        return jssm;
    }

    public void setJssm(String jssm) {
        this.jssm = jssm == null ? null : jssm.trim();
    }

    public String getJsuserid() {
        return jsuserid;
    }

    public void setJsuserid(String jsuserid) {
        this.jsuserid = jsuserid == null ? null : jsuserid.trim();
    }
}