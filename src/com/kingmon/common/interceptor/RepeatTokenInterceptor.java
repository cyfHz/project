package com.kingmon.common.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.util.RequestUtil;
import com.kingmon.common.annon.RepeatToken;


public class RepeatTokenInterceptor extends HandlerInterceptorAdapter{

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
		
		HttpSession session=request.getSession(false);
		if(session!=null){
        	StringBuffer sbBuffer=new StringBuffer();
        	sbBuffer.append("_RepeatToken_")
		        	.append(session.getId())
		        	.append(request.getRequestURI())
		        	.append(request.getQueryString());
			session.removeAttribute(sbBuffer.toString());
		}
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,	HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session=request.getSession();
		if(session!=null){
        	StringBuffer sbBuffer=new StringBuffer();
        	sbBuffer.append("_RepeatToken_")
		        	.append(session.getId())
		        	.append(request.getRequestURI())
		        	.append(request.getQueryString());
			session.removeAttribute(sbBuffer.toString());
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 if (handler instanceof HandlerMethod) {
	            HandlerMethod handlerMethod = (HandlerMethod) handler;
	            Method method = handlerMethod.getMethod();
	            RepeatToken annotation = method.getAnnotation(RepeatToken.class);
	            if (annotation != null) {
	            	HttpSession session=request.getSession();
	            	StringBuffer sbBuffer=new StringBuffer();
	            	sbBuffer.append("_RepeatToken_")
			            	.append(session.getId())
			            	.append(request.getRequestURI())
			            	.append(request.getQueryString());
	            	
	            	Boolean isPosted=(Boolean) session.getAttribute(sbBuffer.toString());
	            	
	            	if(isPosted==null||isPosted==false){
	            		session.setAttribute(sbBuffer.toString(), true);
	            		return true;
	            	}else {
	            		if(RequestUtil.isAcceptJson(request)){
	                		response.setContentType("text/json"); 
	                		response.setCharacterEncoding("UTF-8");
	                		response.getWriter().write(JSON.toJSONString(new KJSONMSG(KConstants.SC_601, "该请求已经在处理，请不要重复提交",new Object())));
	            		}else{
	            			 response.sendRedirect("reapeat.jsp");
	            		}
	            		return false;
	            	}
	            }
	            return true;
	        } else {
	            return super.preHandle(request, response, handler);
	        }
		
	}

}
