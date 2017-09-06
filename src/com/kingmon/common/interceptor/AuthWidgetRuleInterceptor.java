package com.kingmon.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.util.RequestUtil;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.KWebUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.authUtil.WidgetRuleUtil;
import com.kingmon.common.session.SessionUser;
@Deprecated
public class AuthWidgetRuleInterceptor extends HandlerInterceptorAdapter{
	
	private  String[] ignorUrls;//
	 
	public String[] getIgnorUrls() {
		return ignorUrls;
	}

	public void setIgnorUrls(String[] ignorUrls) {
		this.ignorUrls = ignorUrls;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
	}

//	@Override
//	public void postHandle(HttpServletRequest request,	HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
//		try{
//			RequestThreadLocal.clearCurrentReqestInfo();
//		}catch(Exception e){}
//	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(isInignorUrls(request)){
			return true;
		}
		SessionUser user= SecurityUtils.getSessionUser();
		if (handler instanceof HandlerMethod) {
			 HandlerMethod handlerMethod = (HandlerMethod) handler;
            String defaultCode=handlerMethod.getBean().getClass().getName()+"."+handlerMethod.getMethod().getName();
           
	            AuthWidgetRule authWidgetRule = handlerMethod.getMethodAnnotation(AuthWidgetRule.class);
	            if(authWidgetRule==null){
	            	return true;
	            }
	            String widgetRuleCode=authWidgetRule.value();
	           if(!WidgetRuleUtil.isUserHaveWidgetRuleCode(widgetRuleCode,user.getUserId())){
	        	   if(RequestUtil.isAcceptJson(request)){
		        		response.setContentType("text/json"); 
		        		response.setCharacterEncoding("UTF-8");
		        		response.getWriter().write(JSON.toJSONString(new KJSONMSG(KConstants.SC_401, "没有访问权限",new Object())));
		    		}else{
		    			 response.sendRedirect("/noAuth.jsp");
		    		}
	        	   return false;
	           }
	           return true;
	     } else {
	          return super.preHandle(request, response, handler);
	   }
		
	}
	
	private boolean isInignorUrls(HttpServletRequest request){
		if(ignorUrls!=null&&ignorUrls.length>0){
			for(String url : ignorUrls) {    
				if(KWebUtil.pathsMatch(url, request)){
					 return true;
				}
			}
		}
		return false;
	}
}
