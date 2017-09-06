package com.kingmon.project.psam.jlx.controller;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.elasticsearch.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.psam.jlx.model.Jlx;
import com.kingmon.project.psam.jlx.service.IJlxService;
import com.kingmon.project.psam.xzqh.model.Xzqh;
import com.kingmon.project.psam.xzqh.service.XzqhService;

@Controller
@RequestMapping("/psam/jlx")
public class JlxController extends KBaseController {
	private static final String prefix = "psam/jlx/";
	@Autowired
	private IJlxService jlxService;
	@Autowired
	private XzqhService xzqhService;
	@Autowired
	private IOrganizationService orgService;

//-----------------------------loaddata-----------------------------------------------		
	@AuthWidgetRule(value="jlx",desc="街路巷管理",operateType="R",refTable="DZ_JLX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String enterJlxPage() {
		return prefix + "jlxDataGrid";
	}
	
	@AuthWidgetRule(value="jlx.loadJlxDataGrid",desc="街路巷管理",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadJlxDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJlxGridView( @ModelAttribute("paramObject") ParamObject paramObject) {
		DataSet dataSet = jlxService.loadJlxDataSet(paramObject);
		return FastjsonUtil.convert(dataSet);
	}
//-----------------------------updateJlx-----------------------------------------------	
	@AuthWidgetRule(value="jlx.updateJlx",desc="街路巷更新",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateJlx", method = RequestMethod.GET)
	public String enterUpdateJlx(String dzbm, Model model) {
		Jlx jlx = jlxService.getJlxById(dzbm);
		String sszdyjxzqy_xtype=jlxService.getJlxSszdyjxzqyType(jlx==null?"":jlx.getSszdyjxzqy_dzbm());
		
		Organization org=orgService.findOrgByUserId(SecurityUtils.getUserId());
		String orgCodeSubSix=org.getOrgna_code().substring(0,6);//当前登录人员的行政区划
		Xzqh xzqh=xzqhService.selectXzqhBydm(orgCodeSubSix);
		
		setDataAttribute(model, xzqh, "xzqh");
		setDataAttribute(model, jlx, "jlx");
		setDataAttribute(model, sszdyjxzqy_xtype, "sszdyjxzqy_xtype");
		return prefix + "jlxEdit";
	}

	@AuthWidgetRule(value="jlx.updateJlx",desc="街路巷更新",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/updateJlx", method = RequestMethod.POST)
	@ResponseBody
	public Object updateJlx(@ModelAttribute("jlx") Jlx jlx) {
		jlxService.updateJlx(jlx);
		return ajaxDoneSuccess("数据修改成功 ");
	}
	
//-----------------------------addJlx-----------------------------------------------	
	@AuthWidgetRule(value="jlx.addJlx",desc="街路巷添加",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddJlx", method = RequestMethod.GET)
	public String enterAddJlx(@RequestParam  Map<String,String> params ,Model model) {
		String sszdyjxzqy_mc="";
		String XTYPE="";
		String sszdyjxzqy_dzbm=params.get("sszdyjxzqy_dzbm");
		List<Map<String,String>> map=jlxService.selectXzqyMapByDzbm(sszdyjxzqy_dzbm);
		if(map!=null&&map.size()>=0){
			for(Map<String,String> a:map){
				sszdyjxzqy_mc=a.get("MC");
				sszdyjxzqy_dzbm=a.get("DM");
				XTYPE=a.get("XTYPE");
			}
			setDataAttribute(model, sszdyjxzqy_mc, "sszdyjxzqy_mc");
			setDataAttribute(model, XTYPE, "XTYPE");
		}
		
		Organization org=orgService.findOrgByUserId(SecurityUtils.getUserId());
		String orgCodeSubSix=org.getOrgna_code().substring(0,6);//当前登录人员的行政区划
		Xzqh xzqh=xzqhService.selectXzqhBydm(orgCodeSubSix);
		setDataAttribute(model, xzqh, "xzqh");
		
		setDataAttribute(model, sszdyjxzqy_dzbm, "sszdyjxzqy_dzbm");
		return prefix + "jlxAdd";
	}

	@AuthWidgetRule(value="jlx.addJlx",desc="街路添加",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/addJlx", method = RequestMethod.POST)
	@ResponseBody
	public Object addJlx(@ModelAttribute("jlx") Jlx jlx, BindingResult bindingResult) {
		processValidateResult(bindingResult);
		jlxService.addJlx(jlx);
		return ajaxDoneSuccess("数据添加成功 ");
	}
//-----------------------------jlxDetail-----------------------------------------------	
	@SuppressWarnings("rawtypes")
	@AuthWidgetRule(value="jlx.enterJlxPage",desc="街路巷详细信息",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailJlx", method = RequestMethod.GET)
	public Object enterDetailJlx(String dzbm, Model model) throws Exception {
		Map jlx = jlxService.selectDetailByPrimaryKey(dzbm);
		setDataAttribute(model, jlx, "jlx");
		return prefix + "jlxDetail";
	}
	
//-----------------------------revokeJlx-----------------------------------------------		
	@AuthWidgetRule(value="jlx.revokeJlx",desc="街路巷撤销",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterRevokeJlx", method = RequestMethod.GET)
	public Object enterRevokeJlx(String dzbm, Model model) throws Exception {
		Jlx jlx = jlxService.getSimpleJlxById(dzbm);
		setDataAttribute(model, jlx, "jlx");
		return prefix + "revokeJlx";
	}
	
	@AuthWidgetRule(value="jlx.revokeJlx",desc="街路巷撤销",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/revokeJlx", method = RequestMethod.POST)
	@ResponseBody
	public Object revokeJlx(String dzbm, String cxyy) {
		jlxService.revokeJlx(dzbm, cxyy);
		return ajaxDoneSuccess("街路巷（小区）撤销成功 ");
	}
//-----------------------------activateJlx-----------------------------------------------	
	@AuthWidgetRule(value="jlx.activateJlx",desc="街路巷启用",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/activateJlx", method = RequestMethod.POST)
	@ResponseBody
	public Object activateJlx(String dzbm) {
		jlxService.activateJlx(dzbm);
		return ajaxDoneSuccess("街路巷（小区）启用成功 ");
	}
	
//-----------------------------applyUseJlx-reviewJlx----------------------------------------------		
	@AuthWidgetRule(value="jlx.applyUseJlx",desc="街路巷申请",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/applyUseJlx", method = RequestMethod.POST)
	@ResponseBody
	public Object applyUseJlx(String dzbm) {
		jlxService.applyUseJlx(dzbm);
		return ajaxDoneSuccess("街路巷（小区）提交添加申请成功 ");
	}
	
	@AuthWidgetRule(value="jlx.reviewJlx",desc="街路巷审核",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/reviewJlx", method = RequestMethod.POST)
	@ResponseBody
	public Object reviewJlx(String dzbm,String spzt) {
		jlxService.reviewJlx(dzbm,spzt);
		return ajaxDoneSuccess("街路巷（小区）审批成功 ");
	}
	
//-----------------------------------------------------------------------------------------	----------	
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="街路巷作为检索菜单数据加载",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/loadJlxBMMCDataSet" }, method = RequestMethod.POST)
	@ResponseBody
	public  DataSet loadJlxBMMCDataSet(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		return jlxService.loadJlxBMMCDataSet(params);
	}
	
	
//----------------------------查找带回--------------------------------------------------
	/**
	 * 
	 * @param sszdyjxzqy_dzbm :最低一级行政区域主键
	 * @param sszdyjxzqy_xtype：最低一级行政区域类型： 可能参数   xzqh， xzjd， sqjcwh
	 * @param isLoadFromSuperXzqy 是否从sszdyjxzqy_dzbm 0：只有本级节点；  1:本节点 + 上级节点 ，两个节点的数据，； 2:本级节点，上级节点一直到市级节点所有数据<br/>
	 * @param model
	 * @return
	 */
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="街路巷查找带回",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadBingbackJlxPage")
	public Object loadBingbackJlxPage(String sszdyjxzqy_dzbm,String sszdyjxzqy_xtype,String isLoadFromSuperXzqy,Model model) {
		if(sszdyjxzqy_xtype!=null){
			sszdyjxzqy_xtype=sszdyjxzqy_xtype.toUpperCase();
		}
		if(sszdyjxzqy_xtype==null||sszdyjxzqy_xtype.isEmpty()){
			sszdyjxzqy_xtype="XZQH";
		}
		setDataAttribute(model,sszdyjxzqy_dzbm, "sszdyjxzqy_dzbm" );
		setDataAttribute(model,sszdyjxzqy_xtype, "sszdyjxzqy_xtype" );
		setDataAttribute(model,isLoadFromSuperXzqy, "isLoadFromSuperXzqy" );
		return prefix+"jlx.bringback.dialog";
	}
	
	/**
	 * 
	 * @param paramObject
	 * @param sszdyjxzqy_dzbm 所属最低一级行政区域代码
	 * @param sszdyjxzqy_xtype sszdyjxzqy_dzbm 是什么类型 XZQH， XZJD， SQJCWH
	 * @param isLoadFromSuperXzqy  0：只有本级节点；  1:本节点 + 上级节点 ，两个节点的数据，； 2:本级节点，上级节点一直到市级节点<br/>
	 *		 
	 * @return
	 */
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="街路巷查找带回",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value="/loadJlxAjaxData",method=RequestMethod.POST)
	@ResponseBody
	public Object loadJlxAjaxData( @ModelAttribute("paramObject") ParamObject paramObject,
			String sszdyjxzqy_dzbm,
			String sszdyjxzqy_xtype,
			String isLoadFromSuperXzqy,
			String jlxxqdm,
			String jlxxqmc){
		if(jlxxqmc!=null&&!jlxxqmc.isEmpty()){
			paramObject.addWebParam("jlxxqmc", jlxxqmc);
		}
		if(jlxxqdm!=null&&!jlxxqdm.isEmpty()){
			paramObject.addWebParam("jlxxqdm", jlxxqdm);
		}
		DataSet dataSet = jlxService.loadJlxAjaxData(paramObject,sszdyjxzqy_dzbm,sszdyjxzqy_xtype,isLoadFromSuperXzqy);
		return FastjsonUtil.convert(dataSet);
	
	}
	
	@RequestMapping(value="/selectMcBySszdyjxzqyDzbm",method=RequestMethod.POST)
	@ResponseBody
	public Object selectXzqhmcBySszdyjxzqyDzbm(String dzbm){
		Map<String,Object> map=jlxService.selectXzqhmcBySszdyjxzqyDzbm(dzbm);
		return map;
	}
}
