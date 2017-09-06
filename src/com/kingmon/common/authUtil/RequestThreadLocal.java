package com.kingmon.common.authUtil;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingmon.common.session.SessionUser;


public class RequestThreadLocal {
	
	protected final Logger log = LoggerFactory.getLogger(SessionUser.class);
	
	private static final ThreadLocal<SessionUser> contextHolder = new ThreadLocal<SessionUser>();
	private static final ThreadLocal<HttpSession> contextSessonHolder = new ThreadLocal<HttpSession>();
	
	
	public static void setSessionUser(SessionUser sessionUser) {
		contextHolder.set(sessionUser);
	}

	public static SessionUser getSessionUser() {
		return contextHolder.get();
	}

	public static void clearCurrentReqestInfo() {
		contextHolder.remove();
	}
	
	public static void setSession(HttpSession session) {
		contextSessonHolder.set(session);
	}

	public static HttpSession getSession() {
		return contextSessonHolder.get();
	}
	public static void clearCurrentSeesionReqestInfo() {
		contextSessonHolder.remove();
	}
}
