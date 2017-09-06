package com.kingmon.project.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.common.authUtil.DataRuleUtil;
import com.kingmon.common.authUtil.SecurityUtils;

public class DataRangeUtil {

//	public static String getRealScope(String paramFromWeb){
//		String strFromDrule=DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
//		if(SubApStrUtil.isEmptyAfterTrimE(paramFromWeb)){
//			return strFromDrule;
//		}
//		paramFromWeb=""+paramFromWeb;
//		if(paramFromWeb.length()<(""+strFromDrule).length()){
//			return strFromDrule;
//		}else{
//			return paramFromWeb;
//		}
//		
//	}
	
	
	 public static java.util.Date StringToData(String date){
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  Date tdate = null;
		  try {
		   tdate = formatter.parse(date);
		  } catch (ParseException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  return tdate;
		 }

}


