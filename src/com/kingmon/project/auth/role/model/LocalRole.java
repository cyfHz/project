package com.kingmon.project.auth.role.model;

import java.io.Serializable;

/**
 * 本地角色
 * 
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:25:49
 */
public class LocalRole implements Serializable{
	/**app_localrole
	 * 本地角色ID-VARCHAR2(36)
	 */
    private String localrole_id;

    /**
     * 应用领域ID-VARCHAR2(36)
     */
    private String area_id;

    /**
     * 本地角色编号-VARCHAR2(100)
     */
    private String localrole_code;

    /**
     * 本地角色名称-VARCHAR2(100)
     */
    private String localrole_name;

    /**
     * 本地角色描述-VARCHAR2(200)
     */
    private String localrole_desc;

    /**
     * 有效标识：0为无效；1为有效-CHAR(1)
     */
    private String enabled;

    /**
     * 操作时间-VARCHAR2(20)
     */
    private String opratetime;

    /**
     * 应用组织机构ID-VARCHAR2(36)
     */
    private String orgna_id;

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
	 * @return the localrole_code
	 */
	public String getLocalrole_code() {
		return localrole_code;
	}

	/**
	 * @param localrole_code the localrole_code to set
	 */
	public void setLocalrole_code(String localrole_code) {
		this.localrole_code = localrole_code;
	}

	/**
	 * @return the localrole_name
	 */
	public String getLocalrole_name() {
		return localrole_name;
	}

	/**
	 * @param localrole_name the localrole_name to set
	 */
	public void setLocalrole_name(String localrole_name) {
		this.localrole_name = localrole_name;
	}

	/**
	 * @return the localrole_desc
	 */
	public String getLocalrole_desc() {
		return localrole_desc;
	}

	/**
	 * @param localrole_desc the localrole_desc to set
	 */
	public void setLocalrole_desc(String localrole_desc) {
		this.localrole_desc = localrole_desc;
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

}