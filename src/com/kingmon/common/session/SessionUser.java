package com.kingmon.common.session;

import java.io.Serializable;
import java.util.List;

import com.kingmon.project.psam.jwq.model.Jwq;

public class SessionUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sessionId;
	
	private String loginLogId;//登录时对应的id，退出时要更新这条记录
	
	private String  userId;//appuser_id
	private String  loginname;//用户名，即登陆名
	private String  name;//姓名
	private String  enabled;//是否可用
	
	private String sfzh;//用户身份证号
	private String yhip;//用户IP
	
	private String organizationId;
	private String organizationCode;
	private String organizationName;
	private String organizationTel;
	private String userMoile;
	
	private String sssj;//所属
	private String ssfj;//所属分局
	private String sspcs;//所属派出所
	private String ssjwq;//所属警务区
	private String yhjb;//所属级别
	
	private List<String> ssjwqList;
	
	private int userLevel;
	
	private String userLevelStr;//
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSsfj() {
		return ssfj;
	}

	public void setSsfj(String ssfj) {
		this.ssfj = ssfj;
	}

	public String getSspcs() {
		return sspcs;
	}

	public void setSspcs(String sspcs) {
		this.sspcs = sspcs;
	}

	public String getSsjwq() {
		return ssjwq;
	}

	public void setSsjwq(String ssjwq) {
		this.ssjwq = ssjwq;
	}

	public String getYhjb() {
		return yhjb;
	}

	public void setYhjb(String yhjb) {
		this.yhjb = yhjb;
	}

	public List<String> getSsjwqList() {
		return ssjwqList;
	}

	public void setSsjwqList(List<String> ssjwqList) {
		this.ssjwqList = ssjwqList;
	}

	public String getYhip() {
		return yhip;
	}

	public void setYhip(String yhip) {
		this.yhip = yhip;
	}

	public String getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(String loginLogId) {
		this.loginLogId = loginLogId;
	}

	public String getSssj() {
		return sssj;
	}

	public void setSssj(String sssj) {
		this.sssj = sssj;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserLevelStr() {
		return userLevelStr;
	}

	public void setUserLevelStr(String userLevelStr) {
		this.userLevelStr = userLevelStr;
	}

	public String getUserMoile() {
		return userMoile;
	}

	public void setUserMoile(String userMoile) {
		this.userMoile = userMoile;
	}

	public String getOrganizationTel() {
		return organizationTel;
	}

	public void setOrganizationTel(String organizationTel) {
		this.organizationTel = organizationTel;
	}

	
	
}
