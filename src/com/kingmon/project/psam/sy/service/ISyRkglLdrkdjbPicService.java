package com.kingmon.project.psam.sy.service;

import com.kingmon.project.psam.sy.model.SyRkglLdrkdjbPic;

public interface ISyRkglLdrkdjbPicService {
	
	public SyRkglLdrkdjbPic selectpicInfoByZjhm(String zjhm);
	
	public void addPic(SyRkglLdrkdjbPic syRkglLdrkPic);
    /**
     * 根据证件编号加载照片信息
     * @param zjhm
     * @return
     */
	public byte[] loadRkglPic(String zjhm);
}
