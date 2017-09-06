package com.kingmon.project.common.log.service;

import java.util.Map;
import com.kingmon.base.data.DataSet;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.common.log.model.LogOperate;

public interface ILogOperateService {
	@Deprecated
	public void addLogOperate(
			SessionUser sessionUser,
			AuthWidgetRule authWidgetRule,
			Map<String,String> parameterKeyAndType,
			Map<String, String[]> parameterKeyAndvalue);
	
	public void addLogOperate(
			SessionUser sessionUser,
			AuthWidgetRule authWidgetRule,
			Map<String, String[]> parameterKeyAndvalue);
    //获取操作日志信息
    public DataSet loadLogOperateDataSet(Map<String,String> params);
    
    public LogOperate findLogOperateByid(String id);
}
