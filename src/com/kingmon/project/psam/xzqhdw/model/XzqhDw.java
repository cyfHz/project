package com.kingmon.project.psam.xzqhdw.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 行政区划与警务单位的关系
* @ClassName :XzqhDw     
* @Description :   
* @createTime :2015年10月22日  下午1:20:05   
* @author ：zhaohuatai   
* @version :1.0
 */
public class XzqhDw implements Serializable{
	
	/**
	 * ID
	 */
    private String id;

    /**
     * 行政区划代码-VARCHAR2(12)
     */
    private String xzqhdm;

    /**
     * 民警所属的组织机构代码-VARCHAR2(36)
     */
    private String dwid;

    private String xgdw;

    private String xgr;

    private String djr;

    private Date djsj;

    private String djdw;

    /**
     * 是否有效
     */
    private String sfyx;

    private String movesign;

    private Date xgsj;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXzqhdm() {
		return xzqhdm;
	}

	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}

	public String getDwid() {
		return dwid;
	}

	public void setDwid(String dwid) {
		this.dwid = dwid;
	}

	public String getXgdw() {
		return xgdw;
	}

	public void setXgdw(String xgdw) {
		this.xgdw = xgdw;
	}

	public String getXgr() {
		return xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public String getDjr() {
		return djr;
	}

	public void setDjr(String djr) {
		this.djr = djr;
	}

	public Date getDjsj() {
		return djsj;
	}

	public void setDjsj(Date djsj) {
		this.djsj = djsj;
	}

	public String getDjdw() {
		return djdw;
	}

	public void setDjdw(String djdw) {
		this.djdw = djdw;
	}

	public String getSfyx() {
		return sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	public Date getXgsj() {
		return xgsj;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

 
}