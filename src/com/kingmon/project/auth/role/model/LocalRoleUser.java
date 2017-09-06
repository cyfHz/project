package com.kingmon.project.auth.role.model;

import java.io.Serializable;

/**
 * 用户与本地角色关系
 * 
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:29:00
 */
public class LocalRoleUser implements Serializable{
	/**app_localrole_user
	 * ID-VARCHAR2(36)
	 */
    private String id;

    /**
     * APPUSER_ID -VARCHAR2(36)
     */
    private String appuser_id;

    /**
     * 本地角色ID  -VARCHAR2(36)
     */
    private String localrole_id;

    /**
     * 用户ID -VARCHAR2(36)
     */
    private String user_id;

    /**
     * 应用领域ID -VARCHAR2(36)
     */
    private String area_id;

    /**
     * 操作时间- VARCHAR2(20)
     */
    private String opratetime;

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
	 * @return the appuser_id
	 */
	public String getAppuser_id() {
		return appuser_id;
	}

	/**
	 * @param appuser_id the appuser_id to set
	 */
	public void setAppuser_id(String appuser_id) {
		this.appuser_id = appuser_id;
	}

	/**
	 * @return the localrole_id
	 */
	public String getLocalrole_id() {
		return localrole_id;
	}

	/**
	 * @param localrole_id the localrole_id to set
	 */
	public void setLocalrole_id(String localrole_id) {
		this.localrole_id = localrole_id;
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
	 * @return the area_id
	 */
	public String getArea_id() {
		return area_id;
	}

	/**
	 * @param area_id the area_id to set
	 */
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	/**
	 * @return the opratetime
	 */
	public String getOpratetime() {
		return opratetime;
	}

	/**
	 * @param opratetime the opratetime to set
	 */
	public void setOpratetime(String opratetime) {
		this.opratetime = opratetime;
	}

}