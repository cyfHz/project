package com.kingmon.project.psam.jzw.util;

import java.util.Map;

import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;

public class JzwJgValiUtil {

	@SuppressWarnings("unchecked")
	public static void validateMap(Map<?,?> view){
		Map<String, Object> postfixAndWidthRule = (Map<String, Object>)view.get("postfixAndWidthRule");
		
		KAssert.notEmpty(postfixAndWidthRule, "数据有误 ，单元号、楼层号、房间号 位数，不能为空，请检查。");
		
		Map<String, Object> jzwjgMap = (Map<String, Object>)view.get("jzwjg");
		KAssert.notEmpty(jzwjgMap, "数据有误,请检查数据。");
		
		String jzwId = (String)view.get("jzwId");//建筑物Id
		KAssert.hasText(jzwId, "数据有误,请重新打开页面填写数据。");
		
		String width_dy = (String)postfixAndWidthRule.get("width_dy");//单元号位数
		String width_lc =(String)postfixAndWidthRule.get("width_lc");//楼层号位数
		String width_fj =(String)postfixAndWidthRule.get("width_fj");//房间号位数
		
		KAssert.hasText(width_dy, "数据有误,请填写单元号位数。");
		KAssert.hasText(width_lc, "数据有误,请填写楼层号位数。");
		KAssert.hasText(width_fj, "数据有误,请填写房间号位数。");
		
		try{
			Integer int_width_dy=Integer.parseInt(width_dy); if(int_width_dy<0){AlertSLEUtil.Error("单元号位数不能小于0");}
			Integer int_width_lc=Integer.parseInt(width_lc); if(int_width_lc<0){AlertSLEUtil.Error("楼层号位数不能小于0");}
			Integer int_width_fj=Integer.parseInt(width_fj); if(int_width_fj<0){AlertSLEUtil.Error("房间号位数不能小于0");}
		}catch(Exception e){
			throw new ServiceLogicalException("数据转化错误，请检查单元号位数、楼层号位数、房间号位数数等数是否是数字");
		}
		boolean isRegular = Boolean.parseBoolean((String)(view.get("isRegular")));//单元楼层房间是否规则分布
		
		String dys_ =(String) jzwjgMap.get("dys");
		String lcs_ = (String)jzwjgMap.get("lcs");
		String mdyms = (String) jzwjgMap.get("mdyms");
		KAssert.hasText(dys_, "数据有误,请填写单元数。");
		if(isRegular){
			KAssert.hasText(lcs_, "数据有误,请填写楼层数。");
			KAssert.hasText(mdyms, "数据有误,请填写每单元门数。");
		}
	}
}
