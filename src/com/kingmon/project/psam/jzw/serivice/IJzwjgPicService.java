package com.kingmon.project.psam.jzw.serivice;

import java.util.List;

import com.kingmon.project.psam.jzw.model.JzwjgPic;

/**
 * 建筑物结构图片 service
* @ClassName :IJzwjgPicService     
* @Description :   
* @createTime :2015年11月3日  下午3:17:06   
* @author ：zhaohuatai   
* @version :1.0
 */
public interface IJzwjgPicService {

	List<String> loadjgPicIdsByjgId(String jzwjgid);

	byte[] loadJzwjgPic(String jzwjgPicId);
	
	public void deleteJzwjgPic(String jzwjgPicId);
	public String addJzwJgPic(JzwjgPic jzwfjPic,String jzwjgid);
}
