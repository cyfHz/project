package com.kingmon.project.psam.jzw.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 建筑物结构图片
* @ClassName :JzwjgPic     
* @Description :   
* @createTime :2015年11月3日  下午2:46:37   
* @author ：zhaohuatai   
* @version :1.0
 */
public class JzwjgPic implements Serializable{
	
	/**
	 * 主键
	 */
    private String picid;
	
	/**
	 * 所属建筑物结构id
	 */
    private String jzwjgid;
	
	/**
	 * 创建时间
	 */
    private Date creatTime;
	
	/**
	 * 更新时间
	 */
    private Date updatedTime;
	
	/**
	 * MOVESIGN
	 */
    private String movesign;
	
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

	public String getJzwjgid() {
		return jzwjgid;
	}

	public void setJzwjgid(String jzwjgid) {
		this.jzwjgid = jzwjgid;
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

	public String getMovesign() {
		return movesign;
	}

	public void setMovesign(String movesign) {
		this.movesign = movesign;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}



   
}