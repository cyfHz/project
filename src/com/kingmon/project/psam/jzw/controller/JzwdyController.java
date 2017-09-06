//package com.kingmon.project.psam.jzw.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.kingmon.base.data.DataSet;
//import com.kingmon.base.data.ParamObject;
//import com.kingmon.base.service.BaseService;
//import com.kingmon.base.util.FastjsonUtil;
//import com.kingmon.base.web.KBaseController;
//import com.kingmon.common.annon.AuthWidgetRule;
//import com.kingmon.project.psam.jzw.serivice.IJzwdyService;
//@Controller
//@RequestMapping("/psam/jzwdy")
//public class JzwdyController extends KBaseController{
//	
//	private static final String prefix = "psam/jzw/";
//	
//	@Autowired
//	private IJzwdyService jzwdyService;
//	
////----------------------------------loadJzwdyGridview-----------------------------------------
//	@AuthWidgetRule(value="jzwdy",desc="建筑物单元管理",operateType="R",refTable="DZ_JZWDY")
//	@RequestMapping(value = {"/",""},method=RequestMethod.GET)
//	public String enterJzwdyPage() {
//		return prefix + "jzwdyDataGrid";
//	}
//	@AuthWidgetRule(value="jzwdy.loadJzwdyGridView",desc="建筑物单元管理",operateType="W",refTable="DZ_JZWDY")
//	@RequestMapping(value = "/loadJzwdyDataGrid",method=RequestMethod.POST)
//	@ResponseBody
//	public Object loadJzwdyGridView(@ModelAttribute("paramObject") ParamObject paramObject) {
//		DataSet dataSet = jzwdyService.loadJzwdyDataSet(paramObject);
//		return FastjsonUtil.convert(dataSet);
//	}
//}
