package com.kingmon.project.auth.organization.model;

import java.io.Serializable;
import java.util.Date;

public class OrganizaionChange implements Serializable{
	 public OrganizaionChange() {
		}

    public OrganizaionChange(String id, String fromid, String toid, String changesign, String createdby,
			Date createTime) {
		super();
		this.id = id;
		this.fromid = fromid;
		this.toid = toid;
		this.changesign = changesign;
		this.createdby = createdby;
		this.createTime = createTime;
	}

	private String id;

    private String fromid;

    private String toid;

    private String changesign;

    private String createdby;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid == null ? null : fromid.trim();
    }

    public String getToid() {
        return toid;
    }

    public void setToid(String toid) {
        this.toid = toid == null ? null : toid.trim();
    }

    public String getChangesign() {
        return changesign;
    }

    public void setChangesign(String changesign) {
        this.changesign = changesign == null ? null : changesign.trim();
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby == null ? null : createdby.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}