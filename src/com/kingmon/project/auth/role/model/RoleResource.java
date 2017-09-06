package com.kingmon.project.auth.role.model;

import java.io.Serializable;

/**
 * 应用角色资源表
 * app_role_resource
 * @author zhaohuatai
 * @date 2015年10月6日 上午10:04:44
 */
public class RoleResource implements Serializable{
	
	/**
	 * ID-VARCHAR2(36)
	 */
    private String id;
	
	/**
	 * 应用角色ID-VARCHAR2(36)
	 */
    private String role_id;
	
	/**
	 * 应用资源ID -VARCHAR2(36)
	 */
    private String res_id;
	
	/**
	 * 应用领域ID -VARCHAR2(36)
	 */
    private String area_id;
	
	/**
	 * 操作时间-VARCHAR2(20)
	 */
    private String opratetime;
	
	/**
	 * 同步标记(0未同步,1已同步)-CHAR(1)
	 */
    private String movesign;
	
	/**#?
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
	 * @return the res_id
	 */
	public String getRes_id() {
		return res_id;
	}

	/**
	 * @param res_id the res_id to set
	 */
	public void setRes_id(String res_id) {
		this.res_id = res_id;
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