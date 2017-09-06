package com.kingmon.project.auth.rule.model;

import java.io.Serializable;

/**
 * app_widgetrule
 * 应用控件规则
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:56:56
 */
public class Widgetrule implements Serializable{
	
	/**
	 * 应用控件规则ID -VARCHAR2(36)
	 */
    private String rule_id;
	
	/**
	 * 应用资源ID -VARCHAR2(36)
	 */
    private String res_id;
	
	/**
	 * 应用领域ID -VARCHAR2(36)
	 */
    private String area_id;
	
	/**
	 * 应用控件规则名称  -VARCHAR2(100)
	 */
    private String rule_name;
	
	/**
	 * #?
	 * 应用控件默认值-(当没有设置关系时默认的值是什么)1地图气泡显示 0其他方式 (新建:0坐标采集 1标准地址采集 2依附地址采集 3 区域采集)
	 * -VARCHAR2(32)
	 */
    private String rule_defvalue;
	
	/**
	 * 应用控件值（来源于数据字典表-RES_WIDGET_RULEVALUE） 控件事件的路径-VARCHAR2(200)
	 * 
	 */
    private String rule_values;
	
	/**
	 * 应用控件规则描述-VARCHAR2(200)
	 */
    private String rule_desc;
	
	/**
	 * 同一资源下不可重复<br>
	 * 应用控件规则编号-VARCHAR2(40)
	 */
    private String rule_code;
	
	/**
	 * 有效标识：0为无效；1为有效；-CHAR(1)
	 */
    private String enabled;
	
	/**
	 * 操作时间-VARCHAR2(20)
	 */
    private String opratetime;
	
	/**
	 * 同步标记(0未同步,1已同步)-CHAR(1)
	 */
    private String movesign;

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
	 * @return the rule_defvalue
	 */
	public String getRule_defvalue() {
		return rule_defvalue;
	}

	/**
	 * @param rule_defvalue the rule_defvalue to set
	 */
	public void setRule_defvalue(String rule_defvalue) {
		this.rule_defvalue = rule_defvalue;
	}

	/**
	 * @return the rule_values
	 */
	public String getRule_values() {
		return rule_values;
	}

	/**
	 * @param rule_values the rule_values to set
	 */
	public void setRule_values(String rule_values) {
		this.rule_values = rule_values;
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

}