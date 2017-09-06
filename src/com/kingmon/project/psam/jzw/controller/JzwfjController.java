package com.kingmon.project.psam.jzw.controller;

import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.jzw.model.Jzwfj;
import com.kingmon.project.psam.jzw.serivice.IJzwfjService;
import com.kingmon.project.psam.jzw.view.JzwfjView;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.model.Syjwry;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkService;
import com.kingmon.project.psam.sy.service.ISyRkglLdrkdjbService;
import com.kingmon.project.psam.sy.service.ISyjwryService;
@Controller
@RequestMapping("/psam/jzwfj")
public class JzwfjController extends KBaseController{
	private static final String prefix = "psam/jzw/";
	@Autowired
	private IJzwfjService jzwfjService;
	@Autowired
	private ISyRkglCzrkService  syRkglCzrkService;
	@Autowired
	private ISyRkglLdrkdjbService  syLdrkService;
	@Autowired
	private ISyjwryService  syjwryService;
	
	@AuthWidgetRule(value="jzwfj",desc="建筑物房间管理",operateType="R",refTable="DZ_JZWFJ",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/",""})
	public String enterJzwFjManage() {
		return prefix + "jzwfjDataGrid";
	}
	@AuthWidgetRule(value="jzwfj.jzwfjList",desc="建筑物房间数据列表查询",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/jzwfjList", method = RequestMethod.POST)
	@ResponseBody
	public Object jzwfjList(@ModelAttribute("paramObject") ParamObject paramObject) {
		DataSet dataSet = jzwfjService.loadJzwfjInfoDataSet(paramObject);
		return FastjsonUtil.convert(dataSet);
	}
	@AuthWidgetRule(value="jzwfj.addJzwfj",desc="建筑物房间添加",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddJzwfj", method = RequestMethod.GET)
	public String enterAddJzwfj() {
		return prefix + "jzwFjAdd";
	}
	
	
	
	@AuthWidgetRule(value="jzwfj.loadJzwfj",desc="加载建筑物房间列表",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadJzwfj", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJzwfj(String jzwjgId) {
		List<Jzwfj> list = jzwfjService.findJzwfjByJzwjgId(jzwjgId);
		return new KJSONMSG(200,"数据加载成功", list);
	}
	
	@AuthWidgetRule(value="jzwfj.chaiFen",desc="建筑物房间拆分",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/chaiFen", method = RequestMethod.POST)
	@ResponseBody
	public Object chaiFen(@RequestBody JzwfjView view){
		Map<String, Object> map = jzwfjService.chaiFen(view);
		return new KJSONMSG(200,"房间拆分成功", map);
	}
	@AuthWidgetRule(value="jzwfj.heBing",desc="建筑物房间合并",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/heBing", method = RequestMethod.POST)
	@ResponseBody
	public Object heBing(@RequestBody JzwfjView view){
		Map<String, Object> map = jzwfjService.heBing(view);
		return new KJSONMSG(200,"房间合并成功", map);
	}
	//var parm={"refJzwFjId":targetIdArray[0] ,"jzwId":jzwjgId,"addfjmc":addfjxh,"addfjxh":addfjxh};
	@AuthWidgetRule(value="jzwfj.addJzwFj",desc="建筑物房间合并",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/addJzwFj", method = RequestMethod.POST)
	@ResponseBody
	public Object addJzwFj(@RequestParam Map<String,Object> map){
		jzwfjService.AddJzwFj(map);
		return ajaxDoneSuccess("房间添加成功");
	}
	
	@AuthWidgetRule(value="jzwfj.deleteJzwfj",desc="建筑物房间删除",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_DELETE)
	@RequestMapping(value = "/checkCanDelete", method = RequestMethod.POST)
	@ResponseBody
	public Object checkCanDelete(String jzwfjId){
		return jzwfjService.checkCanDelete(jzwfjId);
	}
	
	@AuthWidgetRule(value="jzwfj.deleteJzwfj",desc="建筑物房间删除",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_DELETE)
	@RequestMapping(value = "/deleteJzwfj", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteJzwfj(String jzwfjId){
		jzwfjService.deleteJzwFj(jzwfjId);
		return new KJSONMSG(200,"房间删除成功", null);
	}
	
	/*
	 * 查询房间是否有人员信息
	 */
	@RequestMapping(value = "/selectRkxxForFw", method = RequestMethod.POST)
	@ResponseBody
	public KJSONMSG selectRkxxForFw(String fwid){
		List<SyRkglCzrk> czrk=syRkglCzrkService.selectSyCzrkInfoByJzwfjIdKey(fwid);//常住
		List<SyRkglLdrkdjb> ldrk=syLdrkService.selectSyLdrkInfoByJzwfjIdKey(fwid);//流动
		List<Syjwry> jwry=syjwryService.selectJwryInfoByJzwfjIdKey(fwid);//境外
		KJSONMSG json =  new KJSONMSG();
		if(czrk!=null && czrk.size()>0 || ldrk!=null 
				&& ldrk.size()>0 || jwry!=null && jwry.size()>0){
			json=new KJSONMSG(200, "当前房屋含有人员信息");
		}else{
			json=new KJSONMSG(201, "");
		}
		return json;
	}
	
	//房屋拆分合并时查看房屋是否存在人员信息
	@RequestMapping(value = "/selcetRkForCfHb", method = RequestMethod.POST)
	@ResponseBody
	public KJSONMSG selcetRkForCfHb(String[] jzwfjIds){
		List<SyRkglCzrk> czrk = Lists.newArrayList();
		List<SyRkglLdrkdjb> ldrk = Lists.newArrayList();
		List<Syjwry> jwry = Lists.newArrayList();
		KJSONMSG json =  new KJSONMSG();
		long count = 0;
		for(String fwid : jzwfjIds){
			czrk=syRkglCzrkService.selectSyCzrkInfoByJzwfjIdKey(fwid);//常住
			ldrk=syLdrkService.selectSyLdrkInfoByJzwfjIdKey(fwid);//流动
			jwry=syjwryService.selectJwryInfoByJzwfjIdKey(fwid);//境外
			count=count+czrk.size()+ldrk.size()+jwry.size();
		}
		if(count>0){
			json=new KJSONMSG(200, "当前房屋含有人员信息");
		}else{
			json=new KJSONMSG(201, "");
		}
		return json;
	}
}
