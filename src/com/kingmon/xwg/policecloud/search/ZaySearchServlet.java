package com.kingmon.xwg.policecloud.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *  此类整合时需要添加日志
 *  治安云调 elastic Search 接口
 *  @author hujia
 */
@WebServlet("/zaysearch")
public class ZaySearchServlet extends HttpServlet {
	private static final long serialVersionUID = -3317388847370041617L;

	//此类整合时需要添加日志
	
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//禁止访问
		HtmlUtil.writerJson(response, "禁止访问");
	}
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cookie = request.getHeader("Cookie");
		String host = request.getHeader("Host");
		String user_agent = request.getHeader("User-Agent");
		String tk = request.getParameter("tk");
		System.out.println(cookie + host + user_agent + tk);
		//授权教研   tk =894DAE5CBB4FAFA722E8A00594D30327 and  host 包含zacloud
		if(tk.equals("894DAE5CBB4FAFA722E8A00594D30327")){
			String handler = request.getParameter("t");
			//返回结果
		    SearchResultBean result = null;
		    //处理 jzwjbxx 表
		    if(handler!=null && handler.equals("jzwjbxx")){
		    	result = jzwjbxxHandler(request);
		    }
		    //返回结果
			HtmlUtil.writerJson(response, result);
		}else{
			//禁止访问
			HtmlUtil.writerJson(response, "未授权，禁止访问");
		} 
	}
	
	//处理jzwjbxx
	private SearchResultBean jzwjbxxHandler(HttpServletRequest requestT){
		String table = requestT.getParameter("t");
		String strfrom = requestT.getParameter("from");
		String strsize = requestT.getParameter("size");
		String dzmc = requestT.getParameter("dzmc");
		String xzqh = requestT.getParameter("xzqh");
		
		Map<String,String> param = new HashMap<String,String>();
 		if(dzmc!=null){
 			param.put("dzmc", dzmc);
		}
		if(xzqh!=null){
			param.put("xzqh", xzqh);
		}
		int from = Integer.parseInt(strfrom);
		int size = Integer.parseInt(strsize) ;
		System.out.println(table + strfrom + strsize);
		SearchResultBean result = SearchClientUtils.queryDATA(table, from, size, param);
		return  result;
	}

}
