package com.kingmon.project.psam.jzw.view;

import java.io.Serializable;

public class JzwfjViewModel implements Serializable{

	private String key;
	 /**
     * 房间序号-VARCHAR2(10)
     */
    private String fjxh;

    /**
     * 房间名称-VARCHAR2(100)
     */
    private String fjmc;

    /** #new#
     * 显示位置信息#new--VARCHAR2(100)<br>
     * left,top,width,height
     */
    private String showInfo;

    /**
     * 
     * 建筑物结构ID-VARCHAR2(36)
     */
    private String jzwjgid;

    /**
     * 
     * 建筑物单元ID-VARCHAR2(36)
     */
    private String jzwdyid;

    /**
     * 
     * 建筑物楼层ID-VARCHAR2(36)
     */
    private String jzwlcid;
    
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the fjxh
	 */
	public String getFjxh() {
		return fjxh;
	}

	/**
	 * @param fjxh the fjxh to set
	 */
	public void setFjxh(String fjxh) {
		this.fjxh = fjxh;
	}

	/**
	 * @return the fjmc
	 */
	public String getFjmc() {
		return fjmc;
	}

	/**
	 * @param fjmc the fjmc to set
	 */
	public void setFjmc(String fjmc) {
		this.fjmc = fjmc;
	}

	/**
	 * @return the showInfo
	 */
	public String getShowInfo() {
		return showInfo;
	}

	/**
	 * @param showInfo the showInfo to set
	 */
	public void setShowInfo(String showInfo) {
		this.showInfo = showInfo;
	}

	/**
	 * @return the jzwjgid
	 */
	public String getJzwjgid() {
		return jzwjgid;
	}

	/**
	 * @param jzwjgid the jzwjgid to set
	 */
	public void setJzwjgid(String jzwjgid) {
		this.jzwjgid = jzwjgid;
	}

	/**
	 * @return the jzwdyid
	 */
	public String getJzwdyid() {
		return jzwdyid;
	}

	/**
	 * @param jzwdyid the jzwdyid to set
	 */
	public void setJzwdyid(String jzwdyid) {
		this.jzwdyid = jzwdyid;
	}

	/**
	 * @return the jzwlcid
	 */
	public String getJzwlcid() {
		return jzwlcid;
	}

	/**
	 * @param jzwlcid the jzwlcid to set
	 */
	public void setJzwlcid(String jzwlcid) {
		this.jzwlcid = jzwlcid;
	}
    
}
