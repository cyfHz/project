package com.kingmon.project.psam.jwq.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ENT_JWQ警务区(辖区)
 * 
 * @author zhaohuatai
 * @date 2015年10月6日 上午11:58:24
 */
public class Jwq implements Serializable{
	
	public Jwq() {
		super();
	}

	public Jwq(String jwqid, String jwqbh, String jwqmc) {
		super();
		this.jwqid = jwqid;
		this.jwqbh = jwqbh;
		this.jwqmc = jwqmc;
	}

	/**
	 * 
	 * 警务区ID-VARCHAR2(36)
	 */
    private String jwqid;

    /**
     * APP_ORGANIZATION表主键
     * 
     * 派出所ID-VARCHAR2(36)
     */
    private String pcsid;
    /**
     *  #new#+
     * 不是数据库字段
     * 派出所
     */
    private String pcsmc;   
    /**
     * #new#+
     * 不是数据库字段
     * 上级行政区域_地址名称 --20151101添加
   	 */
    private String sjxzqy_mc;

	/**
     * dz_xzqh 表主键
     * 上级行政区域ID -VARCHAR2(36)
     */
    private String sjxzqyid;

    /**
     * 警务区编号 -VARCHAR2(50)
     */
    private String jwqbh;

    /**
     * 警务区名称 VARCHAR2(100)
     */
    private String jwqmc;

    /**
     * 警务区简介 -VARCHAR2(1000)
     */
    private String jwqjj;

    /**
     * 警务区面积 -NUMBER(10,2)
     */
    private BigDecimal jwqmj;

    /**
     * 居委会数量-NUMBER(10,2)
     */
    private BigDecimal jwhsl;

    /**
     * 农村管区数量-NUMBER(10,2)
     */
    private BigDecimal ncgqsl;

    /**
     * 值： dict_code:0   mc:农村
     *    dict_code:1   mc:城市 
     * 警务区性质 -CHAR(1)
     */
    private String jwqxz;

    /**
     * 是否有效-CHAR(1)
     * 1:有效
     * 0：无效
     */
    private String sfyx;

    /**
     * 启用日期 -VARCHAR2(10)
     */
    private String qyrq;

    /**
     * 失效日期  -VARCHAR2(20)
     */
    private String sxrq;

    /**
     * 修改时间 -VARCHAR2(20)
     */
    private String xgsj;

    /**
     * 备注 -VARCHAR2(200)
     */
    private String bz;

    /**
     * 传输标志 -CHAR(1)
     */
    private String movesign;

    /**
     * 边界坐标值-CLOB
     */
    private String bjzbz;
    

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

    /**
   	 * #新增字段# 归属代码
   	 *   -VARCHAR2(36)
   	 */
   	private String jwqgsdm;
   	
    public String getSjxzqy_mc() {
		return sjxzqy_mc;
	}

	public void setSjxzqy_mc(String sjxzqy_mc) {
		this.sjxzqy_mc = sjxzqy_mc;
	}
	public String getJwqid() {
		return jwqid;
	}

	public void setJwqid(String jwqid) {
		this.jwqid = jwqid;
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

	public String getJwqmc() {
		return jwqmc;
	}

	public void setJwqmc(String jwqmc) {
		this.jwqmc = jwqmc;
	}

	public String getJwqjj() {
		return jwqjj;
	}

	public void setJwqjj(String jwqjj) {
		this.jwqjj = jwqjj;
	}

	public BigDecimal getJwqmj() {
		return jwqmj;
	}

	public void setJwqmj(BigDecimal jwqmj) {
		this.jwqmj = jwqmj;
	}

	public BigDecimal getJwhsl() {
		return jwhsl;
	}

	public void setJwhsl(BigDecimal jwhsl) {
		this.jwhsl = jwhsl;
	}

	public BigDecimal getNcgqsl() {
		return ncgqsl;
	}

	public void setNcgqsl(BigDecimal ncgqsl) {
		this.ncgqsl = ncgqsl;
	}

	public String getJwqxz() {
		return jwqxz;
	}

	public void setJwqxz(String jwqxz) {
		this.jwqxz = jwqxz;
	}

	public String getSfyx() {
		return sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public String getQyrq() {
		return qyrq;
	}

	public void setQyrq(String qyrq) {
		this.qyrq = qyrq;
	}

	public String getSxrq() {
		return sxrq;
	}

	public void setSxrq(String sxrq) {
		this.sxrq = sxrq;
	}

	public String getXgsj() {
		return xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	public String getBjzbz() {
		return bjzbz;
	}

	public void setBjzbz(String bjzbz) {
		this.bjzbz = bjzbz;
	}

	public String getPcsmc() {
		return pcsmc;
	}

	public void setPcsmc(String pcsmc) {
		this.pcsmc = pcsmc;
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

	public String getJwqgsdm() {
		return jwqgsdm;
	}

	public void setJwqgsdm(String jwqgsdm) {
		this.jwqgsdm = jwqgsdm;
	}
    
    
 //#?   
//    登记人	DJR	VARCHAR2(36)
//    登记单位	DJDW	VARCHAR2(36)
//    登记时间	DJSJ	DATE	
//    修改人	XGR	VARCHAR2(36)
//    修改单位	XGDW	VARCHAR2(36)
//    修改时间	XGSJ	DATE

	
   
}