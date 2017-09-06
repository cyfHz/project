package com.kingmon.project.common.log.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.common.log.model.LogError;
@KMapper
public interface LogErrorMapper {
	
	LogError selectByPrimaryKey(String id);
	//添加错误日志
    void addLogError(LogError log);
	//获取所有错误日志信息
	List<Map<String,Object>> selectLogErrorList(Map<String,String> params);
	//获取错误日志数量
	Long selectLogErrorCount(Map<String,String> params);
}