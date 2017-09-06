package com.kingmon.project.common.log.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 应用系统工作日志----未生成
 * app_log
 * @author zhaohuatai
 * @date 2015年10月6日 上午9:31:45
 */
public class Log implements Serializable{
	
	/**
	 * ID-VARCHAR2(36)
	 */
    private String id;
	
	/**
	 * 用户ID -VARCHAR2(36)
	 */
    private String user_id;
	
	/**
	 * 用户登录账号-VARCHAR2(40)
	 */
    private String user_loginname;
	
	/**
	 * 用户姓名-VARCHAR2(100)
	 */
    private String user_name;
	
	/**
	 * 组织机构ID-VARCHAR2(36)
	 */
    private String orgna_id;
	
	/**
	 * 日志类型-VARCHAR2(100)
	 */
    private String log_type;
	
	/**
	 * 操作时间-VARCHAR2(20)
	 */
    private String opratetime1;
	
	/**
	 * 操作对象-VARCHAR2(50)
	 */
    private String log_object;
	
	/**
	 * 日志内容-VARCHAR2(2000)
	 */
    private String log_content;
	
	/**
	 * #?
	 * 领域ID-VARCHAR2(32)
	 */
    private String area_code;
	
	/**
	 * DATE
	 */
    private Date opratetime;
	
	/**
	 * #?
	 * 操作相关url-VARCHAR2(200)
	 */
    private String operate_url;

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
	 * @return the user_loginname
	 */
	public String getUser_loginname() {
		return user_loginname;
	}

	/**
	 * @param user_loginname the user_loginname to set
	 */
	public void setUser_loginname(String user_loginname) {
		this.user_loginname = user_loginname;
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
	 * @return the log_type
	 */
	public String getLog_type() {
		return log_type;
	}

	/**
	 * @param log_type the log_type to set
	 */
	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}

	/**
	 * @return the opratetime1
	 */
	public String getOpratetime1() {
		return opratetime1;
	}

	/**
	 * @param opratetime1 the opratetime1 to set
	 */
	public void setOpratetime1(String opratetime1) {
		this.opratetime1 = opratetime1;
	}

	/**
	 * @return the log_object
	 */
	public String getLog_object() {
		return log_object;
	}

	/**
	 * @param log_object the log_object to set
	 */
	public void setLog_object(String log_object) {
		this.log_object = log_object;
	}

	/**
	 * @return the log_content
	 */
	public String getLog_content() {
		return log_content;
	}

	/**
	 * @param log_content the log_content to set
	 */
	public void setLog_content(String log_content) {
		this.log_content = log_content;
	}

	/**
	 * @return the area_code
	 */
	public String getArea_code() {
		return area_code;
	}

	/**
	 * @param area_code the area_code to set
	 */
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	/**
	 * @return the opratetime
	 */
	public Date getOpratetime() {
		return opratetime;
	}

	/**
	 * @param opratetime the opratetime to set
	 */
	public void setOpratetime(Date opratetime) {
		this.opratetime = opratetime;
	}

	/**
	 * @return the operate_url
	 */
	public String getOperate_url() {
		return operate_url;
	}

	/**
	 * @param operate_url the operate_url to set
	 */
	public void setOperate_url(String operate_url) {
		this.operate_url = operate_url;
	}

}