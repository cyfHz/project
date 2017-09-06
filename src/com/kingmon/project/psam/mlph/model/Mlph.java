package com.kingmon.project.psam.mlph.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 地址门（楼）牌编号信息
 * @author zhaohuatai
 * @date 2015年10月5日 下午8:26:33
 */
public class Mlph implements Serializable{
	
	/**
	 * 主键 uuid
	 * 
	 * 业务流水号-VARCHAR2(36)
	 * !
	 */
    private String ywlsh;

    /**
     * 与主键相同即可<br>
     * 
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
	 * 数据字典 项
	 * 
	 * 地址元素类型代码-VARCHAR2(20)<br>
	 * 默认填写50  
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
     * 不是数据库字段，为了绑定接收参数，业务逻辑中赋值给dzmc
     */
    private String mlphdzmc;

    /**
     * 外键：DZ_XZQH -DZBM
     * 
     * 省市县（区）-VARCHAR2(36)
     * !
     */
    private String ssxqdm;

    /**
     * 
     * 区划内详细地址-VARCHAR2(200)
     * !
     */
    private String qhnxxdz;

    /**
     * 
     *  注意长度 JLXXQ 中名称长度为100<br>
     * 所属街路巷(小区)_街路巷(小区)名称-VARCHAR2(50)
     * !
     */
    private String ssjlxxq_jlxxqmc;

    /**
     *  外键：DZ_JLX -DZBM
     *  
     * 所属街路巷(小区)_地址编码-VARCHAR2(36)
     * !
     */
    private String ssjlxxq_dzbm;

    /**
     * <br>
     * 
     * 所属建筑物_地址编码-VARCHAR2(36)
     * !
     */
    private String ssjzw_dzbm;

    /**
     * 
     * 门(楼)牌号-VARCHAR2(150)
     * !
     */
    private String mlph;
    
//    /**
//     * 所属排--20160224 添加
//     */
//    private String rowline;

    /**
     * 临时门（楼）牌标识-CHAR(1)
     * !
     */
    private String lsmlpbs;

    /**
     * 办理单位_公安机关机构代码-VARCHAR2(20)
     * ?
     */
    private String bldw_gajgjgdm;

    /**
     * 办理单位_公安机关名称-VARCHAR2(50)
     * ?
     */
    private String bldw_gajgmc;

    /**
     * 办理人_姓名-VARCHAR2(20)
     * ?
     */
    private String blr_xm;

    /**
     * 办理时间-DATE
     * ?
     */
    private Date blsj;

    /**
     * 数据归属单位代码  -最底到警务责任区-VARCHAR2(20)
     * ?
     */
    private String sjgsdwdm;

    /**
     * 数据归属单位名称-VARCHAR2(100)
     * ?
     */
    private String sjgsdwmc;

    /**
     * MOVESIGN-CHAR(1)
     */
    private String movesign;

    /**
     * 登记人-VARCHAR2(36) 
     * ?
     */
    private String djr;

    /**
     * 登记单位-VARCHAR2(36) 
     * ?
     */
    private String djdw;

    /**
     * 登记时间(yyyy-mm-dd hh:mi:ss)-DATE
     */
    private Date djsj;

    /**
     * 修改人-VARCHAR2(36)
     */
    private String xgr;

    /**
     * 修改单位-VARCHAR2(36)
     */
    private String xgdw;

    /**
     * 更新时间-DATE
     */
    private Date gxsj;

    /**
     * 申请人ID-VARCHAR2(36)
     */
    private String sqrid;

    /**
     * 子节点的个数-NUMBER
     */
    private BigDecimal childcount;

    /**
     * 中心点横坐标-NUMBER(11,8)
     * !
     */
    private BigDecimal zxdhzb;

    /**
     * 中心点纵坐标-NUMBER(11,8)
     * !
     */
    private BigDecimal zxdzzb;

    /**
     * 注销标志(0否 1是)-CHAR(1)
     * !
     */
    private String deltag;

    /**
     * 注销人-VARCHAR2(50)
     * !
     */
    private String deluser;

    /**
     * 注销时间-DATE
     * !
     */
    private Date deltime;

    /**
     * 所属最低一级行政区划——地址代码-VARCHAR2(36)
     */
    private String sszdyjxzqy_dzbm;

    /**
     * 建筑物名称-VARCHAR2(300)
     * !
     */
    private String jzwmc;

