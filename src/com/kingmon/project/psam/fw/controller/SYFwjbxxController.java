//package com.kingmon.project.psam.fw.controller;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.google.common.collect.Maps;
//import com.kingmon.base.common.KConstants;
//import com.kingmon.base.data.DataSet;
//import com.kingmon.base.data.KJSONMSG;
//import com.kingmon.base.web.KBaseController;
//import com.kingmon.common.annon.AuthWidgetRule;
//import com.kingmon.project.psam.fw.model.SYFwjbxx;
//import com.kingmon.project.psam.fw.service.ISYFwjbxxService;
//import com.kingmon.project.psam.jzw.serivice.IJzwfjPicService;
//import com.kingmon.project.psam.xzjd.model.Xzjd;
//
//
//@Controller
//@RequestMapping("/psam/fw")
//public class SYFwjbxxController extends KBaseController{
//	private static final String prefix = "psam/fw/";
//	
//	@Autowired
//	private ISYFwjbxxService fwjbxxService;
//	@Autowired
//	IJzwfjPicService jzwfjPicService;
//	
//	@AuthWidgetRule(value="fw",desc="房屋管理",operateType="R",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
//	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
//	public String enterFwPage() {
//		return prefix + "fwDataGrid";
//	}
//	
//	@AuthWidgetRule(value="fw.loadFwGridView",desc="房屋数据列表",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
//	@RequestMapping(value = "/loadFwGridGrid", method = RequestMethod.POST)
//	public @ResponseBody DataSet loadFwGridView(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
//		
//		return fwjbxxService.loadSYFwjbxxDataSet(params);
//	}
//	
//	@AuthWidgetRule(value="fw.findSYFwjbxxByFjbm",desc="房屋信息查询",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
//	@RequestMapping(value = "/findSYFwjbxxByFjbm", method = RequestMethod.POST)
//	@ResponseBody 
//	public  Object findSYFwjbxxByFjbm(String fjbm) {
//		SYFwjbxx fw= fwjbxxService.loadSYFwjbxxByFjbm(fjbm);
//		List<String> fjpicList=jzwfjPicService.loadFjPicIdsByfjId(fjbm);
//		Map<String,Object> data=Maps.newHashMap();
//		data.put("fwxx", fw);
//		data.put("fjpicIds", fjpicList);
//		KJSONMSG msg=new KJSONMSG(200, "数据加载成功", data);
//		return msg;
//	}
//
//	@AuthWidgetRule(value="fw.enterDetailFw",desc="房屋信息明细",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
//	@RequestMapping(value = "/enterDetailFw",method=RequestMethod.GET)
//	public String enterDetailFw(String fwid, Model model) {
//		SYFwjbxx fwjbxx = fwjbxxService.getFwByfwid(fwid);
//		setDataAttribute(model, fwjbxx, "fw");
//		return prefix + "fwDetail";
//	}
//	
//	@AuthWidgetRule(value="fw.enterUpdateFw",desc="房屋信息修改",operateType="W",refTable="SY_FWJBXX")
//	@RequestMapping(value = "/enterUpdateFw",method=RequestMethod.GET)
//	public String enterUpdateFw(String fwid, Model model) {
//		SYFwjbxx fwjbxx = fwjbxxService.getFwByfwid(fwid);
//		setDataAttribute(model, fwjbxx, "fw");
//		return prefix + "fwEdit";
//	}
//	
//	@AuthWidgetRule(value="fw.updateOrAddFW",desc="房屋信息修改",operateType="W",refTable="SY_FWJBXX")
//	@RequestMapping(value="/updateOrAddFW",method=RequestMethod.POST)
//	@ResponseBody
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
//}
