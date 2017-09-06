package com.kingmon.project.common.log.view;

import java.io.Serializable;
import java.util.List;

public class LogOperateClobView implements Serializable{
//  {"操作描述":"管理员用户查询",
//	"数据表":"admin_user",
//	"涉及数据":[{"参数":1,"参数类型":"com.sdwangge.policecloud.model.admin.AdminUserModel","值":{"Id":"null","Enabled":"null","Admin_validity_end":"null","Orgna_id":"37","Opratetime":"null","Zp":"null","Admin_loginname":"","Admin_id":"null","Admin_name":"","Admin_sfzh":"null","Area_id":"null","Porgna_id":"null","Ssfj":"null","Sspcs":"null","Ssjwq":"null","User_level":"2","Keyid":"null","Admin_validity_start":"null","Admin_password":"null","Admin_jh":"null","Admin_desc":"null","Order":"null","Sort":"null","Rows":"10","Ssjb":"null","Pager":"com.sdwangge.policecloud.utils.Pager@6a228db7","Ssjwq_id":"null","Endtime":"null","OrgStr":"null","IsSY":"null","MaxX":"null","MaxY":"null","Ssfj_id":"null","Sspcs_id":"null","OrgStrLen":"0","XzqhStr":"null","Starttime":"null","Page":"1","MinX":"null","MinY":"null","Class":"class com.sdwangge.policecloud.model.admin.AdminUserModel"}}]}

	private String 操作描述;
	private String 数据表;
	private List<LogOperateClobParamView> 涉及数据;
	public String get操作描述() {
		return 操作描述;
	}
	public void set操作描述(String 操作描述) {
		this.操作描述 = 操作描述;
	}
	public String get数据表() {
		return 数据表;
	}
	public void set数据表(String 数据表) {
		this.数据表 = 数据表;
	}
	public List<LogOperateClobParamView> get涉及数据() {
		return 涉及数据;
	}
	public void set涉及数据(List<LogOperateClobParamView> 涉及数据) {
		this.涉及数据 = 涉及数据;
	}
	
}
