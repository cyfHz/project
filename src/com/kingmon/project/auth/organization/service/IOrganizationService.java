package com.kingmon.project.auth.organization.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.view.OrgChaiFenPostView;

public interface IOrganizationService {
	/**
	 * 只查询： O.ORGNA_ID ,O.ORGNA_CODE,O.ORGNA_NAME<br>
	 * @param APPUSER_ID app_organization_user 中字段APPUSER_ID， 若为空然会null
	 * @return
	 */
	public Organization findOrgByUserId(String appuser_id);
	
	public DataSet loadChildOrgans(String id);
	
	public Organization findOrganById(String orgna_id);
	
	public DataSet loadOrganizationDataset(ParamObject po);
	
	public  List<Organization> findOrgByIds(@Param("ids") List<String> ids);
	
	public void addOrganization(Organization organization,String type);
	
	//----------------------------------------------------
	public KJSONMSG checkOrgHebing(String[] orgnaIds);
	
	public void processOrgHeBing(String[] fromIds, String toId);
	
	//----------------------------------------------------	
	public DataSet loadOrgChaiFenDataGrid(String fromOrgId);
	
	public KJSONMSG checkOrgChaiFen(String fromId,String[] toIds);
	
	public void  processOrgChaiFen( OrgChaiFenPostView orgChaiFenPostView);

}
