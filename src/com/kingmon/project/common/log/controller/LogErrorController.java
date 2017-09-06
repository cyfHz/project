package com.kingmon.project.common.log.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.common.log.model.LogError;
import com.kingmon.project.common.log.service.ILogErrorService;

@Controller
@RequestMapping("/common/log/logError")
public class LogErrorController extends KBaseController{
	private static final String prefix="common/log/logError/";
	@Autowired
	private ILogErrorService errorService;
	
	@AuthWidgetRule(value="logError",desc="详细错误日志管理",operateType="R",refTable="APP_LOG_ERROR",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value={"/",""})
    public String enterLogErrorPage(){
		return prefix+"logErrorDataGrid";
    }
	
	@AuthWidgetRule(value="logError.enterLogErrorDetail",desc="系统错误日志详细",operateType="W",refTable="APP_LOG_ERROR",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value="/enterLogErrorDetail")
    @ResponseBody
    public Object enterLogErrorDetail(Model model,String id){
		LogError logError=errorService.findLogErrorByid(id);
		setDataAttribute(model,logError, "logError");
	   return prefix+"logErrorClobDetail";
    }
	
	@AuthWidgetRule(value="logError.loadLogErrorDataGrid",desc="查询错误日志信息",operateType="W",refTable="APP_LOG_ERROR",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value="/loadLogErrorDataGrid")
    @ResponseBody
    public Object loadLogErrorDataGrid(@RequestParam Map<String,String> params){
    	DataSet dataSet= errorService.loadLogErrorDataSet(params);
	   return FastjsonUtil.convert(dataSet);
    }
}
