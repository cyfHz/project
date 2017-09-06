package com.kingmon.xwg.policecloud.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于接受返回对象，转为json
 * @author hujia
 *
 */
public class SearchResultBean  implements Serializable{

	private static final long serialVersionUID = 5030936378700503898L;
	
	private Long total;
	private List<Map<String,Object>> rows=new ArrayList<Map<String,Object>>();
	private String type;
	
	
	SearchResultBean(List<Map<String,Object>> rows,Long total){
		this.rows = rows;
		this.total = total;
	}
	
	SearchResultBean(List<Map<String,Object>> rows,Long total,String type){
		this.rows = rows;
		this.total = total;
		this.type = type;
	}


	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
}
