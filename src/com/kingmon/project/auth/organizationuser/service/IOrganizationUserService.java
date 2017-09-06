package com.kingmon.project.auth.organizationuser.service;

import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;

public interface IOrganizationUserService {
	
	/**
	 * 根据用户名和密码查询<br>
	 * @param loginname
	 * @param password
	 * @return
	 */
	public OrganizationUser findByLoginnameAndPws(String loginname,String password);
	
	
	/**
	 * 根据身份证号查询<br>
	 * appuser_id ,orgna_id,user_loginname,user_name, <br>
     * user_sfzh,enabled,ssfj,sspcs,ssjwq,ssjb  
	 * @param sfzh
	 * @return
	 */
	public OrganizationUser findBySfzh(String sfzh);
	
	/**
	 * 根据appuser_id查询<br>
	 * appuser_id ,orgna_id,user_loginname,user_name, <br>
     * user_sfzh,enabled,ssfj,sspcs,ssjwq,ssjb 
	 * @param appuser_id
	 * @return
	 */
	public OrganizationUser findByUserId(String appuser_id);

	/**
	 * 修改密码
	 * @param userId
	 * @param oldPassPageEncoded
	 * @param newPassEncoded
	 */
//	public void changePassword(String userId, String oldPassPageEncoded, String newPassEncoded);

	/**
	 * 根据ID获取个人信息
	 * @param appuser_id
	 * @return
	 */
	public Map<String, Object> loadUserInfoByUserId(String appuser_id);
	
	
	public DataSet loadorgUserDataSet(ParamObject po);
	
	public void addRoleToUser(String[] roleIds, String appuser_id );
	public void removeRoleFromUser(String[] roleids, String app_userid);
	
	public DataSet loadRoleUserNotHaveDataSet(ParamObject po,String appuser_id) ;
	public DataSet loadRoleUserHaveDataSet(ParamObject po,String appuser_id);

	public SessionUser loadUserInfor(String key);
}
