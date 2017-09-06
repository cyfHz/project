package com.kingmon.project.auth.organizationuser.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
@KMapper
public interface OrganizationUserMapper {
//    int deleteByPrimaryKey(String APPUSER_ID);
//
//    int insert(OrganizationUser record);
//
//    int insertSelective(OrganizationUser record);
//
    OrganizationUser selectByPrimaryKey(String appuser_id);
//
    int updateByPrimaryKeySelective(OrganizationUser record);
//
//    int updateByPrimaryKeyWithBLOBs(OrganizationUser record);
//
//    int updateByPrimaryKey(OrganizationUser record);
   /**
    * 为警区分配人员
    * @param map
    */
	void assignedJy(Map<String, Object> map);
	/**
	 * u.appuser_id ,  u.orgna_id,   u.user_loginname,   u.user_name, <br>
     * u.user_sfzh,   u.enabled,   u.ssfj,  u.sspcs,  u.ssjwq,   u.ssjb 
	 * @param appuser_id
	 * @return
	 */
	OrganizationUser selectOrganizationUserByUserId(String appuser_id);
	
	/**
	 * u.appuser_id ,  u.orgna_id,   u.user_loginname,   u.user_name, <br>
     * u.user_sfzh,   u.enabled,   u.ssfj,  u.sspcs,  u.ssjwq,   u.ssjb
	 * @param sfzh
	 * @return
	 */
	OrganizationUser selectOrganizationUserBySfzh(String sfzh);
	
	/**
	 * u.appuser_id ,  u.orgna_id,   u.user_loginname,   u.user_name, <br>
     * u.user_sfzh,   u.enabled,   u.ssfj,  u.sspcs,  u.ssjwq,   u.ssjb
	 * @param loginname
	 * @param password
	 * @return
	 */
	OrganizationUser selectOrganUserByLoginnameAndPws(@Param("loginname")String loginname, @Param("password")String password);
   
	/**
	 * 加载用户信息
	 * @param appuser_id
	 * @return
	 */
	Map<String, Object> loadUserInfoByUserId(String appuser_id);
	
	
	List<OrganizationUser> selectOrgUserByOrgCode(String org_id);
	
}