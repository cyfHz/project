package com.kingmon.project.psam.util;

import java.util.List;
import java.util.Map;

import com.kingmon.base.util.SubApStrUtil;

public class QydmData {

	/**
	 * 截取三级区划的区域代码
	 * @param map QUDM 区域代码
	 * @return
	 */
	public static String getQydm(List<Map<String,String>> map){
		if(map==null||map.isEmpty()){
			return "37";
		}
		String qydm="37";
		for(Map<String,String> a:map){
			qydm=a.get("QUDM");
		}
		if(qydm!=null&&qydm.length()>=6){
			qydm=SubApStrUtil.removeLastChars(qydm, "0");
			if(qydm.length()==3){
				qydm=qydm+"0";
			}
//			return ;
		}
		return qydm;
	}
}
