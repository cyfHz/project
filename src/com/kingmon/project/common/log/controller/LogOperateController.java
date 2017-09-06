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
import com.kingmon.project.common.log.model.LogOperate;
import com.kingmon.project.common.log.service.ILogOperateService;
@Controller
@RequestMapping("/common/log/logOperate")
public class LogOperateController extends KBaseController{
	private static final String prefix="common/log/logOperate/";
	@Autowired
	private ILogOperateService logOperateService;
	
	@AuthWidgetRule(value="logOperate",desc="操作日志管理",operateType="R",refTable="APP_LOG_OPERATE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value={"/",""})
    public String enterLogOperatePage(){
		return prefix+"logOperateDataGrid";
    }
	
	@AuthWidgetRule(value="logOperate.enterLogOperateDetail",desc="操作日志详细",operateType="W",refTable="APP_LOG_OPERATE",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value="/enterLogOperateDetail")
    @ResponseBody
    public Object enterLogOperateDetail(Model model,String id){
		LogOperate logOperate=logOperateService.findLogOperateByid(id);
		setDataAttribute(model,logOperate, "LogOperate");
	   return prefix+"logOperateDetail";
    }
	
	@AuthWidgetRule(value="logOperate.loadLogOperateDataGrid",desc="操作日志数据列表",operateType="W",refTable="APP_LOG_OPERATE",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value="/loadLogOperateDataGrid")
    @ResponseBody
    public Object loadLogOperateDataGrid(@RequestParam Map<String,String> params){
    	DataSet dataSet= logOperateService.loadLogOperateDataSet(params);
	   return FastjsonUtil.convert(dataSet);
    }
}
