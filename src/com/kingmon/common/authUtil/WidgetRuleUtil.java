package com.kingmon.common.authUtil;

import java.util.Collections;
import java.util.List;
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.project.auth.rule.model.Widgetrule;
import com.kingmon.project.auth.rule.service.impl.WidgetruleServiceImpl;
public class WidgetRuleUtil {
	/**
	 * 判断用户是否拥有某权限
	 * @param widgetRuleCode
	 * @param userId
	 * @return
	 */
	public static boolean isUserHaveWidgetRuleCode(String widgetRuleCode,String userId){
		//WidgetruleServiceImpl widgetruleService=SpringBeanFacUtil.getBean(WidgetruleServiceImpl.class);
		//List<String> codes=widgetruleService.loadWidgetRuleCodeListByUserId(userId);
		List<String> codes=loadWidgetRuleCodeListByUserId(userId);
		if(codes!=null&&codes.contains(widgetRuleCode)){
			return true;
		}
		return false;
	}
	/**
	 * 根据用户ID 加载用户权限
	 * @param userId
	 * @return WidgetRuleCode
	 */
	@SuppressWarnings("unchecked")
	public static List<String> loadWidgetRuleCodeListByUserId(String userId){
		WidgetruleServiceImpl widgetruleService=SpringBeanFacUtil.getBean(WidgetruleServiceImpl.class);
		List<String> codes=widgetruleService.loadWidgetRuleCodeListByUserId(userId);
		return (List<String>) (codes==null?Collections.emptyList():codes);
	}
	/**
	 * 
	 * @param userId
	 * @return WidgetList
	 */
	@SuppressWarnings("unchecked")
	public static List<Widgetrule> loadWidgetListByUserId(String userId){
		WidgetruleServiceImpl widgetruleService=SpringBeanFacUtil.getBean(WidgetruleServiceImpl.class);
		List<Widgetrule> rules=widgetruleService.loadWidgetRuleListByUserId(userId);
		return (List<Widgetrule>) (rules==null?Collections.emptyList():rules);
	}
}
