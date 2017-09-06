package com.sdwangge.policecloud.bean;

import java.io.Serializable;
import java.util.List;

import com.sdwangge.policecloud.bean.application.EntJwq;
import com.sdwangge.policecloud.bean.application.EntJwqyjygx;

public class BaseUser extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 主键
	private String username;// 用户名，即登陆名
	private String name; // 姓名
	private String enabled;// 是否可用
	private String orgna_id; // 单位代码
	private String orgna_name; // 用户单位
	private boolean adminFlag; // 管理员标识
	private String tbdw;// 填报单位
	private String yhlx;// 用户类型(公安用户为gayh，费公安用户为registered,管理员为admin)
	private String sfzh;// 用户身份证号
	private String yhip;// 用户IP
	private String porgna_id;// 父组织机构id
	private String ssfj;// 所属分局
	private String sspcs;// 所属派出所
	private String ssjwq;// 所属警务区
	private String sszz;// 所属组织
	private List<EntJwq> entlistdata;
	private List<EntJwqyjygx> entjwqdata;
	private String dwid;// 单位ID
	private String sfsd;// 是否锁定
	private String sfsh;// 是否审核
	private String bdip;// 绑定IP
	private String pcsid;// 派出所id
	private String sjxzqyid;// 上级行政区划id
	private String jwqbh;// 警务区编码

	public String getSfsd() {
		return sfsd;
	}

	public void setSfsd(String sfsd) {
		this.sfsd = sfsd;
	}

	public String getSfsh() {
		return sfsh;
	}

	public void setSfsh(String sfsh) {
		this.sfsh = sfsh;
	}

	public String getBdip() {
		return bdip;
	}

	public void setBdip(String bdip) {
		this.bdip = bdip;
	}

	public String getDwid() {
		return dwid;
	}

	public void setDwid(String dwid) {
		this.dwid = dwid;
	}

	public List<EntJwqyjygx> getEntjwqdata() {
		return entjwqdata;
	}

	public void setEntjwqdata(List<EntJwqyjygx> entjwqdata) {
		this.entjwqdata = entjwqdata;
	}

	private String orgna_type;

	public String getOrgna_type() {
		return orgna_type;
	}

	public void setOrgna_type(String orgnaType) {
		orgna_type = orgnaType;
	}

	public List<EntJwq> getEntlistdata() {
		return entlistdata;
	}

	public void setEntlistdata(List<EntJwq> entlistdata) {
		this.entlistdata = entlistdata;
	}

	public String getPcsid() {
		return pcsid;
	}

	public void setPcsid(String pcsid) {
		this.pcsid = pcsid;
	}

	public String getSjxzqyid() {
		return sjxzqyid;
	}

	public void setSjxzqyid(String sjxzqyid) {
		this.sjxzqyid = sjxzqyid;
	}

	public String getJwqbh() {
		return jwqbh;
	}

	public void setJwqbh(String jwqbh) {
		this.jwqbh = jwqbh;
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

	public String getPorgna_id() {
		return porgna_id;
	}

	public void setPorgna_id(String porgnaId) {
		porgna_id = porgnaId;
	}

	public String getSszz() {
		return sszz;
	}

	public void setSszz(String sszz) {
		this.sszz = sszz;
	}

	public String getYhip() {
		return yhip;
	}

	public void setYhip(String yhip) {
		this.yhip = yhip;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getTbdw() {
		return tbdw;
	}

	public void setTbdw(String tbdw) {
		this.tbdw = tbdw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgna_id() {
		return orgna_id;
	}

	public String getYhlx() {
		return yhlx;
	}

	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}

	public void setOrgna_id(String orgnaId) {
		orgna_id = orgnaId;
	}

	public boolean getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(boolean adminFlag) {
		this.adminFlag = adminFlag;
	}

	public String getOrgna_name() {
		return orgna_name;
	}

	public void setOrgna_name(String orgnaName) {
		orgna_name = orgnaName;
	}

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

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}
