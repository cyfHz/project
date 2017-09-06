package com.kingmon.project.psam.sy.service;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.KJSONMSG;
import com.kingmon.project.psam.sy.model.SyFwLdrk;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;


public interface ISyFwLdrkService {
   
    /**
     * 根据房间Id加载流动人口信息
     * @param jzwfjid
     * @return
     */
	public SyRkglLdrkdjb loadLdrkAccInfo(String jzwfjid);
	
	public List<String> loadLdrkFjByJzwId(String jzwId)	;
	/**
	 * 保存流动人口信息
	 * @param syldrk
	 * @param picbytes 
	 */
	public void saveSyFwLdrkAccInfo(SyRkglLdrkdjb syldrk, byte[] picbytes);
    /**
     * 根据证件号码和房间Id查询流动人口信息
     * @param sfzh
     * @param jzwfjid
     * @return
     */
	public SyFwLdrk validateLdrk(String sfzh, String jzwfjid);
	public KJSONMSG loadLdrkAccInfoApp(String jzwfjid);
    /**
     * 统计采集流动人口的数量
     * @param workInfo
     * @return
     */
	public long loadDayLdrkWorkCount(Map<String, Object> workInfo);
   /**
    * 加载流动人口详细信息
    * @param workInfo
    * @return
    */
	public List<Map<String, Object>> loadFwLdrkList(Map<String, Object> workInfo);
}


