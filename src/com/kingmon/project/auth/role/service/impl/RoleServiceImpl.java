package com.kingmon.project.auth.role.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import com.kingmon.project.auth.role.mapper.RoleMapper;
import com.kingmon.project.auth.role.model.Role;
import com.kingmon.project.auth.role.service.IRoleService;
@Service
public class RoleServiceImpl extends BaseService implements IRoleService{
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Cacheable(value="authTmpResultCache",key="'RoleServiceImpl_selectRoleListByByUserId'+#APPUSER_ID")
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> selectRoleListByByUserId(String APPUSER_ID) {
		List<Role> list=roleMapper.selectRoleListByByUserId(APPUSER_ID);
		return (List<Role>) (list==null?Collections.emptyList():list);
	}
//--------------------------------------------------------------------------------------------------------------------	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadRoleDataSet(ParamObject po) {
		StringBuffer sql=new StringBuffer("");
				sql.append("select r.ROLE_ID,")
				.append(" r.ROLE_NAME,")
				.append(" r.ROLE_DESC,")
				.append(" r.ROLE_CODE,")
				.append(" r.ENABLED,")
				.append(" r.OPRATETIME,")
				.append(" r.AREA_ID,")
				.append(" r.MOVESIGN,")
				.append(" r.ORGANID,")
				.append(" o.orgna_name as ORGNA_NAME,")
				.append(" r.CREATEUSER,")
				.append(" u.user_name  as CREATEUSER_NAME")
				.append(" @from app_role r")
				.append(" left join app_organization_user u")
				.append(" on u.appuser_id = r.createuser")
				.append(" left join app_organization o")
				.append(" on o.orgna_id = r.organid")
				.append(" where 1 = 1");

