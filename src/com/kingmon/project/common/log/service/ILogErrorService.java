package com.kingmon.project.common.log.service;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import com.kingmon.base.data.DataSet;
import com.kingmon.project.common.log.model.LogError;

public interface ILogErrorService {
	
	public void addLogError(JoinPoint joinPoint,Throwable e);

	public DataSet loadLogErrorDataSet(Map<String, String> params);
	
	public LogError findLogErrorByid(String id);
}
