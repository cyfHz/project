package com.kingmon.project.psam.sqjcwh.controller;
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
import com.kingmon.project.psam.sqjcwh.model.Sqjcwh;
import com.kingmon.project.psam.sqjcwh.service.ISqjcwhService;
import com.kingmon.project.psam.xzqh.service.XzqhService;

@Controller
@RequestMapping("/psam/sqjcwh")
public class SqjcwhController extends KBaseController {
	private static final String prefix = "psam/sqjcwh/";
	@Autowired
	private ISqjcwhService sqjcwhService;
	@Autowired
	private XzqhService xzqhService;

//-----------------------------loaddata-----------------------------------------------	
	/**
	 * 跳转社区居村委会管理模块
	 * @return
	 */
	@AuthWidgetRule(value="sqjcwh",desc="社区居村委会管理",operateType="R",refTable="DZ_SQJCWH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String enterSqjcwhPage() {
		return prefix + "sqjcwhDataGrid";
	}
	
	/**
	 * 查询社区居村委会列表信息
	 * @param paramObject
	 * @return 
	 */
	@AuthWidgetRule(value="sqjcwh.loadSqjcwhDataGrid",desc="社区居村委会查看",operateType="W",refTable="DZ_SQJCWH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadSqjcwhDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object loadSqjcwhDataGrid( @ModelAttribute("paramObject") ParamObject paramObject) {
		DataSet dataSet = sqjcwhService.loadSqjcwhDataSet(paramObject);
		return FastjsonUtil.convert(dataSet);
	}
	
//-----------------------------add-----------------------------------------------
	/**
	 * 跳转添加页面
	 * @return
	 */
	@AuthWidgetRule(value="sqjcwh.addSqjcwh",desc="社区居村委会添加",operateType="W",refTable="DZ_SQJCWH",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddSqjcwh", method = RequestMethod.GET)
	public String enterAddSqjcwh() {
		return prefix + "sqjcwhAdd";
	}
	
	/**
	 * 执行添加操作
	 * @param sqjcwh 社区居村委会信息
 	 * @return
	 */
	@AuthWidgetRule(value="sqjcwh.addSqjcwh",desc="社区居村委会添加",operateType="W",refTable="DZ_SQJCWH",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/addSqjcwh", method = RequestMethod.POST)
	@ResponseBody
	public Object addSqjcwh(@ModelAttribute("sqjcwh") Sqjcwh sqjcwh) {
		sqjcwhService.addSqjcwh(sqjcwh);
		return ajaxDoneSuccess("数据添加成功 ");
	}
//---------------------------update-------------------------------------------------
	/**
	 * 跳转更新页面
	 * @param dzbm 地址编码
	 * @param model
	 * @return
	 */
	@AuthWidgetRule(value="sqjcwh.updateSqjcwh",desc="社区居村委会更新",operateType="W",refTable="DZ_SQJCWH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateSqjcwh", method = RequestMethod.GET)
	public String enterUpdateSqjcwh(String dzbm, Model model) {
		Sqjcwh sqjcwh = sqjcwhService.getSqjcwhById(dzbm);
		setDataAttribute(model, sqjcwh, "sqjcwh");
		return prefix + "sqjcwhEdit";
	}
	
	/**
	 * 执行更新操作
	 * @param sqjcwh 社区居村委会信息
	 * @return
	 */
	@AuthWidgetRule(value="sqjcwh.updateSqjcwh",desc="社区居村委会更新",operateType="W",refTable="DZ_SQJCWH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/updateSqjcwh", method = RequestMethod.POST)
	@ResponseBody
	public Object updateSqjcwh(@ModelAttribute("sqjcwh") Sqjcwh sqjcwh) {
		sqjcwhService.updateSqjcwh(sqjcwh);
		return ajaxDoneSuccess("数据修改成功 ");
	}

//----------------------------------revoke------------------------------------------
	/**
	 * 跳转撤销页面
	 * @param dzbm 地址编码
	 * @param model
	 * @return
	 */
	@AuthWidgetRule(value="sqjcwh.revokeSqjcwh",desc="社区居村委会撤销",operateType="W",refTable="DZ_SQJCWH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterRevokeSqjcwh", method = RequestMethod.GET)
	public Object enterRevokeSqjcwh(String dzbm, Model model){
		Sqjcwh sqjcwh = sqjcwhService.getSqjcwhById(dzbm);
		setDataAttribute(model, sqjcwh, "sqjcwh");
		return prefix + "revokeSqjcwh";
	}
	
	/**
	 * 执行撤销操作
	 * @param dzbm 地址编码
	 * @param cxyy 撤销原因
	 * @return
	 */
	@AuthWidgetRule(value="sqjcwh.revokeSqjcwh",desc="社区居村委会撤销",operateType="W",refTable="DZ_SQJCWH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/revokeSqjcwh", method = RequestMethod.POST)
	@ResponseBody
	public Object revokeSqjcwh(String dzbm, String cxyy) {
		sqjcwhService.revokeSqjcwh(dzbm, cxyy);
		return ajaxDoneSuccess("社区居村委会撤销成功 ");
	}
//----------------------------------activate------------------------------------------
	/**
	 * 执行启用操作
	 * @param dzbm
	 * @return
	 */
	@AuthWidgetRule(value="sqjcwh.activateSqjcwh",desc="社区居村委会启用",operateType="W",refTable="DZ_SQJCWH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/activateSqjcwh", method = RequestMethod.POST)
	@ResponseBody
	public Object activateSqjcwh(String dzbm) {
		sqjcwhService.activateSqjcwh(dzbm);
		return ajaxDoneSuccess("社区居村委会启用成功 ");
	}
//----------------------------------Detail------------------------------------------
	/**
	 * 查看社区居居委会详情页面
	 * @param dzbm 地址编码
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@AuthWidgetRule(value="sqjcwh.enterDetailSqjcwh",desc="社区居村委会详细",operateType="W",refTable="DZ_SQJCWH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailSqjcwh", method = RequestMethod.GET)
	public Object enterDetailSqjcwh(String dzbm, Model model) throws Exception {
		Map sqjcwh = sqjcwhService.selectDetailByPrimaryKey(dzbm);
		setDataAttribute(model, sqjcwh, "sqjcwh");
		return prefix + "sqjcwhDetail";
	}
}