		String role_name = (String) po.getWebParam("role_name");
		if (!SubApStrUtil.isEmptyAfterTrimE(role_name)) {
			sql.append("and r.ROLE_NAME like:role_name ");
			po.addSQLParam("role_name", "%"+role_name+"%");
		}
		String role_code = (String) po.getWebParam("role_code");
		if (!SubApStrUtil.isEmptyAfterTrimE(role_code)) {
			sql.append("and r.ROLE_CODE like:role_code ");
			po.addSQLParam("role_code", "%"+role_code+"%");
		}
		if(po.hasOrder()){
			sql.append(" order by ").append("r.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}


	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadResourceRoleNotHaveDataSet(ParamObject po,String role_id) {
		if(!StringUtils.hasText(role_id)){
			return DataSet.newDs();
		}
		String enabled="1";
		StringBuilder sql=new StringBuilder("");
		 sql.append(" select r.RES_ID, r.AREA_ID ,r.RES_NAME , r.RES_TYPE,r.RES_PATHTYPE,r.RES_PVALUE,r.RES_CODE,r.RES_PID ,r.ENABLED ")
			.append(" @from APP_RESOURCE r  ")
		 	.append(" where 1 = 1 ")
			.append(" and ( r.RES_CODE like 'PSAM%' or r.RES_PID like 'PSAM%' )" )
		 	.append(" and  r.ENABLED='"+enabled+"'")
		 	.append(" and  r.RES_ID not in ( select rr.RES_ID from APP_ROLE_RESOURCE rr where rr.ROLE_ID=:role_id and rr.ENABLED='"+enabled+"' )");
		 	po.addSQLParam("role_id", role_id);
			
		String res_name = (String) po.getWebParam("res_name");
		if (!SubApStrUtil.isEmptyAfterTrimE(res_name)) {
			sql.append("and r.RES_NAME like:res_name ");
			po.addSQLParam("role_name", "%"+res_name+"%");
		}
		if(po.hasOrder()){
			sql.append(" order by ").append("r.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
		
	}
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadResourceRoleHaveDataSet(ParamObject po,String role_id){
		if(!StringUtils.hasText(role_id)){
			return DataSet.newDs();
		}
		String enabled="1";
		StringBuilder sql=new StringBuilder("");
		 sql.append("select r.RES_ID, r.AREA_ID ,r.RES_NAME , r.RES_TYPE,r.RES_PATHTYPE,r.RES_PVALUE,r.RES_CODE,r.RES_PID ,r.ENABLED ")
			.append(" @from APP_RESOURCE r ")
			.append(" left join APP_ROLE_RESOURCE rr on r.RES_ID = rr.RES_ID ")
		 	.append(" where 1 = 1 ")
		 	.append(" and ( r.RES_CODE like 'PSAM%' or r.RES_PID like 'PSAM%')" )
		 	.append(" and  rr.ENABLED='"+enabled+"'")
		 	.append(" and  r.ENABLED='"+enabled+"'")
		 	.append("   and rr.ROLE_ID=:role_id ");
		 	po.addSQLParam("role_id", role_id);
			
			String res_name = (String) po.getWebParam("res_name");
			if (!SubApStrUtil.isEmptyAfterTrimE(res_name)) {
				sql.append("and r.RES_NAME like:res_name ");
				po.addSQLParam("role_name", "%"+res_name+"%");
			}
			if(po.hasOrder()){
				sql.append(" order by ").append("r.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
			}
			return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	@SuppressWarnings("unchecked")
	@CacheEvict(value="authTmpResultCache") 
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addResourceToRole(String[] resourceIds, String role_id) {
		KAssert.notNull(resourceIds, "请选择资源");
		KAssert.notNull(role_id, "请选择角色");
		KAssert.notEmpty(resourceIds, "请选择资源");
		
		String deleteSql=" delete from app_role_resource  where RES_ID in (:resourceId )  and ROLE_ID=:role_id "
				+ " and ENABLED='1' ";
		ParamObject[] mapDelArray = new ParamObject[resourceIds.length];  
		for(int i=0;i<resourceIds.length;i++){
			ParamObject po=new ParamObject();
			po.addSQLParam("resourceId", resourceIds[i]);
			po.addSQLParam("role_id",role_id);
			mapDelArray[i]=(po);
		}
		int x[]=jdbcBaseDao.jdbcBatchDelete(deleteSql, mapDelArray);
		
		List<String> resourceList = Arrays.asList(resourceIds);
		
		String sql="INSERT INTO app_role_resource(ID, ROLE_ID,   RES_ID,    AREA_ID,    OPRATETIME, ENABLED) VALUES "
											 + " (:id, :role_id, :resourceId, :areaId, :optime,    :enabled )"; 
		String enabled="1";
		String areaId="1";
		String optime=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
		
		ParamObject[] mapArray = new ParamObject[resourceList.size()]; 
		int index=0;
		for(String resourceid:resourceList){
			ParamObject po=ParamObject.new_NP_NC();
			  po.addSQLParam("id", UUIDUtil.uuid())
			  .addSQLParam("role_id", role_id)
			  .addSQLParam("resourceId", resourceid)
			  .addSQLParam("areaId",areaId)
			  .addSQLParam("optime", optime) 
			  .addSQLParam("enabled", enabled);
			  mapArray[index]=po;
			  index++;
		}
		int[] count=jdbcBaseDao.jdbcBatchInsert(sql, mapArray);
	}
	@CacheEvict(value="authTmpResultCache") 
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeResourceFromRole(String[] resourceIds, String role_id) {
		KAssert.notNull(resourceIds, "请选择资源");
		KAssert.notNull(role_id, "请选择角色");
		KAssert.notEmpty(resourceIds, "请选择资源");
		String sql=" delete from app_role_resource  where RES_ID in (:resourceId )  and ROLE_ID=:role_id ";
		
		ParamObject[] mapArray = new ParamObject[resourceIds.length];  
		for(int i=0;i<resourceIds.length;i++){
			ParamObject po=new ParamObject();
			po.addSQLParam("resourceId", resourceIds[i]);
			po.addSQLParam("role_id",role_id);
			mapArray[i]=(po);
		}
		int x[]=jdbcBaseDao.jdbcBatchDelete(sql, mapArray);
	}

//----------------------------------------------------------------------------------------------------------
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadWidgetRuleRoleNotHaveDataSet(ParamObject po,String role_id){
		if(!StringUtils.hasText(role_id)){
			return DataSet.newDs();
		}
		String enabled="1";
		StringBuilder sql=new StringBuilder("");
		 sql.append("select w.RULE_ID, w.AREA_ID, w.RULE_NAME, w.RULE_DESC, w.RULE_CODE, w.ENABLED,w.OPRATETIME ")
			.append(" @from APP_WIDGETRULE w  ")
		 	.append(" where 1 = 1 ")
		 	.append("  and w.RULE_CODE like '%.%'  ")
		 	.append(" and  w.ENABLED='"+enabled+"'")
		 	.append(" and  w.RULE_ID not in ( select rw.RULE_ID from APP_ROLE_RES_WRULE rw where rw.ROLE_ID=:role_id and rw.ENABLED='"+enabled+"' )");
		 	po.addSQLParam("role_id", role_id);
			
		String rule_name = (String) po.getWebParam("rule_name");
		if (!SubApStrUtil.isEmptyAfterTrimE(rule_name)) {
			sql.append("and r.RULE_NAME like:rule_name ");
			po.addSQLParam("rule_name", "%"+rule_name+"%");
		}
		String rule_code = (String) po.getWebParam("rule_code");
		if (!SubApStrUtil.isEmptyAfterTrimE(rule_code)) {
			sql.append("and r.RULE_CODE like:rule_code ");
			po.addSQLParam("rule_code", "%"+rule_code+"%");
		}
		if(po.hasOrder()){
			sql.append(" order by ").append("w.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadWidgetRuleRoleHaveDataSet(ParamObject po,String role_id){
		if(!StringUtils.hasText(role_id)){
			return DataSet.newDs();
		}
		String enabled="1";
		StringBuilder sql=new StringBuilder("");
		 sql.append("select w.RULE_ID, w.AREA_ID, w.RULE_NAME,  w.RULE_DESC, w.RULE_CODE, w.ENABLED,w.OPRATETIME ")
			.append(" @from APP_WIDGETRULE w ")
			.append(" left join APP_ROLE_RES_WRULE rw on w.RULE_ID = rw.RULE_ID ")
		 	.append(" where 1 = 1 ")
		 	.append("  and w.RULE_CODE like '%.%'  ")
		 	.append(" and  rw.ENABLED='"+enabled+"'")
		 	.append(" and  w.ENABLED='"+enabled+"'")
		 	.append("   and rw.ROLE_ID=:role_id ");
		 	po.addSQLParam("role_id", role_id);
			
			String rule_name = (String) po.getWebParam("rule_name");
			if (!SubApStrUtil.isEmptyAfterTrimE(rule_name)) {
				sql.append("and r.RULE_NAME like:rule_name ");
				po.addSQLParam("rule_name", "%"+rule_name+"%");
			}
			if(po.hasOrder()){
				sql.append(" order by ").append("w.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
			}
			return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	@CacheEvict(value="authTmpResultCache") 
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addWidgetRuleToRole(String[] widgetRuleIds, String role_id) {
		KAssert.notNull(widgetRuleIds, "请选择资源");
		KAssert.notNull(role_id, "请选择角色");
		KAssert.notEmpty(widgetRuleIds, "请选择资源");
		
		String deleteSql=" delete from app_role_res_wrule  where RULE_ID in (:ruleId )  and ROLE_ID=:role_id "
				+ " and ENABLED='1' ";
		ParamObject[] mapDelArray = new ParamObject[widgetRuleIds.length];  
		for(int i=0;i<widgetRuleIds.length;i++){
			ParamObject po=new ParamObject();
			po.addSQLParam("ruleId", widgetRuleIds[i]);
			po.addSQLParam("role_id",role_id);
			mapDelArray[i]=(po);
		}
		int x[]=jdbcBaseDao.jdbcBatchDelete(deleteSql, mapDelArray);
		
		List<String> ruleIdList = Arrays.asList(widgetRuleIds);
	
		String sql="INSERT INTO app_role_res_wrule(ID, RULE_ID,  ROLE_ID,   AREA_ID, OPRATETIME,ENABLED) VALUES "
										     + " (:id, :ruleId,   :role_id, :areaId, :optime,   :enabled )"; 
		String enabled="1";
		String areaId="1";
		String optime=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
		
		ParamObject[] mapArray = new ParamObject[ruleIdList.size()]; 
		int index=0;
		for(String ruleId:ruleIdList){
			ParamObject po=ParamObject.new_NP_NC();
			  po.addSQLParam("id", UUIDUtil.uuid())
			  .addSQLParam("role_id", role_id)
			  .addSQLParam("ruleId", ruleId)
			  .addSQLParam("areaId",areaId)
			  .addSQLParam("optime", optime) 
			  .addSQLParam("enabled", enabled);
			  mapArray[index]=po;
			  index++;
		}
		int[] count=jdbcBaseDao.jdbcBatchInsert(sql, mapArray);
	}
	@CacheEvict(value="authTmpResultCache") 
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeWidgetRuleFromRole(String[] widgetRuleIds, String role_id) {
		KAssert.notNull(widgetRuleIds, "请选择资源");
		KAssert.notNull(role_id, "请选择角色");
		KAssert.notEmpty(widgetRuleIds, "请选择控件");
		String sql=" delete from app_role_res_wrule  where RULE_ID in (:widgetRuleId )  and ROLE_ID=:role_id ";
		
		ParamObject[] mapArray = new ParamObject[widgetRuleIds.length];  
		for(int i=0;i<widgetRuleIds.length;i++){
			ParamObject po=new ParamObject();
			po.addSQLParam("widgetRuleId", widgetRuleIds[i]);
			po.addSQLParam("role_id",role_id);
			mapArray[i]=(po);
		}
		int x[]=jdbcBaseDao.jdbcBatchDelete(sql, mapArray);
	}
	

}
