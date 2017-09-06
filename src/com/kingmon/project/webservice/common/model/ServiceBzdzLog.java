package com.kingmon.project.webservice.common.model;

import java.io.Serializable;
import java.util.Date;

public class ServiceBzdzLog implements Serializable{
	
	
	public ServiceBzdzLog() {
		super();
	}
    public ServiceBzdzLog(String loginid, String loginuser, String loginip,
			Date logintime, String loginuserid) {
		super();
		this.loginid = loginid;
		this.loginuser = loginuser;
		this.loginip = loginip;
		this.logintime = logintime;
		this.loginuserid = loginuserid;
	}

	private String loginid;

    private String loginuser;

    private String loginip;

    private Date logintime;

    private String loginuserid;

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid == null ? null : loginid.trim();
    }

    public String getLoginuser() {
        return loginuser;
    }

    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser == null ? null : loginuser.trim();
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip == null ? null : loginip.trim();
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(String loginuserid) {
        this.loginuserid = loginuserid == null ? null : loginuserid.trim();
    }
}