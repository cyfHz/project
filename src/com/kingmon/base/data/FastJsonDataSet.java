package com.kingmon.base.data;

import java.io.Serializable;

import com.alibaba.fastjson.JSONArray;

public class FastJsonDataSet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long total;
	private JSONArray rows=new JSONArray();
	private JSONArray footer=new JSONArray();
	private String simpleFooter;//合计列
	
//	public DataSet() {
//	}
	public FastJsonDataSet( JSONArray  rows) {
		this.rows = rows;
	}
	public FastJsonDataSet(Long total,JSONArray  rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public FastJsonDataSet(Long total, JSONArray  rows,JSONArray  footer) {
		this.total = total;
		this.rows = rows;
		this.footer = footer;
	}
	public static FastJsonDataSet newDs(){
		return new FastJsonDataSet(0L,new JSONArray());
	}
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public JSONArray  getRows() {
		return rows;
	}
	public void setRows(JSONArray  rows) {
		this.rows = rows;
	}
	public JSONArray  getFooter() {
		return footer;
	}
	public void setFooter(JSONArray  footer) {
		this.footer = footer;
	}

	public String getSimpleFooter() {
		return simpleFooter;
	}

	public void setSimpleFooter(String simpleFooter) {
		this.simpleFooter = simpleFooter;
	}
	@Override
	public String toString() {
		return "DataSet [total=" + total + ", rows=" + rows + ", footer="
				+ footer + ", simpleFooter=" + simpleFooter + "]";
	}
	



}
