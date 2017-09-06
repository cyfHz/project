package com.kingmon.project.psam.appVersion.model;

import java.io.Serializable;

/**
 * 
 */
public class AppVersion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 版本名称
	 */
	private String AppName;
	/**
	 * 版本号
	 */
	private String AppVers;

	private String AppNr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppName() {
		return AppName;
	}

	public void setAppName(String appName) {
		AppName = appName;
	}

	public String getAppVers() {
		return AppVers;
	}

	public void setAppVers(String appVers) {
		AppVers = appVers;
	}

	public String getAppNr() {
		return AppNr;
	}

	public void setAppNr(String appNr) {
		AppNr = appNr;
	}

}
