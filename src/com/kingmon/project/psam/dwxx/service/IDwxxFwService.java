package com.kingmon.project.psam.dwxx.service;

import java.util.List;
import java.util.Map;

public interface IDwxxFwService {

	public List<String> selectDwxxFjByJzwId(String jzwId); 
	
	public List<String> selectDwxxFjByJzwIds(Map<String,String> params); 
}
