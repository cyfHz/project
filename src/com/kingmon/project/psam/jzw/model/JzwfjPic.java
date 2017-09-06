package com.kingmon.project.psam.jzw.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 建筑物房间图片
* @ClassName :JzwfjPic     
* @Description :   
* @createTime :2015年11月3日  下午2:46:23   
* @author ：zhaohuatai   
* @version :1.0
 */
public class JzwfjPic implements Serializable{
	
	/**
	 * 主键
	 */
    private String picid;
	
	/**
	 *所属房间
	 */
    private String jzwfjid;
	
	/**
	 * 主键
	 */
    private String movesign;
	
	/**
	 *创建时间
	 */
    private Date creatTime;
	
	/**
	 *创建时间
	 */
    private Date updatedTime;
	
	/**
	 * 图片
	 */
    private byte[] pic;

	public String getPicid() {
		return picid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public String getJzwfjid() {
		return jzwfjid;
	}

	public void setJzwfjid(String jzwfjid) {
		this.jzwfjid = jzwfjid;
	}

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}


	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

  
}