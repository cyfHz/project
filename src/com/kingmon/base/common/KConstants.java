package com.kingmon.base.common;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kingmon.base.util.UUIDUtil;
public final class KConstants {

	private KConstants() {
	}

	/**
	 * 
	 */
	public static final String PROFILE_PROD = "prod"; 
	
	/**
	 * 
	 * 
	 */
	public static final String LOGIN_USER = "LOGIN_USER_IN_SESSION";
	
	
	
	public static final int SC_200 = 200;// Success
	public static final int SC_300 = 300;// Service_Error
	public static final int SC_401 = 401;// Auth_Error
	public static final int SC_402 = 402;// no_role_Error
	public static final int SC_500 = 500;// Server_Error
	public static final int SC_301 = 301;// timeout
	
	public static final int SC_601 = 601;// 重复提交
	
	public static final String RESPONSE_MSG_KEY  = "kjsonmsg";
	
	
	public static final String SESSIONUSER ="sessionuser";
	
	/** 撤销标记 1*/
	public static final String CXBJ_1="1";
	/** 撤销标记 0*/
	public static final String CXBJ_0="0";
	
	/**使用状态代码  使用中:10 */
	public static final String SYTZDM_INUSE="10";
	/**使用状态代码  停用:20 */
	public static final String SYTZDM_STOPUSE="20";
	/**使用状态代码  未启用:30 */
	public static final String SYTZDM_UNUSE="30";
	/**使用状态代码  维修:40 */
	public static final String SYTZDM_REPAIR="40";
	/**使用状态代码  报废:50 */
	public static final String SYTZDM_STOP="50";
	/**使用状态代码  其他:90 */
	public static final String SYTZDM_OTHER="90";
	
	public static final String GENERATED_UUID=UUIDUtil.uuid();
	
	/**房间标志位 被拆分:1*/
	public static final String CHANGESIGN_CHAIFEN ="1";
	/**房间标志位 被合并:2*/
	public static final String CHANGESIGN_HEBING ="2";
	
	public static final int jzwHeight=70;
	public static final int jzwWidth=120;
	
	public static final List<String> ALLOW_UPLOAD_IMAGE_TYPE=Arrays.asList(new String[]{"jpg","jpeg","bmp","gif","JPG","JPEG","BMP","GIF"});
	
	/**建筑物结构检验通过：1*/
	public static final String ISVALID_JZWJG_YES = "1";
	/**建筑物结构检验不通过：2*/
	public static final String ISVALID_JZWJG_NO = "2";
	
	
	/**建筑物中所有房间坐标已生成：1 */
	public static final String ISBUILD_JZWJG = "1";
	
	
	public static final String DEV_DATA_PROCESS_DATABASE="database";
	public static final String DEV_DATA_PROCESS_ELASTIC="elastic";
	
	public static final String DEV_DATA_PROCESS_CHECK="check";
	
	public static final String WIDGET_IGNOR ="widget_ignor";
	public static final String WIDGET_PUBLIC ="widget_public";
	
	public static final String PSAM_RESOURCE_ROOTCODE= "PSAM_RESOURCE_PARENT_ROOT";
	public static final String PSAM_RESOURCE_PUBLICCODE= "PSAM_RESOURCE_PARENT_PUBLIC";
	public static final String PSAM_RESOURCE_IGNORCODE= "PSAM_RESOURCE_PARENT_IGNOR";
	
//	public static final String  LOGIN_LOG_UUID="LOGIN_LOG_UUID";
	
	public static final String OPER_ADD = "添加";
	public static final String OPER_DELETE = "删除";
	public static final String OPER_UPDATE = "编辑";
	public static final String OPER_SEARCH = "查询";
	public static final String VIEW_QRCODE = "显示二维码";
	
	public static final int USER_LEVEL_JWQ = 12;//警务区
	public static final int USER_LEVEL_PCS= 10;//派出所
	public static final int USER_LEVEL_FJ = 6;//分局
	public static final int USER_LEVEL_SJ = 4;//市局
	public static final int USER_LEVEL_ST = 2;//省厅
	
	
	/**审批状态 未审核：0*/
	public static final String SPZT_INIT = "0";
	/**审批状态 区县审核不通过：1*/
	public static final String SPZT_QXSH_NO = "1";
	/**审批状态 区县审核通过：2*/
	public static final String SPZT_QXSH_YES = "2";
	/**审批状态 市局审核不通过：3*/
	public static final String SPZT_SJSH_NO = "3";
	/**审批状态 市局审核通过：4*/
	public static final String SPZT_SJSH_YES = "4";
	/**审批状态 最终审核通过：5*/
	public static final String SPZT_SUCCESS = "5";
	
	public static final String authIgnorUser = "admin";
	
	public static final String session_mode_memory="MEMORY";
	
	public static final String session_mode_springSession="SPRING_SESSION";
	
	public static final String session_mode_selfRedisRepository="SELF_REDIS_REPOSITORY";
	
	public static final String WEB_SERVICE_AUTH_SESSION="WEB_SERVICE_AUTH_SESSION";
	
	
	public static final  SerializerFeature[] serializerFeatures = {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullListAsEmpty};
	
	public static final String cacheable_prefix ="##CACHEABLE_PREFIX##";
	
	public static final List<String> org_type=Arrays.asList(new String[]{"st","sj","fj","pcs","jwq"}); 
	
	public static final List<String> org_only_type=Arrays.asList(new String[]{"st","sj","fj","pcs"}); 
}
