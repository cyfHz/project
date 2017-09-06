package com.kingmon.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.kingmon.base.common.KConstants;
import com.kingmon.common.authUtil.RequestThreadLocal;
import com.kingmon.common.session.SessionUser;

public class RequestThreadLocalInterceptor extends HandlerInterceptorAdapter{
	
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
		try {
			if(InterceptorUtil.isInignorUrls(request,ignorUrls)){
				super.afterCompletion(request, response, handler, ex);
				return;
			}
			RequestThreadLocal.clearCurrentReqestInfo();
			RequestThreadLocal.clearCurrentSeesionReqestInfo();
		} catch (Exception e) {
		}
	}

	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(InterceptorUtil.isInignorUrls(request,ignorUrls)){
			return true;
		}
		HttpSession sesson=request.getSession();
		SessionUser user= (SessionUser) sesson.getAttribute(KConstants.SESSIONUSER);
		if(user==null){
			return true;
		}
		RequestThreadLocal.setSessionUser(user);
		RequestThreadLocal.setSession(sesson);
//		if(RequestThreadLocal.getSession()==null){
//			RequestThreadLocal.setSession(sesson);
//			
//		}
		return true;
		
	}
}

//if(InterceptorUtil.isInignorUrls(request,ignorUrls)){
//	return true;
//}
//SessionUser user=null;
//HttpSession sesson=request.getSession();
//String sessionId=sesson.getId();
//
//if(KConstants.session_mode_selfRedisRepository.equals(sessionMode)){
//	RedisSessionUserRepository sessionRep=SpringBeanFacUtil.getBean("redisSessionUserRepository");
//	user=sessionRep.getSessionUser(sessionId);
//}else{
//	user= (SessionUser) sesson.getAttribute(KConstants.SESSIONUSER);
//}
//
//if(user==null){
//	return true;
//}
//RequestThreadLocal.setSessionUser(user);
//return true;

