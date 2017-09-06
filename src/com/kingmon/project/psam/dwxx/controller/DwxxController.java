package com.kingmon.project.psam.dwxx.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.web.KBaseController;
import com.kingmon.project.psam.dwxx.model.Dwxx;
import com.kingmon.project.psam.dwxx.service.IDwxxFwService;
import com.kingmon.project.psam.dwxx.service.IDwxxService;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;

@Controller
@RequestMapping("/psam/dwxx")
public class DwxxController extends KBaseController{

	@Autowired
	private IDwxxService dwxxService;
	@Autowired
	private IDwxxFwService dwxxFwService;
	
	private String prefix="psam/dwxx/";
	
	
	@RequestMapping(value = "/validateLoadDwxxAccInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object validateLoadDwxxAccInfo(String jzwfjid, Model model){
		Dwxx dwxx = dwxxService.loadFwDwxxAccInfo(jzwfjid);
		return new KJSONMSG(200,"数据加载成功", dwxx);
	}
	
	@RequestMapping(value = "/enterDwxx",method=RequestMethod.GET)
	public String enterDwxx(String jzwfjid, Model model) {
		setDataAttribute(model, jzwfjid, "jzwfjid");
		return prefix+"dwxx";
	}
	
	@RequestMapping(value = "/loadDwjbxxAccInfo",method=RequestMethod.GET)
	public String loadDwjbxxAccInfo(String jzwfjid, Model model) {
		Dwxx dwxx = dwxxService.loadFwDwxxAccInfo(jzwfjid);
		setDataAttribute(model, dwxx, "dwxx");
		return prefix+"dwxxAccquisition";
	}
	
	@RequestMapping(value="/saveDwjbxxAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object saveDwjbxxAccInfo( @ModelAttribute("dwxx") Dwxx dwxx){
		dwxxService.saveDwjbxxAccInfo(dwxx);
		return ajaxDoneSuccess("操作成功");
	}
	
	
	@RequestMapping(value="/loadJzwFwDwxxForFjid",method=RequestMethod.POST)
	@ResponseBody
	public Object loadJzwFwDwxxForFjid(@RequestParam Map<String,String> params){
		DataSet dwxx=dwxxService.loadJzwFwDwxxForFjid(params);//单位信息
		return dwxx;
	}
	
	@RequestMapping(value="/loadJzwFwLsDwxxForFjid",method=RequestMethod.POST)
	@ResponseBody
	public Object loadJzwFwLsDwxxForFjid(@RequestParam Map<String,String> params){
		DataSet lsdwxx=dwxxService.loadJzwFwLsDwxxForFjid(params);//历史单位
		return lsdwxx;
	}
	
	
	@RequestMapping(value="/revokeFwDwxx",method=RequestMethod.POST)
	@ResponseBody
	public Object revokeFwDwxx(@RequestParam Map<String,Object> params){
		dwxxService.revokeFwDwxx(params);//
		return ajaxDoneSuccess("删除成功");
	}
	
	@RequestMapping(value="/enterFwDwxxDetail",method=RequestMethod.GET)
	public String enterFwDwxxDetail(String dwid,Model model){
		Dwxx dwxx = dwxxService.selectDwXxById(dwid);
		setDataAttribute(model, dwxx, "dwxx");
		return prefix+"dwxxDetail";
	}
	
	@RequestMapping(value="/loadDwxxFj",method=RequestMethod.POST)
	@ResponseBody
	public Object loadDwxxFj(String jzwId){
		List<String> fjidList=dwxxFwService.selectDwxxFjByJzwId(jzwId);
		String resType="dwxx";
		Map<String,Object> reMap=Maps.newHashMap();
		reMap.put("resType", resType);
		reMap.put("fjidList", fjidList);
		KJSONMSG msg=new KJSONMSG(200,"数据加载成功", reMap);
		return msg;
	}
	
	//房屋 单位分类
	@RequestMapping(value="/loadDwxxFjForLb",method=RequestMethod.POST)
	@ResponseBody
	public Object loadDwxxFjForLb(@RequestParam Map<String,String> params){//String jzwId,String table_value
		String resType=params.get("table_value");
		List<String> fjidList=dwxxFwService.selectDwxxFjByJzwIds(params);
//		System.err.println("table_value:--------"+resType+"------"+fjidList.size());
		Map<String,Object> reMap=Maps.newHashMap();
		reMap.put("resType", resType);
		reMap.put("fjidList", fjidList);
		KJSONMSG msg=new KJSONMSG(200,"数据加载成功", reMap);
		return msg;
	}
}
