package com.kingmon.project.auth.organizationuser.controller;

import java.util.Map;

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
import com.kingmon.base.util.KAssert;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.kingmon.project.auth.rule.service.IWidgetruleService;

@Controller
@RequestMapping("/auth/organizationUser")
public class OrganizationUserController extends KBaseController {
	private static final String prefix = "auth/organizationUser/";

	@Autowired
	private IOrganizationUserService orgUserService;
	@Autowired
	private IWidgetruleService widgetruleService;

	@AuthWidgetRule(value="orgUser",desc="系统用户管理",operateType="R",refTable="APP_ORGANIZATION_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" })
	public String enterOrgUser() {
		return prefix + "orgUserDataGrid";
	}
	
	@AuthWidgetRule(value="orgUser.loadOrgUserGrid",desc="用户列表加载",operateType="W",refTable="APP_ORGANIZATION_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadOrgUserGrid",method=RequestMethod.POST)
	@ResponseBody
	public Object loadOrgUserGrid(@ModelAttribute("paramObject") ParamObject paramObject) {
		DataSet dataSet = orgUserService.loadorgUserDataSet(paramObject);
		return FastjsonUtil.convert(dataSet);
	}
	
//------------------------------------------------------------------------
	
	@AuthWidgetRule(value="orgUser.loadUserInfo",desc="用户个人信息加载",operateType="W",refTable="APP_ORGANIZATION_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadUserInfo", method = RequestMethod.GET)
	public Object loadUserInfo(Model model) {
		SessionUser user = SecurityUtils.getSessionUser();
		KAssert.notNull(user, "账户未登录，请重新登录");
		Map<String, Object> userInfo = orgUserService.loadUserInfoByUserId(user.getUserId());
		setDataAttribute(model, userInfo, "userInfo");
		return prefix+"userInfo";
	}
	
	@AuthWidgetRule(value="orgUser.changePassWord",desc="修改密码",operateType="W",refTable="APP_ORGANIZATION_USER",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterChangePassWord", method = RequestMethod.GET)
	public String enterChangePassWord(Model model, String userId) {
		if (userId == null) {
			userId = SecurityUtils.getSessionUser().getUserId();
		}
		OrganizationUser user = orgUserService.findByUserId(userId);
		setDataAttribute(model, user, "organizationUser");
		return prefix + "changePassword";
	}

//	@AuthWidgetRule(value="orgUser.changePassWord",desc="修改密码",operateType="W",refTable="APP_ORGANIZATION_USER",crudType=KConstants.OPER_UPDATE)
//	@RequestMapping(value = "/changePassWord", method = RequestMethod.POST)
//	@ResponseBody
//	public Object changePassWord(String userId, String oldPass, String newPass) {
//		orgUserService.changePassword(userId, oldPass, newPass);
//		return ajaxDoneSuccess("修改成功");
//	}
	
//-------------------分配角色------------------------------------------
	@AuthWidgetRule(value="orgUser.roleAssign",desc="用户个人信息加载",operateType="W",refTable="APP_ROLE_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterRoleAssign", method = RequestMethod.GET)
	public Object enterRoleAssign(Model model,String appuser_id) {
		setDataAttribute(model, appuser_id, "appuser_id");
		return prefix+"roleAssign";
	}
	
	@AuthWidgetRule(value="orgUser.roleAssign",desc="加载用户没有的角色",operateType="W",refTable="APP_ROLE_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadRoleUserNotHaveDataSet",method=RequestMethod.POST)
	@ResponseBody
	public Object loadRoleUserNotHaveDataSet(@ModelAttribute("paramObject") ParamObject paramObject,String appuser_id) {
		DataSet dataSet = orgUserService.loadRoleUserNotHaveDataSet(paramObject, appuser_id);
		return FastjsonUtil.convert(dataSet);
	}
	
	@AuthWidgetRule(value="orgUser.roleAssign",desc="加载用户拥有的角色",operateType="W",refTable="APP_ROLE_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadRoleUserHaveDataSet",method=RequestMethod.POST)
	@ResponseBody
	public Object loadRoleUserHaveDataSet(@ModelAttribute("paramObject") ParamObject paramObject,String appuser_id) {
		DataSet dataSet = orgUserService.loadRoleUserHaveDataSet(paramObject,appuser_id);
		return FastjsonUtil.convert(dataSet);
	}
	
	@AuthWidgetRule(value="orgUser.addRoleToUser",desc="分配角色给用户",operateType="W",refTable="APP_ROLE_USER",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/addRoleToUser", method = RequestMethod.POST)
	@ResponseBody
	public Object addRoleToUser(String[] roleIds, String appuser_id) {
		orgUserService.addRoleToUser(roleIds, appuser_id);
		return ajaxDoneSuccess("操作成功!");
	}
	
	@AuthWidgetRule(value="orgUser.removeRoleFromUser",desc="删除该用户的角色",operateType="W",refTable="APP_ROLE_USER",crudType=KConstants.OPER_DELETE)
	@RequestMapping(value = "/removeRoleFromUser", method = RequestMethod.POST)
	@ResponseBody
	public Object removeRoleFromUser(String[] roleIds, String appuser_id) {
		orgUserService.removeRoleFromUser(roleIds, appuser_id);
		return ajaxDoneSuccess("操作成功!");
	}
}
