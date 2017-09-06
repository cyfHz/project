package com.kingmon.project.elastic.model;

import com.alibaba.fastjson.JSONObject;

public class Json extends JSONObject{
	public Json add(String key,Object val){
		this.put(key, val);
		return this;
	}
	public Json add(String key,String val){
		this.put(key, val);
		return this;
	}
	public Json(){}
	public Json(String key ,String val){
		this.put(key, val);
	}
}