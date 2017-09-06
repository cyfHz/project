package com.kingmon.project.psam.jlx.utli;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.project.psam.jlx.model.Jlx;

public class JlxUtil {
	public static String getShzt(String pzdm,int userLevel,String spzt,Jlx jlx){
		String setSpzt = null;
		String spzt_old = jlx.getSpzt();
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
					if (!KConstants.SPZT_SJSH_YES.equals(spzt_old)) {
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
//	/**
//	 *  获取街路巷审核结果
//	 * @param pzdm:1派出所申请 2：区县审核  3：区县+市局审核  4：市局审核
//	 * @param userLevel 用户级别
//	 * @param spzt 审核配置
//	 * @param jlx 街路巷
//	 * @return
//	 */
//	public static String getShzt(String pzdm,int userLevel,String spzt,Jlx jlx){
//		String setSpzt = null;
//		String spzt_old = jlx.getSpzt();
//		if ("2".equals(pzdm)) {// 只需要区县审核
//			if (userLevel == 6) {
//				// 区县审核 // 如果已通过最终审核或者市局已经审核 则区县不能再次审核,否则 可以审核	
//				if(KConstants.SPZT_SJSH_YES.equals(spzt_old) || KConstants.SPZT_SUCCESS.equals(spzt_old)||KConstants.SPZT_SJSH_NO.equals(spzt_old)){
//					AlertSLEUtil.Error("市局已经审核，分局无权进行再次审核。");
//				}else if(KConstants.SPZT_QXSH_YES.equals(spzt_old)){
//					AlertSLEUtil.Error("区县已经审核成功，无需再次进行审核。");
//				}else{
//					if ("yes".equals(spzt)) {
//						 setSpzt=KConstants.SPZT_SUCCESS;
//					} else {
//						setSpzt=KConstants.SPZT_QXSH_NO;
//					}
//				}
//				
//			} else {
//				AlertSLEUtil.Error("您对该街路巷小区没有审核权限，请联系管理员。");
//			}
//		} else if ("3".equals(pzdm)) {// 需要市局+区县审核
//			if (userLevel == 6) {// 区县审核
//				// 如果已通过最终审核或者市局已经审核 则区县不能再次审核,否则 可以审核			
//				if(KConstants.SPZT_SJSH_YES.equals(spzt_old) || KConstants.SPZT_SUCCESS.equals(spzt_old)||KConstants.SPZT_SJSH_NO.equals(spzt_old)){
//					AlertSLEUtil.Error("市局已经审核，分局无权进行再次审核。");
//				}else if(KConstants.SPZT_QXSH_YES.equals(spzt_old)){
//					AlertSLEUtil.Error("区县已经审核成功，等待市局审核。");
//				}else{
//					if ("yes".equals(spzt)) {
//						setSpzt=KConstants.SPZT_QXSH_YES;
//					} else {
//						setSpzt=KConstants.SPZT_QXSH_NO;
//					}
//				}
//			} else if (userLevel == 4) {// 市局审核
//				// 区县审核通过以后可进行市局审核
//				if (KConstants.SPZT_QXSH_YES.equals(spzt_old)) {// 区县通过后市局审核
//					if ("yes".equals(spzt)) {
//						setSpzt=KConstants.SPZT_SUCCESS;
//					} else {
//						setSpzt=KConstants.SPZT_SJSH_NO;
//					}
//				}else if (KConstants.SPZT_QXSH_NO.equals(spzt_old)) {//区县未通过
//					AlertSLEUtil.Error("区县审核尚未通过，请等待。");
//				}else if(KConstants.SPZT_SJSH_YES.equals(spzt_old)|| KConstants.SPZT_SUCCESS.equals(spzt_old)||KConstants.SPZT_SJSH_NO.equals(spzt_old)){
//					AlertSLEUtil.Error("市局已经审核，无需再次进行审核。");
//				}else {
//					AlertSLEUtil.Error("区县尚未审核，请等待。");
//				}
//			} else {
//				AlertSLEUtil.Error("您对该街路巷小区没有审核权限，请联系管理员。");
//			}
//		}else if("4".equals(pzdm)){//只需要市局审核
//			if (userLevel == 4) {// 市局审核
//				if(KConstants.SPZT_SUCCESS.equals(spzt_old)||KConstants.SPZT_SUCCESS.equals(spzt_old)){//最终审核，市局审核已通过则无需审核
//					AlertSLEUtil.Error("审核已通过，无需再次审核。");
//				}else{
//					if ("yes".equals(spzt)) {
//						 setSpzt=KConstants.SPZT_SUCCESS;
//					} else {
//						setSpzt=KConstants.SPZT_SJSH_NO;
//					}
//				}
//			} else {
//				AlertSLEUtil.Error("您对该街路巷小区没有审核权限，请联系管理员。");
//			}
//		}else if ("0".equals(pzdm)) {
//			AlertSLEUtil.Error("该街路巷小区不需要审核。");
//		}
//		
//		return setSpzt;
//	}
}
