package com.kingmon.project.common.log.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.common.log.model.LogLogin;
@KMapper
public interface LogLoginMapper {
    
	//添加登陆日志
    void addLogLogin(LogLogin logLogin);
	//获取登陆日志信息
	List<Map<String,Object>> selectLogLoginList(Map<String,String> params);
	//获取日志数量
	Long selectLogLoginCount(Map<String,String> params);
	
	LogLogin selectByPrimaryKey(String id);
	
	int updateLogoutTime(LogLogin logLogin);
	
}