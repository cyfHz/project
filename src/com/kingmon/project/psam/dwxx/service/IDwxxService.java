package com.kingmon.project.psam.dwxx.service;

import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.psam.dwxx.model.Dwxx;

public interface IDwxxService {

	/**
	 * 加载单位基本信息
	 * 
	 * @param jzwfjid
	 * @return
	 */
	public Dwxx loadFwDwxxAccInfo(String fjid);

	public void saveDwjbxxAccInfo(Dwxx dwxx);

//	public List<Dwxx> loadJzwFwDwxxForFjid(String fjid);
//
//	public List<Map<String, Object>> loadJzwFwLsDwxxForFjid(String fjid);
	
	public DataSet loadJzwFwDwxxForFjid(Map<String,String> params);
	
	public DataSet loadJzwFwLsDwxxForFjid(Map<String,String> params);
	
	public void revokeFwDwxx(Map<String,Object> params);
	
	public Dwxx selectDwXxById(String dwid);
}
