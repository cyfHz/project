package com.kingmon.common.authUtil;

import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.kingmon.base.common.KConstants;
import com.kingmon.common.session.SessionUser;

public class SecurityUtils {
	
	/**
	 * 获取 com.kingmon.common.authUtil.SessionUser
	 * @return
	 */
	public static SessionUser getSessionUser(){
		return RequestThreadLocal.getSessionUser();
	}
	/**
	 *  获取用户 ID
	 *  
	 * @return
	 */
	public static String getUserId(){
		SessionUser user=RequestThreadLocal.getSessionUser();
		if(user==null){
			return null;
		}
		return user.getUserId();
	}
	/**
	 *  获取用户 登录名
	 * @return
	 */
	public static String getLoginname(){
		SessionUser user=RequestThreadLocal.getSessionUser();
		if(user==null){
			return null;
		}
		return user.getLoginname();
	}
	/**
	 * 获取用户 名
	 * @return
	 */
	public static String getUserName(){
		SessionUser user=RequestThreadLocal.getSessionUser();
		if(user==null){
			return null;
		}
		return user.getName();
	}
	/**
	 * 获取用户 身份证号码
	 * @return
	 */
	public static String getUserSfzh(){
		SessionUser user=RequestThreadLocal.getSessionUser();
		if(user==null){
			return null;
		}
		return user.getSfzh();
	}
	/**
	 * 获取用户 状态 1：启用； 0：禁用 
	 * @return
	 */
	public static String getUserEnabled(){
		SessionUser user=RequestThreadLocal.getSessionUser();
		if(user==null){
			return null;
		}
		return user.getEnabled();
	}
	/**
	 *获取用户所在组织机构编码
	 * @return
	 */
	public static String getUserOrgCode(){
		SessionUser user=RequestThreadLocal.getSessionUser();
		if(user==null){
			return null;
		}
		return user.getOrganizationCode();
	}
	/**
	 * 获取用户所在组织机构ID
	 * @return
	 */
	public static String getUserOrgId(){
		SessionUser user=RequestThreadLocal.getSessionUser();
		if(user==null){
			return null;
		}
		return user.getOrganizationId();
	}
	/**
	 * 获取用户所在组织机构名称
	 * @return
	 */
	public static String getUserOrgName(){
		SessionUser user=RequestThreadLocal.getSessionUser();
		if(user==null){
			return null;
		}
		return user.getOrganizationName();
	}
	
	/**
	 * 获取用户级别
	 * @return
	 */
	public static int getUserLevel(){
//		SessionUser user=RequestThreadLocal.getSessionUser();
		SessionUser user=getUserFromSession();
		if(user==null){
			return Integer.MIN_VALUE;
		}
		int userLevel=user.getUserLevel();
		if(userLevel>0){
			return userLevel;
		}
		return DataRuleUtil.getUserLevel(user.getUserId());
	}
	
	/**
	 * 获取Session
	 * @return
	 */
	public static HttpSession getHtttpSession(){
		HttpSession session=RequestThreadLocal.getSession();
		return session;
	}
	/**
	 * 通过Session 获取SessionUser
	 * @return
	 */
	public static SessionUser getUserFromSession(){
		HttpSession session= getHtttpSession();
		if(session==null){
			return null;
		}
		SessionUser user= (SessionUser) session.getAttribute(KConstants.SESSIONUSER);
		return user;
	}
	/**
	 * 修改用户级别
	 * @return
	 */
	public static int refreshUserLevel(){
		HttpSession session=RequestThreadLocal.getSession();
		SessionUser user= (SessionUser) session.getAttribute(KConstants.SESSIONUSER);
		if(user==null){
			return -1;
		}
		int level=DataRuleUtil.getUserLevel(user.getUserId());
		user.setUserLevel(level);
		session.setAttribute(KConstants.SESSIONUSER, user);
		RequestThreadLocal.setSessionUser(user);
		
		return level;
	}
	
	/**
	 * 获取用户级别,字符串 37 3701等
	 * @return
	 */
	public static String getUserLevelStr(){
		SessionUser user=RequestThreadLocal.getSessionUser();
		if(user==null){
			return null;
		}
		String userLevelStr=user.getUserLevelStr();
		if(StringUtils.hasText(userLevelStr)){
			return userLevelStr;
		}
		return DataRuleUtil.getXzqhStr(user.getUserId());
	}
}
