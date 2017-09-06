package com.kingmon.zay;

import java.util.List;

import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jwq.model.JwqyJygx;

public class JavaAuthBaseUser extends JavaAuthBaseBean{
	private String id;//主键
	private String username;//用户名，即登陆名
	private String name; //姓名
	private String enabled;//是否可用
	private String orgna_id; //单位代码
	private String orgna_name; //用户单位
	private boolean adminFlag; // 管理员标识
	private String tbdw;//填报单位
	private String yhlx;//用户类型(公安用户为gayh，费公安用户为registered,管理员为admin)
	private String sfzh;//用户身份证号
	private String yhip;//用户IP
	private String porgna_id;
	private String ssfj;
	private String sspcs;
	private String ssjwq;
	private String sszz;//所属组织
	private List<Jwq> entlistdata;
	private List<JwqyJygx> entjwqdata;
	private String dwid;//单位ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getOrgna_id() {
		return orgna_id;
	}
	public void setOrgna_id(String orgna_id) {
		this.orgna_id = orgna_id;
	}
	public String getOrgna_name() {
		return orgna_name;
	}
	public void setOrgna_name(String orgna_name) {
		this.orgna_name = orgna_name;
	}
	public boolean isAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(boolean adminFlag) {
		this.adminFlag = adminFlag;
	}
	public String getTbdw() {
		return tbdw;
	}
	public void setTbdw(String tbdw) {
		this.tbdw = tbdw;
	}
	public String getYhlx() {
		return yhlx;
	}
	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}
	public String getSfzh() {
		return sfzh;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	public String getYhip() {
		return yhip;
	}
	public void setYhip(String yhip) {
		this.yhip = yhip;
	}
	public String getPorgna_id() {
		return porgna_id;
	}
	public void setPorgna_id(String porgna_id) {
		this.porgna_id = porgna_id;
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
	public String getSszz() {
		return sszz;
	}
	public void setSszz(String sszz) {
		this.sszz = sszz;
	}
	public List<Jwq> getEntlistdata() {
		return entlistdata;
	}
	public void setEntlistdata(List<Jwq> entlistdata) {
		this.entlistdata = entlistdata;
	}
	public List<JwqyJygx> getEntjwqdata() {
		return entjwqdata;
	}
	public void setEntjwqdata(List<JwqyJygx> entjwqdata) {
		this.entjwqdata = entjwqdata;
	}
	public String getDwid() {
		return dwid;
	}
	public void setDwid(String dwid) {
		this.dwid = dwid;
	}
	
}
