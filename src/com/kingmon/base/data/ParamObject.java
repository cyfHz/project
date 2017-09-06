package com.kingmon.base.data;

import java.util.HashMap;
import java.util.Map;
import com.kingmon.base.util.SubApStrUtil;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class ParamObject extends HashMap{
	
	private int page=1;
	private int rows=10;
	
	private String order;//direction 
	private String sort;//sort field

	private Boolean isOffset;
	
	private Boolean isNeedCount;
	
	private Map<String,Object> webParams =new HashMap<String,Object>();
	
	public ParamObject() {
		super();
		this.setInitType(POType.S_O_C);
	}
	
	public ParamObject(POType initType) {
		super();
		initType(initType);
	}
	public ParamObject(POType initType,Integer page,Integer rows) {
		this.page = page;
		this.rows = rows;
		initType(initType);
	}
	public ParamObject(POType initType,Integer page,Integer rows, String sort, String order) {
		this.page = page;
		this.rows = rows;
		this.sort = sort;
		this.order = order;
		initType(initType);
	}
	public static ParamObject new_P_C(){
		return new ParamObject(POType.S_O_C);
	}
	public static ParamObject new_P_NC(){
		return new ParamObject(POType.S_O_NC);
	}
	public static ParamObject new_NP_C(){
		return new ParamObject(POType.S_NO_C);
	}
	public static ParamObject new_NP_NC(){
		return new ParamObject(POType.S_NO_NC);
	}
	public  boolean hasOrder(){
		return !SubApStrUtil.isEmptyAfterTrimE(order);
	}
	
	public  Object getWebParam(String key){
		if(this.webParams==null||this.webParams.isEmpty()||key==null||key.trim().length()==0){
			return null;
		}
		Object res=webParams.get(key);
		return SubApStrUtil.trimToEmptyIfStr(res);
	}
	public ParamObject addWebParam(String key, Object value){
		this.webParams.put(key, value);
		return this;
	}
	public ParamObject addSQLParam(Object key, Object value){
		this.put(key, value);
		return this;
	}
	public Object getSQLParam(String key){
		if(this.isEmpty()||key==null||key.trim().length()==0){
			return null;
		}
		return this.get(key);
	}
	private POType initType;
	
	public void initType(POType initType){
		 this.initType=initType;
		 switch (this.initType) { 
		 	case S_O_C:  
				this.isOffset=new Boolean(true);
				this.isNeedCount=new Boolean(true);
	            break; 
		 	case S_O_NC:  
				this.isOffset=new Boolean(true);
				this.isNeedCount=new Boolean(false);
	            break;
		 	case S_NO_C:  
				this.isOffset=new Boolean(false);
				this.isNeedCount=new Boolean(true);
	            break;
		 	case S_NO_NC:  
				this.isOffset=new Boolean(false);
				this.isNeedCount=new Boolean(false);
	            break;
	        default:
				this.isOffset=new Boolean(true);
				this.isNeedCount=new Boolean(true);
		 }
	}
//-------------------------------------------------------------------------

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Boolean getIsOffset() {
		return isOffset;
	}

	public void setIsOffset(Boolean isOffset) {
		this.isOffset = isOffset;
	}

	public Boolean getIsNeedCount() {
		return isNeedCount;
	}

	public void setIsNeedCount(Boolean isNeedCount) {
		this.isNeedCount = isNeedCount;
	}

	public POType getInitType() {
		return initType;
	}

	public void setInitType(POType initType) {
		this.initType = initType;
	}

	public Map<String, Object> getWebParams() {
		return webParams;
	}

	public void setWebParams(Map<String, Object> webParams) {
		this.webParams = webParams;
	}
	
}
