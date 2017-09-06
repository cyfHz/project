package com.kingmon.project.psam.datasync.model;

import java.io.Serializable;

public class DzDataSyncBiz implements Serializable{
	
	public DzDataSyncBiz() {
			super();
	}
    public DzDataSyncBiz(String id) {
		this.id = id;
	}
    public DzDataSyncBiz(String id, String logicmethod, String status,String describe) {
		super();
		this.id = id;
		this.logicmethod = logicmethod;
		this.enabled = status;
//		this.createtime = createtime;
//		this.createuser = createuser;
		this.describe = describe;
	}

	private String id;
    private String logicmethod;
    private String enabled;
//    private Date createtime;
//    private String createuser;
    private String describe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLogicmethod() {
        return logicmethod;
    }

    public void setLogicmethod(String logicmethod) {
        this.logicmethod = logicmethod == null ? null : logicmethod.trim();
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

//    public Date getCreatetime() {
//        return createtime;
//    }
//
//    public void setCreatetime(Date createtime) {
//        this.createtime = createtime;
//    }
//
//    public String getCreateuser() {
//        return createuser;
//    }
//
//    public void setCreateuser(String createuser) {
//        this.createuser = createuser == null ? null : createuser.trim();
//    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }
}