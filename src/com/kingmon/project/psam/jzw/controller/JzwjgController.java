package com.kingmon.project.psam.jzw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;
import com.kingmon.project.psam.jzw.model.Jzwjg;
import com.kingmon.project.psam.jzw.serivice.IJzwfjService;
import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
import com.kingmon.project.psam.jzw.serivice.IJzwjgPicService;
import com.kingmon.project.psam.jzw.serivice.IJzwjgService;
@Controller
@RequestMapping("/psam/jzwjg")
public class JzwjgController extends KBaseController{
	
	private static final String prefix = "psam/jzw/";
	
	@Autowired
	private IJzwjgService jzwjgService;
	@Autowired
	private IJzwjbxxService jzwjbxxService;
	@Autowired
	private IJzwfjService jzwfjService;
	@Autowired
	private IJzwjgPicService jzwjgPicService;

	
//----------------------------------结构采集等，或者 展示-----------------------------------------
	/**
	 * 
	 * @param jzwId
	 * @param model
	 * @return 200：结构信息存在（正确） 301：结构信息不存在，300： 结构信息错误
	 */
	@AuthWidgetRule(value="jzwjbxx.jzwjg",desc="建筑物结构信息是否存在",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value ="/checkIsHaveJg",method=RequestMethod.POST)
	@ResponseBody
	public Object checkIsHaveJg(String jzwId,Model model) {
//		Jzwjg jzwjg=jzwjgService.findJzwjgByJzwId(jzwId);
//		if(jzwjg==null){
//			 KJSONMSG msg=new KJSONMSG(301,"建筑物结构信息不存在 ");
//			return msg;
//		}else{
//			KJSONMSG msg=jzwjgService.validateJzwjgByJzwId(jzwId);
//			//KJSONMSG msg=new KJSONMSG(200,"建筑物结构信息存在 ");
//			return msg;
//		}
		
		KJSONMSG msg=jzwjgService.validateJzwjgByJzwId(jzwId);
		return msg;
	}
	
