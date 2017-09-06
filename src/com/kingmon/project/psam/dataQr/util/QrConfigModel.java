package com.kingmon.project.psam.dataQr.util;

import java.io.Serializable;

public class QrConfigModel implements Serializable {
//			#key=显示标题#查询表#查询主键字段#查询标题显示字段
//			mlph=门楼牌号#dz_mlph#ywlsh#dzmc
	private String configKey;
	private String showTitle;
	private String queryTable;
	private String queryPrimaryField;
	private String queryShowField;
	public String getConfigKey() {
		return configKey;
	}
	public QrConfigModel setConfigKey(String configKey) {
		this.configKey = configKey;
		return this;
	}
	public String getShowTitle() {
		return showTitle;
	}
	public QrConfigModel setShowTitle(String showTitle) {
		this.showTitle = showTitle;
		return this;
	}
	public String getQueryTable() {
		return queryTable;
	}
	public QrConfigModel setQueryTable(String queryTable) {
		this.queryTable = queryTable;
		return this;
	}
	public String getQueryPrimaryField() {
		return queryPrimaryField;
	}
	public QrConfigModel setQueryPrimaryField(String queryPrimaryField) {
		this.queryPrimaryField = queryPrimaryField;
		return this;
	}
	public String getQueryShowField() {
		return queryShowField;
	}
	public QrConfigModel setQueryShowField(String queryShowField) {
		this.queryShowField = queryShowField;
		return this;
	}
	
	
}
