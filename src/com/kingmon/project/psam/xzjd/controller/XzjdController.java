package com.kingmon.project.psam.xzjd.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.xzjd.model.Xzjd;
import com.kingmon.project.psam.xzjd.service.IXzjdService;
import com.kingmon.project.psam.xzqh.service.XzqhService;

@Controller
@RequestMapping("/psam/xzjd")
public class XzjdController extends KBaseController {

	private static final String prefix = "psam/xzjd/";

	@Autowired
	private IXzjdService xzjdService;
	@Autowired
	private XzqhService xzqhService;
	
//-----------------------------loaddata-----------------------------------------------	
	/**
	 * 跳转乡镇街道管理模块页面
	 * @return 页面路径信息
	 */
	@AuthWidgetRule(value="xzjd",desc="乡镇街道管理",operateType="R",refTable="DZ_XZJD",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" },method=RequestMethod.GET)
	public String enterXzjdPage() {
		return prefix + "xzjdDataGrid";
	}
	
	/**
	 * 乡镇街道列表查询
	 * @param paramObject
	 * @return
	 */
	@AuthWidgetRule(value="xzjd.loadXzjdDataGrid",desc="乡镇街道查看",operateType="W",refTable="DZ_XZJD",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadXzjdDataGrid",method=RequestMethod.POST)
	@ResponseBody
	public Object loadXzjdDataGrid(@ModelAttribute("paramObject") ParamObject paramObject) {
		DataSet dataSet = xzjdService.loadXzjdDataSet(paramObject);
		return FastjsonUtil.convert(dataSet);
	}
//-----------------------------add-----------------------------------------------
	/**
	 * 跳转添加页面
	 * @return 页面路径信息
	 */
	@AuthWidgetRule(value="xzjd.addXzjd",desc="乡镇街道添加",operateType="W",refTable="DZ_XZJD",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddXzjd",method=RequestMethod.GET)
	public String enterAddXzjd() {
		return prefix + "xzjdAdd";
	}
	
	/**
	 * 执行添加操作
	 * @param xzjd 乡镇街道信息
	 * @return
	 */
	@AuthWidgetRule(value="xzjd.addXzjd",desc="乡镇街道添加",operateType="W",refTable="DZ_XZJD",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/addXzjd",method=RequestMethod.POST)
	@ResponseBody
	public Object addXzjd(@ModelAttribute("xzjd")Xzjd xzjd) {
		xzjdService.addXzjd(xzjd);
		return ajaxDoneSuccess("数据添加成功 ");
	}
//---------------------------update-------------------------------------------------	
	/**
	 * 跳转更新页面
	 * @param dzbm 乡镇街道地址编码
	 * @param model
	 * @return 页面地址信息
	 */
	@AuthWidgetRule(value="xzjd.updateXzjd",desc="乡镇街道更新",operateType="W",refTable="DZ_XZJD",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateXzjd",method=RequestMethod.GET)
	public String enterUpdateXzjd(String dzbm, Model model) {
		Xzjd xzjd = xzjdService.getXzjdById(dzbm);
		setDataAttribute(model, xzjd, "xzjd");
		return prefix + "xzjdEdit";
	}
	
	/**
	 * 执行更新操作
	 * @param xzjd 乡镇街道信息
	 * @return
	 */
	@AuthWidgetRule(value="xzjd.updateXzjd",desc="乡镇街道更新",operateType="W",refTable="DZ_XZJD",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/updateXzjd")
	@ResponseBody
	public Object updateXzjd(@ModelAttribute("xzjd") Xzjd xzjd) {
		xzjdService.updateXzjd(xzjd);
		return ajaxDoneSuccess("数据修改成功 ");
	}
//----------------------------------Detail------------------------------------------		
	/**
	 * 跳转详情页面
	 * @param dzbm 乡镇街道地址编码
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@AuthWidgetRule(value="xzjd.enterDetailXzjd",desc="乡镇街道查看详细",operateType="W",refTable="DZ_XZJD",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailXzjd",method=RequestMethod.GET)
	public String enterDetailXzjd(String dzbm, Model model) {
		Map xzjd = xzjdService.selectDetailByPrimaryKey(dzbm);
		setDataAttribute(model, xzjd, "xzjd");
		return prefix + "xzjdDetail";
	}
	
//----------------------------------revoke------------------------------------------	
	/**
	 * 跳转撤销页面
	 * @param dzbm 地址编码
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthWidgetRule(value="xzjd.revokeXzjd",desc="乡镇街道撤销",operateType="W",refTable="DZ_XZJD",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterRevokeXzjd",method=RequestMethod.GET)
    public Object enterRevokeXzjd(String dzbm,Model model) throws Exception{
		Xzjd xzjd = xzjdService.getXzjdById(dzbm);
		setDataAttribute(model, xzjd, "xzjd");
		return prefix+"revokeXzjd";
	}
	
	/**
	 * 执行撤销操作
	 * @param dzbm 地址编码
	 * @param cxyy 撤销原因
	 * @return
	 */
	@AuthWidgetRule(value="xzjd.revokeXzjd",desc="乡镇街道撤销",operateType="W",refTable="DZ_XZJD",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/revokeXzjd",method=RequestMethod.POST)
	@ResponseBody
	public Object revokeXzjd(String dzbm,String cxyy) {
		xzjdService.revokeXzjd(dzbm,cxyy);
		return ajaxDoneSuccess("乡镇街道撤销成功 ");
	}
	
//----------------------------------activate------------------------------------------
	/**
	 * 启用操作
	 * @param dzbm 地址编码
	 * @return
	 */
	@AuthWidgetRule(value="xzjd.activateXzjd",desc="乡镇街道启用",operateType="W",refTable="DZ_XZJD",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/activateXzjd",method=RequestMethod.POST)
	@ResponseBody
	public Object activateXzjd(String dzbm) {
		xzjdService.activateXzjd(dzbm);
		return ajaxDoneSuccess("乡镇街道启用成功 ");
	}
//----------------------------------loadBingbackPage------------------------------------------	
//	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="查找带回乡镇街道信息",operateType="W",refTable="",crudType=KConstants.OPER_UPDATE)
//	@RequestMapping(value = "/loadBingbackPage")
//	public Object loadBingbackPage() {
//		return prefix+"xzjd.dialog";
//	}
	//----------------------------------Excel Import------------------------------------------	
	/**
	 * 跳转导入页面
	 * @return
	 * @throws Exception
	 */
	@AuthWidgetRule(value="xzjd.importdata",desc="导入乡镇街道信息",operateType="W",refTable="DZ_SQJCWH",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterImportXzjd",method=RequestMethod.GET)
    public Object enterImportXzjd() throws Exception{
		return prefix+"uploadAndImport";
	}
	
}
