package com.kingmon.base.util;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RequestUtil {
	
	public static  boolean isAcceptJson(HttpServletRequest httpRequest){
		 boolean isAcceptJson=false;
		/*if (httpRequest.getHeader("x-requested-with") != null    && httpRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {  
			 return true;
		} */
//		 boolean isAcceptJson=false;
		 String accept = httpRequest.getHeader("accept");
		 if(accept!=null&&accept.toLowerCase().contains("application/json")){
			 isAcceptJson=true;
		 }else{
			 isAcceptJson=false;
		 }
		 return isAcceptJson;
	}
	
	 /**
	  * 
	  * @param request
	  * @return
	  */
	@SuppressWarnings("unchecked")
	public static String getRequestParams(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
		if (params == null) {
			return JSON.toJSONString("");
		}
		return JSON.toJSONString(params);
	 }
	@SuppressWarnings("unchecked")
	public static String getRequestHeaders(HttpServletRequest request) {
	        Map<String, List<String>> headers = Maps.newHashMap();
	        Enumeration<String> namesEnumeration = request.getHeaderNames();
	        while(namesEnumeration!=null&&namesEnumeration.hasMoreElements()) {
	            String name = namesEnumeration.nextElement();
	            Enumeration<String> valueEnumeration = request.getHeaders(name);
	            List<String> values = Lists.newArrayList();
	            while(valueEnumeration.hasMoreElements()) {
	                values.add(valueEnumeration.nextElement());
	            }
	            headers.put(name, values);
	        }
	        return JSON.toJSONString(headers);
	 }
}
