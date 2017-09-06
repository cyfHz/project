package com.kingmon.project.auth.organizationuser.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.common.authUtil.DataRuleUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.auth.organization.mapper.OrganizationMapper;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organizationuser.mapper.OrganizationUserMapper;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.kingmon.project.psam.jwq.mapper.JwqyJygxMapper;
@Service
public class OrganizationUserServiceImpl  extends BaseService implements IOrganizationUserService{

	@Autowired
	private  OrganizationUserMapper organizationUserMapper;
	
	@Autowired
	private OrganizationMapper organizationMapper;
	
	@Autowired
	private JwqyJygxMapper jwqyJygxMapper;
	
	@Cacheable(value="authTmpResultCache",key="'OrganizationUserServiceImpl_findOrganizationUserByUserId'+#appuser_id")
	@Override
	public OrganizationUser findByUserId(String appuser_id) {
		if(!StringUtils.hasText(appuser_id)){
			return null;
		}
		OrganizationUser user= organizationUserMapper.selectOrganizationUserByUserId(appuser_id);
		return user;
	}
	
	@Cacheable(value="authTmpResultCache",key="'OrganizationUserServiceImpl_findOrganizationUserBySfzh'+#sfzh")
	@Override
	public OrganizationUser findBySfzh(String sfzh) {
		if(!StringUtils.hasText(sfzh)){
			return null;
		}
		OrganizationUser user=organizationUserMapper.selectOrganizationUserBySfzh(sfzh);
		return user;
	}
	@Override
	public OrganizationUser findByLoginnameAndPws(String loginname, String password) {
		if(!StringUtils.hasText(loginname)){
			return null;
		}
		if(!StringUtils.hasText(password)){
			return null;
		}
		OrganizationUser user=organizationUserMapper.selectOrganUserByLoginnameAndPws(loginname, password);
		return user;
	}

//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public void changePassword(String userId, String oldPassPageEncoded, String newPassEncoded) {
//		KAssert.notNull(userId, "参数错误");
//		KAssert.notNull(oldPassPageEncoded, "参数错误");
//		KAssert.notNull(newPassEncoded, "参数错误");
//		KAssert.hasText(userId, "参数错误");
//		KAssert.hasText(oldPassPageEncoded, "参数错误");
//		KAssert.hasText(newPassEncoded, "参数错误");
//		oldPassPageEncoded=oldPassPageEncoded.trim();
//		newPassEncoded=newPassEncoded.trim();
//		
//		OrganizationUser user = organizationUserMapper.selectOrganizationUserByUserId(userId);
//		KAssert.notNull(user, "未查询到数据");
//		
//		String sql = "select count(1) from APP_ORGANIZATION_USER x where x.APPUSER_ID=:appuser_id and x.USER_PASSWORD=:user_password ";
//		long count = jdbcBaseDao.jdbcQueryCount(sql, ParamObject.new_NP_NC()
//					.addSQLParam("appuser_id", userId)
//					.addSQLParam("user_password", oldPassPageEncoded));
//		if (count <= 0) {
//			AlertSLEUtil.Error("原密码填写不正确。");
//		}
//		
//		user.setUser_password(newPassEncoded);
//		organizationUserMapper.updateByPrimaryKeySelective(user);
//	}
	
	
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadorgUserDataSet(ParamObject po) {
		StringBuilder sql=new StringBuilder("");
		sql.append(" SELECT u.APPUSER_ID, ")
		.append(" u.AREA_ID, ")
		.append(" u.ORGNA_ID, ")
		.append(" u.USER_ID, ")
		.append(" u.USER_NAME, ")
		.append(" u.USER_SEX, ")
		.append(" u.USER_SFZH, ")
		.append(" u.SSFJ, ")
		.append(" u.SSPCS, ")
		.append(" u.SSJWQ, ")
		.append(" u.SSJB, ")
		.append(" u.USER_MOBILE, ")
		.append(" u.OPRATETIME ")
		.append(" @from APP_ORGANIZATION_USER u ")
		.append(" where 1=1 ");
		String user_name = (String) po.getWebParam("user_name");
		if (!SubApStrUtil.isEmptyAfterTrimE(user_name)) {
			sql.append("and u.USER_NAME like:user_name ");
			po.addSQLParam("user_name", "%"+user_name+"%");
		}
		
		if(po.hasOrder()){
			sql.append(" order by ").append("u.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

	@Override
	public Map<String, Object> loadUserInfoByUserId(String appuser_id) {
		return organizationUserMapper.loadUserInfoByUserId(appuser_id);
	}
	
	//------------------------------------------------------------------------------------
	//@Cacheable(value="authTmpResultCache",key="'RoleServiceImpl_selectRoleListByByUserId'+#APPUSER_ID")
	@CacheEvict(value="authTmpResultCache",key="'RoleServiceImpl_selectRoleListByByUserId'+#APPUSER_ID") 
	@SuppressWarnings({ "unchecked", "unused" })
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addRoleToUser(String[] roleIds, String APPUSER_ID ){
	
		KAssert.notNull(APPUSER_ID, "请选择用户");
		KAssert.notEmpty(roleIds, "请选择角色");
		
		String deleteSql=" delete from app_role_user  where ROLE_ID in (:roleIds )  and APPUSER_ID=:appuserid "
				+ " and ENABLED='1' ";
		ParamObject[] mapDelArray = new ParamObject[roleIds.length];  
		for(int i=0;i<roleIds.length;i++){
			ParamObject po=new ParamObject();
			po.addSQLParam("roleIds", roleIds[i]);
			po.addSQLParam("appuserid",APPUSER_ID);
			mapDelArray[i]=(po);
		}
		int x[]=jdbcBaseDao.jdbcBatchDelete(deleteSql, mapDelArray);
		
		List<String> roleList = Arrays.asList(roleIds);
		
		String sql="INSERT INTO app_role_user(ID, APPUSER_ID, ROLE_ID,  AREA_ID, OPRATETIME,ENABLED) VALUES "
										+ " (:id,:appuser_id, :role_id, :areaId, :optime,   :enabled )"; 
		String enabled="1";
		String areaId="1";
		String optime=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
		
		ParamObject[] mapArray = new ParamObject[roleList.size()]; 
		int index=0;
		for(String roleid:roleList){
			ParamObject po=ParamObject.new_NP_NC();
			  po.addSQLParam("id", UUIDUtil.uuid())
			  .addSQLParam("appuser_id", APPUSER_ID)
			  .addSQLParam("role_id", roleid)
			  .addSQLParam("areaId",areaId)
			  .addSQLParam("optime", optime) 
			  .addSQLParam("enabled", enabled);
			  mapArray[index]=po;
			  index++;
		}
		int[] count=jdbcBaseDao.jdbcBatchInsert(sql, mapArray);
	}
	@SuppressWarnings({ "unused", "unchecked" })
	@CacheEvict(value="authTmpResultCache",key="'RoleServiceImpl_selectRoleListByByUserId'+#APPUSER_ID") 
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void removeRoleFromUser(String[] roleids, String APPUSER_ID){
		KAssert.notNull(roleids, "请选择角色");
		KAssert.notNull(APPUSER_ID, "请选择用户");
		KAssert.notEmpty(roleids, "请选择角色");
		String sql=" delete from app_role_user  where ROLE_ID in (:rolid )  and APPUSER_ID=:app_userid ";
		
		ParamObject[] mapArray = new ParamObject[roleids.length];  
		for(int i=0;i<roleids.length;i++){
			ParamObject po=new ParamObject();
			po.addSQLParam("rolid", roleids[i]);
			po.addSQLParam("app_userid",APPUSER_ID);
			mapArray[i]=(po);
		}
		int x[]=jdbcBaseDao.jdbcBatchDelete(sql, mapArray);
		
	}
	
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadRoleUserHaveDataSet(ParamObject po,String appuser_id) {
		if(!StringUtils.hasText(appuser_id)){
			return DataSet.newDs();
		}
		String enabled="1";
		StringBuilder sql=new StringBuilder("");
		 sql.append(" select r.ROLE_ID, r.ROLE_CODE, r.ROLE_NAME, r.OPRATETIME,r.ENABLED,r.ORGANID ")
			.append(" @from APP_ROLE r  ")
			.append(" left join APP_ROLE_USER ru on r.ROLE_ID = ru.ROLE_ID ")
		 	.append(" where 1 = 1 ")
		 	.append(" and  ru.ENABLED='"+enabled+"'")
		 	.append(" and  r.ENABLED='"+enabled+"'")
		 	.append("   and ru.APPUSER_ID=:appuser_id ");
		 	po.addSQLParam("appuser_id", appuser_id);
			
		String role_name = (String) po.getWebParam("role_name");
		if (!SubApStrUtil.isEmptyAfterTrimE(role_name)) {
			sql.append("and r.ROLE_NAME like:role_name ");
			po.addSQLParam("role_name", "%"+role_name+"%");
		}
		if(po.hasOrder()){
			if("role_name".equalsIgnoreCase(po.getSort())){
				sql.append(" order by ").append("r.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
			}else{
				sql.append(" order by ").append("ru.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
			}
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadRoleUserNotHaveDataSet(ParamObject po,String appuser_id) {
		if(!StringUtils.hasText(appuser_id)){
			return DataSet.newDs();
		}
		String enabled="1";
		StringBuilder sql=new StringBuilder("");
		 sql.append(" select r.ROLE_ID, r.ROLE_CODE, r.ROLE_NAME, r.OPRATETIME,r.ENABLED,r.ORGANID ")
			.append(" @from APP_ROLE r  ")
		 	.append(" where 1 = 1 ")
		 	.append(" and  r.ENABLED='"+enabled+"'")
		 	.append(" and  r.ROLE_ID not in ( select ru.ROLE_ID from APP_ROLE_USER ru where ru.APPUSER_ID=:appuser_id and ru.ENABLED='"+enabled+"' )");
		 	po.addSQLParam("appuser_id", appuser_id);
			
		String role_name = (String) po.getWebParam("role_name");
		if (!SubApStrUtil.isEmptyAfterTrimE(role_name)) {
			sql.append("and r.ROLE_NAME like:role_name ");
			po.addSQLParam("role_name", "%"+role_name+"%");
		}
		if(po.hasOrder()){
			if("role_name".equalsIgnoreCase(po.getSort())){
				sql.append(" order by ").append("r.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
			}else{
				sql.append(" order by ").append("ru.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
			}
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	
	public SessionUser loadUserInfor(String key){
		SessionUser sessionUser=new SessionUser();
//		OrganizationUser orgUser=findBySfzh(key);
		if(!StringUtils.hasText(key)){
//			371323198912074911
			return null;
		}
		OrganizationUser orgUser=organizationUserMapper.selectByPrimaryKey(key);
		if(orgUser==null){
			return null;
		}
		sessionUser.setLoginname(orgUser.getUser_loginname());
		sessionUser.setName(orgUser.getUser_name());
		sessionUser.setSfzh(orgUser.getUser_sfzh());
		sessionUser.setUserId(orgUser.getAppuser_id());
		sessionUser.setEnabled(orgUser.getEnabled());
		sessionUser.setUserMoile(orgUser.getUser_mobile());
		
		Organization organization=organizationMapper.selectOrgByUserId(orgUser.getAppuser_id());
		if(organization==null){
			return null;
		}
		sessionUser.setOrganizationId(organization.getOrgna_id());
		sessionUser.setOrganizationCode(organization.getOrgna_code());
		sessionUser.setOrganizationName(organization.getOrgna_name());
		sessionUser.setOrganizationTel(organization.getOrgna_tel());
			int userLevel=DataRuleUtil.getUserLevel(orgUser.getAppuser_id());
//			int userLevel=SecurityUtils.getUserLevel();
			if(userLevel<0){
				return null;
			}
			sessionUser.setYhjb(""+userLevel);
			String userLevelStr= DataRuleUtil.getXzqhStr(orgUser.getAppuser_id());
			sessionUser.setUserLevel(userLevel);//所属级别
			sessionUser.setUserLevelStr(userLevelStr);
			
			String org_code_sssj=null;//所属
			String org_code_ssfj=null;//所属分局
			String org_code_sspcs=null;//所属派出所
			// 12：警务区 ； 9：派出所[20160218改为：10]-->10  ；   6:分局  ；  4:市局  ；  2: 省厅
			switch (userLevel) {
			case 12:
				List<String> jwqBhList=jwqyJygxMapper.selectJwqBhByUserId(orgUser.getAppuser_id());
				sessionUser.setSsjwqList(jwqBhList);
				org_code_sspcs=organization.getOrgna_code();
				org_code_ssfj=org_code_sspcs.substring(0, 6)+"000000";
				org_code_sssj=org_code_sspcs.substring(0, 4)+"00000000";
				sessionUser.setSspcs(org_code_sspcs);
				sessionUser.setSsfj(org_code_ssfj);
				sessionUser.setSssj(org_code_sssj);
				break;
			case 9://：派出所[20160218改为：10]-->10  ；
				org_code_sspcs=organization.getOrgna_code();
				org_code_ssfj=org_code_sspcs.substring(0, 6);
				org_code_sssj=org_code_sspcs.substring(0, 4);
				sessionUser.setSspcs(org_code_sspcs);
				sessionUser.setSsfj(org_code_ssfj);
				sessionUser.setSssj(org_code_sssj);
				break;
			case 10://：派出所[20160218改为：10]-->10  ；
				org_code_sspcs=organization.getOrgna_code();
				org_code_ssfj=org_code_sspcs.substring(0, 6)+"000000";
				org_code_sssj=org_code_sspcs.substring(0, 4)+"00000000";
				sessionUser.setSspcs(org_code_sspcs);
				sessionUser.setSsfj(org_code_ssfj);
				sessionUser.setSssj(org_code_sssj);
				break;
			case 6:
				 org_code_ssfj=organization.getOrgna_code();
				 org_code_sssj=org_code_ssfj.substring(0, 4)+"00000000";
				//user.setSspcs(org_code_sspcs);
				sessionUser.setSsfj(org_code_ssfj);
				sessionUser.setSssj(org_code_sssj);
				break;
			case 4:
				org_code_sssj=organization.getOrgna_code();
				sessionUser.setSssj(org_code_sssj);
				break;
			case 2:
				break;
			default:
				break;
			}
			return sessionUser;
	}
}
