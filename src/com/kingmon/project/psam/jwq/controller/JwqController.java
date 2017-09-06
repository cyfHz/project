package com.kingmon.project.psam.jwq.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jwq.service.IJwqService;
import com.kingmon.project.psam.orgarea.service.IOrgPgisAreaService;

@Controller
@RequestMapping("/psam/jwq")
public class JwqController extends KBaseController {
	private static final String prefix = "psam/jwq/";

	@Autowired
	private IJwqService jwqService;

	@Autowired
	private IOrgPgisAreaService orgPgisAreaService;

	@AuthWidgetRule(value = "jwq", desc = "警务区管理", operateType = "R", refTable = "ENT_JWQ", crudType = KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/", "" })
	public String enterJwqManage() {
		return prefix + "jwqDataGrid";
	}

	@AuthWidgetRule(value = "jwq.jwqList", desc = "警务区数据列表", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_SEARCH)
	@RequestMapping(value = "/jwqList", method = RequestMethod.POST)
	@ResponseBody
	public Object jwqList(@ModelAttribute("paramObject") ParamObject paramObject) {
		DataSet dataSet = jwqService.loadJwqDataSet(paramObject);
		return FastjsonUtil.convert(dataSet);
	}

	// -------------------------------addJwq--------------------------------------------------
	@AuthWidgetRule(value = "jwq.addJwq", desc = "警务区添加", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddJwq", method = RequestMethod.GET)
	public String enterAddJwq() {
		return prefix + "jwqAdd";
	}

	@AuthWidgetRule(value = "jwq.addJwq", desc = "警务区添加", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_ADD)
	@RequestMapping(value = "/addJwq", method = RequestMethod.POST)
	@ResponseBody
	public Object addJwq(@ModelAttribute("jwq") Jwq jwq, BindingResult bindingResult) {
		jwqService.addJwq(jwq);
		return ajaxDoneSuccess("警务区添加成功 ");
	}

	// ---------------------------------updateJwq------------------------------------------------
	@AuthWidgetRule(value = "jwq.updateJwq", desc = "警务区更新", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateJwq", method = RequestMethod.GET)
	public String enterUpdateJwq(String jwqid, Model model) {
		Jwq jwq = jwqService.loadJwqByIdForUpdate(jwqid);
		setDataAttribute(model, jwq, "jwq");
		return prefix + "jwqEdit";
	}

	@AuthWidgetRule(value = "jwq.updateJwq", desc = "警务区更新", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_UPDATE)
	@RequestMapping(value = "/updateJwq", method = RequestMethod.POST)
	@ResponseBody
	public Object updateJwq(@ModelAttribute("jwq") Jwq jwq, BindingResult bindingResult) {
		jwqService.updatejwq(jwq);
		return ajaxDoneSuccess("警务区信息修改成功 ");
	}

	// ---------------------------------detailJwq------------------------------------------------
	@AuthWidgetRule(value = "jwq.enterDetailJwq", desc = "警务区详细信息", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailJwq", method = RequestMethod.GET)
	public String enterDetailJwq(Model model, String jwqid) {
		Jwq jwq = jwqService.loadJwqByIdForUpdate(jwqid);
		setDataAttribute(model, jwq, "jwq");
		return prefix + "jwqDetail";
	}

	// -------------------------------------注销--------启用-----------------------------------
	@AuthWidgetRule(value = "jwq.cancelJwq", desc = "警务区注销", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_UPDATE)
	@RequestMapping(value = "/cancelJwq", method = RequestMethod.POST)
	@ResponseBody
	public Object cancelJwq(String id) {
		jwqService.cancelJwq(id);
		return ajaxDoneSuccess("注销警务区成功！");
	}

	@AuthWidgetRule(value = "jwq.activateJwq", desc = "警务区启用", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_UPDATE)
	@RequestMapping(value = "/activateJwq", method = RequestMethod.POST)
	@ResponseBody
	public Object activateJwq(String id) {
		jwqService.activateJwq(id);
		return ajaxDoneSuccess("启用警务区成功！");
	}

	// -------------------------------------------------------------------------
	@AuthWidgetRule(value = KConstants.WIDGET_PUBLIC, desc = "警务区查找带回", operateType = "W", refTable = "ENT_JWQ")
	@RequestMapping(value = "/loadBingbackjwqPage")
	public Object loadBingbackjwqPage() {
		return prefix + "jwqlookback";
	}

	@AuthWidgetRule(value = KConstants.WIDGET_PUBLIC, desc = "警务区查找带回数据列表", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_SEARCH)
	@RequestMapping(value = "/jwqListForLookBack", method = RequestMethod.POST)
	@ResponseBody
	public Object jwqListForLookBack(@ModelAttribute("paramObject") ParamObject paramObject) {
		DataSet dataSet = jwqService.loadJwqDataSet(paramObject);
		return FastjsonUtil.convert(dataSet);
	}

	// -------------------------规划----------------------------------------------------

	@AuthWidgetRule(value = "jwq.loadJwqBianjie", desc = "警务区规划", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadJwqBianjie", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJwqBianjie(String jwqid) {
		Jwq jwq = jwqService.getJwqById(jwqid);
		KJSONMSG msg = new KJSONMSG(200, "数据加载成功", jwq);
		return msg;
	}
	
	@AuthWidgetRule(value = "jwq.loadJwqBianjie", desc = "警务区规划", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadJwqBjInSamePsc", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJwqBjInSamePsc(String jwqid) {
		List<Jwq> jwqList= jwqService.loadJwqBjInSamePsc(jwqid);
		List<Map<String,Object>> data=Lists.newArrayList();
		for(Jwq jwq:jwqList){
			if(jwq.getJwqid().equals(jwqid)){
				continue;
			}
			Map<String,Object> map=Maps.newHashMap();
			map.put("JWQID", jwq.getJwqid());
			map.put("JWQBH", jwq.getJwqbh());
			map.put("BJZBZ", jwq.getBjzbz());
			map.put("JWQMC", jwq.getJwqmc());
			data.add(map);
		}
		KJSONMSG msg = new KJSONMSG(200, "数据加载成功", data);
		return msg;
	}
	
	@AuthWidgetRule(value = "jwq.updateBjzbz", desc = "警务区规划", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_UPDATE)
	@RequestMapping(value = "/updateBjzbz", method = RequestMethod.POST)
	@ResponseBody
	public Object updateBjzbz(String jwqid, String bjzbz) {
		jwqService.updateBjzbz(jwqid, bjzbz);
		return ajaxDoneSuccess("规划成功");
	}

	// -----------------------------------------------------------------------------

	// json集合：[{type： "1",id:"";bh:"",mc:"",bjzbz:""}]
	// 省厅2; 市局4; 分局6; 派出所9--10; 警务区12; 特巡警8
	@AuthWidgetRule(value = "jwq.loadManageBianjie", desc = "加载管辖边界", operateType = "W", refTable = "ENT_JWQ", crudType = KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadManageBianjie", method = RequestMethod.POST)
	@ResponseBody
	public Object loadManageBianjie() {
		// int userLevel=DataRuleUtil.getUserLevel(SecurityUtils.getUserId());
		int userLevel = SecurityUtils.getUserLevel();// zht-20160219
		List<Map<String, Object>> bianjieList = Lists.newArrayList();

		bianjieList = jwqService.loadUserJwqBianjie(SecurityUtils.getUserId());
		// System.out.println(bianjieList.size());
		if (bianjieList != null && bianjieList.size() > 0) {
			KJSONMSG msg = new KJSONMSG(200, "警务边界数据加载成功", bianjieList);
			return msg;
		}
		switch (userLevel) { // 警务区没有数据
		case 12:// 警务区
			bianjieList = jwqService.loadUserJwqBianjie(SecurityUtils.getUserId());
			break;
		case 9://
		case 10:// zht-20160219
		case 6://
		case 4://
		case 2://
		default:
			bianjieList = orgPgisAreaService.loadUserOrgPgisBianjie(SecurityUtils.getUserId());
			break;
		}
		if (bianjieList == null || bianjieList.isEmpty()) {
			KJSONMSG msg = new KJSONMSG(300, "该用户管辖区域边界数据为空");
			msg.setConfirmMsg(""+userLevel);
			return msg;
		}
		KJSONMSG msg = new KJSONMSG(200, "数据加载成功", bianjieList);
		msg.setConfirmMsg(""+userLevel);
		return msg;
	}

	// -------------------------------拆分合并-------------------------------------------------------------------------------
	// @AuthWidgetRule(value="jwq.jwqHebing",desc="警务区合并",operateType="W",refTable="ENT_JWQ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/checkJwqHebing", method = RequestMethod.POST)
	@ResponseBody
	public Object checkJwqHeBing(String[] jwqIds) {
		return jwqService.checkJwqHebing(jwqIds);
	}

	// @AuthWidgetRule(value="jwq.jwqHebing",desc="警务区合并",operateType="W",refTable="ENT_JWQ")
	@RequestMapping(value = "/processJwqHebing", method = RequestMethod.POST)
	@ResponseBody
	public Object processJwqHebing(String[] fromIds, String toId) {
		jwqService.processJwqHeBing(fromIds, toId);
		return ajaxDoneSuccess("操作成功");
	}

	// @AuthWidgetRule(value="jwq.jwqChaiFen",desc="警务区拆分",operateType="W",refTable="ENT_ORGANIZATION_PGIS_AREA",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/loadJwqChaiFenDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJwqChaiFenDataGrid(String fromJwqId) {
		DataSet dataSet = jwqService.loadJwqChaiFenDataGrid(fromJwqId);
		return FastjsonUtil.convert(dataSet);
	}

	/**
	 * 警务区拆分 ，拆分到的目标警务区不准再包含自己
	 */
	// 3d387b6c-7b0e-4887-8f85-76039ffe4ca6
	// [619384e2-1236-4393-bbfe-7186a402882c,
	// e200bc5b-5b48-4d4f-be5e-9ef04d49683d]
	// @AuthWidgetRule(value="jwq.jwqChaiFen",desc="警务区拆分",operateType="W",refTable="ENT_JWQ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/processJwqChaiFen", method = RequestMethod.POST)
	@ResponseBody
	public Object processJwqChaiFen(String fromId, String[] toIds) {
		jwqService.processJwqChaiFen(fromId, toIds);
		return ajaxDoneSuccess("操作成功");
	}

	/**
	 * @param fromId
	 * @param toIds
	 * @return
	 */
	@RequestMapping(value = "/processJwqGuihuaUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Object processJwqGuihuaUpdate(String fromId, String[] toIds) {
		jwqService.processJwqChaiFen(fromId, toIds);
		return ajaxDoneSuccess("操作成功");
	}
	
}
