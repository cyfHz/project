package com.kingmon.project.webservice.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.webservice.common.model.ServiceBzdzRejectIp;
import com.kingmon.project.webservice.common.service.BzdzRejectIpService;
@Controller
@RequestMapping("/psam/webservice/bzdzRejectIp")
public class BzdzRejectIpController extends KBaseController{
	
	private String prefix="/psam/webservice/bzdzRejectIp/";
	
	@Autowired
	private BzdzRejectIpService bzdzRejectIpService;
	
	@AuthWidgetRule(value="bzdzRejectIp",desc="限制IP管理",operateType="R",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET)
	public String enterBzdzRejectIp() {
		return prefix + "bzdzRejectIpDataGrid";
	}

	@AuthWidgetRule(value="bzdzRejectIp.loadBzdzLogAll",desc="WEB限制IP列表加载",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadBzdzRejectIpAll", method = RequestMethod.POST)
	@ResponseBody
	public DataSet loadBzdzRejectIpAll(@RequestParam  Map<String,String> params ,HttpServletRequest request){
		return bzdzRejectIpService.rejectIpList(params);
	}

	@AuthWidgetRule(value="bzdzRejectIp.addRejectIp",desc="WEB添加限制IP信息",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterAddBzdzRejectIp", method = RequestMethod.GET)
	public String enterAddBzdzRejectIp(Model model) {
		setDataAttribute(model, SecurityUtils.getUserName(), "tjr");
		setDataAttribute(model, SecurityUtils.getUserOrgName(), "tjdw");
		return prefix + "bzdzRejectIpAdd";
	}
	
	@AuthWidgetRule(value="bzdzRejectIp.addRejectIp",desc="WEB添加限制IP信息",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/addRejectIp", method = RequestMethod.POST)
	@ResponseBody
	public Object addRejectIp(@RequestParam Map<String,Object> params){
		bzdzRejectIpService.addIp(params);
		return ajaxDoneSuccess("数据添加成功！");
	}
	
	@AuthWidgetRule(value="bzdzRejectIp.saveXzjkRejectIp",desc="WEB修改限制接口信息",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterRejectIpXzjk", method = RequestMethod.GET)
	public String enterRejectIpXzjk(String ipid,Model model) {
		ServiceBzdzRejectIp rejectIp=bzdzRejectIpService.selectById(ipid);
		setDataAttribute(model, rejectIp, "rejectIp");
		return prefix + "bzdzRejectIpXzjk";
	}
	
	@AuthWidgetRule(value="bzdzRejectIp.saveXzjkRejectIp",desc="WEB修改限制接口信息",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/saveXzjkRejectIp", method = RequestMethod.POST)
	@ResponseBody
	public Object saveXzjkRejectIp(@RequestParam Map<String,Object> params){
		bzdzRejectIpService.saveXzjkRejectIp(params);
		return ajaxDoneSuccess("限制接口修改成功！");
	}
	
	@AuthWidgetRule(value="bzdzRejectIp.updateIpSfyx",desc="WEB修改限制IP信息",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/updateIpSfyx", method = RequestMethod.POST)
	@ResponseBody
	public Object updateIpSfyx(String ipid,String sfyy,String ip){
		bzdzRejectIpService.removeIp(ipid,sfyy,ip);
		return ajaxDoneSuccess("IP限制是否有效修改成功！");
	}
	
}
