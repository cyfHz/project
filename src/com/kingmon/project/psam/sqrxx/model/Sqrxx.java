package com.kingmon.project.psam.sqrxx.model;

import java.io.Serializable;
/**
 * 
 * 地址_申请人信息
 * @author zhaohuatai
 * @date 2015年10月5日 下午6:37:55
 */
public class Sqrxx implements Serializable{
	/**
	 * 申请人编号-VARCHAR2(36)
	 */
    private String sqrid;
    /**
	 * 申请人姓名-VARCHAR2(50)
	 */
    private String sqrxm;
    /**
	 * 申请人公民身份证号码-VARCHAR2(18)
	 */
    private String sqrgmsfzhm;
    /**
	 * 申请人联系电话-VARCHAR2(50)
	 */
    private String sqrlxdh;
    /**
	 * 申请单位名称-VARCHAR2(100)
	 */
    private String sqdwmc;
    /**
	 * 申请单位联系电话-VARCHAR2(50)
	 */
    private String sqdwlxdh;
	public String getSqrid() {
		return sqrid;
	}
	public void setSqrid(String sqrid) {
		this.sqrid = sqrid;
	}
	public String getSqrxm() {
		return sqrxm;
	}
	public void setSqrxm(String sqrxm) {
		this.sqrxm = sqrxm;
	}
	public String getSqrgmsfzhm() {
		return sqrgmsfzhm;
	}
	public void setSqrgmsfzhm(String sqrgmsfzhm) {
		this.sqrgmsfzhm = sqrgmsfzhm;
	}
	public String getSqrlxdh() {
		return sqrlxdh;
	}
	public void setSqrlxdh(String sqrlxdh) {
		this.sqrlxdh = sqrlxdh;
	}
	public String getSqdwmc() {
		return sqdwmc;
	}
	public void setSqdwmc(String sqdwmc) {
		this.sqdwmc = sqdwmc;
	}
	public String getSqdwlxdh() {
		return sqdwlxdh;
	}
	public void setSqdwlxdh(String sqdwlxdh) {
		this.sqdwlxdh = sqdwlxdh;
	}
    
	
   
}