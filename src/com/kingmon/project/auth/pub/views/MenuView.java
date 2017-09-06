package com.kingmon.project.auth.pub.views;

import java.io.Serializable;
import java.util.List;

public class MenuView implements Serializable{

	private String message;
	private String statusCode;
	private List<MenuGroupView> data;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public List<MenuGroupView> getData() {
		return data;
	}
	public void setData(List<MenuGroupView> data) {
		this.data = data;
	}
	
	
	

}
