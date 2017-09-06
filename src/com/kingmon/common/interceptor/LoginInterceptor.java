package com.kingmon.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.alibaba.fastjson.JSON;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.util.RequestUtil;
import com.kingmon.common.session.SessionUser;


public class LoginInterceptor extends HandlerInterceptorAdapter{
	private  String[] ignorUrls;//
	 
	public String[] getIgnorUrls() {
		return ignorUrls;
	}

	public void setIgnorUrls(String[] ignorUrls) {
		this.ignorUrls = ignorUrls;
	}
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
		super.afterCompletion(request, response, handler, ex);
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(InterceptorUtil.isInignorUrls(request,ignorUrls)){
			return true;
		}
//		String requestURI = KWebUtil.getPathWithinApplication(request);
		HttpSession session = request.getSession();
//		String sid=session.getId();
		SessionUser user = (SessionUser) session.getAttribute(KConstants.SESSIONUSER);
        if(user==null){
        	if(RequestUtil.isAcceptJson(request)){
        		response.setContentType("text/json"); 
        		response.setCharacterEncoding("UTF-8");
        		response.getWriter().write(JSON.toJSONString(new KJSONMSG(KConstants.SC_401, "未登录或超时",new Object())));
    		}else{
    			response.sendRedirect(request.getContextPath()+"/login.jsp");
    		}
            return false;  
        }  
        return true;
	}
	
}




//if(InterceptorUtil.isInignorUrls(request,ignorUrls)){
//	return true;
//}
////String requestURI = KWebUtil.getPathWithinApplication(request);
//HttpSession session = request.getSession();
//String sid=session.getId();
//SessionUser user =null;
////if(KConstants.session_mode_selfRedisRepository.equals(sessionMode)){
////	//redis方案, 目前不可行，20151214直接舍弃，
////	  user=SecurityUtils.getSessionUser();
////	 if(user==null){
////	     RedisSessionUserRepository sessionRep=SpringBeanFacUtil.getBean("redisSessionUserRepository");
////	     user=sessionRep.getSessionUser(sid);
////	 }
////}else{
////	//系统采用springSession 方案
////	 user = (SessionUser) session.getAttribute(KConstants.SESSIONUSER);
////}
// user = (SessionUser) session.getAttribute(KConstants.SESSIONUSER);
//if(user==null){
//	if(RequestUtil.isAcceptJson(request)){
//		response.setContentType("text/json"); 
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().write(JSON.toJSONString(new KJSONMSG(KConstants.SC_401, "未登录或超时",new Object())));
//	}else{
//		 response.sendRedirect(request.getContextPath()+"/login.jsp");
//	}
//    return false;  
//}  
//return true;
//}