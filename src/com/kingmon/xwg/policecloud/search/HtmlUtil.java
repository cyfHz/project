package com.kingmon.xwg.policecloud.search;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

/**
 * 页面输出json 工具类
 * @author hujia
 */
public class HtmlUtil {
	
	
	public static void writerJson(HttpServletResponse response, String jsonStr) {
		writer(response, jsonStr);
	}

	public static void writerJson(HttpServletResponse response, Object object) {
		try {
			response.setContentType("application/json");
			writer(response, JSON.toJSONString(object));
		} catch (JSONException e) {
			response.setContentType("application/json");
			writer(response, "{success:false}");
		}
	}

	private static void writer(HttpServletResponse response, String str) {
		try {
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			 System.err.println(e.getLocalizedMessage());
		}
	}
}
