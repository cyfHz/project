package com.kingmon.project.common.log.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.common.log.mapper.LogMapper;
import com.kingmon.project.common.log.model.Log;
import com.kingmon.project.common.log.service.ILogService;

@Service
public class LogServiceImpl extends BaseService implements ILogService {

	@Autowired
	private LogMapper logMapper;
	
	//添加应用系统日志
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addLog(AuthWidgetRule authWidgetRule,
			//HandlerMethod handlerMethod,
			HttpServletRequest request,
			Map<String,String> parameterKeyAndType,
			Map<String, String[]> parameterKeyAndvalue) {
		try {
			 SessionUser user=SecurityUtils.getSessionUser();
			 Log log = new Log();
			 Date date= new Date();
			 
	         log.setId(UUIDUtil.uuid());
	         log.setOperate_url(request.getRequestURL().toString());
	         log.setOpratetime(date);
	         log.setOpratetime1(ZDateUtil.getdefaultFormatDateTime(date));
	         log.setOrgna_id(user.getOrganizationId());
	         log.setUser_id(user.getUserId());
	         log.setUser_loginname(user.getLoginname());
	         log.setUser_name(user.getName());
	         
	         //----------------------------------------??##
	         log.setArea_code(null); //领域ID
	         log.setLog_type(null);
	         log.setLog_object(null);
	         log.setLog_content(null);//需要确定记录什么内容
	         logMapper.insertSelective(log);
	         
		} catch (Exception e) {
			//e.printStackTrace();
		}
		

	}
	//获取应用系统日志信息
	@Override
	public DataSet loadLogDataSet(Map<String,String> params) {
		
		PaginationUtil.initPageAndSort(params);
		//前台传递的Map参数处理
		String user_loginname = (String) params.get("user_loginname");
		if(user_loginname!=null&&!user_loginname.isEmpty()){
			params.put("user_loginname", "%"+user_loginname+"%");
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
		return new DataSet(logMapper.selectLogListCount(params),logMapper.selectLogList(params));
	}
	@Override
	public Log findLogByid(String id) {
		if(StringUtils.hasText(id)){
			return logMapper.selectByPrimaryKey(id);
		}
		return null;
	}
}
