package com.kingmon.project.common.log.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kingmon.base.data.DataSet;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.common.log.model.LogLogin;

public interface ILogLoginService {
	
	public void addLogLogin(SessionUser user, HttpServletRequest request,boolean islogin,String loginLogid,String loginType,String imei);
	
	public DataSet loadLogLoginDataSet(Map<String,String> params);
	
	public LogLogin findLogLoginByid(String id);
}
