package com.kingmon.project.psam.jzw.serivice;

import java.util.List;

import com.kingmon.project.psam.jzw.model.JzwfjPic;


/**
 * 建筑物房间图片 service
* @ClassName :IJzwfjPicService     
* @Description :   
* @createTime :2015年11月3日  下午3:17:25   
* @author ：zhaohuatai   
* @version :1.0
 */
public interface IJzwfjPicService {
	
	public  byte[] loadJzwfjPic(String jzwfjPicId);
	
	public List<String> loadFjPicIdsByfjId(String jzwfjid);

	public void deletefwpic(String fjpicid);
	
	public String addJzwFjPic(JzwfjPic jzwfjPic,String jzwfjid);
 }
