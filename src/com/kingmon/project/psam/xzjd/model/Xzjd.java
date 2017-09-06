package com.kingmon.project.psam.xzjd.model;

import java.io.Serializable;
import java.util.Date;
/**
 * #new#新加字段标识
 * 地址--乡镇街道
 * @author zhaohuatai
 * @date 2015年10月5日 下午6:37:55
 */
public class Xzjd implements Serializable{
	
	/**
	 *  主键uuid、<br>
	 *  必填** <br>
	 * 地址编码-VARCHAR2(36)
	 */
    private String dzbm;
    
    /**
  	 *	10	省市县（区），11	省，12	市，13	县（区），20	乡镇(街道)<br>
	 *	21	建制乡，22	建制镇，23	街道办事处<br>
	 *	30	社区、居(村)委会，31	社区，32	居委会，33	村委会，34	  组<br>
	 *	40	街路巷(小区)，41	  街路巷，42	  小区，43	  单位（住宅）院，44	  自然村<br>
	 *	50	建筑物<br>
	 *	60	建筑物单元房屋，61	单元（门），62	  室（号）<br>
	 *
	 * 必填** <br>
	 * 数据字典 项 
	 * 
	 * 地址元素类型代码-VARCHAR2(20)
	 * 
	 */
    private String dzyslxdm;
    
    
    /**
 	 *  6+3 位，不可重复 <br>
	 *  必填** <br>
   	 *  乡镇（街道）代码-VARCHAR2(36)
   	 */
    private String xzjddm;
    
    /**
     *  必填** <br>
   	 *  乡镇（街道）名称-VARCHAR2(100)
   	 */
    private String xzjdmc;
    
    /**
     *  必填** <br>
   	 *  别名简称-VARCHAR2(100)
   	 */
    private String bmjc;
    
    /**
     * 外键 --DZ_XZQH：DZBM <br>
     * 
   	 *  上级行政区域_地址编码-VARCHAR2(36)
   	 */
    private String sjxzqy_dzbm;
    
    /**
     * #new#+
     * 不是数据库字段
     * 上级行政区域_地址名称 --20151024添加
   	 */
    private String sjxzqy_mc;
    
    
    /**
     * 时间格式：yyyy-MM-dd hh:mm:ss<br>
   	 *  设立日期-DATE
   	 */
    private Date slrq;
    
    /**
     * 时间格式：yyyy-MM-dd hh:mm:ss<br>
   	 *  启用日期-DATE
   	 */
    private Date qyrq;
    
    /**
     * 时间格式：yyyy-MM-dd hh:mm:ss<br>
   	 *  停用日期-DATE
   	 */
    private Date tyrq;
    
    /**
	 * 使用状态DZ_SYZT(10,使用中;20,停用;30,未启用;40,维修;50,报废;60,撤销;90,其他)<br>
	 * 
	 *  数据字典 项DZ_DATADIC-DECLARE_CODE:0002000000 
	 *  
	 * 使用状态代码-VARCHAR2(2)
   	 *  
   	 */
    private String syztdm;
    
    /**
     * 根据BMJC 拼音生成 ZJF<br>
	 * 助记符-VARCHAR2(60)
   	 */
    private String zjf;
    /**
     * 不用操作
   	 *  MOVESIGN-CHAR(1)
   	 */
    private String movesign;
    
//-------------------CX------------------------------------------------    
    
    /**
     * 时间格式：yyyy-MM-dd hh:mm:ss<br>
   	 *  撤销时间-DATE
   	 */
    private Date cxsj;
    
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
     * #new# 新加字段标记
     * 外键 来自 APP_ORGANIZATION <br>
	 * 
	 * 此处为当前登录用户所在单位<br>
	 * 
   	 *  撤销单位-VARCHAR2(36)
   	 */
    private String cxdw;
    
    
    /**
     * 涉及到 撤销 时，必填<br>
     * 
   	 * 撤销原因-VARCHAR2(100)
   	 */
    private String cxyy;
    
//-------------------DJ------------------------------------------------      
    /**
 	 * 外键 来自 APP_ORGANIZATION_USER <br>
	 * 此处为当前登录用户<br>
	 * 
   	 * 登记人-VARCHAR2(36)
   	 */
    private String djr;
    
    /**
     * 外键 来自 APP_ORGANIZATION <br>
	 * 
	 * 此处为当前登录用户所在单位<br>
	 * 
   	 *  登记单位-VARCHAR2(36)
   	 */
    private String djdw;
    
    /**
     * 当前时间<br>
   	 * 登记时间(yyyy-mm-dd hh:mi:ss)-DATE
   	 */
    private Date djsj;
    
//---------------------XG----------------------------------------------   
    /**
     * 时间格式：yyyy-MM-dd hh:mm:ss<br>
   	 *  更新时间-DATE
   	 */
    private Date gxsj;
    /**
	 * 外键 来自 APP_ORGANIZATION_USER <br>
	 * 此处为当前登录用户<br>
   	 *  修改人-VARCHAR2(36)
   	 */
    private String xgr;
    
    /**
     * 外键 来自 APP_ORGANIZATION <br>
	 * 此处为当前登录用户所在单位<br>
   	 *  修改单位-VARCHAR2(36)
   	 */
    private String xgdw;
    /**
     * 撤销标记-char(1) 1:撤销（注销），其他正常
     */
    private String cxbj;

    /**
	 * #新增字段# 归属代码
	 *   -VARCHAR2(36)
	 */
	private String xzjdgsdm;
	
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

	public String getXzjddm() {
		return xzjddm;
	}

	public void setXzjddm(String xzjddm) {
		this.xzjddm = xzjddm;
	}

	public String getXzjdmc() {
		return xzjdmc;
	}

	public void setXzjdmc(String xzjdmc) {
		this.xzjdmc = xzjdmc;
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

	public String getZjf() {
		return zjf;
	}

	public void setZjf(String zjf) {
		this.zjf = zjf;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	public Date getCxsj() {
		return cxsj;
	}

	public void setCxsj(Date cxsj) {
		this.cxsj = cxsj;
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

	public String getCxyy() {
		return cxyy;
	}

	public void setCxyy(String cxyy) {
		this.cxyy = cxyy;
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

	public Date getGxsj() {
		return gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
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

	/**
	 * @return the cxbj
	 */
	public String getCxbj() {
		return cxbj;
	}

	/**
	 * @param cxbj the cxbj to set
	 */
	public void setCxbj(String cxbj) {
		this.cxbj = cxbj;
	}

	public String getSjxzqy_mc() {
		return sjxzqy_mc;
	}

	public void setSjxzqy_mc(String sjxzqy_mc) {
		this.sjxzqy_mc = sjxzqy_mc;
	}

	public String getXzjdgsdm() {
		return xzjdgsdm;
	}

	public void setXzjdgsdm(String xzjdgsdm) {
		this.xzjdgsdm = xzjdgsdm;
	}
    
}