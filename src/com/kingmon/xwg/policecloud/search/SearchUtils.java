package com.kingmon.xwg.policecloud.search;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 治安云 调用search环境接口
 * @author hujia
 */
public class SearchUtils {
	
	//此类整合时需要添加日志
	
	private static final String S_IP_PORT_T = "http://localhost:8801/psam/zaysearch";//测试地址  http://localhost:8080/ElasticInferface/helloServlet
	private static final String S_IP_PORT = ""; //正式地址

	public static void main(String[] args) throws Throwable {
		 
       StringBuffer result = new StringBuffer();
    	System.out.println("--"+result.toString()+"--");
    	
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("hujia", "name");
    	map.put("name", "hujia");
    	System.out.println(setMap2String(map));
    	
    	getDataList("jzwjbxx","0");
	}
	
	public static String getDataList(String table,String from) throws Exception{
		return queryDataList(table, from, "10",null);
	}
	public static String getDataList(String table,String from,String size) throws Exception{
		return queryDataList(table, from, size,null);
	}
	
	public static String getDataList(String table,String from,String size,Map<String,String> map) throws Exception{
		String parms = setMap2String(map);
		return queryDataList(table, from, size,parms);
	}
	
    private static String queryDataList(String table,String from,String size,String parms) throws Exception{
		
		URL urls = new URL(S_IP_PORT_T);
		//设置HEAD
		HttpURLConnection hconn = (HttpURLConnection) urls.openConnection();
		hconn.setRequestMethod("POST");
		hconn.setDoInput(true);
		hconn.setDoOutput(true);
		hconn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
		hconn.setRequestProperty("Accept-Encoding", "gzip, deflate");
		hconn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		hconn.setRequestProperty("Cache-Control", "max-age=0");
		hconn.setRequestProperty("Cookie", "uid=d2ee3253494a10fb21a4c5d71ad3f13251460214217");
		hconn.setRequestProperty("Host", "zacloud.pcp.sd");
		hconn.setRequestProperty("If-Modified-Since", "Wed, 06 Apr 2016 08:44:18 GMT");
		hconn.setRequestProperty("If-None-Match", "a042bc81e08fd11:8c9");
		hconn.setRequestProperty("Connection", "keep-alive");
		hconn.setRequestProperty("Referer", "http://zacloud.pcp.sd/");
		hconn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
		hconn.setConnectTimeout(5000);
		hconn.connect();
		//设置参数
		StringBuffer params = new StringBuffer();
		params.append("tk=894DAE5CBB4FAFA722E8A00594D30327&");
		params.append("t="+table+"&");
		params.append("size="+size+"&");
		params.append("from="+from);
		if(parms!=null && !parms.equals("")){
			params.append(parms);
		}
		System.err.println(params);
		//发送参数
		hconn.getOutputStream().write(params.toString().getBytes("utf-8"));
		//响应状态
		System.err.println(hconn.getResponseCode());
		InputStream in = hconn.getInputStream();
		BufferedReader read = new BufferedReader(new InputStreamReader(in,"utf-8"));
		StringBuffer result = new StringBuffer();
		String temp  = "";
		//将输入流写入
		while ((temp = read.readLine())!=null) {
			result.append(temp);
			//System.err.println(temp);
		}
		in.close();
		hconn.disconnect();
		//返回结果
		//System.out.println(result);
		return result.toString();
	}
    
    /**
     * Map 转 String,简单化：默认只处理Map<String,String>，不考了其他
     * @param map
     */
    private static String setMap2String(Map<String,String> map){
    	if(map==null) return "";
		Entry<?,?> entry;
		String temp = "";
    	StringBuffer result = new StringBuffer();
    	for(Iterator<?> iterator = map.entrySet().iterator();iterator.hasNext();){
    		entry = (Entry<?, ?>) iterator.next();
    		temp = "&" +entry.getKey().toString().trim() + "=" + entry.getValue().toString().trim();
    		result = result.append(temp);
    	}
    	return result.toString().substring(1);
    }

}
