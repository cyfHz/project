package com.kingmon.project.psam.sy.controller;

import java.util.List;
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

import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.jzw.serivice.IJzwfjPicService;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.model.Syjwry;
import com.kingmon.project.psam.sy.service.ISyFwCzrkService;
import com.kingmon.project.psam.sy.service.ISyFwJwryService;
import com.kingmon.project.psam.sy.service.ISyFwLdrkService;
import com.kingmon.project.psam.sy.service.ISyFwjbxxService;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkService;
import com.kingmon.project.psam.sy.service.ISyRkglLdrkdjbService;
import com.kingmon.project.psam.sy.service.ISyjwryService;
import com.kingmon.project.psam.xzjd.model.Xzjd;
@Controller
@RequestMapping("/psam/fw")
public class SYFwjbxxController extends KBaseController{
	private static final String prefix = "psam/fw/";
	
	@Autowired
	private ISyFwjbxxService fwjbxxService;
	@Autowired
	IJzwfjPicService jzwfjPicService;
	@Autowired
	private ISyRkglCzrkService  syRkglCzrkService;
	@Autowired
	private ISyRkglLdrkdjbService  syLdrkService;
	@Autowired
	private ISyjwryService  syjwryService;
	
	
	
	@AuthWidgetRule(value="fw",desc="房屋管理",operateType="R",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String enterFwPage() {
		return "psam/sy/fwjbxx/fwDataGrid";
	}
	
	@AuthWidgetRule(value="fw.loadFwGridView",desc="房屋数据列表",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadFwGridGrid", method = RequestMethod.POST)
	public @ResponseBody DataSet loadFwGridView(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		
		return fwjbxxService.loadSYFwjbxxDataSet(params);
	}
//-------------------AccInfo-----------------------------------------------------------	
	@AuthWidgetRule(value="fw.saveFwjbxxAccInfo",desc="房屋信息采集",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/loadFwjbxxAccInfo",method=RequestMethod.GET)
	public Object loadFwjbxxAccInfo(String jzwfjid, Model model) {
		SyFwjbxx fwjbxx = fwjbxxService.loadFwjbxxAccInfo(jzwfjid);
		setDataAttribute(model, fwjbxx, "fwjbxx");
		return "psam/sy/fwjbxx/fwAccquisition";
	}
	
	@AuthWidgetRule(value="fw.saveFwjbxxAccInfo",desc="验证房屋信息是否完整",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/validateLoadFwjbxxAccInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object validateLoadFwjbxxAccInfo(String jzwfjid, Model model){
		SyFwjbxx fwjbxx = fwjbxxService.loadFwjbxxAccInfo(jzwfjid);
		return new KJSONMSG(200,"数据加载成功", fwjbxx);
	}
	
	@AuthWidgetRule(value="fw.saveFwjbxxAccInfo",desc="房屋信息采集",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/saveFwjbxxAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object saveFwjbxxAccInfo( @ModelAttribute("syFwjbxx")SyFwjbxx syFwjbxx){
		fwjbxxService.saveFwjbxxAccInfo(syFwjbxx);
		return ajaxDoneSuccess("操作成功");
	}
//-------------------AccInfo-----------------------------------------------------------		
	
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="房屋信息加载",operateType="W",refTable="SY_FWJBXX")
	@RequestMapping(value = "/enterFwJbxx",method=RequestMethod.GET)
	public String enterFwJbxx(String jzwfjid, Model model) {
		SyFwjbxx fwjbxx = fwjbxxService.loadSYFwjbxxByFjbm(jzwfjid);
		setDataAttribute(model, fwjbxx, "fw");
		return prefix + "fwEdit";
	}
	
	
//	@AuthWidgetRule(value="fw.findSYFwjbxxByFjbm",desc="房屋信息查询",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/findSYFwjbxxByFjbm", method = RequestMethod.POST)
	@ResponseBody 
	public  Object findSYFwjbxxByFjbm(String fjbm) {
		SyFwjbxx fw= fwjbxxService.loadSYFwjbxxByFjbm(fjbm);
		List<String> fjpicList=jzwfjPicService.loadFjPicIdsByfjId(fjbm);
		Map<String,Object> data=Maps.newHashMap();
		data.put("fwxx", fw);
		data.put("fjpicIds", fjpicList);
		KJSONMSG msg=new KJSONMSG(200, "数据加载成功", data);
		return msg;
	}

	@AuthWidgetRule(value="fw.enterDetailFw",desc="房屋信息明细",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailFw",method=RequestMethod.GET)
	public String enterDetailFw(String fwid, Model model) {
		SyFwjbxx fwjbxx = fwjbxxService.getFwByfwid(fwid);
		setDataAttribute(model, fwjbxx, "fw");
		return "psam/sy/fwjbxx/fwDetail";
	}
	
	/**
	 * 房屋人口信息明细
	 * @param fwid
	 * @param model
	 * @return
	 */
	/*@AuthWidgetRule(value="fw.enterDetailFw",desc="房屋信息明细",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailFwRk",method=RequestMethod.GET)
	public String enterDetailFwRk(String fwid, Model model) {
//		SyFwjbxx fwjbxx = fwjbxxService.loadFwjbxxAccInfo(fwid);
		SyFwjbxx fwjbxx = fwjbxxService.loadSYFwjbxxByFjbm(fwid);//房屋
		if(fwjbxx!=null){
			setDataAttribute(model, fwjbxx, "fw");
		}
		List<SyRkglCzrk> czrk=syRkglCzrkService.selectSyCzrkInfoByJzwfjIdKey(fwid);//常住
		if(czrk!=null&&czrk.size()>0){
			setDataAttribute(model, czrk, "czrk");
		}
		List<SyRkglLdrkdjb> ldrk=syLdrkService.selectSyLdrkInfoByJzwfjIdKey(fwid);//流动
		if(ldrk!=null&&ldrk.size()>0){
			setDataAttribute(model, ldrk, "ldrk");
		}
		List<Syjwry> jwry=syjwryService.selectJwryInfoByJzwfjIdKey(fwid);//境外
		if(jwry!=null&&jwry.size()>0){
			setDataAttribute(model, jwry, "jwry");
		}
        if(fwjbxx==null&&czrk.size()<=0&&ldrk.size()<=0&&jwry.size()<=0){
			setDataAttribute(model, "当前房屋没有房屋及人口信息", "msg");
		}
		return "psam/jzw/jzwFwRkDetail";
	}*/
	@AuthWidgetRule(value="fw.enterDetailFw",desc="房屋信息明细",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailFwRk",method=RequestMethod.GET)
	public String enterDetailFwRk(String fwid, Model model) {
//		SyFwjbxx fwjbxx = fwjbxxService.loadFwjbxxAccInfo(fwid);
		SyFwjbxx fwjbxx = fwjbxxService.loadSYFwjbxxByFjbm(fwid);//房屋
		setDataAttribute(model, fwjbxx, "fw");
		setDataAttribute(model, fwid, "fwid");
		return "psam/jzw/jzwFwRkDetail";
	}
	@AuthWidgetRule(value="fw.enterUpdateFw",desc="房屋信息修改",operateType="W",refTable="SY_FWJBXX")
	@RequestMapping(value = "/enterUpdateFw",method=RequestMethod.GET)
	public String enterUpdateFw(String fwid, Model model) {
		SyFwjbxx fwjbxx = fwjbxxService.getFwByfwid(fwid);
		setDataAttribute(model, fwjbxx, "fw");
		return "psam/sy/fwjbxx/fwEdit";
	}
	
	@AuthWidgetRule(value="fw.updateOrAddFW",desc="房屋信息修改",operateType="W",refTable="SY_FWJBXX")
	@RequestMapping(value="/updateOrAddFW",method=RequestMethod.POST)
	@ResponseBody
	public Object updateFwInfo(@ModelAttribute("fw") SyFwjbxx fw) {
		fwjbxxService.updateFwInfo(fw);
		return ajaxDoneSuccess("数据修改成功 ");
	}
//	public Object updateOrAddFW(@RequestParam  Map<String,Object> params ,HttpServletRequest request){
//		String fwid=(String) params.get("fwid");
//		if(fwid!=null && !"".equals(fwid)){
//			fwjbxxService.updateFw(params);
//		}
//		else{
//			fwjbxxService.addFw(params);
//		}
//		return ajaxDoneSuccess("操作成功");
//	}
	

//-----------------------------2016-0810----查询房屋内历史人员记录-------------------------------
	@RequestMapping(value="/loadJzwFwLsCzrk",method=RequestMethod.POST)
	@ResponseBody
	public Object loadJzwFwLsCzrk(@RequestParam Map<String,String> params){
//		List<SyRkglCzrk> czrk=syRkglCzrkService.selectSyCzrkInfoByJzwfjIdKey(fwid);//常住
		DataSet czrk = syRkglCzrkService.selectSyCzrkForFwLsry(params);
		return czrk;
	}
	
	@RequestMapping(value="/loadJzwFwLsLdrk",method=RequestMethod.POST)
	@ResponseBody
	public Object loadJzwFwLsLdrk(@RequestParam Map<String,String> params){
//		List<SyRkglLdrkdjb> ldrk=syLdrkService.selectSyLdrkInfoByJzwfjIdKey(fwid);//流动
		DataSet ldrk = syLdrkService.selectSyLdrkForFwLsry(params);
		return ldrk;
	}
	
	@RequestMapping(value="/loadJzwFwLsJwry",method=RequestMethod.POST)
	@ResponseBody
	public Object loadJzwFwLsJwry(@RequestParam Map<String,String> params){
//		List<Syjwry> jwry=syjwryService.selectJwryInfoByJzwfjIdKey(fwid);//境外
		DataSet jwry = syjwryService.selectSyJwrkForFwLsry(params);
		return jwry;
	}
//-----------------------------2016-0810----查询房屋内历史人员记录-end------------------------------
	
	//-----------------------------2016-0810----查询房屋内当前所住人员信息-------------------------------
		@RequestMapping(value="/loadJzwFwCzrkJbxx",method=RequestMethod.POST)
		@ResponseBody
		public Object loadJzwFwCzrkJbxx(@RequestParam Map<String,String> params){//String fwid
//			List<SyRkglCzrk> czrk=syRkglCzrkService.selectSyCzrkInfoFwByJzwfjIdKey(fwid);//常住
			DataSet czrk = syRkglCzrkService.selectSyCzrkForFw(params);
			return czrk;
		}
		
		@RequestMapping(value="/loadJzwFwLdrkJbxx",method=RequestMethod.POST)
		@ResponseBody
		public Object loadJzwFwLdrkJbxx(@RequestParam Map<String,String> params){
//			List<SyRkglLdrkdjb> ldrk=syLdrkService.selectSyLdrkInfoFwByJzwfjIdKey(fwid);//流动
			DataSet ldrk = syLdrkService.selectSyLdrkForFw(params);
			return ldrk;
		}
		
		@RequestMapping(value="/loadJzwFwJwryJbxx",method=RequestMethod.POST)
		@ResponseBody
		public Object loadJzwFwJwryJbxx(@RequestParam Map<String,String> params){
//			List<Syjwry> jwry=syjwryService.selectJwryInfoFwByJzwfjIdKey(fwid);//境外
			DataSet jwry = syjwryService.selectSyJwrkForFw(params);
			return jwry;
		}
	//-----------------------------2016-0810----查询房屋内当前所住人员信息-end------------------------------
		
		@RequestMapping(value = "/revokeFwRyxx",method=RequestMethod.POST)
		@ResponseBody
		public Object revokeFwRyxx(@RequestParam Map<String, Object> params) {//String rylb,String sfzh,String rkid
			fwjbxxService.revokeFwRyxx(params);
			return ajaxDoneSuccess("删除成功 ");
		} 
	
	
	
	
	
}