    /**
     * 警务区编号（备选）-VARCHAR2(20)
     * ?
     */
    private String jwqbh;

    /**
     * 警务区名称（备选）-VARCHAR2(200)
     *  ?
     */
    private String jwqmc;

    /**
     * 数据来源 0后台,1终端 -CHAR(1)
     */
    private String fromby;

    /**
     * 设备编号-VARCHAR2(50)
     */
    private String sbid;

    /**
     * GPS  x -NUMBER(11,8)
     */
    private BigDecimal gpsx;

    /**
     * GPS  y-NUMBER(11,8)
     */
    private BigDecimal gpsy;

    /**
     * 门楼牌号类型ID 对应 字典表的mlphlx-CHAR(1)
     * !
     */
    private String mlphlxid;

    /**
     * 
     * 门楼牌号类型名称-VARCHAR2(20)
     * !
     */
    private String mlphlxmc;

    /**
     * 导入时间-VARCHAR2(14)
     */
    private String drsj;

    /**
     * 所属派出所-VARCHAR2(36)
     */
    private String sspcs;

    /**
     * 所属分局-VARCHAR2(36)
     */
    private String ssfj;

    /**
     * 所属市局-VARCHAR2(36)
     */
    private String sssj;
    /**
     * #new#
     * 审核结果char(1) 0:正在申请 1、审核未通过2、审核通过
     */
    private String spzt;
    /**
     * #new#
     * 审核配置代码char(1) 0:不需要审核1、派出所申请2、区县审核3、市局审核
     */
    private String shpzdm;

    
    
    /**
     * 是否已经加入到elastic
     */
    private String isIndexed;
    
    /**
     * 创建时间(导入时间)
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

    //-------------------------------------------------   
    
    private String cxyy;
   /**
    * 原始警务区编号
    */
   private String originaljwqbh;
    
   /**
  	 * #新增字段# 归属代码
  	 *   -VARCHAR2(36)
  	 */
  	private String mlphgsdm;
  	
