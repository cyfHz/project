package com.kingmon.project.psam.jzw.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 建筑物基本信息
 * 
 * @author zhaohuatai
 * @date 2015年10月5日 下午7:52:09
 */
public class Jzwjbxx implements Serializable{
	
	/**
	 *  主键 uuid<br>
	 * 地址编码-VARCHAR2(36)
	 * !
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
	 * 数据字典 项  app_dictionary_type--DZYSFL
     * 
     * 建筑物类型代码-VARCHAR2(2)
     * !
     */
    private String dzyslxdm;

    /**
     * 
     * 地址-VARCHAR2(200)
     * !
     */
    private String dzmc;

    /**
     * 
     * 建筑物名称-VARCHAR2(300)
     * !
     */
    private String jzwmc;

    /**
     * 
     * 别名简称-VARCHAR2(200)
     * !
     */
    private String bmjc;

    /**
     * 建筑物
     * 中心点横坐标-NUMBER(11,8)
     * !
     */
    private BigDecimal zxdhzb;

    /**
     * 建筑物
     * 中心点纵坐标-NUMBER(11,8)
     * !
     */
    private BigDecimal zxdzzb;

    /**
     * #? 外键
     *  
     * 警务责任区代码-VARCHAR2(36)
     */
    private String zaglssjwzrqdm;

    /**
     * #? 外键
     * 所属最低一级行政区域_地址编码-VARCHAR2(36)
     * !
     */
    private String sszdyjxzqy_dzbm;

    /**
     * 
     * 所属街路巷（小区）_地址编码-VARCHAR2(36)
     * !
     */
    private String ssjlxxq_dzbm;

    /**
     * 地(住)址存在标识-CHAR(1)
     * !
     */
    private String dzzczbz;

    /**
     * 地(住)址在用标识-CHAR(1)
     * !
     */
    private String dzzzybs;

    /**
     * 更新时间-DATE
     * !
     */
    private Date gxsj;

    /**
     *  启用日期-DATE
     *  ?
     */
    private Date qyrq;

    /**
     * 停用日期-DATE
     * ?
     */
    private Date tyrq;

    /**
     * 登记单位_公安机关机构代码-VARCHAR2(20)
     * ?
     */
    private String djdw_gajgjgdm;

    /**
     * 登记单位_公安机关名称-VARCHAR2(100)
     * ?
     */
    private String djdw_gajgmc;

    /**
     * 登记人_姓名-VARCHAR2(60)
     * ?
     */
    private String djr_xm;

    /**
     * #? 含义
     * MOVESIGN-CHAR(1)
     */
    private String movesign;

    /**
     * 助记符-VARCHAR2(60)
     * !
     */
    private String zjf;

    /**
     * #? 外键
     * 登记人-VARCHAR2(36)
     * ?
     */
    private String djr;

    /**
     * 登记单位-VARCHAR2(36)
     *  ?
     */
    private String djdw;

    /**
     * 登记时间-DATE
     * ?
     */
    private Date djsj;

    /**#? 外键
     * 修改人-VARCHAR2(36)
     * !
     */
    private String xgr;

    /**
     * #? 外键
     * 修改单位-VARCHAR2(36)
     * !
     */
    private String xgdw;

    /**
     * 1:可用  0：不可用--CHAR(1)
     * !
     */
    private String enable;

    /**
     * 子节点个数-NUMBER
     */
    private BigDecimal childcount;

    /**
     * 
     * 警务责任区名称-VARCHAR2(100)
     * ?
     */
    private String zaglssjwzrqmc;

    /**
     * 删除时间-DATE
     */
    private Date deltime;

    /**
     * 是否已经加入到elastic
     * !
     */
    private String isIndexed;
    
    /**
     * 创建时间(导入时间)
     * !
     */
    private Date createTime;
    
    
    
    //-------------------------------------------------  
    /**
     * 二维码-clob
     */
    private String qrCode;
    
    /**
     * Mac地址
     * MAC_ADDRESS varchar2(20);
     */
    private String macAddress;
    
    /**
     * 
     * TF卡号 varchar2(36);
     */
    private String tfCardNum;
    
    /**
     * IMEI号 varchar2(20);
     */
    private String imeiNum;
    
    /**
     * SIM卡号-varchar2(20)
     */
    private String simNum;
    
    /**
     * NUMBER(11,8)
     */
    private BigDecimal gpsX;
    
    /**
     * NUMBER(11,8)
     */
    private BigDecimal gpsY;
    //-------------------------------------------------   
    /**
     * 原所属警务区编号
     */
    private String originaljwqbh;
    
