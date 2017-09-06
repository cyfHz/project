package com.kingmon.project.psam.dataStatistic.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.psam.dataStatistic.service.IDataStatisticService;

@Controller
@RequestMapping("/psam/dataStatistic")
public class DataStatisticController extends KBaseController{
	private static final String prefix = "psam/dataStatistic/";
	@Autowired
	private IDataStatisticService dataStatisticService;
	
	@Value(value="${statistic_data_elastic}")
	private String esStatistic;
	
//	@AuthWidgetRule(value="dataStatistic.enterDataStatistic",desc="进入采集统计页面",operateType="R",refTable="",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public Object enterDataStatistic( ) {
		return prefix+"dataNewStatistic";
		//return prefix+"dataStatistic";
	}
	@RequestMapping(value="/loadSjStatisticData",method=RequestMethod.POST)
	@ResponseBody
	public Object loadSjStatisticData(){
		List<Map<String,Object>> datax=Lists.newArrayList();
		datax=dataStatisticService.loadSjStatisticData();
		//List<Map<String,Object>> datax=dataStatisticService.loadSjStatisticData();
		KJSONMSG msg=new KJSONMSG(200,"数据加载成功", datax);
		return msg;
	}
	@RequestMapping(value="/enterFjDataStatistic",method=RequestMethod.GET)
	public Object enterFjDataStatistic(Model model, String sjName){
		setDataAttribute(model, sjName, "sjName");
		return prefix+"dataFjStatistic";
	}
	
	@RequestMapping(value="/loadFjStatisticData",method=RequestMethod.POST)
	@ResponseBody
	public Object loadFjStatisticData(String sjName){
		List<Map<String,Object>> datax=dataStatisticService.loadFjStatisticData(sjName);
		KJSONMSG msg=new KJSONMSG(200,"数据加载成功", datax);
		return msg;
	}
	
	@RequestMapping(value="/enterNewOneDataStatistic",method=RequestMethod.GET)
	public Object enterNewOneDataStatistic(Model model, String orgCode){
		setDataAttribute(model, orgCode, "orgCode");
		return prefix+"dataNewOneStatistic";
	}
	@RequestMapping(value="/enterNewTwoDataStatistic",method=RequestMethod.GET)
	public Object enterNewTwoDataStatistic(Model model, String orgCode){
		setDataAttribute(model, orgCode, "orgCode");
		return prefix+"dataNewTwoStatistic";
	}
	
	//-------------------------------------------

	@RequestMapping(value="/loadNewStatisticData",method=RequestMethod.POST)
	@ResponseBody
	public Object loadNewStatisticData(String orgCode,boolean isQueryChild){
		int level = SecurityUtils.getUserLevel();
		if(orgCode == null || orgCode.isEmpty()){
			orgCode = SecurityUtils.getUserOrgCode();
		}
		List<Map<String,Object>> datax = Lists.newArrayList();
		if(esStatistic.equals("elastic")){
			 datax=dataStatisticService.loadStatisticDataFromEs(orgCode,level,isQueryChild);
		}else{
			datax=dataStatisticService.loadNewStatisticData(orgCode,level,isQueryChild);
		}
		KJSONMSG msg=new KJSONMSG(200,"数据加载成功", datax);
		return msg;
	}
	
}
