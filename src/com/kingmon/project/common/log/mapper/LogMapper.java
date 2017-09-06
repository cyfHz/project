package com.kingmon.project.common.log.mapper;

import java.util.List;
import java.util.Map;
import com.kingmon.base.common.KMapper;
import com.kingmon.project.common.log.model.Log;
@KMapper
public interface LogMapper {
	
	Log selectByPrimaryKey(String id);
	
    void insertSelective(Log log);
	
	List<Map<String,Object>> selectLogList(Map<String,String> params);
	
	Long selectLogListCount(Map<String,String> params);
	
    
}