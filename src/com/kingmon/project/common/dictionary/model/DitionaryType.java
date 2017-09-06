package com.kingmon.project.common.dictionary.model;

import java.io.Serializable;

/**
 * 系统数据字典类别
 * 
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:14:32
 */
public class DitionaryType implements Serializable{
	
	/**
	 * 主键uuid<br>
	 * 必填<br>
	 * 类型ID-VARCHAR2(36)
	 */
    private String type_id;
	
	/**
	 * 唯一<br>
	 * 必填<br>
	 * 类型编号-VARCHAR2(50)
	 */
    private String type_code;
	
	/**
	 * 必填<br>
	 * 类别名称-VARCHAR2(100)
	 */
    private String type_mc;
	
	/**必填<br>
	 * 说明-VARCHAR2(100)
	 */
    private String type_sm;
	
	/**
	 * 注意判断有效性<br>
	 * 必填<br>
	 * 有效标识：1为有效；0为无效； -CHAR(1)
	 */
    private String enabled;
	
	/**
	 * 时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 必填<br>
	 * 操作时间-VARCHAR2(20)
	 */
    private String opratetime;
	
	/**
	 * 表内数据未明确格式，此处以数字做排列序号
	 * 必填<br>
	 * 排列序号-VARCHAR2(20)
	 */
    private String type_order;

	/**
	 * @return the type_id
	 */
	public String getType_id() {
		return type_id;
	}

	/**
	 * @param type_id the type_id to set
	 */
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	/**
	 * @return the type_code
	 */
	public String getType_code() {
		return type_code;
	}

	/**
	 * @param type_code the type_code to set
	 */
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	/**
	 * @return the type_mc
	 */
	public String getType_mc() {
		return type_mc;
	}

	/**
	 * @param type_mc the type_mc to set
	 */
	public void setType_mc(String type_mc) {
		this.type_mc = type_mc;
	}

	/**
	 * @return the type_sm
	 */
	public String getType_sm() {
		return type_sm;
	}

	/**
	 * @param type_sm the type_sm to set
	 */
	public void setType_sm(String type_sm) {
		this.type_sm = type_sm;
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
	 * @return the type_order
	 */
	public String getType_order() {
		return type_order;
	}

	/**
	 * @param type_order the type_order to set
	 */
	public void setType_order(String type_order) {
		this.type_order = type_order;
	}

}