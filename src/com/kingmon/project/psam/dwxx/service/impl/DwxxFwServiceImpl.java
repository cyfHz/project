package com.kingmon.project.psam.dwxx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.service.BaseService;
import com.kingmon.project.psam.dwxx.mapper.DwxxFwMapper;
import com.kingmon.project.psam.dwxx.service.IDwxxFwService;

@Service
public class DwxxFwServiceImpl extends BaseService implements IDwxxFwService{

	@Autowired
	private DwxxFwMapper dwxxFwMapper;
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public List<String> selectDwxxFjByJzwId(String jzwId){
		if(StringUtils.hasText(jzwId)){
			return dwxxFwMapper.selectDwxxFjByJzwId(jzwId);
		}
		return null; 
	}
	
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public List<String> selectDwxxFjByJzwIds(Map<String,String> params){
		String jzwId=params.get("jzwId");
		String table_value=params.get("table_value");
		if(table_value!=null && !table_value.isEmpty()){
			params.put("table_value", "%"+table_value+"%");
		}
		if(StringUtils.hasText(jzwId)){
			return dwxxFwMapper.selectDwxxFjByJzwIds(params);
		}
		return null; 
	}
}
