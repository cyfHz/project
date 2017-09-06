package com.kingmon.common.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.kingmon.common.authUtil.KWebUtil;

public class InterceptorUtil {
	
	public static  boolean isInignorUrls(HttpServletRequest request,String[] ignorUrls){
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
