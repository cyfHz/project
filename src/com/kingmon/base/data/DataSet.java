package com.kingmon.base.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataSet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long total;
	private List<Map<String,Object>> rows=new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> footer=new ArrayList<Map<String,Object>>();//合计列
	private String simpleFooter;//合计列
	
//	public DataSet() {
//	}
	public DataSet( List<Map<String,Object>>  rows) {
		this.rows = rows;
	}
	public DataSet(Long total, List<Map<String,Object>>  rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public DataSet(Long total, List<Map<String,Object>>  rows,List<Map<String,Object>>  footer) {
		this.total = total;
		this.rows = rows;
		this.footer = footer;
	}
	
	public static DataSet newDs(){
		return new DataSet(0L, new ArrayList<Map<String, Object>>());
	}
	
	public Map<String,Object> findUniqueRow(String columnName,Object value){
		if(rows!=null&&rows.size()>0){
			for(Map<String,Object> map:rows){
				Object obj = map.get(columnName);
				if(obj!=null&&obj.equals(value)){
					return map;
				}
			}
		}
		return Collections.emptyMap();
	}
	
	public Long getTotal() {
		return total;
	}
	public DataSet setTotal(Long total) {
		this.total = total;
		return this;
	}
	public List<Map<String,Object>>  getRows() {
		return rows;
	}
	public DataSet setRows(List<Map<String,Object>>  rows) {
		this.rows = rows;
		return this;
	}
	public List<Map<String,Object>>  getFooter() {
		return footer;
	}
	public DataSet setFooter(List<Map<String,Object>>  footer) {
		this.footer = footer;
		return this;
	}

	public String getSimpleFooter() {
		return simpleFooter;
	}

	public DataSet setSimpleFooter(String simpleFooter) {
		this.simpleFooter = simpleFooter;
		return this;
	}
	@Override
	public String toString() {
		return "DataSet [total=" + total + ", rows=" + rows + ", footer="
				+ footer + ", simpleFooter=" + simpleFooter + "]";
	}
	



}
