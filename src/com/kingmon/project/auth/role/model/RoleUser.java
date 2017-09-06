package com.kingmon.project.auth.role.model;

import java.io.Serializable;

/**
 * 用户角色关系
 * app_role_user
 * @author zhaohuatai
 * @date 2015年10月6日 上午10:02:53
 */
public class RoleUser implements Serializable{
	
	/**
	 * ID-VARCHAR2(36)
	 */
    private String id;
	
	/**
	 * #?
	 * APPUSER_ID -VARCHAR2(36)
	 */
    private String appuser_id;
	
	/**
	 * 应用角色ID -VARCHAR2(36)
	 */
    private String role_id;
	
	/**#?
	 * 用户ID -VARCHAR2(36)
	 */
    private String user_id;
	
	/**
	 * 应用领域ID -VARCHAR2(36)
	 */
    private String area_id;
	
	/**
	 * 操作时间-VARCHAR2(20)
	 */
    private String opratetime;
	
	/**
	 * #?
	 * 同步标记(0未同步,1已同步)-CHAR(1)
	 */
    private String movesign;
	
	/**
	 * 是否可用-CHAR(1)
	 */
    private String enabled;

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
	 * @return the role_id
	 */
	public String getRole_id() {
		return role_id;
	}

	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(String role_id) {
		this.role_id = role_id;
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

	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}