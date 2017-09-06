package com.kingmon.project.psam.orgarea.model;

import java.io.Serializable;

public class OrgPgisArea implements Serializable{
	
	/**
	 * 机构主键
	 */
    private String orgnaId;
	
	/**
	 * 是否有效
	 */
    private String enabled;
	
	/**
	 * 创建时间VARCHAR2(20)
	 */
    private String created;
	
	/**
	 * 创建人
	 */
    private String createdby;
	
	/**
	 *面积
	 */
    private String area;
	
	/**
	 * 
	 */
    private String movesign;
	
	/**
	 * 机构所在PGIS范围
	 */
    private String mapArea;

    public String getOrgnaId() {
        return orgnaId;
    }

    public void setOrgnaId(String orgnaId) {
        this.orgnaId = orgnaId == null ? null : orgnaId.trim();
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created == null ? null : created.trim();
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby == null ? null : createdby.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getMovesign() {
        return movesign;
    }

    public void setMovesign(String movesign) {
        this.movesign = movesign == null ? null : movesign.trim();
    }

    public String getMapArea() {
        return mapArea;
    }

    public void setMapArea(String mapArea) {
        this.mapArea = mapArea == null ? null : mapArea.trim();
    }
}