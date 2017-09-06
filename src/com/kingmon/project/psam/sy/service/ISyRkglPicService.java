package com.kingmon.project.psam.sy.service;

import com.kingmon.project.psam.sy.model.SyRkglPic;


public interface ISyRkglPicService {
	
	public void addPic(SyRkglPic syRkglPic);
    /**
     * 根据证件编号加载照片信息
     * @param zjhm
     * @return
     */
	public byte[] loadRkglPic(String zjhm);
}
