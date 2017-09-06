package com.kingmon.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.seril.JdkSerialUtil;
import com.kingmon.common.redis.JedisManager;

import com.kingmon.common.session.SessionUser;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.sdwangge.policecloud.bean.BaseUser;



public class ZayAppAuthInterceptor extends HandlerInterceptorAdapter{
	private  String[] ignorUrls;//
	@Autowired
	private IOrganizationUserService organizationUserService;
	@Autowired
	private JedisManager jedisManager;
	

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(InterceptorUtil.isInignorUrls(request,ignorUrls)){
			return true;
		}
		String urrl=request.getRequestURL().toString();
		String zay_session_id=request.getParameter("JSESSIONID");
		if(zay_session_id==null||zay_session_id.isEmpty()){
			//不拦截，直接给后面拦截器处理
			return true;
		}
		HttpSession session = request.getSession();
    	String sessionId=session.getId();
    	if(zay_session_id.equals(sessionId)){
    		//如果传过来zay_session_id与内存中sessionid 相同，说明已经登录过，直接给后面处理
    		return true;
    	}
    	//redis中加载session
		String appuser_id=getAppUserIdByZaySessionId(zay_session_id);
		SessionUser sessionUser=organizationUserService.loadUserInfor(appuser_id);
    	if(sessionUser==null){
    		return true;
		}
     	sessionUser.setSessionId(sessionId);
     	session.setAttribute(KConstants.SESSIONUSER, sessionUser);
        return true;
	}

	private String getAppUserIdByZaySessionId(String zay_session_id) {
		byte[] byteuser =null;
		
		try {
			 byteuser =jedisManager.getValueByKey(0, zay_session_id.getBytes());
			 
		} catch (Exception e) {
			return null;
		}
		
		BaseUser baseuser = (BaseUser)JdkSerialUtil.deserialize(byteuser);
	//	BaseUser baseuser = (BaseUser)JdkSerialUtil.unserialize(byteuser);
		if(baseuser!=null){
			return baseuser.getId();
		}
		return null;
	}
	 
	public String[] getIgnorUrls() {
		return ignorUrls;
	}

	public void setIgnorUrls(String[] ignorUrls) {
		this.ignorUrls = ignorUrls;
	}
}
