package com.kingmon.base.util;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

public class PaginationUtil {
	public static final int MAX_ROWS_ALLOW = 100;
	public static final String DEFAULT_ROWS_CNT = "10";
	public static void initPageAndSort(Map<String,String> params){
		if(params==null){return;}
		String  page = params.get("page");
		String  rows = params.get("rows");
		String  sort = params.get("sort");
		String  order = params.get("order");
		
		if(page==null||!NumberUtils.isNumber(page)){
			page = "1";
		}
		if(rows==null||!NumberUtils.isNumber(rows)){
			rows = DEFAULT_ROWS_CNT;
		}
		
		
		int p = Integer.valueOf(page);
		int r = Integer.valueOf(rows);
		
		if(p<1){
			p=1;
		}
		if(r<0){
			r = 0;
		}
		
		if(r>MAX_ROWS_ALLOW){
			r = MAX_ROWS_ALLOW;
		}
		params.remove("page");
		params.remove("rows");
		params.put("pagestart", ((p-1)*r)+"");
		params.put("pageend", (p*r)+"");
		
		if(sort!=null&& !sort.isEmpty()){
			if(order==null||order.isEmpty()){
				order = "ASC";
			}
			if(!order.equals("asc")&&!order.equals("desc")){
				order = "ASC";
			}
			params.put("sort", sort);
			params.put("order",order);
		}else{
			params.remove("sort");
			params.remove("order");
		}
	}
	
	
	public static void initElasticPageAndSort(Map<String,String> params){
		if(params==null){return;}
		String  page = params.get("page");
		String  rows = params.get("rows");
		String  sort = params.get("sort");
		String  order = params.get("order");
		
		if(page==null||!NumberUtils.isNumber(page)){
			page = "1";
		}
		if(rows==null||!NumberUtils.isNumber(rows)){
			rows = DEFAULT_ROWS_CNT;
		}
		
		
		int p = Integer.valueOf(page);
		int r = Integer.valueOf(rows);
		
		if(p<1){
			p=1;
		}
		if(r<0){
			r = 0;
		}
		
		if(r>MAX_ROWS_ALLOW){
			r = MAX_ROWS_ALLOW;
		}
		params.remove("page");
		params.remove("rows");
		params.put("from", ((p-1)*r)+"");
		params.put("size", r+"");
		
		if(sort!=null&& !sort.isEmpty()){
			if(order==null||order.isEmpty()){
				order = "ASC";
			}
			if(!order.equals("asc")&&!order.equals("desc")){
				order = "ASC";
			}
			params.put("sort", sort);
			params.put("order",order);
		}else{
			params.remove("sort");
			params.remove("order");
		}
	}
}
