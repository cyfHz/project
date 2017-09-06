package com.kingmon.project.auth.role.model;

import java.io.Serializable;

/**
 * 应用角色表
 * app_role
 * @author zhaohuatai
 * @date 2015年10月6日 上午10:00:25
 */
public class Role implements Serializable{
	
	/**
	 * 应用角色ID-VARCHAR2(36)
	 */
    private String role_id;
	
	/**
	 * 角色名称-VARCHAR2(60)
	 */
    private String role_name;
	
	/**
	 * 角色描述-VARCHAR2(200)
	 */
    private String role_desc;
	
	/**
	 * 应用角色编号-VARCHAR2(40)
	 */
    private String role_code;
	
	/**
	 * 有效标识：0为无效；1为有效；-CHAR(1)
	 */
    private String enabled;
	
	/**
	 * 操作时间-VARCHAR2(20)
	 */
    private String opratetime;
	
	/**
	 * 所属领域VARCHAR2(36)
	 */
    private String area_id;
	
	/**
	 * 同步标记(0未同步,1已同步)-CHAR(1)
	 */
    private String movesign;
	
	/**
	 *组织机构-VARCHAR2(36) 
	 */
    private String organid;
	
	/**
	 * #?
	 * 创建人-VARCHAR2(36)
	 */
    private String createuser;

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
	 * @return the role_name
	 */
	public String getRole_name() {
		return role_name;
	}

	/**
	 * @param role_name the role_name to set
	 */
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	/**
	 * @return the role_desc
	 */
	public String getRole_desc() {
		return role_desc;
	}

	/**
	 * @param role_desc the role_desc to set
	 */
	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}

	/**
	 * @return the role_code
	 */
	public String getRole_code() {
		return role_code;
	}

	/**
	 * @param role_code the role_code to set
	 */
	public void setRole_code(String role_code) {
		this.role_code = role_code;
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
	 * @return the organid
	 */
	public String getOrganid() {
		return organid;
	}

	/**
	 * @param organid the organid to set
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
	}

	/**
	 * @return the createuser
	 */
	public String getCreateuser() {
		return createuser;
	}

	/**
	 * @param createuser the createuser to set
	 */
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

}