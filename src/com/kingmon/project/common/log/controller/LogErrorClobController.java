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
import com.kingmon.project.common.log.model.LogErrorClob;
import com.kingmon.project.common.log.service.ILogErrorClobService;

@Controller
@RequestMapping("/common/log/logErrorClob")
public class LogErrorClobController extends KBaseController{
	private static final String prefix="common/log/logErrorClob/";
	@Autowired
	private ILogErrorClobService clobService;
	
	
	@AuthWidgetRule(value="logErrorClob",desc="错误日志管理",operateType="R",refTable="APP_LOG_ERROR_CLOB",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value={"/",""})
    public String enterLogErrorClobPage(){
		return prefix+"logErrorClobDataGrid";
    }
	
	@AuthWidgetRule(value="logErrorClob.enterLogErrorClobDetail",desc="系统错误日志详细",operateType="W",refTable="APP_LOG_ERROR_CLOB",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value="/enterLogErrorClobDetail")
    @ResponseBody
    public Object enterLogErrorClobDetail(Model model,String id){
		LogErrorClob logErrorClob=clobService.findLogErrorClobByid(id);
		setDataAttribute(model,logErrorClob, "logErrorClob");
	   return prefix+"enterlogErrorClobDetail";
    }
	
	@AuthWidgetRule(value="logErrorClob.loadLogErrorClobDataGrid",desc="错误日志数据列表",operateType="W",refTable="APP_LOG_ERROR_CLOB",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value="/loadLogErrorClobDataGrid")
    @ResponseBody
    public Object loadLogErrorClobDataGrid(@RequestParam Map<String,String> params){
    	DataSet dataSet= clobService.loadLogErrorClobDataSet(params);
	   return FastjsonUtil.convert(dataSet);
    }
}
