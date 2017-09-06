package com.kingmon.base.data;

import java.io.Serializable;

	
public class KJSONMSG implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int statusCode ;//状态码，***交互必须----
	private String message;//提示信息***交互必须----
	private String forwardUrl;//server返回后，跳转url ***不是必须----
	private String confirmMsg;//server返回后确认信息，***不是必须----
	private String token;//认证码 ***不是必须----
	private Object data;//数据，认证码 ***不是必须----
	
	public KJSONMSG() {
		super();
	}
	public KJSONMSG(int statusCode, String message) {
		this();
		this.statusCode = statusCode;
		this.message = message;
	}
	public KJSONMSG(int statusCode,Object data) {
		this();
		this.statusCode = statusCode;
		this.data = data;
	}
	public KJSONMSG(int statusCode, String message,Object data) {
		this();
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}

	public KJSONMSG(int statusCode, String message,String forwardUrl,String confirmMsg) {
		this();
		this.statusCode = statusCode;
		this.message = message;
		this.forwardUrl = forwardUrl;
		this.confirmMsg = confirmMsg;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	public String getConfirmMsg() {
		return confirmMsg;
	}
	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
//	public DataSet getData() {
//		return data;
//	}
//	public void setData(DataSet data) {
//		this.data = data;
//	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
