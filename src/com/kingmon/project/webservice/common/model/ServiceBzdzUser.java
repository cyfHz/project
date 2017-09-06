package com.kingmon.project.webservice.common.model;

import java.io.Serializable;
import java.util.Date;

public class ServiceBzdzUser implements Serializable {
	/**
	 * 主键
	 */
	private String systemid;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 用户密码
	 */
	private String userpassword;

	/**
	 * 负责人姓名
	 */
	private String fzrname;

	/**
	 * 负责人身份证号
	 */
	private String fzrsfzh;

	/**
	 * 介绍人姓名
	 */
	private String jsrname;

	/**
	 * 介绍人身份证号
	 */
	private String jsrsfzh;

	/**
	 * 介绍人警号
	 */
	private String jsrpoliceid;

	/**
	 * 公司名称
	 */
	private String companyname;

	/**
	 * 注册时间
	 */
	private Date registertime;

	/**
	 * 审核人
	 */
	private String shr;

	/**
	 * 审核时间
	 */
	private Date shtime;

	/**
	 * 是否审核
	 */
	private String sfsh;

	/**
	 * 绑定IP
	 */
	private String bdip;

	/**
	 * 是否锁定
	 */
	private String sfsd;

	/**
	 * 锁定时间
	 */
	private Date sdtime;

	/**
	 * 锁定人
	 */
	private String sdr;

	/**
	 * 删除标记
	 */
	private String deltag;

	/**
	 * 删除用户
	 */
	private String deluser;

	/**
	 * 删除时间
	 */
	private Date deltime;

	/**
	 * 登记人
	 */
	private String djr;

	/**
	 * 登记单位
	 */
	private String djdw;

	/**
	 * 登记时间
	 */
	private Date djsj;

	/**
	 * 登记人名称
	 */
	private String djrmc;

	/**
	 * 登记单位名称
	 */
	private String djdwmc;

	/**
	 * 修改人名称
	 */
	private String xgrmc;

	/**
	 * 修改单位名称
	 */
	private String xgdwmc;

	/**
	 * 修改人
	 */
	private String xgr;

	/**
	 * 修改单位
	 */
	private String xgdw;

	/**
	 * 更新时间
	 */
	private String gxsj;

	/**
	 * 
	 */
	private String movesign;

	/**
	 * 新增 --限制登录次数(分钟) 空为不限次数
	 */
	private Long rejectCount;

	/**
	 * 登录时存储tokenId
	 */
	private String tokenId;
	
	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid == null ? null : systemid.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword == null ? null : userpassword.trim();
	}

	public String getFzrname() {
		return fzrname;
	}

	public void setFzrname(String fzrname) {
		this.fzrname = fzrname == null ? null : fzrname.trim();
	}

	public String getFzrsfzh() {
		return fzrsfzh;
	}

	public void setFzrsfzh(String fzrsfzh) {
		this.fzrsfzh = fzrsfzh == null ? null : fzrsfzh.trim();
	}

	public String getJsrname() {
		return jsrname;
	}

	public void setJsrname(String jsrname) {
		this.jsrname = jsrname == null ? null : jsrname.trim();
	}

	public String getJsrsfzh() {
		return jsrsfzh;
	}

	public void setJsrsfzh(String jsrsfzh) {
		this.jsrsfzh = jsrsfzh == null ? null : jsrsfzh.trim();
	}

	public String getJsrpoliceid() {
		return jsrpoliceid;
	}

	public void setJsrpoliceid(String jsrpoliceid) {
		this.jsrpoliceid = jsrpoliceid == null ? null : jsrpoliceid.trim();
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname == null ? null : companyname.trim();
	}

	public Date getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	public String getShr() {
		return shr;
	}

	public void setShr(String shr) {
		this.shr = shr == null ? null : shr.trim();
	}

	public Date getShtime() {
		return shtime;
	}

	public void setShtime(Date shtime) {
		this.shtime = shtime;
	}

	public String getSfsh() {
		return sfsh;
	}

	public void setSfsh(String sfsh) {
		this.sfsh = sfsh == null ? null : sfsh.trim();
	}

	public String getBdip() {
		return bdip;
	}

	public void setBdip(String bdip) {
		this.bdip = bdip == null ? null : bdip.trim();
	}

	public String getSfsd() {
		return sfsd;
	}

	public void setSfsd(String sfsd) {
		this.sfsd = sfsd == null ? null : sfsd.trim();
	}

	public Date getSdtime() {
		return sdtime;
	}

	public void setSdtime(Date sdtime) {
		this.sdtime = sdtime;
	}

	public String getSdr() {
		return sdr;
	}

	public void setSdr(String sdr) {
		this.sdr = sdr == null ? null : sdr.trim();
	}

	public String getDeltag() {
		return deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag == null ? null : deltag.trim();
	}

	public String getDeluser() {
		return deluser;
	}

	public void setDeluser(String deluser) {
		this.deluser = deluser == null ? null : deluser.trim();
	}

	public Date getDeltime() {
		return deltime;
	}

	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

	public String getDjr() {
		return djr;
	}

	public void setDjr(String djr) {
		this.djr = djr == null ? null : djr.trim();
	}

	public String getDjdw() {
		return djdw;
	}

	public void setDjdw(String djdw) {
		this.djdw = djdw == null ? null : djdw.trim();
	}

	public Date getDjsj() {
		return djsj;
	}

	public void setDjsj(Date djsj) {
		this.djsj = djsj;
	}

	public String getDjrmc() {
		return djrmc;
	}

	public void setDjrmc(String djrmc) {
		this.djrmc = djrmc == null ? null : djrmc.trim();
	}

	public String getDjdwmc() {
		return djdwmc;
	}

	public void setDjdwmc(String djdwmc) {
		this.djdwmc = djdwmc == null ? null : djdwmc.trim();
	}

	public String getXgrmc() {
		return xgrmc;
	}

	public void setXgrmc(String xgrmc) {
		this.xgrmc = xgrmc == null ? null : xgrmc.trim();
	}

	public String getXgdwmc() {
		return xgdwmc;
	}

	public void setXgdwmc(String xgdwmc) {
		this.xgdwmc = xgdwmc == null ? null : xgdwmc.trim();
	}

	public String getXgr() {
		return xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr == null ? null : xgr.trim();
	}

	public String getXgdw() {
		return xgdw;
	}

	public void setXgdw(String xgdw) {
		this.xgdw = xgdw == null ? null : xgdw.trim();
	}

	public String getGxsj() {
		return gxsj;
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj == null ? null : gxsj.trim();
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign == null ? null : movesign.trim();
	}

	public Long getRejectCount() {
		return rejectCount;
	}

	public void setRejectCount(Long rejectCount) {
		this.rejectCount = rejectCount;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}


}