    /**
   	 * #新增字段# 归属代码
   	 *   -VARCHAR2(36)
   	 */
   	private String jzwgsdm;
   	
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

	public String getDzmc() {
		return dzmc;
	}

	public void setDzmc(String dzmc) {
		this.dzmc = dzmc;
	}

	public String getJzwmc() {
		return jzwmc;
	}

	public void setJzwmc(String jzwmc) {
		this.jzwmc = jzwmc;
	}

	public String getBmjc() {
		return bmjc;
	}

	public void setBmjc(String bmjc) {
		this.bmjc = bmjc;
	}

	public BigDecimal getZxdhzb() {
		return zxdhzb;
	}

	public void setZxdhzb(BigDecimal zxdhzb) {
		this.zxdhzb = zxdhzb;
	}

	public BigDecimal getZxdzzb() {
		return zxdzzb;
	}

	public void setZxdzzb(BigDecimal zxdzzb) {
		this.zxdzzb = zxdzzb;
	}

	public String getZaglssjwzrqdm() {
		return zaglssjwzrqdm;
	}

	public void setZaglssjwzrqdm(String zaglssjwzrqdm) {
		this.zaglssjwzrqdm = zaglssjwzrqdm;
	}

	public String getSszdyjxzqy_dzbm() {
		return sszdyjxzqy_dzbm;
	}

	public void setSszdyjxzqy_dzbm(String sszdyjxzqy_dzbm) {
		this.sszdyjxzqy_dzbm = sszdyjxzqy_dzbm;
	}

	public String getSsjlxxq_dzbm() {
		return ssjlxxq_dzbm;
	}

	public void setSsjlxxq_dzbm(String ssjlxxq_dzbm) {
		this.ssjlxxq_dzbm = ssjlxxq_dzbm;
	}

	public String getDzzczbz() {
		return dzzczbz;
	}

	public void setDzzczbz(String dzzczbz) {
		this.dzzczbz = dzzczbz;
	}

	public String getDzzzybs() {
		return dzzzybs;
	}

	public void setDzzzybs(String dzzzybs) {
		this.dzzzybs = dzzzybs;
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

	public String getDjdw_gajgjgdm() {
		return djdw_gajgjgdm;
	}

	public void setDjdw_gajgjgdm(String djdw_gajgjgdm) {
		this.djdw_gajgjgdm = djdw_gajgjgdm;
	}

	public String getDjdw_gajgmc() {
		return djdw_gajgmc;
	}

	public void setDjdw_gajgmc(String djdw_gajgmc) {
		this.djdw_gajgmc = djdw_gajgmc;
	}

	public String getDjr_xm() {
		return djr_xm;
	}

	public void setDjr_xm(String djr_xm) {
		this.djr_xm = djr_xm;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
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

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public BigDecimal getChildcount() {
		return childcount;
	}

	public void setChildcount(BigDecimal childcount) {
		this.childcount = childcount;
	}

	public String getZaglssjwzrqmc() {
		return zaglssjwzrqmc;
	}

	public void setZaglssjwzrqmc(String zaglssjwzrqmc) {
		this.zaglssjwzrqmc = zaglssjwzrqmc;
	}

	public Date getDeltime() {
		return deltime;
	}

	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

	public String getIsIndexed() {
		return isIndexed;
	}

	public void setIsIndexed(String isIndexed) {
		this.isIndexed = isIndexed;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getTfCardNum() {
		return tfCardNum;
	}

	public void setTfCardNum(String tfCardNum) {
		this.tfCardNum = tfCardNum;
	}

	public String getImeiNum() {
		return imeiNum;
	}

	public void setImeiNum(String imeiNum) {
		this.imeiNum = imeiNum;
	}

	public String getSimNum() {
		return simNum;
	}

	public void setSimNum(String simNum) {
		this.simNum = simNum;
	}

	public BigDecimal getGpsX() {
		return gpsX;
	}

	public void setGpsX(BigDecimal gpsX) {
		this.gpsX = gpsX;
	}

	public BigDecimal getGpsY() {
		return gpsY;
	}

	public void setGpsY(BigDecimal gpsY) {
		this.gpsY = gpsY;
	}

	public String getOriginaljwqbh() {
		return originaljwqbh;
	}

	public void setOriginaljwqbh(String originaljwqbh) {
		this.originaljwqbh = originaljwqbh;
	}

	public String getJzwgsdm() {
		return jzwgsdm;
	}

	public void setJzwgsdm(String jzwgsdm) {
		this.jzwgsdm = jzwgsdm;
	}

	
	

}