package com.kingmon.project.psam.xzqh.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 地址--行政区划;省市县（区）信息
 * 
 * @author zhaohuatai
 * @date 2015年10月6日 下午12:05:36
 */
public class Xzqh implements Serializable {

	/**
	 * 主键uuid、<br>
	 * 
	 * 必填** <br>
	 * 
	 * 地址编码 -VARCHAR2(36)
	 */
	private String dzbm;

	/**
	 * 
	 * 10 省市县（区），11 省，12 市，13 县（区），20 乡镇(街道)<br>
	 * 21 建制乡，22 建制镇，23 街道办事处<br>
	 * 30 社区、居(村)委会，31 社区，32 居委会，33 村委会，34 组<br>
	 * 40 街路巷(小区)，41 街路巷，42 小区，43 单位（住宅）院，44 自然村<br>
	 * 50 建筑物<br>
	 * 60 建筑物单元房屋，61 单元（门），62 室（号）<br>
	 *
	 * 必填** <br>
	 * 数据字典 项
	 * 
	 */
	private String dzyslxdm;

	/**
	 * 6位，不可重复 <br>
	 * 必填** <br>
	 * 行政区划代码-VARCHAR2(12) <br>
	 */
	private String xzqhdm;

	/**
	 * 必填** <br>
	 * 行政区划名称 -VARCHAR2(60)
	 */
	private String xzqhmc;

	/**
	 * 必填** <br>
	 * 别名简称 -VARCHAR2(60)
	 */
	private String bmjc;

	/**
	 * 自身外键 --juuid<br>
	 * 必填** <br>
	 * 上级行政区域_地址编码-VARCHAR2(36)
	 */
	private String sjxzqy_dzbm;

	/**
	 * 时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 
	 * 必填** <br>
	 * 设立日期 -DATE
	 */
	private Date slrq;

	/**
	 * 时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 
	 * 撤销时间 -DATE
	 */
	private Date cxsj;

	/**
	 * 时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 
	 * 更新时间-DATE
	 */
	private Date gxsj;

	/**
	 * 启用日期 -DATE
	 */
	private Date qyrq;

	/**
	 * 时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 
	 * 停用日期 -DATE
	 */
	private Date tyrq;

	/**
	 * 
	 * 使用状态DZ_SYZT(10,使用中;20,停用;30,未启用;40,维修;50,报废;60,撤销;90,其他)<br>
	 * 
	 * 数据字典 项
	 * 
	 * 使用状态代码-VARCHAR2(2)
	 */
	private String syztdm;

	/**
	 * 
	 * 不用处理<br>
	 * 
	 * MOVESIGN
	 */
	private String movesign;

	/**
	 * 涉及到 撤销 时，必填<br>
	 * 
	 * 撤销原因 -VARCHAR2(100)
	 */
	private String cxyy;

	/**
	 * #new# 新加字段标记
	 * 
	 * 外键 来自 APP_ORGANIZATION_USER <br>
	 * 此处为当前登录用户<br>
	 * 
	 * 撤销人-VARCHAR2(36)
	 */
	private String cxr;

	/**
	 * #new# 新加字段标记 外键 来自 APP_ORGANIZATION <br>
	 * 
	 * 此处为当前登录用户所在单位<br>
	 * 
	 * 撤销单位-VARCHAR2(36)
	 */
	private String cxdw;

	/**
	 * 
	 * 根据BMJC 拼音生成 ZJF<br>
	 * 助记符-VARCHAR2(60)
	 */
	private String zjf;

	/**
	 * 外键 来自 APP_ORGANIZATION_USER <br>
	 * 此处为当前登录用户<br>
	 * 
	 * 登记人-VARCHAR2(36)
	 */
	private String djr;

	/**
	 * 
	 * 外键 来自 APP_ORGANIZATION <br>
	 * 
	 * 此处为当前登录用户所在单位<br>
	 * 
	 * 登记单位-VARCHAR2(36)
	 */
	private String djdw;

