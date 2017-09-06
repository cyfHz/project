package com.kingmon.project.psam.jwq.model;

import java.io.Serializable;

/**
 * 警务区域警员关系
* @ClassName :JwqyJygx     
* @Description :   
* @createTime :2015年10月15日  下午2:43:57   
* @author ：zhaohuatai   
* @version :1.0
 */
public class JwqyJygx implements Serializable{
	
	/**
	 * VARCHAR2(36)
	 */
    private String id;
	
	/**
	 * VARCHAR2(36)
	 */
    private String jwqid;
	
	/**
	 * VARCHAR2(36)
	 */
    private String user_id;
	
	/**
	 * VARCHAR2(36)
	 */
    private String movesign;
	
	/**
	 * 是否有效CHAR(1)
	 */
    private String sfyx;
	
	/**
	 * 启用日期VARCHAR2(10)
	 */
    private String qyrq;
	
	/**
	 * 失效日期VARCHAR2(10)
	 */
    private String sxrq;
	
	/**
	 * 修改时间VARCHAR2(10)
	 */
    private String xgsj;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJwqid() {
		return jwqid;
	}

	public void setJwqid(String jwqid) {
		this.jwqid = jwqid;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
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

   
}