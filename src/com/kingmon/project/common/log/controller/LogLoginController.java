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
import com.kingmon.project.common.log.model.LogLogin;
import com.kingmon.project.common.log.service.ILogLoginService;

@Controller
@RequestMapping("/common/log/logLogin")
public class LogLoginController extends KBaseController{

	private static final String prefix="common/log/logLogin/";
	@Autowired
	private ILogLoginService logLoginService;
	
	@AuthWidgetRule(value="logLogin",desc="登陆日志信息",operateType="R",refTable="APP_LOG_ERROR",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value={"/",""})
    public String enterLogLoginPage(){
		return prefix+"logLoginDataGrid";
    }
	
	@AuthWidgetRule(value="logLogin.enterLogLoginDetail",desc="登陆日志详细",operateType="W",refTable="APP_LOG",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value="/enterLogLoginDetail")
    @ResponseBody
    public Object enterLogLoginDetail(Model model,String id){
		LogLogin logLogin=logLoginService.findLogLoginByid(id);
		setDataAttribute(model,logLogin, "LogLogin");
	   return prefix+"logLoginDetail";
    }
	
	@AuthWidgetRule(value="logLogin.loadLogLoginDataGrid",desc="登陆日志信息",operateType="W",refTable="APP_LOG_ERROR",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value="/loadLogLoginDataGrid")
    @ResponseBody
    public Object loadLogLoginDataGrid(@RequestParam Map<String,String> params){
    	DataSet dataSet= logLoginService.loadLogLoginDataSet(params);
	   return FastjsonUtil.convert(dataSet);
    }
}
