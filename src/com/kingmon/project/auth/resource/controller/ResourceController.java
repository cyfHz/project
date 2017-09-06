package com.kingmon.project.auth.resource.controller;

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
import com.kingmon.project.auth.resource.service.IResourceService;
@Controller
@RequestMapping("/auth/resource")
public class ResourceController extends KBaseController{
	private static final String prefix = "auth/resource/";
	
	@Autowired
	private IResourceService resourceService;
	
 //@AuthWidgetRule(value="orgUser.loadUserInfo",desc="用户个人信息加载",operateType="W",refTable="APP_ORGANIZATION_USER",crudType=KConstants.OPER_SEARCH)
	
	@AuthWidgetRule(value="resource",desc="系统资源管理",operateType="R",refTable="APP_RESOURCE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" })
	public String enterOrgUser() {
		return prefix + "resourceTreeGrid";
	}
	
	@AuthWidgetRule(value="resource.loadResourceTreeGrid",desc="系统资源列表",operateType="W",refTable="APP_RESOURCE",crudType=KConstants.OPER_SEARCH)
	@ResponseBody
	@RequestMapping(value = "/loadResourceTreeGrid")
	public Object loadResourceTreeGrid(@ModelAttribute("paramObject")ParamObject paramObject) throws Exception {
		DataSet dataSet = resourceService.loadResourceTreeGrid(paramObject);
		return dataSet;
	}
	
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="系统资源树形数据加载",operateType="W",refTable="APP_RESOURCE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadResourceTree", method = RequestMethod.POST)
	@ResponseBody
	public Object loadResourceTree(String id) {
		DataSet dataSet= resourceService.loadResourceTree(id);
		return FastjsonUtil.convert(dataSet);
	}
	

}
