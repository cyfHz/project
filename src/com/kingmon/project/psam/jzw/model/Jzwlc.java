package com.kingmon.project.psam.jzw.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 建筑物楼层
 * @author zhaohuatai
 * @date 2015年10月5日 下午6:37:55
 */
public class Jzwlc implements Serializable{
	
	/**
	 * 主键 uuid
	 *  必填** <br>
	 * 建筑物楼层ID-VARCHAR2(36)
	 */
    private String jzwlcid;
    
    /**
     * 外键：建筑物结构（DZ_JZWJG）JZWJGID <br>
	 * 建筑物结构ID-VARCHAR2(36)
	 */
    private String jzwjgid;
    
    /**
     * 外键：建筑物结构（DZ_JZWDY）JZWDYID<br>
     * 
	 * 实际此处是建筑物单元ID-VARCHAR2(36)
	 */
    private String jzwdyid;
    
    
    /**
	 * 楼层序号-VARCHAR2(3)
	 */
    private String lcxh;
    
    /**
	 * 楼层名称-VARCHAR2(100)
	 */
    private String lcmc;
    
    /**
	 * 最后更新日期-DATE
	 */
    private Date zhgxrq;
    
    /**
     * 不用操作
	 * MOVESIGN-CHAR(1)
	 */
    private String movesign;

	public String getJzwlcid() {
		return jzwlcid;
	}

	public void setJzwlcid(String jzwlcid) {
		this.jzwlcid = jzwlcid;
	}

	public String getJzwjgid() {
		return jzwjgid;
	}

	public void setJzwjgid(String jzwjgid) {
		this.jzwjgid = jzwjgid;
	}

	public String getJzwdyid() {
		return jzwdyid;
	}

	public void setJzwdyid(String jzwdyid) {
		this.jzwdyid = jzwdyid;
	}

	public String getLcxh() {
		return lcxh;
	}

	public void setLcxh(String lcxh) {
		this.lcxh = lcxh;
	}

	public String getLcmc() {
		return lcmc;
	}

	public void setLcmc(String lcmc) {
		this.lcmc = lcmc;
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