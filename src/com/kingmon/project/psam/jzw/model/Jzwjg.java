package com.kingmon.project.psam.jzw.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 建筑物结构
 * @author zhaohuatai
 * @date 2015年10月5日 下午6:37:55
 */
public class Jzwjg implements Serializable{
	public Jzwjg() {
		super();
	}
	public Jzwjg(String jzwjgid) {
		super();
		this.jzwjgid = jzwjgid;
	}
	public Jzwjg(String jzwjgid, String isbuild) {
		super();
		this.jzwjgid = jzwjgid;
		this.isbuild = isbuild;
	}
	public Jzwjg(
			 String jzwjgid, String dzbm, String jzwid,
			 Short dys, Short lcs,  Short mdyms,
			 Short dxcs, Short dxmcms,
			 String sflcxt, String sfmsxt,
			 
			Date scsj,Date zhgxrq, 
			String isvalid, String isbuild) {
		super();
		this.jzwjgid = jzwjgid;
		this.dzbm = dzbm;
		this.jzwid = jzwid;
		this.lcs = lcs;
		this.dys = dys;
		this.mdyms = mdyms;
		this.scsj = scsj;
		this.sflcxt = sflcxt;
		this.sfmsxt = sfmsxt;
		this.zhgxrq = zhgxrq;
		this.dxcs = dxcs;
		this.dxmcms = dxmcms;
		this.isvalid = isvalid;
		this.isbuild = isbuild;
	}


	/**
	 * 主键<br>
	 * 与DZ_JZWJBXX共享主键<br>
	 * 
	 * 建筑物结构ID-VARCHAR2(36)
	 */
    private String jzwjgid;
    
    /**
     * 此处与主键相同即可<br>
     * 
	 * 地址编码-VARCHAR2(36)
	 */
    private String dzbm;
    
    /**
     * 与主键相同即可<br>
	 * 建筑物ID-VARCHAR2(36)
	 */
    private String jzwid;
    
    /**
     * 
	 * 楼层数-NUMBER(3)
	 */
    private Short lcs;
    
    /**
	 * 单元数/行数-NUMBER(3)
	 */
    private Short dys;
    
    /**
	 * 每层门数/房间数-NUMBER(3)
	 */
    private Short mdyms;
    
    /**
	 * 生成时间-DATE
	 */
    private Date scsj;
    
    /**
     * 1:相同，0：不同
	 * 是否单元楼层相同-CHAR(1)
	 */
    private String sflcxt;
    
    /**
     * 1:相同，0：不同
	 * 是否门数相同-CHAR(1)
	 */
    private String sfmsxt;
    
    /**
	 * 最后更新日期-DATE
	 */
    private Date zhgxrq;
    
    /**
     * #?
	 * MOVESIGN-CHAR(1)
	 */
    private String movesign;
    
    /**
	 * 地下层数-NUMBER(3)
	 */
    private Short dxcs;
    
    /**
	 * 地下每层门数/房间数-NUMBER(3)
	 */
    private Short dxmcms;
    
    /**
	 * 地下楼层类型:0：无地下室 1：规则分单元地下室 2：不规则地下室
	 */
    private String dxType;
    
    /**
     * #new#
     * 建筑物结构是否通过校验--CHAR(1)<br>
     *  未进行过校验:null，检验通过：1 校验不通过：2
     */
    private String isvalid;
    
    /**
     * #new# 
     * 建筑物结构所有房间是否已经生成坐标--CHAR(1)<br>
     *  未生成坐标：null   生成：1
     */
    private String isbuild;

	public String getJzwjgid() {
		return jzwjgid;
	}

	public void setJzwjgid(String jzwjgid) {
		this.jzwjgid = jzwjgid;
	}

	public String getDzbm() {
		return dzbm;
	}

	public void setDzbm(String dzbm) {
		this.dzbm = dzbm;
	}

	public String getJzwid() {
		return jzwid;
	}

	public void setJzwid(String jzwid) {
		this.jzwid = jzwid;
	}

	public Short getLcs() {
		return lcs;
	}

	public void setLcs(Short lcs) {
		this.lcs = lcs;
	}

	public Short getDys() {
		return dys;
	}

	public void setDys(Short dys) {
		this.dys = dys;
	}

	public Short getMdyms() {
		return mdyms;
	}

	public void setMdyms(Short mdyms) {
		this.mdyms = mdyms;
	}

	public Date getScsj() {
		return scsj;
	}

	public void setScsj(Date scsj) {
		this.scsj = scsj;
	}

	public String getSflcxt() {
		return sflcxt;
	}

	public void setSflcxt(String sflcxt) {
		this.sflcxt = sflcxt;
	}

	public Date getZhgxrq() {
		return zhgxrq;
	}

	public void setZhgxrq(Date zhgxrq) {
		this.zhgxrq = zhgxrq;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	public Short getDxcs() {
		return dxcs;
	}

	public void setDxcs(Short dxcs) {
		this.dxcs = dxcs;
	}

	public Short getDxmcms() {
		return dxmcms;
	}

	public void setDxmcms(Short dxmcms) {
		this.dxmcms = dxmcms;
	}

	/**
	 * @return the sfmsxt
	 */
	public String getSfmsxt() {
		return sfmsxt;
	}

	/**
	 * @param sfmsxt the sfmsxt to set
	 */
	public void setSfmsxt(String sfmsxt) {
		this.sfmsxt = sfmsxt;
	}

	/**
	 * @return the isvalid
	 */
	public String getIsvalid() {
		return isvalid;
	}

	/**
	 * @param isvalid the isvalid to set
	 */
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	/**
	 * @return the isbuild
	 */
	public String getIsbuild() {
		return isbuild;
	}

	/**
	 * @param isbuild the isbuild to set
	 */
	public void setIsbuild(String isbuild) {
		this.isbuild = isbuild;
	}

	public String getDxType() {
		return dxType;
	}

	public void setDxType(String dxType) {
		this.dxType = dxType;
	}

}