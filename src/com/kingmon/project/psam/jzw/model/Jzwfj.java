package com.kingmon.project.psam.jzw.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * #new#
 * 建筑物房间
 * @author zhaohuatai
 * @date 2015年10月5日 下午7:31:56
 */
public class Jzwfj implements Serializable{
	/**
	 * 建筑物房间ID-VARCHAR2(36)
	 */
    private String jzwfjid;

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
     * 房间序号-VARCHAR2(10)
     */
    private String fjxh;

    /**
     * 房间名称-VARCHAR2(100)
     */
    private String fjmc;

    /**
     * 最后更新日期-DATE
     */
    private Date zhgxrq;

    /**
     * #?
     * MOVESIGN-CHAR(1)
     */
    private String movesign;

    /**
     * 
     * 创建人-VARCHAR2(50)
     */
    private String createdby;

    /**
     * 创建时间-Date
     */
    private Date created;

    /**
     * 修改人-VARCHAR2(50)
     */
    private String updatedby;

    /**
     * 修改时间 
     */
    private Date updated;

    /**
     * 注销标志-CHAR(1)
     */
    private String deltag;

    /**
     * 注销人-VARCHAR2(50)
     */
    private String deluser;

    /**
     * 注销时间-DATE
     */
    private Date deltime;
    
    /**
     * #new#
     * VARCHAR2(2)
     * 展示方案
     */
    private String showMode;
    
    /** #new#
     * 显示位置信息#new--VARCHAR2(100)<br>
     * left,top,width,height
     */
    private String showInfo;
    
    /**
     * #new#
     * 标识被拆分或被合并 -char(1)
     *  	标志被拆分或被合并 1:被拆分 2：被合并
     */
    private String changeSign;
    
    //-------------------------------------------------  
    /**
     * 二维码-clob
     */
    private String qrCode;
    
    /**
     * Mac地址
     * MAC_ADDRESS varchar2(20);
     */
    private String macAddress;
    
    /**
     * 
     * TF卡号 varchar2(36);
     */
    private String tfCardNum;
    
    /**
     * IMEI号 varchar2(20);
     */
    private String imeiNum;
    
    /**
     * SIM卡号-varchar2(20)
     */
    private String simNum;
    
    /**
     * NUMBER(11,8)
     */
    private BigDecimal gpsX;
    
    /**
     * NUMBER(11,8)
     */
    private BigDecimal gpsY;
    //-------------------------------------------------   
    
    
	public String getJzwfjid() {
		return jzwfjid;
	}

	public void setJzwfjid(String jzwfjid) {
		this.jzwfjid = jzwfjid;
	}

	public String getJzwjgid() {
		return jzwjgid;
	}

	public void setJzwjgid(String jzwjgid) {
		this.jzwjgid = jzwjgid;
	}

	public String getJzwdyid() {
		return jzwdyid;
	}

	public void setJzwdyid(String jzwdyid) {
		this.jzwdyid = jzwdyid;
	}

	public String getJzwlcid() {
		return jzwlcid;
	}

	public void setJzwlcid(String jzwlcid) {
		this.jzwlcid = jzwlcid;
	}

	public String getFjxh() {
		return fjxh;
	}

	public void setFjxh(String fjxh) {
		this.fjxh = fjxh;
	}

	public String getFjmc() {
		return fjmc;
	}

	public void setFjmc(String fjmc) {
		this.fjmc = fjmc;
	}

	public Date getZhgxrq() {
		return zhgxrq;
	}

	public void setZhgxrq(Date zhgxrq) {
		this.zhgxrq = zhgxrq;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getDeltag() {
		return deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	public String getDeluser() {
		return deluser;
	}

	public void setDeluser(String deluser) {
		this.deluser = deluser;
	}

	public Date getDeltime() {
		return deltime;
	}

	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}



	public String getShowMode() {
		return showMode;
	}

	public void setShowMode(String showMode) {
		this.showMode = showMode;
	}

	public String getShowInfo() {
		return showInfo;
	}

	public void setShowInfo(String showInfo) {
		this.showInfo = showInfo;
	}

	/**
	 * @return the changeSign
	 */
	public String getChangeSign() {
		return changeSign;
	}

	/**
	 * @param changeSign the changeSign to set
	 */
	public void setChangeSign(String changeSign) {
		this.changeSign = changeSign;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getTfCardNum() {
		return tfCardNum;
	}

	public void setTfCardNum(String tfCardNum) {
		this.tfCardNum = tfCardNum;
	}

	public String getImeiNum() {
		return imeiNum;
	}

	public void setImeiNum(String imeiNum) {
		this.imeiNum = imeiNum;
	}

	public String getSimNum() {
		return simNum;
	}

	public void setSimNum(String simNum) {
		this.simNum = simNum;
	}

	public BigDecimal getGpsX() {
		return gpsX;
	}

	public void setGpsX(BigDecimal gpsX) {
		this.gpsX = gpsX;
	}

	public BigDecimal getGpsY() {
		return gpsY;
	}

	public void setGpsY(BigDecimal gpsY) {
		this.gpsY = gpsY;
	}

}