package com.kingmon.project.psam.appException.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;

@KMapper
public interface AppExceptionLogMapper {

	public void insertAppException(Map<String, Object> params);
	
	public List<Map<String, Object>> selectAppException(Map<String, Object> params);
}
