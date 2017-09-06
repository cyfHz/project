package com.kingmon.base.util;

import java.util.ArrayList;
import java.util.Map;
import com.kingmon.base.data.DataSet;

public class FastjsonUtil {
	public static Object convert(Object object){
		if(object==null){
			return new Object();
		}
		return object;
	}
	public static Object convert(DataSet dataSet){
		if(dataSet==null){
			return new DataSet(0L,new ArrayList<Map<String,Object>>());
		}
		return dataSet;
	}
	
}
