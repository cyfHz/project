package com.kingmon.project.common.log.view;

import java.io.Serializable;
import java.util.Map;

public class LogOperateClobParamView implements Serializable{
//    {"操作描述":"管理员用户查询",
//	"数据表":"admin_user",
//	"涉及数据":[{"参数":1,"参数类型":"com.sdwangge.policecloud.model.admin.AdminUserModel","值":{"Id":"null","Enabled":"null","Admin_validity_end":"null","Orgna_id":"37","Opratetime":"null","Zp":"null","Admin_loginname":"","Admin_id":"null","Admin_name":"","Admin_sfzh":"null","Area_id":"null","Porgna_id":"null","Ssfj":"null","Sspcs":"null","Ssjwq":"null","User_level":"2","Keyid":"null","Admin_validity_start":"null","Admin_password":"null","Admin_jh":"null","Admin_desc":"null","Order":"null","Sort":"null","Rows":"10","Ssjb":"null","Pager":"com.sdwangge.policecloud.utils.Pager@6a228db7","Ssjwq_id":"null","Endtime":"null","OrgStr":"null","IsSY":"null","MaxX":"null","MaxY":"null","Ssfj_id":"null","Sspcs_id":"null","OrgStrLen":"0","XzqhStr":"null","Starttime":"null","Page":"1","MinX":"null","MinY":"null","Class":"class com.sdwangge.policecloud.model.admin.AdminUserModel"}}]}

	private String 参数;
	private String 参数类型;
	private Map<String,Object> 值;
	public String get参数() {
		return 参数;
	}
	public void set参数(String 参数) {
		this.参数 = 参数;
	}
	public String get参数类型() {
		return 参数类型;
	}
	public void set参数类型(String 参数类型) {
		this.参数类型 = 参数类型;
	}
	public Map<String, Object> get值() {
		return 值;
	}
	public void set值(Map<String, Object> 值) {
		this.值 = 值;
	}
	
	
}