	/**
	 * 当前时间<br>
	 * 时间格式：yyyy-MM-dd hh:mm:ss<br>
	 * 登记时间 -DATE
	 */
	private Date djsj;

	/**
	 * 外键 来自 APP_ORGANIZATION_USER <br>
	 * 此处为当前登录用户<br>
	 * 
	 * 修改人-VARCHAR2(36)
	 */
	private String xgr;

	/**
	 * 外键 来自 APP_ORGANIZATION <br>
	 * 此处为当前登录用户所在单位<br>
	 * 修改单位 -VARCHAR2(36)
	 */
	private String xgdw;

	/**
	 * #new# 撤销标记char(1) 1:撤销（注销），其他正常
	 */
	private String cxbj;
	
	/**
	 * #新增字段# 归属代码
	 *   -VARCHAR2(36)
	 */
	private String xzqhgsdm;

	public String getDzbm() {
		return dzbm;
	}

	public void setDzbm(String dzbm) {
		this.dzbm = dzbm;
	}

	public String getDzyslxdm() {
		return dzyslxdm;
	}

	public void setDzyslxdm(String dzyslxdm) {
		this.dzyslxdm = dzyslxdm;
	}

	public String getXzqhdm() {
		return xzqhdm;
	}

	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}

	public String getXzqhmc() {
		return xzqhmc;
	}

	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}

	public String getBmjc() {
		return bmjc;
	}

	public void setBmjc(String bmjc) {
		this.bmjc = bmjc;
	}

	public String getSjxzqy_dzbm() {
		return sjxzqy_dzbm;
	}

	public void setSjxzqy_dzbm(String sjxzqy_dzbm) {
		this.sjxzqy_dzbm = sjxzqy_dzbm;
	}

	public Date getSlrq() {
		return slrq;
	}

	public void setSlrq(Date slrq) {
		this.slrq = slrq;
	}

	public Date getCxsj() {
		return cxsj;
	}

	public void setCxsj(Date cxsj) {
		this.cxsj = cxsj;
	}

	public Date getGxsj() {
		return gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

	public Date getQyrq() {
		return qyrq;
	}

	public void setQyrq(Date qyrq) {
		this.qyrq = qyrq;
	}

	public Date getTyrq() {
		return tyrq;
	}

	public void setTyrq(Date tyrq) {
		this.tyrq = tyrq;
	}

	public String getSyztdm() {
		return syztdm;
	}

	public void setSyztdm(String syztdm) {
		this.syztdm = syztdm;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	public String getCxyy() {
		return cxyy;
	}

	public void setCxyy(String cxyy) {
		this.cxyy = cxyy;
	}

	public String getCxr() {
		return cxr;
	}

	public void setCxr(String cxr) {
		this.cxr = cxr;
	}

	public String getCxdw() {
		return cxdw;
	}

	public void setCxdw(String cxdw) {
		this.cxdw = cxdw;
	}

	public String getZjf() {
		return zjf;
	}

	public void setZjf(String zjf) {
		this.zjf = zjf;
	}

	public String getDjr() {
		return djr;
	}

	public void setDjr(String djr) {
		this.djr = djr;
	}

	public String getDjdw() {
		return djdw;
	}

	public void setDjdw(String djdw) {
		this.djdw = djdw;
	}

	public Date getDjsj() {
		return djsj;
	}

	public void setDjsj(Date djsj) {
		this.djsj = djsj;
	}

	public String getXgr() {
		return xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public String getXgdw() {
		return xgdw;
	}

	public void setXgdw(String xgdw) {
		this.xgdw = xgdw;
	}

	public String getCxbj() {
		return cxbj;
	}

	public void setCxbj(String cxbj) {
		this.cxbj = cxbj;
	}

	public String getXzqhgsdm() {
		return xzqhgsdm;
	}

	public void setXzqhgsdm(String xzqhgsdm) {
		this.xzqhgsdm = xzqhgsdm;
	}

}