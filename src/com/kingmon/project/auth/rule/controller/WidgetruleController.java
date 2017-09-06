package com.kingmon.project.auth.rule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.auth.rule.service.IWidgetruleService;

@Controller
@RequestMapping("/auth/widgetrule")
public class WidgetruleController  extends KBaseController{
	private static final String prefix = "auth/rule/widgetrule/";
	@Autowired
	private IWidgetruleService widgetruleService;

	@AuthWidgetRule(value="widgetrule",desc="系统操作管理",operateType="R",refTable="APP_WIDGETRULE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" })
	public String enterOrgPgisArea() {
		return prefix + "/widgetruleDataGrid";
	}
	
	@AuthWidgetRule(value="widgetrule.loadWidgetRuleDataGrid",desc="系统操作数据列表加载",operateType="W",refTable="APP_WIDGETRULE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadWidgetRuleDataGrid",method=RequestMethod.POST)
	@ResponseBody
	public Object loadWidgetRuleDataGrid(@ModelAttribute("paramObject") ParamObject paramObject) {
		DataSet dataSet = widgetruleService.loadWidgetRuleDataset(paramObject);
		return FastjsonUtil.convert(dataSet);
	}
	
	@RequestMapping(value = "/scan",method=RequestMethod.GET)
	@ResponseBody
	public Object scan() {
		 widgetruleService.scanWidgetrule("com.kingmon.project");;
		return FastjsonUtil.convert(null);
	}
}
