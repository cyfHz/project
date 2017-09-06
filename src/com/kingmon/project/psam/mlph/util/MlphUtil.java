package com.kingmon.project.psam.mlph.util;

import org.springframework.util.StringUtils;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.AlertSLEUtil;

public class MlphUtil {
	public static void main(String[] args){
		String x="371626400000";
		System.out.println(x.substring(0, 4));
		System.out.println(x.substring(0, 6));
		System.out.println(x.substring(0, 9));
	}
	
	
	/**
	 * 门楼牌号最终审核结果
	 * @param pzdm 配置代码 pzdm:1派出所申请 2：区县审核  3：区县+市局审核  4：市局审核
	 * @param userLevel 用户级别
	 * @param flag 门楼牌号的审核配置
	 * @param spzt_old 审核结果
	 * @return 审核状态码
	 */
	public static String Mplspzt(String pzdm,int userLevel,String flag,String spzt_old){
	
		String spzt="0";
		if ("2".equals(pzdm)) {//只需要区县审核
			if (userLevel==6) {
				//区县审核
				//如果已通过最终审核或者市局已经审核，则区县不能审核，否则可以审核
				if(KConstants.SPZT_SJSH_YES.equals(spzt_old) || KConstants.SPZT_SUCCESS.equals(spzt_old)||KConstants.SPZT_SJSH_NO.equals(spzt_old)){
					AlertSLEUtil.Error("市局已经审核，分局无权进行再次审核。");
				}else if(KConstants.SPZT_QXSH_YES.equals(spzt_old)){
					AlertSLEUtil.Error("区县已经审核成功，无需再次进行审核。");
				}else{
					if ("yes".equals(flag)) {
						spzt = KConstants.SPZT_SUCCESS;
					}else{
						spzt = KConstants.SPZT_QXSH_NO;
					}
				}
				
			}else{
				AlertSLEUtil.Error("您对该门楼牌号没有审核权限，请联系管理员。");
			}
		}else if ("3".equals(pzdm)) {//需要市局和区县审核
			if (userLevel==6) {//区县审核
				//如果已通过最终审核或者市局已经审核 则区县不能再次审核,否则 可以审核
				if(KConstants.SPZT_SJSH_YES.equals(spzt_old) || KConstants.SPZT_SUCCESS.equals(spzt_old)||KConstants.SPZT_SJSH_NO.equals(spzt_old)){
					AlertSLEUtil.Error("市局已经审核，分局无权进行再次审核。");
				}else if(KConstants.SPZT_QXSH_YES.equals(spzt_old)){
					AlertSLEUtil.Error("区县已经审核成功，等待市局审核。");
				}else{
					if ("yes".equals(flag)) {
						spzt=KConstants.SPZT_QXSH_YES;
					}else{
						spzt=KConstants.SPZT_QXSH_NO;
					}
				}
			}else if (userLevel==4) {//市局审核
				//区县审核通过以后可进行市局审核	
				if(KConstants.SPZT_QXSH_YES.equals(spzt_old)){
					if ("yes".equals(flag)) {
						spzt=KConstants.SPZT_SUCCESS ;
					}else{
						spzt=KConstants.SPZT_SJSH_NO;
					}
				}else if (KConstants.SPZT_QXSH_NO.equals(spzt_old)){
					AlertSLEUtil.Error("区县审核尚未通过，请等待。");
				}else if(KConstants.SPZT_SJSH_YES.equals(spzt_old)||KConstants.SPZT_SJSH_NO.equals(spzt_old)||KConstants.SPZT_SUCCESS.equals(spzt_old)){
					AlertSLEUtil.Error("市局已经审核，无需再次进行审核。");
				}else {
					AlertSLEUtil.Error("区县尚未审核，请等待。");
				}
	
			}else{
				AlertSLEUtil.Error("您对该门楼牌号没有审核权限，请联系管理员。");
			}
		}else if("4".equals(pzdm)){//只需市局审核
			if (userLevel==4) {
				//市局审核
				//最终审核，市局审核已通过则无需审核
				if(KConstants.SPZT_SJSH_YES.equals(spzt_old) || KConstants.SPZT_SUCCESS.equals(spzt_old)){
					AlertSLEUtil.Error("审核已通过，无需再次审核。");
				}else{
					if ("yes".equals(flag)) {
						spzt = KConstants.SPZT_SUCCESS;
					}else{
						spzt = KConstants.SPZT_SJSH_NO;
					}
				}
				
			}else{
				AlertSLEUtil.Error("您对该门楼牌号没有审核权限，请联系管理员。");
			}
		}else if ("0".equals(pzdm)) {
			AlertSLEUtil.Error("该门楼牌号不需要审核。");
//			jlx.setSpzt(KConstants.SPZT_SUCCESS);
		}
		return spzt;
	}
}