	@AuthWidgetRule(value="jzwjbxx.showjzwjg",desc="建筑物结构展示",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value ="/enterShowJzwjg",method=RequestMethod.GET)
	public String enterShowJzwjg(Model model,String jzwId) {
		Jzwjg jzwjg=jzwjgService.findJustJzwjgByJzwId(jzwId);
		setDataAttribute(model, jzwjg.getJzwjgid(), "jzwjgId");
		return prefix + "jzwjg";
	}
	/**
	 * 建筑物结构信息存在跳转信息页面
	 * @param jzwId
	 * @param model
	 * @return
	 */
	@AuthWidgetRule(value="jzwjbxx.enterSeachJzwjg",desc="查看建筑物结构",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value ="/enterSeachJzwjg",method=RequestMethod.GET)
	public String enterSeachJzwjg(String jzwId,Model model) {
		Jzwjg jzwjg=jzwjgService.findJustJzwjgByJzwId(jzwId);
		if(jzwjg!=null){
			setDataAttribute(model, jzwjg.getJzwjgid(), "jzwjgId");
		}else{
			String msg="未查询到该建筑物下的结构信息";
			setDataAttribute(model, msg, "msg");
			return prefix + "info";
		}
		return prefix + "jzwjg.seach";
	}
	/**
	 * 建筑物结构信息不存在跳转提示页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/enterNoJg",method=RequestMethod.GET)
	public String enterNoJg(Model model){
		String msg="未查询到该建筑物下的结构信息";
		setDataAttribute(model, msg, "msg");
		return prefix + "info";
	}
	/**
	 * @param jzwId
	 * @param jzwType 01:楼层规整楼房,筒子楼; 02:不规整楼房; 03:平房(别墅、自建小楼、独立平房、四合院平房)
	 * @param model
	 * @return
	 */
	@AuthWidgetRule(value="jzwjbxx.addjzwjg",desc="建筑物结构添加",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/enterJzwjgAddPage",method=RequestMethod.GET)
	public String enterJzwjgAddPage(String jzwId,String jzwType,Model model) {
		Jzwjbxx jzwjbxx=jzwjbxxService.getJzwJbxxById(jzwId);
		if(jzwjbxx==null){
			String msg="未查询到该建筑物信息";
			setDataAttribute(model, msg, "msg");
			return prefix + "info";
		}
		String zaglssjwzrqdm = jzwjbxx.getZaglssjwzrqdm();
		String levelStr = SecurityUtils.getUserLevelStr();
		int level = SecurityUtils.getUserLevel();
		if (zaglssjwzrqdm == null || zaglssjwzrqdm.length() < 12) {
			String msg = "该建筑物所属警务区代码为空，不能进行结构数据维护";
			setDataAttribute(model, msg, "msg");
			return prefix + "info";
		}
		if (!zaglssjwzrqdm.substring(0, level).startsWith(levelStr)) {
			String msg = "该建筑物不在当前用户管辖范围内，不能进行结构数据维护";
			setDataAttribute(model, msg, "msg");
			return prefix + "info";
		}
		if("01".equals(jzwType)){//01:楼层规整楼房,筒子楼;
			setDataAttribute(model, jzwjbxx, "jzwjbxx");
			return prefix + "jzwjg.add.regular";
		}else if("02".equals(jzwType)){//02:不规整楼房
			setDataAttribute(model, jzwjbxx, "jzwjbxx");
			return prefix + "jzwjg.add.notregular";
		}else  if("03".equals(jzwType)){//03:平房(别墅、自建小楼、独立平房、四合院平房)
			setDataAttribute(model, jzwjbxx, "jzwjbxx");
			return prefix + "jzwjg.add.single";
		}else{
			String msg="位置建筑结构类型";
			setDataAttribute(model, msg, "msg");
			return prefix + "info";
		}
	}
	
	@SuppressWarnings("rawtypes")
	@AuthWidgetRule(value="jzwjbxx.addjzwjg",desc="建筑物结构添加",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/addJzwjg",method=RequestMethod.POST)
	@ResponseBody
	public Object addJzwjg(@RequestBody Map view) {
		jzwjgService.addAnddBuildJzwjg(view);
		return new KJSONMSG(200,"添加成功", null);
	}
	
	
//----------加载建筑物结构加载iframe------------------------------------------------	
	@AuthWidgetRule(value="jzwjbxx.showjzwjg",desc="进入建筑物结构加载页面",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/enterJzwjgIframe", method = RequestMethod.GET)
	public Object enterJzwjgIframe(Model model,String jzwjgId) {
		String jzwId=jzwjgService.findJustJzwIdByJgid(jzwjgId);
		setDataAttribute(model, jzwjgId, "jzwjgId");
		setDataAttribute(model, jzwId, "jzwId");
		return prefix + "jzwjgfj";
	}	
//----------加载建筑物结构数据------------------------------------------------
	@AuthWidgetRule(value="jzwjbxx.showjzwjg",desc="建筑物结构加载",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/loadJzwjg", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJzwjg(String jzwjgId,String isJustJg) {
		Map<String,Object> map=new HashMap<String, Object>();
		if("1".equals(isJustJg)){
			Jzwjg jzwjg=jzwjgService.findValidateAndBuildJzwjgById(jzwjgId);
			map.put("jzwjg", jzwjg);
		}else{
			Jzwjg jzwjg=jzwjgService.findValidateAndBuildJzwjgById(jzwjgId);
			List<Map<String,Object>> list = jzwfjService.findFjMapByJzwjgId(jzwjgId);
			map.put("jzwjg", jzwjg);
			map.put("jzwfj", list);
		}
		return new KJSONMSG(200,"数据加载成功", map);
	}
	
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="检查是否可以添加房间",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/checkCanAdd", method = RequestMethod.POST)
	@ResponseBody
	public Object checkCanAdd(String refJzwFjId,String jzwId,boolean isUpDown){
		return jzwjgService.canAdd(refJzwFjId,jzwId,isUpDown);
	}
	
//--------------------------------建筑物结构基本信息和照片信息--------------------------	
//	@AuthWidgetRule(value="jzwjbxx.showjzwjg",desc="建筑物信息",operateType="W",refTable="SY_FWJBXX")
//	@RequestMapping(value = "/findJzwjgjbxxByJzwjgId", method = RequestMethod.POST)
//	@ResponseBody 
//	public Object findJzwjgjbxxByJzwjgId(String jzwjgid){
//		Jzwjg jgxx=jzwjgService.findValidateAndBuildJzwjgById(jzwjgid);
//		List <String> jgpicList=jzwjgPicService.loadjgPicIdsByjgId(jzwjgid);
//		Map<String,Object> data=Maps.newHashMap();
//		data.put("jgxx", jgxx);
//		data.put("jgpicIds", jgpicList);
//		KJSONMSG msg=new KJSONMSG(200, "数据加载成功", data);
//		return msg;
//	}
//--------------------------------单元楼层--------------------------------------

	//params={"jg_dys":jg_dys,"jg_lcs":jg_lcs,"jg_mdyms":jg_mdyms,"jg_dxcs":jg_dxmcms};
	@AuthWidgetRule(value="jzwjbxx.updateJzwjgDyLcXh",desc="更新建筑物单元楼层信息",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/updateJzwjg",method=RequestMethod.POST)
	@ResponseBody
	public Object updateJzwjg( @RequestParam Map<String,Object> view) {
		jzwjgService.updateJzwjg(view);
		return new KJSONMSG(200,"保存成功", null);
	}
	
	@AuthWidgetRule(value="jzwjbxx.loadJzwjgDyLcInfo",desc="加载建筑物单元楼层信息页面",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value="/enterJzwjgDyLcInfo",method = RequestMethod.GET)
	public Object enterJzwjgDyLcInfo(Model model,String jzwid){
		Jzwjg jg=jzwjgService.findJustJzwjgByJzwId(jzwid);
		if(jg==null){
			setDataAttribute(model, "该建筑物不存在结构信息", "msg");
			return prefix+"info";
		}
		String isBuild=jg.getIsbuild();
		String isValid=jg.getIsvalid();
		
		setDataAttribute(model, jg.getJzwjgid(), "jzwjgid");
		setDataAttribute(model, jg, "jzwjg");
		setDataAttribute(model, isBuild, "isBuild");
		setDataAttribute(model, isValid, "isValid");
		//isBuild="2";
		if("1".equals(isBuild)){
			return prefix+"jzwjgDyLcXH_isBuild";
		}else{
			return prefix+"jzwjgDyLcXH_invalide";
		}
	}
	
	@AuthWidgetRule(value="jzwjbxx.loadJzwjgDyLcInfo",desc="加载建筑物单元楼层信息数据",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value="/loadJzwjgDyLcInfo",method = RequestMethod.POST)
	@ResponseBody
	public Object loadJzwjgDyLcInfo(@RequestParam  Map<String,String> params){
		DataSet dataSet =jzwjgService.loadJzwjgDyLcInfo(params);
		return dataSet;
	}
	
	//{"id":row.id,"xh":row.xh,"mc":row.mc,"lcs":row.lcs,"jgtype":row.jgtype};
	@AuthWidgetRule(value="jzwjbxx.updateJzwjgDyLcXh",desc="更新建筑物单元楼层信息",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/updateJzwjgDyLcXh",method=RequestMethod.POST)
	@ResponseBody
	public Object updateJzwjgDyLcXh( @RequestParam Map<String,Object> view) {
	//	jzwjgService.addJzwjg(view);
		jzwjgService.updateJzwjgDyLcXh_20160521(view);
		return new KJSONMSG(200,"保存成功", null);
	}
//--------------------------------单元楼层--------------------------------------
	//var params={"jzwjgid":jzwjgid,"dy_xh":dy_xh,"dy_mc":dy_mc,"dy_lcs":dy_lcs,"dy_ms":dy_ms};
	@AuthWidgetRule(value="jzwjbxx.updateJzwjgDyLcXh",desc="更新建筑物单元楼层信息",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/addJzwjgDy",method=RequestMethod.POST)
	@ResponseBody
	public Object addJzwjgDy(@RequestParam Map<String,Object> view) {
//		jzwjgService.addJzwjgDy(view);
		jzwjgService.addJzwjgDy_20160521(view);
		
		return new KJSONMSG(200,"保存成功", null);
	}
	
	@AuthWidgetRule(value="jzwjbxx.updateJzwjgDyLcXh",desc="更新建筑物单元楼层信息",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/delJzwjgDy",method=RequestMethod.POST)
	@ResponseBody
	public Object delJzwjgDy(String jzwjgid,String dyid) {
//		jzwjgService.delJzwjgDy(jzwjgid,dyid);
		jzwjgService.delJzwjgDy_20160521(jzwjgid,dyid);
		
		return new KJSONMSG(200,"删除成功");
	}
	//--------------------------------------------------------------------------------------------
	//{"jzwjgid":jzwjgid,"jzwjgDyid":ss_dyid,"lc_xh":lc_xh,"lc_mc":lc_mc};
	@AuthWidgetRule(value="jzwjbxx.updateJzwjgDyLcXh",desc="更新建筑物单元楼层信息",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/addJzwjgLc",method=RequestMethod.POST)
	@ResponseBody
	public Object addJzwjgLc(@RequestParam Map<String,Object> view) {
		jzwjgService.addJzwjgLc(view);
		return new KJSONMSG(200,"保存成功");
	}
	//{"jzwjgid":jzwjgid,"jzwjgDyid":ss_dyid,"max_lc_xh":max_lc_xh,"default_lc_mc":default_lc_mc};
	@AuthWidgetRule(value="jzwjbxx.updateJzwjgDyLcXh",desc="更新建筑物单元楼层信息",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/batchAddJzwjgLc",method=RequestMethod.POST)
	@ResponseBody
	public Object batchAddJzwjgLc(@RequestParam Map<String,Object> view) {
		jzwjgService.batchAddJzwjgLc(view);
		return new KJSONMSG(200,"保存成功", null);
	}
	@AuthWidgetRule(value="jzwjbxx.updateJzwjgDyLcXh",desc="更新建筑物单元楼层信息",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/delJzwjgLc",method=RequestMethod.POST)
	@ResponseBody
	public Object delJzwjgLc(String jzwjgid,String lcid) {
		jzwjgService.delJzwjgLc(jzwjgid, lcid);
		return new KJSONMSG(200,"删除成功", null);
	}	
	
	@AuthWidgetRule(value="jzwjbxx.updateJzwjgDyLcXh",desc="更新建筑物单元楼层信息",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/delJzwjgfj",method=RequestMethod.POST)
	@ResponseBody
	public Object delJzwjgfj(String jzwjgid,String fjid) {
		jzwjgService.delJzwjgfj(jzwjgid, fjid);
		return new KJSONMSG(200,"删除成功", null);
	}
}
