package com.kingmon.project.common.log.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.common.log.mapper.LogOperateMapper;
import com.kingmon.project.common.log.model.LogOperate;
import com.kingmon.project.common.log.service.ILogOperateService;

@Service
public class LogOperateServiceImpl extends BaseService implements ILogOperateService{

	@Autowired
	private  LogOperateMapper operateMapper;
	
	@Async(value="logExecutor")
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addLogOperate(SessionUser sessionUser,AuthWidgetRule authWidgetRule,
							Map<String,String> parameterKeyAndType,
							Map<String, String[]> parameterKeyAndvalue) {
		try {
			LogOperate operate = new LogOperate(sessionUser,authWidgetRule);
	        operate.setLog_text(genClobText(authWidgetRule,parameterKeyAndvalue));
			operateMapper.addLogOperate(operate);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	@Async(value="logExecutor")
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addLogOperate(SessionUser sessionUser,AuthWidgetRule authWidgetRule,
							Map<String, String[]> parameterKeyAndvalue) {
		try {
			LogOperate operate = new LogOperate(sessionUser,authWidgetRule);
	        operate.setLog_text(genClobText(authWidgetRule,parameterKeyAndvalue));
			operateMapper.addLogOperate(operate);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	/*
	 * //	        {"操作描述":"管理员用户查询",
//	        	"数据表":"admin_user",
	        
//	        	"涉及数据":[
//	        				{"参数":1,
//	        					"参数类型":"com.sdwangge.policecloud.model.admin.AdminUserModel",
//	        					"值":{"Id":"null","Enabled":"null","Admin_validity_end":"null","Orgna_id":"37","Opratetime":"null","Zp":"null","Admin_loginname":"","Admin_id":"null","Admin_name":"","Admin_sfzh":"null","Area_id":"null","Porgna_id":"null","Ssfj":"null","Sspcs":"null","Ssjwq":"null","User_level":"2","Keyid":"null","Admin_validity_start":"null","Admin_password":"null","Admin_jh":"null","Admin_desc":"null","Order":"null","Sort":"null","Rows":"10","Ssjb":"null","Pager":"com.sdwangge.policecloud.utils.Pager@6a228db7","Ssjwq_id":"null","Endtime":"null","OrgStr":"null","IsSY":"null","MaxX":"null","MaxY":"null","Ssfj_id":"null","Sspcs_id":"null","OrgStrLen":"0","XzqhStr":"null","Starttime":"null","Page":"1","MinX":"null","MinY":"null","Class":"class com.sdwangge.policecloud.model.admin.AdminUserModel"}}]}

	 */
	private String genClobText(AuthWidgetRule authWidgetRule,Map<String, String[]> parameterKeyAndvalue){
		  StringBuffer clobText=new StringBuffer("");
	        String trs= JSON.toJSONString(parameterKeyAndvalue);
	        clobText.append("\"{操作描述\":").append("\""+authWidgetRule.desc()+"\",");
	        clobText.append("\"数据表\":").append("\""+authWidgetRule.refTable()+"\",");
	        clobText.append("\"涉及数据\":").append("["+trs+"]");
	      return clobText.toString();
	}

	@Override
	public DataSet loadLogOperateDataSet(Map<String, String> params) {
		PaginationUtil.initPageAndSort(params);
		String user_loginname = params.get("user_loginname");
		if(user_loginname!=null&&!user_loginname.isEmpty()){
			params.put("user_loginname", "%"+user_loginname+"%");
		}else{
			params.remove("user_loginname");
		}
		String start_time = params.get("start_time");
		if (start_time != null && !start_time.isEmpty()) {
			params.put("start_time",start_time);
		}else{
			params.remove("start_time");
		}
		String end_time = params.get("end_time");
		if (end_time != null && !end_time.isEmpty()) {
			params.put("end_time",  end_time);
		}else{
			params.remove("end_time");
		}
		return new DataSet(operateMapper.selectLogOperateCount(params),operateMapper.selectLogOperateList(params));
	}

	@Override
	public LogOperate findLogOperateByid(String id) {
		
		return operateMapper.selectByPrimaryKey(id);
	}

}
