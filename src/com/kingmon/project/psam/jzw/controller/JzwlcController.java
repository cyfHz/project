package com.kingmon.project.psam.jzw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.jzw.model.Jzwlc;
import com.kingmon.project.psam.jzw.serivice.IJzwlcService;
@Controller
@RequestMapping("/psam/jzwlc")
public class JzwlcController extends KBaseController{
private static final String prefix = "psam/jzw/";
	
	@Autowired
	private IJzwlcService jzwlcService;
	
//----------------------------------loadJzwlcGridview-----------------------------------------
//	@AuthWidgetRule(value="jzwlc",desc="建筑物楼层管理",operateType="R",refTable="DZ_JZWLC")
//	@RequestMapping(value = {"/",""},method=RequestMethod.GET)
//	public String enterJzwlcPage() {
//		return prefix + "jzwlcDataGrid";
//	}
//	@AuthWidgetRule(value="jzwlc.loadJzwlcGridView",desc="建筑物楼层管理",operateType="W",refTable="DZ_JZWLC")
//	@RequestMapping(value = "/loadJzwlcDataGrid",method=RequestMethod.POST)
//	@ResponseBody
//	public Object loadJzwlcGridView(@ModelAttribute("paramObject") ParamObject paramObject) {
//		DataSet dataSet = jzwlcService.loadJzwlcDataSet(paramObject);
//		return FastjsonUtil.convert(dataSet);
//	}
	@RequestMapping(value = "/loadJzwlcByJzwJgid",method=RequestMethod.POST)
	@ResponseBody
	public Object loadJzwlcByJzwJgid(String jzwjgId){
		List<Jzwlc> list=jzwlcService.loadJzwlcByJzwJgid(jzwjgId);
		return list;
	}
}
