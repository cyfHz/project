package com.kingmon.project.psam.appException.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 移动端异常捕获
 * 
 * @author Administrator
 *
 */
public class AppExceptionLog implements Serializable {

	/**
	 * 编号
	 */
	private String id;

	/**
	 * 用户编号
	 */
	private String appuser_id;

	/**
	 * 设备id
	 */
	private String sbid;
	
	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 类名
	 */
	private String classes;

	/**
	 * 方法名
	 */
	private String mothod;

	/**
	 * 异常内容
	 */
	private String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppuser_id() {
		return appuser_id;
	}

	public void setAppuser_id(String appuser_id) {
		this.appuser_id = appuser_id;
	}

	public String getSbid() {
		return sbid;
	}

	public void setSbid(String sbid) {
		this.sbid = sbid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getMothod() {
		return mothod;
	}

	public void setMothod(String mothod) {
		this.mothod = mothod;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
