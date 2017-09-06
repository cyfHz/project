package com.kingmon.project.psam.appException.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.DBTimeUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.project.psam.appException.mapper.AppExceptionLogMapper;
import com.kingmon.project.psam.appException.service.IAppExceptionLogService;

@Service
public class AppExceptionLogServiceImpl extends BaseService implements IAppExceptionLogService{

	@Autowired
	private AppExceptionLogMapper appExceptionLogMapper;
	
	public void insertAppException(Map<String,Object> params){
		if(params!=null&&!(params.isEmpty())){
			String id=UUIDUtil.uuid();//id
			params.put("id", id);
			params.put("createtime", DBTimeUtil.getDBTime());
			params.put("message",""+params.get("message"));
			appExceptionLogMapper.insertAppException(params);
		}
	}
}
