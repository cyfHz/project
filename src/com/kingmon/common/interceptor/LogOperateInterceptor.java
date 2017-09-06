package com.kingmon.common.interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.Maps;
import com.kingmon.base.util.RequestUtil;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.common.log.service.ILogOperateService;
//import com.kingmon.project.common.log.service.ILogService;

public class LogOperateInterceptor extends HandlerInterceptorAdapter{

	private  String[] ignorUrls;//
	@Autowired
	private ILogOperateService operateService;
//	@Autowired
//	private ILogService logService;
	
	public String[] getIgnorUrls() {
		return ignorUrls;
	}

	public void setIgnorUrls(String[] ignorUrls) {
		this.ignorUrls = ignorUrls;
	}
	@Transactional
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(InterceptorUtil.isInignorUrls(request,ignorUrls)){
			return true;
		}

		if (handler instanceof HandlerMethod) {
//			Map<String,String> parameterKeyAndType=Maps.newHashMap();
			
//			@SuppressWarnings("unchecked")
			Map<String, String[]> parameterKeyAndvalue = request.getParameterMap();
            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            
//            MethodParameter[] methodParameters=handlerMethod.getMethodParameters();
//            Method methed= handlerMethod.getMethod();
//            Class<?>[] parameterTypes = methed.getParameterTypes();
//            Parameter[] parameters = methed.getParameters();
          
            AuthWidgetRule authWidgetRule = handlerMethod.getMethodAnnotation(AuthWidgetRule.class);
            SessionUser sessionUser= SecurityUtils.getSessionUser();
            //添加操作日志
            operateService.addLogOperate( sessionUser,authWidgetRule, parameterKeyAndvalue);
		}
		return true;
		
	}
	
	
}
