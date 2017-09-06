package com.kingmon.project.webservice.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.webservice.common.model.ServiceBzdzUser;
import com.kingmon.project.webservice.common.service.BzdzUserService;


@Controller
@RequestMapping("/psam/webservice/bzdzuser")
public class BzdzUserController extends KBaseController {
	private static final String prefix = "psam/webservice/bzdzuser/";

	@Autowired
	private BzdzUserService bzdzUserService;
	
	@AuthWidgetRule(value="bzdzuser",desc="用户管理",operateType="R",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET)
	public String enterBzdzUser() {
		return prefix + "bzdzuserDataGrid";
	}

	@AuthWidgetRule(value="bzdzuser.loadBzdzUserAll",desc="WEB用户列表加载",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadBzdzUserAll", method = RequestMethod.POST)
	@ResponseBody
	public DataSet loadBzdzUserAll(@RequestParam  Map<String,String> params ,HttpServletRequest request){
		return bzdzUserService.bzdzuserList(params);
	}
	
	@AuthWidgetRule(value="bzdzuser.addUser",desc="WEB用户添加",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterAddBzdzUser", method = RequestMethod.GET)
	public String enterAddBzdzUser() {
		return prefix + "bzdzuserAdd";
	}
	
	@AuthWidgetRule(value="bzdzuser.addUser",desc="WEB用户添加",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public Object addUser(@ModelAttribute("serviceBzdzUser")ServiceBzdzUser serviceBzdzUser){
		bzdzUserService.addBzdzUser(serviceBzdzUser);
		return ajaxDoneSuccess("数据添加成功！");
	}
	
	@AuthWidgetRule(value="bzdzuser.saveUser",desc="WEB更新用户",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateBzdzUser", method = RequestMethod.GET)
	public String enterUpdateBzdzUser(String systemid, Model model) {
		ServiceBzdzUser bzdzuser=bzdzUserService.selectByPrimaryKey(systemid);
		setDataAttribute(model, bzdzuser, "bzdzuser");
		return prefix + "bzdzuserEdit";
	}
	
	@AuthWidgetRule(value="bzdzuser.saveUser",desc="WEB更新用户",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public Object saveUser(@ModelAttribute("serviceBzdzUser")ServiceBzdzUser serviceBzdzUser){
		bzdzUserService.updateBzdzUser(serviceBzdzUser);
		return ajaxDoneSuccess("数据更新成功！");
	}
	
	@AuthWidgetRule(value="bzdzuser.reviewUser",desc="WEB审核用户",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/reviewUser", method = RequestMethod.POST)
	@ResponseBody
	public Object reviewUser(String systemid,String spzt){
		bzdzUserService.reviewUser(systemid, spzt);
		return ajaxDoneSuccess("操作成功！");
	
		
	}
	
	@AuthWidgetRule(value="bzdzuser.unlockedUser",desc="WEB解锁用户",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterJsBzdzUser", method = RequestMethod.GET)
	public String enterJsBzdzUser(String systemid, Model model) {
		ServiceBzdzUser bzdzuser=bzdzUserService.selectByPrimaryKey(systemid);
		setDataAttribute(model, bzdzuser, "bzdzuser");
		return prefix + "bzdzuserjs";
	}
	
	@AuthWidgetRule(value="bzdzuser.unlockedUser",desc="WEB解锁用户",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/unlockedUser", method = RequestMethod.POST)
	@ResponseBody
	public Object unlockedUser(@RequestParam  Map<String,Object> params){
		bzdzUserService.unlockedUser(params);
		return ajaxDoneSuccess("解锁成功！");
	}
	
	@AuthWidgetRule(value="bzdzuser.lockedUser",desc="WEB锁定用户",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterSdBzdzUser", method = RequestMethod.GET)
	public String enterSdBzdzUser(String systemid, Model model) {
		ServiceBzdzUser bzdzuser=bzdzUserService.selectByPrimaryKey(systemid);
		setDataAttribute(model, bzdzuser, "bzdzuser");
		return prefix + "bzdzusersd";
	}
	
	@AuthWidgetRule(value="bzdzuser.lockedUser",desc="WEB锁定用户",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/lockedUser", method = RequestMethod.POST)
	@ResponseBody
    public Object lockedUser(@RequestParam  Map<String,Object> params){
    	bzdzUserService.lockedUser(params);
		return ajaxDoneSuccess("锁定成功！");
	}
	
	@AuthWidgetRule(value="bzdzuser.deleteUser",desc="WEB删除用户",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
    public Object deleteUser(String systemid){
    	bzdzUserService.deleteBzdzUser(systemid);
		return ajaxDoneSuccess("删除成功！");
	}
}
