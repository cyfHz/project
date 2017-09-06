package com.kingmon.project.psam.sy.model;

import java.io.Serializable;
import java.util.Date;

public class SyRkglLdrkdjbPic implements Serializable{
	
	/**
	 * 主键
	 */
    private String id;

    /**
     * 人口类型：1czrk ，2 ldrk，3 jwry -CHAR(2)
     */
    private String rklx;

    private Date createTime;

    /**
     * VARCHAR2(36)
     */
    private String createUser;

    /**
     * 人口id
     */
    private String rkid;

    /**
     * 人口证件编号
     */
    private String rkzjbh;

    /**
     * 照片字段
     */
    private byte[] pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRklx() {
        return rklx;
    }

    public void setRklx(String rklx) {
        this.rklx = rklx == null ? null : rklx.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getRkid() {
        return rkid;
    }

    public void setRkid(String rkid) {
        this.rkid = rkid == null ? null : rkid.trim();
    }

    public String getRkzjbh() {
        return rkzjbh;
    }

    public void setRkzjbh(String rkzjbh) {
        this.rkzjbh = rkzjbh == null ? null : rkzjbh.trim();
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
}