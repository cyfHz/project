package com.kingmon.project.common.log.service;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import com.kingmon.base.data.DataSet;
import com.kingmon.project.common.log.model.LogErrorClob;

public interface ILogErrorClobService {
	
	public void addLogErrorClob(JoinPoint joinPoint, Throwable e);

	public DataSet loadLogErrorClobDataSet(Map<String, String> params);
	
	public LogErrorClob findLogErrorClobByid(String id);
}
