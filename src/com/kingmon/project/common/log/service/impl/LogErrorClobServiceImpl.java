package com.kingmon.project.common.log.service.impl;

import java.util.Date;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.project.common.log.mapper.LogErrorClobMapper;
import com.kingmon.project.common.log.model.LogErrorClob;
import com.kingmon.project.common.log.service.ILogErrorClobService;

@Service
public class LogErrorClobServiceImpl extends BaseService implements ILogErrorClobService {

	@Autowired
	private LogErrorClobMapper errorClobMapper;
	@Async(value="logExecutor")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addLogErrorClob(JoinPoint joinPoint, Throwable e) {
		try {
			String msg = "";
			String exceptionCode = e.getClass().getName();// 异常类型代码
			String exceptionDetail = e.getMessage();// 异常详细信息
			msg = "异常代码:"+exceptionCode+";错误详细信息:["+exceptionDetail+"]";
			LogErrorClob clob = new LogErrorClob();
			clob.setId(UUIDUtil.uuid());
			clob.setCreatetime(new Date());
			clob.setMessage(msg);
			errorClobMapper.addLogErrorClob(clob);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public DataSet loadLogErrorClobDataSet(Map<String, String> params) {
		PaginationUtil.initPageAndSort(params);
		String user_loginname = params.get("user_loginname");
		if (user_loginname != null && !user_loginname.isEmpty()) {
			params.put("user_loginname", "%" + user_loginname + "%");
		}else{
			params.remove("user_loginname");
		}
		String start_time = params.get("start_time");
		if (start_time != null && !start_time.isEmpty()) {
			params.put("start_time",start_time);
		}else{
			params.remove("start_time");
		}
		String end_time = params.get("end_time");
		if (end_time != null && !end_time.isEmpty()) {
			params.put("end_time",  end_time);
		}else{
			params.remove("end_time");
		}
		return new DataSet(errorClobMapper.selectLogErrorClobCount(params), errorClobMapper.selectLogErrorClobList(params));

	}

	@Override
	public LogErrorClob findLogErrorClobByid(String id) {
		if(StringUtils.hasText(id)){
			return errorClobMapper.selectByPrimaryKey(id);
		}
		return null;
	}

}
