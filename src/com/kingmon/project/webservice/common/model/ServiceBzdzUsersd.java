package com.kingmon.project.webservice.common.model;

import java.io.Serializable;
import java.util.Date;

public class ServiceBzdzUsersd implements Serializable{
    private String sdid;

    private String sdr;

    private Date sdsj;

    private String sdsm;

    private String sduser;

    public String getSdid() {
        return sdid;
    }

    public void setSdid(String sdid) {
        this.sdid = sdid == null ? null : sdid.trim();
    }

    public String getSdr() {
        return sdr;
    }

    public void setSdr(String sdr) {
        this.sdr = sdr == null ? null : sdr.trim();
    }

    public Date getSdsj() {
        return sdsj;
    }

    public void setSdsj(Date sdsj) {
        this.sdsj = sdsj;
    }

    public String getSdsm() {
        return sdsm;
    }

    public void setSdsm(String sdsm) {
        this.sdsm = sdsm == null ? null : sdsm.trim();
    }

    public String getSduser() {
        return sduser;
    }

    public void setSduser(String sduser) {
        this.sduser = sduser == null ? null : sduser.trim();
    }
}