package com.kingmon.project.auth.role.service;

import java.util.List;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.auth.role.model.Role;

public interface IRoleService {

	/**
	 * R.ROLE_ID, R.ROLE_NAME,R.ROLE_CODE, R.ORGANID <br>
	 * @param userId
	 * @return
	 */
	public List<Role> selectRoleListByByUserId(String userId);
	
	public DataSet loadRoleDataSet(ParamObject po) ;
	
	public DataSet loadResourceRoleNotHaveDataSet(ParamObject po,String role_id) ;
	public DataSet loadResourceRoleHaveDataSet(ParamObject po,String role_id);
	public void addResourceToRole(String[] resourceIds, String role_id);
	public void removeResourceFromRole(String[] resourceIds, String role_id);
//------------------------------------------------------------------------------------------	
	public DataSet loadWidgetRuleRoleNotHaveDataSet(ParamObject po,String role_id) ;
	public DataSet loadWidgetRuleRoleHaveDataSet(ParamObject po,String role_id);
	public void addWidgetRuleToRole(String[] widgetRuleIds, String role_id);

	public void removeWidgetRuleFromRole(String[] widgetRuleIds, String role_id);

}
