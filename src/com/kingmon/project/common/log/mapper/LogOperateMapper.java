package com.kingmon.project.common.log.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.common.log.model.LogOperate;
@KMapper
public interface LogOperateMapper {
	//添加操作日志
    void addLogOperate(LogOperate logOperate);
	//获取操作日志信息
	List<Map<String,Object>> selectLogOperateList(Map<String,String> params);
	//获取操作日志数量
	Long selectLogOperateCount(Map<String,String> params);
	
	LogOperate selectByPrimaryKey(String id);
}