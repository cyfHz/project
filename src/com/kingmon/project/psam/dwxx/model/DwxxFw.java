package com.kingmon.project.psam.dwxx.model;

import java.io.Serializable;
import java.util.Date;

public class DwxxFw implements Serializable {

	/**
	 * 主键 -VARCHAR2(36)
	 * 
	 */
	private String id;

	/**
	 * 单位编号-VARCHAR2(36)
	 */
	private String dwid;

	/**
	 * 房间编号-VARCHAR2(36)
	 */
	private String fjid;

	/**
	 * 登记人-VARCHAR2(36)
	 */
	private String djr;

	/**
	 * 登记单位-VARCHAR2(36)
	 */
	private String djdw;

	/**
	 * 登记人名称-VARCHAR2(100)
	 */
	private String djrmc;

	/**
	 * 登记单位名称-VARCHAR2(200)
	 */
	private String djdwmc;

	/**
	 * 登记时间-DATE
	 */
	private Date djsj;

	/**
	 * MOVESIGN-CHAR(1)
	 */
	private String movesign;

	/**
	 * 设备ID-VARCHAR2(50)
	 */
	private String sbid;

	/**
	 * 删除标记（0-未删除，1-已删除）-CHAR(1)
	 */
	private String deltag;

	/**
	 * 删除时间-DATE
	 */
	private Date deltime;

	/**
	 * 删除人-VARCHAR2(36)
	 */
	private String deluser;

	/**
	 * 所属建筑物-VARCHAR2(36)
	 */
	private String jzwid;

	private String tf;
	private String imei;
	private String sim;

	/**
	 * Mac地址 MAC_ADDRESS varchar2(20);
	 */
	private String macAddress;

	private Date gxsj;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDwid() {
		return dwid;
	}

	public void setDwid(String dwid) {
		this.dwid = dwid;
	}

	public String getFjid() {
		return fjid;
	}

	public void setFjid(String fjid) {
		this.fjid = fjid;
	}

	public String getDjr() {
		return djr;
	}

	public void setDjr(String djr) {
		this.djr = djr == null ? null : djr.trim();
	}

	public String getDjdw() {
		return djdw;
	}

	public void setDjdw(String djdw) {
		this.djdw = djdw == null ? null : djdw.trim();
	}

	public String getDjrmc() {
		return djrmc;
	}

	public void setDjrmc(String djrmc) {
		this.djrmc = djrmc == null ? null : djrmc.trim();
	}

	public String getDjdwmc() {
		return djdwmc;
	}

	public void setDjdwmc(String djdwmc) {
		this.djdwmc = djdwmc == null ? null : djdwmc.trim();
	}

	public Date getDjsj() {
		return djsj;
	}

	public void setDjsj(Date djsj) {
		this.djsj = djsj;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign == null ? null : movesign.trim();
	}

	public String getSbid() {
		return sbid;
	}

	public void setSbid(String sbid) {
		this.sbid = sbid == null ? null : sbid.trim();
	}

	public String getDeltag() {
		return deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag == null ? null : deltag.trim();
	}

	public Date getDeltime() {
		return deltime;
	}

	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

	public String getDeluser() {
		return deluser;
	}

	public void setDeluser(String deluser) {
		this.deluser = deluser == null ? null : deluser.trim();
	}

	public String getJzwid() {
		return jzwid;
	}

	public void setJzwid(String jzwid) {
		this.jzwid = jzwid == null ? null : jzwid.trim();
	}

	public String getTf() {
		return tf;
	}

	public void setTf(String tf) {
		this.tf = tf == null ? null : tf.trim();
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei == null ? null : imei.trim();
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim == null ? null : sim.trim();
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public Date getGxsj() {
		return gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

}
