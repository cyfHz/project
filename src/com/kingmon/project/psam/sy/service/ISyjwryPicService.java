package com.kingmon.project.psam.sy.service;

import com.kingmon.project.psam.sy.model.SyjwryPic;


public interface ISyjwryPicService {
	
	public SyjwryPic selectpicInfoByZjhm(String zjhm);
	
	public void addPic(SyjwryPic syjwryPic);
    /**
     * 根据证件编号加载照片信息
     * @param zjhm
     * @return
     */
	public byte[] loadRkglPic(String zjhm);
}
