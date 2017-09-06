package com.kingmon.project.common.log.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.kingmon.base.data.DataSet;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.common.log.model.Log;

public interface ILogService {
	
    public void addLog(AuthWidgetRule authWidgetRule,
			//HandlerMethod handlerMethod,
			HttpServletRequest request,
			Map<String,String> parameterKeyAndType,
			Map<String, String[]> parameterKeyAndvalue);
    
    public DataSet loadLogDataSet(Map<String,String> param);
    
    public Log findLogByid(String id);
}
