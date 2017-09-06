package com.kingmon.project.psam.sy.service;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.KJSONMSG;
import com.kingmon.project.psam.sy.model.SyFwJwry;
import com.kingmon.project.psam.sy.model.Syjwry;

import java.util.List;


public interface ISyFwJwryService {
 

	
	public List<String> loadJwryFjByJzwId(String jzwId);
	  /**
     * 根据建筑物房间Id加载境外人员信息
     * @param jzwfjid
     * @return
     */
	public Syjwry loadSyjwryAccInfo(String jzwfjid);
	/**
	 * 采集境外人员信息
	 * @param jwry
	 * @param picbytes 
	 */
	public void saveSyFwJwryAccInfo(Syjwry jwry, byte[] picbytes);
	/**
	 * 根据身份证号和房间号查询境外人员信息
	 * @param sfzh
	 * @param jzwfjid
	 * @return
	 */
	public SyFwJwry validateJwry(String sfzh, String jzwfjid);
	public KJSONMSG loadSyjwryAccInfoApp(String jzwfjid);
	
	public long loadDayJwryWorkCount(Map<String, Object> workInfo);
	
	public List<Map<String, Object>> loadFwJwryList(Map<String, Object> workInfo);
}

