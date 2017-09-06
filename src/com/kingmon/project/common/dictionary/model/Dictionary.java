package com.kingmon.project.common.dictionary.model;

import java.io.Serializable;

/**
 * 系统数据字典表
 * 
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:21:32
 */
public class Dictionary implements Serializable{
	/**app_dictionary
	 * 主键uuid<br>
	 * 必填** <br>
	 * 类型ID-VARCHAR2(36)
	 */
    private String dict_id;

    /**
     * 外键<br>
     * 必填** <br>
     * 所属类型ID-VARCHAR2(36)
     */
    private String type_id;

    /**
     * 值为 APP_DICTIONARY_TYPE 中TYPE_CODE<br>
     * 可为空** <br>
     * 类型编号-VARCHAR2(50)
     */
    private String type_code;

    /**
     * 本类编号<br>
     * 
     * 所属APP_DICTIONARY_TYPE 内不可重复<br>
     * 必填** <br>
     * 编号-VARCHAR2(50)
     */
    private String dict_code;

    /**
     * 名称-VARCHAR2(500)
     * 必填** <br>
     */
    private String dict_mc;

    /**
     * 简称-VARCHAR2(500)
     */
    private String dict_jc;

    /**
     * 说明-VARCHAR2(500)
     */
    private String dict_sm;

    /**
     * 注意判断有效性<br>
     * 有效标识：1为有效；0为无效；-CHAR(1)
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
    private String dict_order;

    /**
     * 所属 系统<br>
     * 必填<br>
     * 应用领域ID-VARCHAR2(36)
     */
    private String area_id;

	/**
	 * @return the dict_id
	 */
	public String getDict_id() {
		return dict_id;
	}

	/**
	 * @param dict_id the dict_id to set
	 */
	public void setDict_id(String dict_id) {
		this.dict_id = dict_id;
	}

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
	 * @return the dict_code
	 */
	public String getDict_code() {
		return dict_code;
	}

	/**
	 * @param dict_code the dict_code to set
	 */
	public void setDict_code(String dict_code) {
		this.dict_code = dict_code;
	}

	/**
	 * @return the dict_mc
	 */
	public String getDict_mc() {
		return dict_mc;
	}

	/**
	 * @param dict_mc the dict_mc to set
	 */
	public void setDict_mc(String dict_mc) {
		this.dict_mc = dict_mc;
	}

	/**
	 * @return the dict_jc
	 */
	public String getDict_jc() {
		return dict_jc;
	}

	/**
	 * @param dict_jc the dict_jc to set
	 */
	public void setDict_jc(String dict_jc) {
		this.dict_jc = dict_jc;
	}

	/**
	 * @return the dict_sm
	 */
	public String getDict_sm() {
		return dict_sm;
	}

	/**
	 * @param dict_sm the dict_sm to set
	 */
	public void setDict_sm(String dict_sm) {
		this.dict_sm = dict_sm;
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
	 * @return the dict_order
	 */
	public String getDict_order() {
		return dict_order;
	}

	/**
	 * @param dict_order the dict_order to set
	 */
	public void setDict_order(String dict_order) {
		this.dict_order = dict_order;
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

}