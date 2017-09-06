package com.kingmon.project.common.log.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 登录日志
 * app_log_login
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:47:56
 */
public class LogLogin implements Serializable{
	/**
	 * ID-VARCHAR2(36)
	 */
    private String id;

    /**
     * 用户ID -VARCHAR2(36)
     */
    private String user_id;

    /**
     * 用户姓名-VARCHAR2(100)
     */
    private String user_name;

    /**
     * 组织机构ID-VARCHAR2(36)
     */
    private String orgna_id;

    /**
     *登录时间
     */
    private Date logintime;
    /**
     * #?
     * 登录时间-VARCHAR2(50)
     */
    private String logintime1;

    /**
     * 
     * 下线时间-DATE
     */
    private Date logouttime;
    
    /**
     * #?
     */
    private String logouttime1;

    /**
     * 登录方式-VARCHAR2(50)
     */
    private String logintype;

    /**
     * IP登录-VARCHAR2(20)
     */
    private String ip;

    /**
     * 终端类型-VARCHAR2(50)
     */
    private String loginclient;

    /**
     * 备注-VARCHAR2(500)
     */
    private String memo;

    /**
     * 用户类型  0是管理员账号 1是企业用户-NUMBER(1)
     */
    private Short user_type;

    /**
     *GPS_X-VARCHAR2(50)
     */
    private String gps_x;

    /**
     * GPS_Y-VARCHAR2(50)
     */
    private String gps_y;

    /**
     * 设备id-VARCHAR2(50)
     */
    private String sbid;

    /**
     * CHAR(1)
     */
    private String movesign;

    
    /**
     * 2016-0708新增 imei
     */
    private String imei;
    
    
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
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * @return the orgna_id
	 */
	public String getOrgna_id() {
		return orgna_id;
	}

	/**
	 * @param orgna_id the orgna_id to set
	 */
	public void setOrgna_id(String orgna_id) {
		this.orgna_id = orgna_id;
	}

	/**
	 * @return the logintime
	 */
	public Date getLogintime() {
		return logintime;
	}

	/**
	 * @param logintime the logintime to set
	 */
	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	/**
	 * @return the logintime1
	 */
	public String getLogintime1() {
		return logintime1;
	}

	/**
	 * @param logintime1 the logintime1 to set
	 */
	public void setLogintime1(String logintime1) {
		this.logintime1 = logintime1;
	}

	/**
	 * @return the logouttime
	 */
	public Date getLogouttime() {
		return logouttime;
	}

	/**
	 * @param logouttime the logouttime to set
	 */
	public void setLogouttime(Date logouttime) {
		this.logouttime = logouttime;
	}

	/**
	 * @return the logouttime1
	 */
	public String getLogouttime1() {
		return logouttime1;
	}

	/**
	 * @param logouttime1 the logouttime1 to set
	 */
	public void setLogouttime1(String logouttime1) {
		this.logouttime1 = logouttime1;
	}

	/**
	 * @return the logintype
	 */
	public String getLogintype() {
		return logintype;
	}

	/**
	 * @param logintype the logintype to set
	 */
	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the loginclient
	 */
	public String getLoginclient() {
		return loginclient;
	}

	/**
	 * @param loginclient the loginclient to set
	 */
	public void setLoginclient(String loginclient) {
		this.loginclient = loginclient;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the user_type
	 */
	public Short getUser_type() {
		return user_type;
	}

	/**
	 * @param user_type the user_type to set
	 */
	public void setUser_type(Short user_type) {
		this.user_type = user_type;
	}

	/**
	 * @return the gps_x
	 */
	public String getGps_x() {
		return gps_x;
	}

	/**
	 * @param gps_x the gps_x to set
	 */
	public void setGps_x(String gps_x) {
		this.gps_x = gps_x;
	}

	/**
	 * @return the gps_y
	 */
	public String getGps_y() {
		return gps_y;
	}

	/**
	 * @param gps_y the gps_y to set
	 */
	public void setGps_y(String gps_y) {
		this.gps_y = gps_y;
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
	 * @return the movesign
	 */
	public String getMovesign() {
		return movesign;
	}

	/**
	 * @param movesign the movesign to set
	 */
	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	
}