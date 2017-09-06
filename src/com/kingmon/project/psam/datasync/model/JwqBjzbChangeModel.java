package com.kingmon.project.psam.datasync.model;

import java.io.Serializable;

public class JwqBjzbChangeModel implements Serializable{
	private String searchFrom;
	private String searchField;
	private String tableName;
	
	
	
	private JwqBjzbChangeModel() {
		super();
	}
	
	private JwqBjzbChangeModel(String searchFrom, String searchField, String tableName) {
		super();
		this.searchFrom = searchFrom;
		this.searchField = searchField;
		this.tableName = tableName;
	}
	public static JwqBjzbChangeModel newMo(String searchFrom, String searchField, String tableName){
		return new JwqBjzbChangeModel( searchFrom,  searchField,  tableName);
	}
	public String getSearchFrom() {
		return searchFrom;
	}
	public void setSearchFrom(String searchFrom) {
		this.searchFrom = searchFrom;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
