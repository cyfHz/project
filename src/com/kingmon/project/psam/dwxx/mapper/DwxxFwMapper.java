package com.kingmon.project.psam.dwxx.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.dwxx.model.DwxxFw;

@KMapper
public interface DwxxFwMapper {

	int insertSelective(DwxxFw record);

	DwxxFw selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(DwxxFw record);
	
	DwxxFw selectDwxxFwForDwId(String id);
	
	/**
	 * 从房屋 移除单位
	 * @param params
	 */
	public void revokeFwDwxx(Map<String,Object> params);
	
	public List<String> selectDwxxFjByJzwId(String jzwId); 
	
	public List<String> selectDwxxFjByJzwIds(Map<String,String> params); 
}
