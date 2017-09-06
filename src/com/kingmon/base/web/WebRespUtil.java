package com.kingmon.base.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.util.RequestUtil;

public class WebRespUtil {
	
	public static void respJSON(HttpServletResponse httpResponse,KJSONMSG jsonmsg) throws IOException{
		httpResponse.setContentType("text/json"); 
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.getWriter().write(JSON.toJSONString(jsonmsg));
	}
	
	public static void sendRedirectURL(HttpServletRequest httpRequest,HttpServletResponse httpResponse,String url) throws IOException, ServletException{
		StringBuilder targetUrl=new StringBuilder("");
		if (url!=null && url.startsWith("/")) {
	         targetUrl.append(httpRequest.getContextPath());
	     }
	    targetUrl.append(url);
		httpResponse.sendRedirect(targetUrl.toString());
	}
	
	public static void forwardRUL(HttpServletRequest httpRequest,HttpServletResponse httpResponse,String url) throws ServletException, IOException{
		StringBuilder targetUrl=new StringBuilder("");
		if (url!=null && url.startsWith("/")) {
	         targetUrl.append(httpRequest.getContextPath());
	     }
	    targetUrl.append(url);
		httpRequest.getRequestDispatcher(targetUrl.toString()).forward(httpRequest,httpResponse);
	}
	
	
	public static void responeseJsonOrForward(HttpServletRequest httpRequest,HttpServletResponse httpResponse,KJSONMSG jsonmsg,String url) throws ServletException, IOException{
		if(RequestUtil.isAcceptJson(httpRequest)){
			httpResponse.setContentType("text/json"); 
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.getWriter().write(JSON.toJSONString(jsonmsg));
		}else{
			StringBuilder targetUrl=new StringBuilder("");
			if (url!=null && url.startsWith("/")) {
		         targetUrl.append(httpRequest.getContextPath());
		    }
		    targetUrl.append(url);
			httpRequest.setAttribute(KConstants.RESPONSE_MSG_KEY, jsonmsg);
			httpRequest.getRequestDispatcher(targetUrl.toString()).forward(httpRequest,httpResponse);
		}
		
	}
	public static void responeseJsonOrRedirect(HttpServletRequest httpRequest,HttpServletResponse httpResponse,KJSONMSG jsonmsg,String url) throws ServletException, IOException{
		if(RequestUtil.isAcceptJson(httpRequest)){
			httpResponse.setContentType("text/json"); 
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.getWriter().write(JSON.toJSONString(jsonmsg));
		}else{
			StringBuilder targetUrl=new StringBuilder("");
			if (url!=null && url.startsWith("/")) {
		         targetUrl.append(httpRequest.getContextPath());
		    }
		    targetUrl.append(url);
			httpResponse.sendRedirect(targetUrl.toString());
		}
	}
	

}
