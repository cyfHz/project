package com.kingmon.project.auth.rule.model;

import java.io.Serializable;

/**
 * 应用数据规则
 * 
 * @author zhaohuatai
 * @date 2015年10月6日 上午8:49:53
 */
public class Datarule implements Serializable{
	/**
	 * 应用数据规则ID -VARCHAR2(36)
	 */
    private String rule_id;

    /**
     * 应用领域ID -VARCHAR2(36)
     */
    private String area_id;

    /**
     * 应用数据规则名称 -VARCHAR2(100)
     */
    private String rule_name;

    /**
     * 应用数据值类型 -VARCHAR2(32)
     */
    private String rule_datatype;

    /**
     * 应用数据值 -VARCHAR2(400)
     */
    private String rule_datavalue;

    /**
     * 应用数据规则描述 -VARCHAR2(200)
     */
    private String rule_desc;

    /**
     * 应用数据规则编号 -VARCHAR2(40)
     */
    private String rule_code;

    /**
     * 有效标识：0为无效；1为有效； -CHAR(1)
     */
    private String enabled;

    /**
     * 操作时间 -VARCHAR2(20)
     */
    private String opratetime;

	/**
	 * @return the rule_id
	 */
	public String getRule_id() {
		return rule_id;
	}

	/**
	 * @param rule_id the rule_id to set
	 */
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
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
	 * @return the rule_name
	 */
	public String getRule_name() {
		return rule_name;
	}

	/**
	 * @param rule_name the rule_name to set
	 */
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	/**
	 * @return the rule_datatype
	 */
	public String getRule_datatype() {
		return rule_datatype;
	}

	/**
	 * @param rule_datatype the rule_datatype to set
	 */
	public void setRule_datatype(String rule_datatype) {
		this.rule_datatype = rule_datatype;
	}

	/**
	 * @return the rule_datavalue
	 */
	public String getRule_datavalue() {
		return rule_datavalue;
	}

	/**
	 * @param rule_datavalue the rule_datavalue to set
	 */
	public void setRule_datavalue(String rule_datavalue) {
		this.rule_datavalue = rule_datavalue;
	}

	/**
	 * @return the rule_desc
	 */
	public String getRule_desc() {
		return rule_desc;
	}

	/**
	 * @param rule_desc the rule_desc to set
	 */
	public void setRule_desc(String rule_desc) {
		this.rule_desc = rule_desc;
	}

	/**
	 * @return the rule_code
	 */
	public String getRule_code() {
		return rule_code;
	}

	/**
	 * @param rule_code the rule_code to set
	 */
	public void setRule_code(String rule_code) {
		this.rule_code = rule_code;
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

}