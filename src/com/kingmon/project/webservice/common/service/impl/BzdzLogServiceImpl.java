package com.kingmon.project.webservice.common.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.project.webservice.common.mapper.ServiceBzdzLogMapper;
import com.kingmon.project.webservice.common.service.BzdzLogService;
@Service
public class BzdzLogServiceImpl extends BaseService implements BzdzLogService{

	@Autowired
	private ServiceBzdzLogMapper serviceBzdzLogMapper;
	
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	public DataSet bzdzLogList(Map<String, String> params){
		PaginationUtil.initPageAndSort(params);
		String loginuser = params.get("loginuser");
		if(loginuser!=null && !loginuser.isEmpty()){
			params.put("loginuser", "%"+loginuser+"%");
		}else{
			params.remove("loginuser");
		}
		return new DataSet(serviceBzdzLogMapper.logListCount(params), serviceBzdzLogMapper.logList(params));
	}
}
