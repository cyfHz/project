package com.kingmon.project.psam.dataQr.enums;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.collect.Maps;

public enum QrType {

	MLPH("mlph"),
	JZW("_jzw"),
    FW("__fw");
	
	
	private String value;

	QrType(String value){ 
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static QrType getValueQr(String tableType) {
		for(QrType c:QrType.values()){
			if((""+tableType).equals(c.getValue())){
				return c;
			}
		}
		return null;
	}
	
	private static final Map<String,String> configMap=Maps.newHashMap();
	
	private void initConfigMap(){
		Object arg1=configMap.put(""	, "");
	}
	class configMap extends HashMap<String, Object>{
		public configMap add(String key,Object param){
			this.put(key, param);
			return this;
		}
	}
	
}
