package com.kingmon.project.psam.jzw.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 建筑物单元/行
 * 
 * @author zhaohuatai
 * @date 2015年10月5日 下午7:17:12
 */
public class Jzwdy implements Serializable{
	/**
	 * 主键 uuid
	 *  必填** <br>
	 * 建筑物单元ID-VARCHAR2(36)
	 */
    private String jzwdyid;

    /**
     * 外键：建筑物结构（DZ_JZWJG）JZWJGID 
     * 
     * 建筑物结构ID-VARCHAR2(36)
     */
    private String jzwjgid;

    /**
     * 单元序号VARCHAR2(3)
     */
    private String dyxh;

    /**
     * 单元名称-VARCHAR2(100)
     */
    private String dymc;

    /**
     * 楼层数-NUMBER(3)
     */
    private Short lcs;

    /**
     * 单元门数/房间数-NUMBER(3)
     */
    private Short dyms;

    /**
     * 时间格式：yyyy-MM-dd hh:mm:ss<br>
     * 最后更新日期-DATE
     */
    private Date zhgxrq;

    /**
     * 不用操作
     * MOVESIGN
     */
    private String movesign;

	public String getJzwdyid() {
		return jzwdyid;
	}

	public void setJzwdyid(String jzwdyid) {
		this.jzwdyid = jzwdyid;
	}

	public String getJzwjgid() {
		return jzwjgid;
	}

	public void setJzwjgid(String jzwjgid) {
		this.jzwjgid = jzwjgid;
	}

	public String getDyxh() {
		return dyxh;
	}

	public void setDyxh(String dyxh) {
		this.dyxh = dyxh;
	}

	public String getDymc() {
		return dymc;
	}

	public void setDymc(String dymc) {
		this.dymc = dymc;
	}

	public Short getLcs() {
		return lcs;
	}

	public void setLcs(Short lcs) {
		this.lcs = lcs;
	}

	public Short getDyms() {
		return dyms;
	}

	public void setDyms(Short dyms) {
		this.dyms = dyms;
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


   
}