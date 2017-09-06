package com.kingmon.project.psam.jzw.model;

import java.util.Date;

/**
 * 建筑物房间拆分合并历史记录
* @ClassName :JzwfjChange     
* @Description :   
* @createTime :2015年11月3日  下午3:12:05   
* @author ：zhaohuatai   
* @version :1.0
 */
public class JzwfjChange {
	/**
	 * 主键
	 */
    private String id;

    /**
     * 被拆分/合并房间ID
     */
    private String fromfjid;

    /**
     * 拆分/合并后房间ID
     */
    private String tofjid;

    /**
     * 记录拆分还是合并
     */
    private String changesign;

    /**
     * 时间
     */
    private Date created;

    /**
     * 创建人
     */
    private String createdby;

    /**
     * 创建单位
     */
    private String createddw;

    /**
     * movesign
     */
    private String movesign;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFromfjid() {
        return fromfjid;
    }

    public void setFromfjid(String fromfjid) {
        this.fromfjid = fromfjid == null ? null : fromfjid.trim();
    }

    public String getTofjid() {
        return tofjid;
    }

    public void setTofjid(String tofjid) {
        this.tofjid = tofjid == null ? null : tofjid.trim();
    }

    public String getChangesign() {
        return changesign;
    }

    public void setChangesign(String changesign) {
        this.changesign = changesign == null ? null : changesign.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby == null ? null : createdby.trim();
    }

    public String getCreateddw() {
        return createddw;
    }

    public void setCreateddw(String createddw) {
        this.createddw = createddw == null ? null : createddw.trim();
    }

    public String getMovesign() {
        return movesign;
    }

    public void setMovesign(String movesign) {
        this.movesign = movesign == null ? null : movesign.trim();
    }
}