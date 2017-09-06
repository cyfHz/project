package com.kingmon.project.common.log.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.common.log.model.LogError;
import com.kingmon.project.common.log.model.LogErrorClob;

@KMapper
public interface LogErrorClobMapper {
	//添加大型错误日志
    void addLogErrorClob(LogErrorClob logErrorClob);
	//获取所有大型错误日志信息
	List<Map<String,Object>> selectLogErrorClobList(Map<String,String> params);
	//获取大型错误日志数量
	Long selectLogErrorClobCount(Map<String,String> params);
	
	LogErrorClob selectByPrimaryKey(String id);
}