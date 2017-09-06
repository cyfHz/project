package com.kingmon.project.psam.sy.service;

import com.kingmon.project.psam.sy.model.SyRkglCzrkPic;

public interface ISyRkglCzrkPicService {
	
	public SyRkglCzrkPic selectpicInfoByZjhm(String zjhm);
	
	public void addPic(SyRkglCzrkPic syRkglCzrkPic);
    /**
     * 根据证件编号加载照片信息
     * @param zjhm
     * @return
     */
	public byte[] loadRkglPic(String zjhm);
}
