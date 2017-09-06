package com.kingmon.project.webservice.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.webservice.common.service.BzdzLogService;

@Controller
@RequestMapping("/psam/webservice/bzdzlog")
public class BzdzLogController extends KBaseController{

	private String prefix="/psam/webservice/bzdzlog/";
	@Autowired
	private BzdzLogService bzdzLogService;
	
	@AuthWidgetRule(value="bzdzlog",desc="日志管理",operateType="R",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET)
	public String enterBzdzUser() {
		return prefix + "bzdzlogDataGrid";
	}

	@AuthWidgetRule(value="bzdzlog.loadBzdzLogAll",desc="WEB日志列表加载",operateType="W",refTable="SERVICE_BZDZ_USER",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadBzdzLogAll", method = RequestMethod.POST)
	@ResponseBody
	public DataSet loadBzdzLogAll(@RequestParam  Map<String,String> params ,HttpServletRequest request){
		return bzdzLogService.bzdzLogList(params);
	}
}