	public String getYwlsh() {
		return ywlsh;
	}

	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}

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

	public String getSsxqdm() {
		return ssxqdm;
	}

	public void setSsxqdm(String ssxqdm) {
		this.ssxqdm = ssxqdm;
	}

	public String getQhnxxdz() {
		return qhnxxdz;
	}

	public void setQhnxxdz(String qhnxxdz) {
		this.qhnxxdz = qhnxxdz;
	}

	public String getSsjlxxq_jlxxqmc() {
		return ssjlxxq_jlxxqmc;
	}

	public void setSsjlxxq_jlxxqmc(String ssjlxxq_jlxxqmc) {
		this.ssjlxxq_jlxxqmc = ssjlxxq_jlxxqmc;
	}

	public String getSsjlxxq_dzbm() {
		return ssjlxxq_dzbm;
	}

	public void setSsjlxxq_dzbm(String ssjlxxq_dzbm) {
		this.ssjlxxq_dzbm = ssjlxxq_dzbm;
	}

	public String getSsjzw_dzbm() {
		return ssjzw_dzbm;
	}

	public void setSsjzw_dzbm(String ssjzw_dzbm) {
		this.ssjzw_dzbm = ssjzw_dzbm;
	}

	public String getMlph() {
		return mlph;
	}

	public void setMlph(String mlph) {
		this.mlph = mlph;
	}

	public String getLsmlpbs() {
		return lsmlpbs;
	}

	public void setLsmlpbs(String lsmlpbs) {
		this.lsmlpbs = lsmlpbs;
	}

	public String getBldw_gajgjgdm() {
		return bldw_gajgjgdm;
	}

	public void setBldw_gajgjgdm(String bldw_gajgjgdm) {
		this.bldw_gajgjgdm = bldw_gajgjgdm;
	}

	public String getBldw_gajgmc() {
		return bldw_gajgmc;
	}

	public void setBldw_gajgmc(String bldw_gajgmc) {
		this.bldw_gajgmc = bldw_gajgmc;
	}

	public String getBlr_xm() {
		return blr_xm;
	}

	public void setBlr_xm(String blr_xm) {
		this.blr_xm = blr_xm;
	}

	public Date getBlsj() {
		return blsj;
	}

	public void setBlsj(Date blsj) {
		this.blsj = blsj;
	}

	public String getSjgsdwdm() {
		return sjgsdwdm;
	}

	public void setSjgsdwdm(String sjgsdwdm) {
		this.sjgsdwdm = sjgsdwdm;
	}

	public String getSjgsdwmc() {
		return sjgsdwmc;
	}

	public void setSjgsdwmc(String sjgsdwmc) {
		this.sjgsdwmc = sjgsdwmc;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
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

	public Date getGxsj() {
		return gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

	public String getSqrid() {
		return sqrid;
	}

	public void setSqrid(String sqrid) {
		this.sqrid = sqrid;
	}

	public BigDecimal getChildcount() {
		return childcount;
	}

	public void setChildcount(BigDecimal childcount) {
		this.childcount = childcount;
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

	public String getDeltag() {
		return deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	public String getDeluser() {
		return deluser;
	}

	public void setDeluser(String deluser) {
		this.deluser = deluser;
	}

	public Date getDeltime() {
		return deltime;
	}

	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

	public String getSszdyjxzqy_dzbm() {
		return sszdyjxzqy_dzbm;
	}

	public void setSszdyjxzqy_dzbm(String sszdyjxzqy_dzbm) {
		this.sszdyjxzqy_dzbm = sszdyjxzqy_dzbm;
	}

	public String getJzwmc() {
		return jzwmc;
	}

	public void setJzwmc(String jzwmc) {
		this.jzwmc = jzwmc;
	}

	public String getJwqbh() {
		return jwqbh;
	}

	public void setJwqbh(String jwqbh) {
		this.jwqbh = jwqbh;
	}

	public String getJwqmc() {
		return jwqmc;
	}

	public void setJwqmc(String jwqmc) {
		this.jwqmc = jwqmc;
	}

	public String getFromby() {
		return fromby;
	}

	public void setFromby(String fromby) {
		this.fromby = fromby;
	}

	public String getSbid() {
		return sbid;
	}

	public void setSbid(String sbid) {
		this.sbid = sbid;
	}

	public BigDecimal getGpsx() {
		return gpsx;
	}

	public void setGpsx(BigDecimal gpsx) {
		this.gpsx = gpsx;
	}

	public BigDecimal getGpsy() {
		return gpsy;
	}

	public void setGpsy(BigDecimal gpsy) {
		this.gpsy = gpsy;
	}

	public String getMlphlxid() {
		return mlphlxid;
	}

	public void setMlphlxid(String mlphlxid) {
		this.mlphlxid = mlphlxid;
	}

	public String getMlphlxmc() {
		return mlphlxmc;
	}

	public void setMlphlxmc(String mlphlxmc) {
		this.mlphlxmc = mlphlxmc;
	}

	public String getDrsj() {
		return drsj;
	}

	public void setDrsj(String drsj) {
		this.drsj = drsj;
	}

	public String getSspcs() {
		return sspcs;
	}

	public void setSspcs(String sspcs) {
		this.sspcs = sspcs;
	}

	public String getSsfj() {
		return ssfj;
	}

	public void setSsfj(String ssfj) {
		this.ssfj = ssfj;
	}

	public String getSssj() {
		return sssj;
	}

	public void setSssj(String sssj) {
		this.sssj = sssj;
	}

	public String getSpzt() {
		return spzt;
	}

	public void setSpzt(String spzt) {
		this.spzt = spzt;
	}

	public String getShpzdm() {
		return shpzdm;
	}

	public void setShpzdm(String shpzdm) {
		this.shpzdm = shpzdm;
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

	public String getMlphdzmc() {
		return mlphdzmc;
	}

	public void setMlphdzmc(String mlphdzmc) {
		this.mlphdzmc = mlphdzmc;
	}

	public String getCxyy() {
		return cxyy;
	}

	public void setCxyy(String cxyy) {
		this.cxyy = cxyy;
	}

//	public String getRowline() {
//		return rowline;
//	}
//
//	public void setRowline(String rowline) {
//		this.rowline = rowline;
//	}

	public String getOriginaljwqbh() {
		return originaljwqbh;
	}

	public void setOriginaljwqbh(String originaljwqbh) {
		this.originaljwqbh = originaljwqbh;
	}

	public String getMlphgsdm() {
		return mlphgsdm;
	}

	public void setMlphgsdm(String mlphgsdm) {
		this.mlphgsdm = mlphgsdm;
	}



}