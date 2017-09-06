package com.kingmon.project.webservice.fwxx;
/**
 * 房屋查询服务条件类
 * @ClassName: FwxxQuery 
 * <p>Title: 房屋查询服务条件类</p>
 * @version: v1.0
 * @author: 蒋金敏 
 * @date: 2015年12月15日 下午1:17:07
 */
public class FwxxQuery {

	private String jlx;    //街路巷编码
	private String xzqh;   //行政区划主键
	private String jzwid;  //建筑物ID
	private String jzwFjid;//建筑物房间id
	private String jzwdyid;//建筑物单元id
	private String keyword;//关键字检索
	private String fjxh;   //房间序号
	/**
	 * @return the jlx
	 */
	public String getJlx() {
		return jlx;
	}
	/**
	 * @param jlx the jlx to set
	 */
	public void setJlx(String jlx) {
		this.jlx = jlx;
	}
	/**
	 * @return the xzqh
	 */
	public String getXzqh() {
		return xzqh;
	}
	/**
	 * @param xzqh the xzqh to set
	 */
	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}
	/**
	 * @return the jzwid
	 */
	public String getJzwid() {
		return jzwid;
	}
	/**
	 * @param jzwid the jzwid to set
	 */
	public void setJzwid(String jzwid) {
		this.jzwid = jzwid;
	}
	/**
	 * @return the jzwFjid
	 */
	public String getJzwFjid() {
		return jzwFjid;
	}
	/**
	 * @param jzwFjid the jzwFjid to set
	 */
	public void setJzwFjid(String jzwFjid) {
		this.jzwFjid = jzwFjid;
	}
	/**
	 * @return the jzwdyid
	 */
	public String getJzwdyid() {
		return jzwdyid;
	}
	/**
	 * @param jzwdyid the jzwdyid to set
	 */
	public void setJzwdyid(String jzwdyid) {
		this.jzwdyid = jzwdyid;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @return the fjxh
	 */
	public String getFjxh() {
		return fjxh;
	}
	/**
	 * @param fjxh the fjxh to set
	 */
	public void setFjxh(String fjxh) {
		this.fjxh = fjxh;
	}

}
