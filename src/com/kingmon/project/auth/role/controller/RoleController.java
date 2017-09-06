package com.kingmon.project.auth.role.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.kingmon.project.auth.role.service.IRoleService;
import com.kingmon.project.auth.rule.service.IWidgetruleService;
@Controller
@RequestMapping("/auth/role")
public class RoleController extends KBaseController {
	private static final String prefix = "auth/role/";
	
	
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IWidgetruleService widgetruleService;
	
	@AuthWidgetRule(value="role",desc="系统角色管理",operateType="R",refTable="APP_ROLE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" })
	public String enterRoleManage() {
		return prefix + "roleDataGrid";
	}
	
	@AuthWidgetRule(value="role.loadRoleDataGrid",desc="用户角色列表",operateType="W",refTable="APP_ROLE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadRoleDataGrid",method=RequestMethod.POST)
	@ResponseBody
	public Object loadRoleDataGrid(@ModelAttribute("paramObject") ParamObject paramObject) {
		DataSet dataSet = roleService.loadRoleDataSet(paramObject);
		return FastjsonUtil.convert(dataSet);
	}
//----------------------------------------------------------------------------------------------------------------------------------------------	
	@AuthWidgetRule(value="role.resourceAssign",desc="资源分配",operateType="W",refTable="APP_ROLE_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterResourceAssign", method = RequestMethod.GET)
	public Object enterResourceAssign(Model model,String role_id) {
		setDataAttribute(model, role_id, "role_id");
		return prefix+"resourceAssign";
	}

	@AuthWidgetRule(value="role.loadResourceRoleNotHaveDataSet",desc="加载角色没有的资源",operateType="W",refTable="APP_ROLE_RESOURCE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadResourceRoleNotHaveDataSet",method=RequestMethod.POST)
	@ResponseBody
	public Object loadResourceRoleNotHaveDataSet(@ModelAttribute("paramObject") ParamObject paramObject,String role_id) {
		DataSet dataSet = roleService.loadResourceRoleNotHaveDataSet(paramObject, role_id);
		return FastjsonUtil.convert(dataSet);
	}
	@AuthWidgetRule(value="role.loadResourceRoleHaveDataSet",desc="加载角色拥有的资源",operateType="W",refTable="APP_ROLE_RESOURCE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadResourceRoleHaveDataSet",method=RequestMethod.POST)
	@ResponseBody
	public Object loadResourceRoleHaveDataSet(@ModelAttribute("paramObject") ParamObject paramObject,String role_id) {
		DataSet dataSet = roleService.loadResourceRoleHaveDataSet(paramObject,role_id);
		return FastjsonUtil.convert(dataSet);
	}
	
	@AuthWidgetRule(value="role.addResourceToRole",desc="分配资源给角色",operateType="W",refTable="APP_ROLE_RESOURCE",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/addResourceToRole", method = RequestMethod.POST)
	@ResponseBody
	public Object addRoleToRole(String[] resourceIds, String role_id) {
		roleService.addResourceToRole(resourceIds, role_id);
		return ajaxDoneSuccess("操作成功!");
	}
	
	@AuthWidgetRule(value="role.removeResourceFromRole",desc="删除该角色的资源",operateType="W",refTable="APP_ROLE_RESOURCE",crudType=KConstants.OPER_DELETE)
	@RequestMapping(value = "/removeResourceFromRole", method = RequestMethod.POST)
	@ResponseBody
	public Object removeResourceFromRole(String[] resourceIds, String role_id) {
		roleService.removeResourceFromRole(resourceIds, role_id);
		return ajaxDoneSuccess("操作成功!");
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@AuthWidgetRule(value="role.widgetRuleAssign",desc="控件分配",operateType="W",refTable="APP_ROLE_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterWidgetRuleAssign", method = RequestMethod.GET)
	public Object enterWidgetRuleAssign(Model model,String role_id) {
		setDataAttribute(model, role_id, "role_id");
		return prefix+"widgetRuleAssign";
	}
	
	
	@AuthWidgetRule(value="role.loadWidgetRuleRoleNotHaveDataSet",desc="加载角色没有的控件",operateType="W",refTable="APP_ROLE_RES_WRULE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadWidgetRuleRoleNotHaveDataSet",method=RequestMethod.POST)
	@ResponseBody
	public Object loadWidgetRuleRoleNotHaveDataSet(@ModelAttribute("paramObject") ParamObject paramObject,String role_id) {
		DataSet dataSet = roleService.loadWidgetRuleRoleNotHaveDataSet(paramObject, role_id);
		return FastjsonUtil.convert(dataSet);
	}
	@AuthWidgetRule(value="role.loadWidgetRuleRoleHaveDataSet",desc="加载角色拥有的控件",operateType="W",refTable="APP_ROLE_RES_WRULE",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadWidgetRuleRoleHaveDataSet",method=RequestMethod.POST)
	@ResponseBody
	public Object loadWidgetRuleRoleHaveDataSet(@ModelAttribute("paramObject") ParamObject paramObject,String role_id) {
		DataSet dataSet = roleService.loadWidgetRuleRoleHaveDataSet(paramObject,role_id);
		return FastjsonUtil.convert(dataSet);
	}
	
	@AuthWidgetRule(value="role.addWidgetRuleToRole",desc="分配控件给角色",operateType="W",refTable="APP_ROLE_RES_WRULE",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/addWidgetRuleToRole", method = RequestMethod.POST)
	@ResponseBody
	public Object addWidgetRuleToRole(String[] widgetRuleIds, String role_id) {
		roleService.addWidgetRuleToRole(widgetRuleIds, role_id);
		return ajaxDoneSuccess("操作成功!");
	}
	
	@AuthWidgetRule(value="role.removeWidgetRuleFromRole",desc="删除角色的控件",operateType="W",refTable="APP_ROLE_RES_WRULE",crudType=KConstants.OPER_DELETE)
	@RequestMapping(value = "/removeWidgetRuleFromRole", method = RequestMethod.POST)
	@ResponseBody
	public Object removeWidgetRuleFromRole(String[] widgetRuleIds, String role_id) {
		roleService.removeWidgetRuleFromRole(widgetRuleIds, role_id);
		return ajaxDoneSuccess("操作成功!");
	}
}
