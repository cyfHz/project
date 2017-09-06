package com.kingmon.project.psam.jzw.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class JzwfjView implements Serializable{
	
	private String[] oldJzwfjIds;
	
	private List<JzwfjViewModel> jzwfjInfoList;
	
	private String flag;
	
	private String macAddress;
	private String tfCardNum;
	private String imeiNum;
	private String simNum;
	private BigDecimal gpsX;
	private BigDecimal gpsY;
	private String userId;
	
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * @return the oldJzwfjIds
	 */
	public String[] getOldJzwfjIds() {
		return oldJzwfjIds;
	}
	/**
	 * @param oldJzwfjIds the oldJzwfjIds to set
	 */
	public void setOldJzwfjIds(String[] oldJzwfjIds) {
		this.oldJzwfjIds = oldJzwfjIds;
	}
	/**
	 * @return the jzwfjInfoList
	 */
	public List<JzwfjViewModel> getJzwfjInfoList() {
		return jzwfjInfoList;
	}
	/**
	 * @param jzwfjInfoList the jzwfjInfoList to set
	 */
	public void setJzwfjInfoList(List<JzwfjViewModel> jzwfjInfoList) {
		this.jzwfjInfoList = jzwfjInfoList;
	}
	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}
	/**
	 * @param macAddress the macAddress to set
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	/**
	 * @return the tfCardNum
	 */
	public String getTfCardNum() {
		return tfCardNum;
	}
	/**
	 * @param tfCardNum the tfCardNum to set
	 */
	public void setTfCardNum(String tfCardNum) {
		this.tfCardNum = tfCardNum;
	}
	/**
	 * @return the imeiNum
	 */
	public String getImeiNum() {
		return imeiNum;
	}
	/**
	 * @param imeiNum the imeiNum to set
	 */
	public void setImeiNum(String imeiNum) {
		this.imeiNum = imeiNum;
	}
	/**
	 * @return the simNum
	 */
	public String getSimNum() {
		return simNum;
	}
	/**
	 * @param simNum the simNum to set
	 */
	public void setSimNum(String simNum) {
		this.simNum = simNum;
	}
	/**
	 * @return the gpsX
	 */
	public BigDecimal getGpsX() {
		return gpsX;
	}
	/**
	 * @param gpsX the gpsX to set
	 */
	public void setGpsX(BigDecimal gpsX) {
		this.gpsX = gpsX;
	}
	/**
	 * @return the gpsY
	 */
	public BigDecimal getGpsY() {
		return gpsY;
	}
	/**
	 * @param gpsY the gpsY to set
	 */
	public void setGpsY(BigDecimal gpsY) {
		this.gpsY = gpsY;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
