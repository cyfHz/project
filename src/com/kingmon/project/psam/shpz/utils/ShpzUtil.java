package com.kingmon.project.psam.shpz.utils;

import org.springframework.util.StringUtils;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.project.psam.jlx.model.Jlx;

public class ShpzUtil {
	
	
	/**
	 * 添加门楼牌号或者街路巷：获取当前审批状态
	 * 
	 * @param pzdm： 配置代码 各地市不同
	 * @param userLevel : 当前用户级别
	 * @return
	 */
	public static String getSpzt(String pzdm, int userLevel) {
		String spzt  = "0";
		if(StringUtils.isEmpty(pzdm) || StringUtils.isEmpty(userLevel)){
			return spzt;
		}
		if("0".equals(pzdm)){//不需要审核
			spzt = KConstants.SPZT_SUCCESS;
		}else if("1".equals(pzdm)){//派出所申请
//			if(!(KConstants.USER_LEVEL_JWQ ==userLevel)){//不是警务区
//				spzt = KConstants.SPZT_SUCCESS;
//			}
		}else if("2".equals(pzdm)){//区县审核
			if(userLevel<=KConstants.USER_LEVEL_FJ ){//区县
				spzt = KConstants.SPZT_SUCCESS;
			}
		}else if("3".equals(pzdm)){//区县+市局审核 
			if(userLevel ==KConstants.USER_LEVEL_FJ){//市局
				spzt = KConstants.SPZT_QXSH_YES;
			}else if(userLevel<=KConstants.USER_LEVEL_SJ ){
				spzt = KConstants.SPZT_SUCCESS;
			}
		}else if("4".equals(pzdm)){
			if(userLevel<=KConstants.USER_LEVEL_SJ){//市局
				spzt = KConstants.SPZT_SUCCESS;
			}
		}
		if(userLevel==KConstants.USER_LEVEL_ST){//当前用户是省厅
			spzt = KConstants.SPZT_SUCCESS;
		}
		return spzt;
	}
	
	public static String getShResult(String pzdm,int userLevel,String spzt,String spzt_old){
		String setSpzt = null;
		//String spzt_old = jlx.getSpzt();
		 if ("0".equals(pzdm)) {
			AlertSLEUtil.Error("该数据不需要审核。");
		 }else if ("2".equals(pzdm)) {// 只需要区县审核
			if (userLevel!= KConstants.USER_LEVEL_FJ) {
				AlertSLEUtil.Error("当前用户没有审核权限。");
			} else {
				if ("yes".equals(spzt)) {
					 setSpzt=KConstants.SPZT_SUCCESS;
				} else {
					setSpzt=KConstants.SPZT_QXSH_NO;
				}
			}
		} else if ("3".equals(pzdm)) {//区县审核+需要市局
			if(!(userLevel == KConstants.USER_LEVEL_FJ||userLevel == KConstants.USER_LEVEL_SJ)){
				AlertSLEUtil.Error("当前用户没有审核权限。");
			}else{
				if(userLevel == KConstants.USER_LEVEL_FJ){// 区县审核
					if(KConstants.SPZT_SUCCESS.equals(spzt_old)||KConstants.SPZT_SJSH_YES.equals(spzt_old) || KConstants.SPZT_SJSH_NO.equals(spzt_old)){
						AlertSLEUtil.Error("市局已经审核，分局无权进行再次审核。");
					}else{
						if ("yes".equals(spzt)) {
							setSpzt=KConstants.SPZT_QXSH_YES;
						} else {
							setSpzt=KConstants.SPZT_QXSH_NO;
						}
					}
				}else if(userLevel == KConstants.USER_LEVEL_SJ){
					// 区县审核通过以后可进行市局审核
				/*	if (!KConstants.SPZT_QXSH_YES.equals(spzt_old)) {//KConstants.SPZT_SJSH_YES
						AlertSLEUtil.Error("该数据尚未通过分局审核，请等待分局审核。");
					}else{
						if ("yes".equals(spzt)) {
							setSpzt=KConstants.SPZT_SUCCESS;
						} else {
							setSpzt=KConstants.SPZT_SJSH_NO;
						}
					}*/
					if (!KConstants.SPZT_QXSH_YES.equals(spzt_old)&&!KConstants.SPZT_SJSH_NO.equals(spzt_old)&&
							!KConstants.SPZT_SUCCESS.equals(spzt_old)&&
							!KConstants.SPZT_SJSH_YES.equals(spzt_old)) {//KConstants.SPZT_SJSH_YES
						AlertSLEUtil.Error("该数据尚未通过分局审核，请等待分局审核。");
					}else{
						if ("yes".equals(spzt)) {
							setSpzt=KConstants.SPZT_SUCCESS;
						} else {
							setSpzt=KConstants.SPZT_SJSH_NO;
						}
					}
				}
			}
			
		}else if("4".equals(pzdm)){//只需要市局审核
			if (userLevel != KConstants.USER_LEVEL_SJ) {// 市局审核
				AlertSLEUtil.Error("当前用户没有审核权限，请联系管理员。");
			} else {
				if ("yes".equals(spzt)) {
					 setSpzt=KConstants.SPZT_SUCCESS;
				} else {
					setSpzt=KConstants.SPZT_SJSH_NO;
				}
			}
		}
		return setSpzt;
	}
}
