package com.kingmon.project.webservice.common.model;

import java.io.Serializable;
import java.util.Date;

public class ServiceBzdzRejectIp implements Serializable {

	/**
	 * 主键
	 */
	public String ipid;

	/**
	 * 是否有用
	 */
	public String sfyy;

	/**
	 * ip
	 */
	public String ip;

	/**
	 * ip限制接口
	 */
	public String ipxzjk;

	/**
	 * 添加人
	 */
	public String tjr;

	/**
	 * 添加时间
	 */
	public Date tjsj;

	/**
	 * 添加单位
	 */
	public String tjdw;

	/**
	 * 修改人
	 */
	public String xgr;

	/**
	 * 修改时间
	 */
	public Date xgsj;

	/**
	 * 修改单位
	 */
	public String xgdw;

	/**
	 *  IP地址转化Long型存储
	 */
	public Long longip;
	
	
	public String getIpid() {
		return ipid;
	}

	public void setIpid(String ipid) {
		this.ipid = ipid;
	}

	public String getSfyy() {
		return sfyy;
	}

	public void setSfyy(String sfyy) {
		this.sfyy = sfyy;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpxzjk() {
		return ipxzjk;
	}

	public void setIpxzjk(String ipxzjk) {
		this.ipxzjk = ipxzjk;
	}

	public String getTjr() {
		return tjr;
	}

	public void setTjr(String tjr) {
		this.tjr = tjr;
	}

	public String getTjdw() {
		return tjdw;
	}

	public void setTjdw(String tjdw) {
		this.tjdw = tjdw;
	}

	public String getXgr() {
		return xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public String getXgdw() {
		return xgdw;
	}

	public void setXgdw(String xgdw) {
		this.xgdw = xgdw;
	}

	public Date getTjsj() {
		return tjsj;
	}

	public void setTjsj(Date tjsj) {
		this.tjsj = tjsj;
	}

	public Date getXgsj() {
		return xgsj;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	public Long getLongip() {
		return longip;
	}

	public void setLongip(Long longip) {
		this.longip = longip;
	}



}
