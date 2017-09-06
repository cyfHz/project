package com.kingmon.project.auth.organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.auth.organization.OrgUtilX;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.auth.organization.view.OrgChaiFenPostView;
@Controller
@RequestMapping("/auth/organization")
public class OrganizationController extends KBaseController{
	
	private static final String prefix="auth/organization/";
	
	@Autowired
	private IOrganizationService  organizationService;
	
	 
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="组织机构查找带回",operateType="W",refTable="",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value="/loadOrgBingbackPage")
	public Object loadOrgBingbackPage(Model model ,String porgid) {
		setDataAttribute(model, porgid, "porgid");
		return prefix+"organization.bringback.dialog";
	}
	
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="组织结机构据加载",operateType="W",refTable="APP_ORGANIZATION",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadOrganTree", method = RequestMethod.POST)
	@ResponseBody
	public Object loadOrganTree(String id) {
		//null会被缓存住，导致 多次数据错误
		if(id==null||id.isEmpty()){
			id=KConstants.cacheable_prefix+UUIDUtil.uuid();
		}
		DataSet dataSet= organizationService.loadChildOrgans(id);
		return FastjsonUtil.convert(dataSet);
	}

//--------------------------------------组织机构---------------------------------------------
//	@AuthWidgetRule(value="organization.addOrganization",desc="组织结机构添加",operateType="W",refTable="APP_ORGANIZATION",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddOrganization",method=RequestMethod.GET)
	public String enterAddOrganization(Model model,String porgid) {
		if(porgid!=null&&!porgid.isEmpty()){
			Organization porg=organizationService.findOrganById(porgid);
			String ptype=OrgUtilX.checkOrg(porg==null?"":porg.getOrgna_code());
			setDataAttribute(model, ptype, "ptype");
			setDataAttribute(model, porg, "porg");
			return prefix + "organizationAdd_pid";
		}
		return prefix + "organizationAdd";
	} 
//	@AuthWidgetRule(value="organization.addOrganization",desc="组织结机构添加",operateType="W",refTable="APP_ORGANIZATION",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/addOrganization",method=RequestMethod.POST)
	@ResponseBody
	public Object addOrganization(@ModelAttribute("organization")Organization organization,String type) {
		organizationService.addOrganization(organization, type);
		return ajaxDoneSuccess("数据添加成功 ");
	}
//----------------------------------------ChaiFen--------------------------------------------------------------------------------------------
//	@AuthWidgetRule(value="organization.orgChaiFen",desc="组织结机构合并",operateType="W",refTable="ENT_ORGANIZATION_PGIS_AREA",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/loadOrgChaiFenDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object loadOrgChaiFenDataGrid(String fromOrgId) {
		DataSet dataSet = organizationService.loadOrgChaiFenDataGrid(fromOrgId);
		return FastjsonUtil.convert(dataSet);
	}

//	@AuthWidgetRule(value="organization.orgChaiFen",desc="组织结机构拆分",operateType="W",refTable="APP_ORGANIZATION",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/checkOrgChaiFen", method = RequestMethod.POST)
	@ResponseBody
	public Object checkOrgChaiFen(String fromId,String[] toIds) {
		return organizationService.checkOrgChaiFen(fromId,toIds);
	}
//	@AuthWidgetRule(value="organization.orgChaiFen",desc="组织结机构拆分",operateType="W",refTable="APP_ORGANIZATION",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/processOrgChaiFen", method = RequestMethod.POST)
	@ResponseBody
	public Object processOrgChaiFen(@RequestBody OrgChaiFenPostView orgChaiFenPostView) {
		organizationService.processOrgChaiFen(orgChaiFenPostView);
		 return ajaxDoneSuccess("操作成功 ");
	}
	
//----------------------------------------HeBing--------------------------------------------------

//	@AuthWidgetRule(value="organization.orgHeBing",desc="组织结机构合并",operateType="W",refTable="APP_ORGANIZATION",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/checkOrgHeBing", method = RequestMethod.POST)
	@ResponseBody
	public Object checkOrgHeBing(String[] oranIds) {
		return organizationService.checkOrgHebing(oranIds);
	}
//	@AuthWidgetRule(value="organization.orgHeBing",desc="组织结机构合并",operateType="W",refTable="APP_ORGANIZATION",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/processOrgHeBing", method = RequestMethod.POST)
	@ResponseBody
	public Object processOrgHeBing(String[] fromIds,String toId) {
		 organizationService.processOrgHeBing(fromIds, toId);
		 return ajaxDoneSuccess("操作成功 ");
	}

}
