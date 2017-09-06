package com.kingmon.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.alibaba.fastjson.JSON;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.util.RequestUtil;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.KWebUtil;
import com.kingmon.common.authUtil.ResourcePermUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.authUtil.WidgetRuleUtil;
import com.kingmon.common.session.SessionUser;

public class WidgetRuleInterceptor extends HandlerInterceptorAdapter{
	
	private  String[] ignorUrls;//
	 
	public String[] getIgnorUrls() {
		return ignorUrls;
	}

	public void setIgnorUrls(String[] ignorUrls) {
		this.ignorUrls = ignorUrls;
	}
	@Value("${auth.ignor.user}")
	private String authIgnorUser;
	
//	@Override
//	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
//	}
//	@Override
//	public void postHandle(HttpServletRequest request,	HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
//	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(InterceptorUtil.isInignorUrls(request,ignorUrls)){
			return true;
		}
		String requestURI = KWebUtil.getPathWithinApplication(request);
		SessionUser user= SecurityUtils.getSessionUser();
		if(StringUtils.hasText(KConstants.authIgnorUser)&&KConstants.authIgnorUser.equals(user.getLoginname())){
			//System.out.println(PropConst.getAuthIgnorUser());
			return true;
		}
		if (handler instanceof HandlerMethod) {
	            HandlerMethod handlerMethod = (HandlerMethod) handler;
	            AuthWidgetRule authWidgetRule = handlerMethod.getMethodAnnotation(AuthWidgetRule.class);
	            if(authWidgetRule==null){
	            	return true;
	            }
	            //WIDGET_PUBLIC 或者WIDGET_IGNOR 公共的共，跳过拦截
	            String widgetRuleCode=authWidgetRule.value();
	            String desc=authWidgetRule.desc();
	            if(KConstants.WIDGET_IGNOR.equals(widgetRuleCode)||KConstants.WIDGET_PUBLIC.equals(widgetRuleCode)){
	            	return true;
	            }
	            
	            if(!widgetRuleCode.contains(".")){
	            	//此时是resource
	            	if(!ResourcePermUtil.isUserHaveResource(widgetRuleCode,user.getUserId())){
			        	   if(RequestUtil.isAcceptJson(request)){
				        		response.setContentType("text/json"); 
				        		response.setCharacterEncoding("UTF-8");
				        		response.getWriter().write(JSON.toJSONString(new KJSONMSG(KConstants.SC_402, "当前用户没有访问权限: [ "+desc+" ]",new Object())));
				    		}else{
				    			 response.sendRedirect(request.getContextPath()+"/static/pages/401.html");
				    		}
			        	   return false;
			           }
	            	return true;
	            }else{
	            	//此时是WidgetRule
	            	if(!WidgetRuleUtil.isUserHaveWidgetRuleCode(widgetRuleCode,user.getUserId())){
			        	   if(RequestUtil.isAcceptJson(request)){
				        		response.setContentType("text/json"); 
				        		response.setCharacterEncoding("UTF-8");
				        		response.getWriter().write(JSON.toJSONString(new KJSONMSG(KConstants.SC_402, "当前用户没有访问权限: [ "+desc+" ]",new Object())));
				    		}else{
				    			 response.sendRedirect(request.getContextPath()+"/static/pages/401.html");
				    		}
			        	   return false;
			           }
		            
		           return true;
	            	
	            }
	     } else {
	          return super.preHandle(request, response, handler);
	   }
		
	}
	
}
