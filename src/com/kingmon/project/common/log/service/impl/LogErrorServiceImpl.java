package com.kingmon.project.common.log.service.impl;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.project.common.log.mapper.LogErrorMapper;
import com.kingmon.project.common.log.model.LogError;
import com.kingmon.project.common.log.service.ILogErrorService;

@Service
public class LogErrorServiceImpl extends BaseService implements ILogErrorService {

	@Autowired
	private LogErrorMapper errorMapper;
	@Async(value="logExecutor")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addLogError(JoinPoint joinPoint, Throwable e) {
		try {
			String params = "";
			 if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                 for (int i = 0; i < joinPoint.getArgs().length; i++) {
                         params += joinPoint.getArgs()[i].toString() + ";";
                 }
			 }
			 if(ServiceLogicalException.class.getSimpleName().equals(e.getClass().getSimpleName())){
				 return;
			 }
			String exceptionCode = joinPoint.getTarget().getClass().getName();// 异常类型代码
			String exceptionDetail = e.getMessage();// 异常详细信息
			String method = joinPoint.getTarget().getClass().getName() + "."+ joinPoint.getSignature().getName() + "()";// 异常方法
			LogError error = new LogError();
			error.setId(UUIDUtil.uuid());
			error.setClasses(exceptionCode);// 错误类名
			error.setMothod(method);// 错误方法
			error.setCreatetime(ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS));
			//--------------------------------?????
			error.setLoglevel("ERROR");// 错误等级
			error.setLx("P");// 数据来源类型(P后台,终端B)
			error.setMsg("错误详细信息:["+exceptionDetail+"];"+"参数:"+params);// 错误信息
			error.setSbid(null);// 设备id
	
			errorMapper.addLogError(error);
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
	}

	@Override
	public DataSet loadLogErrorDataSet(Map<String, String> params) {
		PaginationUtil.initPageAndSort(params);
		String sbid = params.get("sbid");
		if (sbid != null && !sbid.isEmpty()) {
			params.put("sbid", "%" + sbid + "%");
		}else{
			params.remove("sbid");
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
		return new DataSet(errorMapper.selectLogErrorCount(params), errorMapper.selectLogErrorList(params));

	}

	@Override
	public LogError findLogErrorByid(String id) {
		if(StringUtils.hasText(id)){
			return errorMapper.selectByPrimaryKey(id);
		}
		return null;
	}

}
