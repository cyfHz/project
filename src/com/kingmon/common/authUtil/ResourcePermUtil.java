package com.kingmon.common.authUtil;

import java.util.Collections;
import java.util.List;

import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.project.auth.resource.model.Resource;
import com.kingmon.project.auth.resource.service.impl.ResourceServiceImpl;
public class ResourcePermUtil {
	
	public static boolean isUserHaveResource(String resCode,String userId){
		//ResourceServiceImpl resourceService=SpringBeanFacUtil.getBean(ResourceServiceImpl.class);
		//List<String> resCodeList=resourceService.findResCodeListByUserId(userId);
		
		List<String> resCodeList=loadResCodeListByUserId(userId);
		if(resCodeList!=null&&resCodeList.contains(resCode)){
			return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public static List<String> loadResCodeListByUserId(String userId){
		ResourceServiceImpl resourceService=SpringBeanFacUtil.getBean(ResourceServiceImpl.class);
		List<String> resCodeList=resourceService.findResCodeListByUserId(userId);
		return (List<String>) (resCodeList==null?Collections.emptyList():resCodeList);
	}
	@SuppressWarnings("unchecked")
	public static List<Resource> loadResListByUserId(String userId){
		ResourceServiceImpl resourceService=SpringBeanFacUtil.getBean(ResourceServiceImpl.class);
		List<Resource> resCodeList=resourceService.findResListByUserId(userId);
		return (List<Resource>) (resCodeList==null?Collections.emptyList():resCodeList);
	}

}
