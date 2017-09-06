package com.kingmon.project.common.log.model;

import java.io.Serializable;

/**
 * 
 * app_log_error
 * 
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:35:52
 */
public class LogError implements Serializable{
    /**
     * IDVARCHAR2(36)
     */
    private String id;
    
	/**
	 * CLASSES-VARCHAR2(255)
	 */
    private String classes;

    /**
     * MOTHOD- VARCHAR2(255)
     */
    private String mothod;

    /**
     * #?
     * 时间-VARCHAR2(255)
     */
    private String createtime;

    /**
     * #?
     * LOGLEVEL - VARCHAR2(20)
     */
    private String loglevel;

    /**
     * 数据来源类型(P后台,终端B)-VARCHAR2(10)
     */
    private String lx;

    /**
     * 设备id-VARCHAR2(50)
     */
    private String sbid;

    /**
     * 错误信息-CLOB
     */
    private String msg;
    
    private String imei;
    private String TF;
    private String gpsX;
    private String GPSY;
    

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the classes
	 */
	public String getClasses() {
		return classes;
	}

	/**
	 * @param classes the classes to set
	 */
	public void setClasses(String classes) {
		this.classes = classes;
	}

	/**
	 * @return the mothod
	 */
	public String getMothod() {
		return mothod;
	}

	/**
	 * @param mothod the mothod to set
	 */
	public void setMothod(String mothod) {
		this.mothod = mothod;
	}

	/**
	 * @return the createtime
	 */
	public String getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return the loglevel
	 */
	public String getLoglevel() {
		return loglevel;
	}

	/**
	 * @param loglevel the loglevel to set
	 */
	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}

	/**
	 * @return the lx
	 */
	public String getLx() {
		return lx;
	}

	/**
	 * @param lx the lx to set
	 */
	public void setLx(String lx) {
		this.lx = lx;
	}

	/**
	 * @return the sbid
	 */
	public String getSbid() {
		return sbid;
	}

	/**
	 * @param sbid the sbid to set
	 */
	public void setSbid(String sbid) {
		this.sbid = sbid;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getTF() {
		return TF;
	}

	public void setTF(String tF) {
		TF = tF;
	}

	public String getGpsX() {
		return gpsX;
	}

	public void setGpsX(String gpsX) {
		this.gpsX = gpsX;
	}

	public String getGPSY() {
		return GPSY;
	}

	public void setGPSY(String gPSY) {
		GPSY = gPSY;
	}

}