package com.kingmon.project.psam.orgarea.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.psam.orgarea.model.OrgPgisArea;
import com.kingmon.project.psam.orgarea.service.IOrgPgisAreaService;

@Controller
@RequestMapping("/psam/orgPgisArea")
public class OrgPgisAreaController extends KBaseController {
	
	private static final String prefix = "psam/orgArea/";
	@Autowired
	private 
	IOrgPgisAreaService  orgPgisAreaServie;
	@Autowired
	private IOrganizationService  organizationService;
	
	@AuthWidgetRule(value="orgPgisArea",desc="区域规划管理",operateType="R",refTable="ENT_ORGANIZATION_PGIS_AREA",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" })
	public String enterOrgPgisArea() {
		return prefix + "orgPgisAreaDataGrid";
	}
	@AuthWidgetRule(value="orgPgisArea.loadOrganizationGrid",desc="组织机构区域加载",operateType="W",refTable="ENT_ORGANIZATION_PGIS_AREA",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadOrganizationGrid",method=RequestMethod.POST)
	@ResponseBody
	public Object loadOrganizationGrid(@ModelAttribute("paramObject") ParamObject paramObject) {
		DataSet dataSet = organizationService.loadOrganizationDataset(paramObject);
		return FastjsonUtil.convert(dataSet);
	}
	
	
	@AuthWidgetRule(value="orgPgisArea.loadOrgPgisArea",desc="区域规划加载",operateType="W",refTable="ENT_ORGANIZATION_PGIS_AREA",crudType=KConstants.OPER_SEARCH)
	@RequestMapping("/loadOrgPgisArea")
	public @ResponseBody Object loadOrgPgisArea(String orgid){
		OrgPgisArea orgPgisArae= orgPgisAreaServie.findByOrgnaId(orgid);
		Organization org=organizationService.findOrganById(orgid);
		if(org==null){
			return new KJSONMSG(300,"未查询到该组织机构");
		}
		Map<String,Object> map=Maps.newHashMap();
		if(orgPgisArae!=null){
			if("0".equals(orgPgisArae.getEnabled())){
				return new KJSONMSG(300,"该组织机构区域已禁用");
			}
			map.put("orgPgisAraeid", orgPgisArae.getOrgnaId());
			map.put("mapArea", orgPgisArae.getMapArea());
		}
		map.put("orgid", org.getOrgna_id());
		map.put("orgname", org.getOrgna_name());
		map.put("orgaddres", org.getOrgna_address());
		map.put("orgcode", org.getOrgna_code());
		return new KJSONMSG(200,"数据加载成功", map);
	}
	

	@AuthWidgetRule(value="orgPgisArea.saveOrgPgisArea",desc="区域规划添加",operateType="W",refTable="ENT_ORGANIZATION_PGIS_AREA",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/saveOrgPgisArea", method = RequestMethod.POST)
	public @ResponseBody Object saveOrgPgisArea(String orgid,String mapArea) {
		orgPgisAreaServie.saveOrgPgisArea(orgid,mapArea);
		return ajaxDoneSuccess("区域规划添加成功 ");
	}
	
}
