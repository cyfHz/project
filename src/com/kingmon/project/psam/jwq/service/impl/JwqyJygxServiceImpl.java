package com.kingmon.project.psam.jwq.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organizationuser.mapper.OrganizationUserMapper;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jwq.mapper.JwqyJygxMapper;
import com.kingmon.project.psam.jwq.service.IJwqyJygxService;

@Service
public class JwqyJygxServiceImpl extends BaseService implements IJwqyJygxService{

	@Autowired
	private JwqMapper jwqMapper;
	@Autowired
    private OrganizationUserMapper organizationUserMapper;
	@Autowired
	private JwqyJygxMapper jwqyJygxMapper;


	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadPcsJyNotInJwqDataSet(ParamObject po,String jwqid) {
		if(!StringUtils.hasText(jwqid)){
			return DataSet.newDs();
		}
		String pcsid=jwqMapper.selectPcsIdByJwqid(jwqid);
		if(!StringUtils.hasText(pcsid)){
			return DataSet.newDs();
		}
		String sfyx="1";
		StringBuilder sql=new StringBuilder("");
		sql.append(" SELECT  ")
			.append(" u.APPUSER_ID,")
			.append(" u.USER_NAME,")
			.append(" u.USER_SFZH,")
			.append(" u.USER_MOBILE,")
			.append(" u.OPRATETIME,")
			.append(" o.ORGNA_ID,")
			.append(" o.ORGNA_NAME ")
			.append(" @from APP_ORGANIZATION_USER u ")
			.append(" left join app_organization o on o.orgna_id=u.ORGNA_ID ")
			.append(" where 1=1 ");
			sql.append(" and u.ORGNA_ID ='"+pcsid+"'  ");
			sql.append(" and u.APPUSER_ID not in  ");
			sql.append(" ( select j.USER_ID from ent_jwqyjygx j where j.JWQID=:jwqid and j.SFYX='"+sfyx+"' )");
			po.addSQLParam("jwqid", jwqid);
			
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
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadPcsJyInJwqDataSet(ParamObject po,String jwqid) {
		if(!StringUtils.hasText(jwqid)){
			return DataSet.newDs();
		}
		String pcsid=jwqMapper.selectPcsIdByJwqid(jwqid);
		if(!StringUtils.hasText(pcsid)){
			return DataSet.newDs();
		}
		String sfyx="1";
		StringBuilder sql=new StringBuilder("");
		 sql.append("select j.ID,")
			.append("j.JWQID,")
			.append("j.SFYX,")
			.append("j.QYRQ,")
			.append("j.SXRQ,")
			.append("j.XGSJ,")
			.append("u.APPUSER_ID,")
			.append("u.USER_SFZH,")
			.append("u.USER_MOBILE,")
			.append("u.USER_NAME,")
			.append("o.ORGNA_ID,")
			.append("o.ORGNA_NAME")
			.append(" @from ent_jwqyjygx j")
			.append(" left join APP_ORGANIZATION_USER u on u.APPUSER_ID = j.USER_ID")
			.append(" left join APP_ORGANIZATION o  on u.ORGNA_ID = o.ORGNA_ID")
		 	.append(" where 1 = 1 ")
		 	.append(" and  j.SFYX='"+sfyx+"'");
		 	sql.append("   and j.JWQID=:jwqid ");
		 	po.addSQLParam("jwqid", jwqid);
			sql.append("  and o.ORGNA_ID='"+pcsid+"' ");
			
		String user_name = (String) po.getWebParam("user_name");
		if (!SubApStrUtil.isEmptyAfterTrimE(user_name)) {
			sql.append("and u.USER_NAME like:user_name ");
			po.addSQLParam("user_name", "%"+user_name+"%");
		}
		if(po.hasOrder()){
			if("USER_NAME".equals(po.getSort())){
				sql.append(" order by ").append("u.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
			}else{
				sql.append(" order by ").append("j.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
			}
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	
	

	
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addJyToJwq(String[] app_userids, String jwqid){
	
		KAssert.notNull(app_userids, "请选择用户");
		KAssert.notEmpty(app_userids, "请选择用户");
		KAssert.notNull(jwqid, "请选择警务区");
		
//		List<String> strList=Arrays.asList(app_userids);
//		List<String> userList = Lists.newArrayList();
//		String selectSql="select USER_ID as userid from ent_jwqyjygx  where USER_ID in (:app_userids )  and JWQID=:jwqid";
//		Collections.addAll(userList, app_userids);
//		
//		List<Map<String,Object>> listHaveIntable=(List<Map<String, Object>>) jdbcBaseDao.jdbcQueryMapList(selectSql, 
//					ParamObject.new_NP_NC().
//					addSQLParam("app_userids", strList).
//					addSQLParam("jwqid", jwqid));
//		
//		if(listHaveIntable!=null&&listHaveIntable.size()>0){
//			for(Map<String,Object> map: listHaveIntable){
//				String userid=(String) map.get("userid");
//				userList.remove(userid);
//			}
//		}
		List<String> strList=Arrays.asList(app_userids);
		String deleteSql=" delete from ent_jwqyjygx  where USER_ID in (:app_userids )  and JWQID=:jwqid";
				//+ " and JWQID=:jwqid ";
				//+ " and SFYX='1' ";
		ParamObject[] mapDelArray = new ParamObject[app_userids.length];  
		for(int i=0;i<app_userids.length;i++){
			ParamObject po=new ParamObject();
			po.addSQLParam("app_userids", app_userids[i]);
			po.addSQLParam("jwqid",jwqid);
			mapDelArray[i]=(po);
		}
		int x=jdbcBaseDao.jdbcDelete(deleteSql, ParamObject.new_NP_NC().addSQLParam("app_userids", strList).addSQLParam("jwqid",jwqid));
		
		List<String> userList = Arrays.asList(app_userids);
		String sql="INSERT INTO ent_jwqyjygx(ID,JWQID, USER_ID, SFYX, QYRQ, XGSJ,MOVESIGN) VALUES "
										+ "(:id,:jwqid,:user_id,:sfyx,:qyrq,:xgsj,'0')";
		String sfyx="1";
		String qyrq=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD);//日期
		String xgsj=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
		
		//List<ParamObject> list=Lists.newArrayList();
		
		ParamObject[] mapArray = new ParamObject[userList.size()]; 
		int index=0;
		for(String userid:userList){
			ParamObject po=ParamObject.new_NP_NC();
			  po.addSQLParam("id", UUIDUtil.uuid())
			  .addSQLParam("jwqid", jwqid)
			  .addSQLParam("user_id", userid)
			  .addSQLParam("sfyx", sfyx)
			  .addSQLParam("qyrq", qyrq) 
			  .addSQLParam("xgsj", xgsj);
			  //list.add(po);
			  mapArray[index]=po;
			  index++;
		}
		//SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(list.toArray());
		int[] count=jdbcBaseDao.jdbcBatchInsert(sql, mapArray);
		SecurityUtils.refreshUserLevel();
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void removeJyFromJwq(String[] app_userids, String jwqid){
		KAssert.notNull(app_userids, "请选择用户");
		KAssert.notEmpty(app_userids, "请选择用户");
		KAssert.notNull(jwqid, "请选择警务区");
		//List<String> strList=Arrays.asList(app_userids);
		String sql=" delete from ent_jwqyjygx  where USER_ID in (:app_userids )  and JWQID=:jwqid ";
		
		ParamObject[] mapArray = new ParamObject[app_userids.length];  
		for(int i=0;i<app_userids.length;i++){
			ParamObject po=new ParamObject();
			po.addSQLParam("app_userids", app_userids[i]);
			po.addSQLParam("jwqid",jwqid);
			mapArray[i]=(po);
		}
		
		int x[]=jdbcBaseDao.jdbcBatchDelete(sql, mapArray);
		
		SecurityUtils.refreshUserLevel();
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void disableJwqyJygx(String app_userid, String jwqid){
		KAssert.notNull(app_userid, "请选择用户");
		KAssert.notNull(jwqid, "请选择警务区");
		String sfyx="0";
		String sxrq=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD);//失效日期
		String xgsj=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
		String sql=" update ent_jwqyjygx set SFYX=:sfyx, SXRQ=:sxrq,XGSJ=:xgsj where USER_ID=:app_userid and jwqid=:jwqid";
		int x=jdbcBaseDao.jdbcUpdate(sql, ParamObject.new_NP_NC()
									.addSQLParam("sfyx", sfyx)
									.addSQLParam("sxrq", sxrq)
									.addSQLParam("xgsj", xgsj)
									.addSQLParam("app_userid", app_userid)
									.addSQLParam("jwqid", jwqid));
		SecurityUtils.refreshUserLevel();
	}
//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public void enableJwqyJygx(String app_userid, String jwqid){
//		KAssert.notNull(app_userid, "请选择用户");
//		KAssert.notNull(jwqid, "请选择警务区");
//		String sfyx="1";
//		String qyrq=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD);//日期
//		String xgsj=ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS);
//		String sql=" update ent_jwqyjygx set SFYX=:sfyx, QYRQ=:qyrq,XGSJ=:xgsj where USER_ID=:app_userid and jwqid=:jwqid";
//		int x=jdbcBaseDao.jdbcUpdate(sql, ParamObject.new_NP_NC()
//									.addSQLParam("sfyx", sfyx)
//									.addSQLParam("qyrq", qyrq)
//									.addSQLParam("xgsj", xgsj)
//									.addSQLParam("app_userid", app_userid)
//									.addSQLParam("jwqid",jwqid));
//	}

	//@Cacheable
	@Override
	public List<String> findJwqIdByAppuserId(String appuser_id,String sfyx) {
		if(!StringUtils.hasText(appuser_id)){
			return null;
		}
		String sql=" select j.JWQID  from ent_jwqyjygx j where j.USER_ID =:appuser_id";
		ParamObject po=ParamObject.new_NP_NC().addSQLParam("appuser_id", appuser_id);
		
		if(StringUtils.hasText(sfyx)){
			sql+=" and j.SFYX=:sfyx ";
			po.addSQLParam("sfyx", sfyx);
		}
		List<String>  lists=Lists.newArrayList();
		List<Map<String, Object>> list=(List<Map<String, Object>>) jdbcBaseDao.jdbcQueryMapList(sql,po);
		if(list!=null&&!list.isEmpty()){
			for(Map<String, Object> map:list){
				lists.add((String)map.get("JWQID"));
			}
		}
		return lists;
//		List<String> list=jwqyJygxMapper.seelctJwqIdByAppuserId(appuser_id, sfyx);

	}
//	@Cacheable(value="authTmpResultCache",key="'JwqyJygxServiceImpl_isUserAssignedToJwq'+#appuser_id")
	@Override
	public boolean isUserAssignedToJwq(String appuser_id) {
		if(!StringUtils.hasText(appuser_id)){
			return false;
		}
		String sql=" select count(1) from ent_jwqyjygx j where j.USER_ID =:appuser_id  and j.SFYX='1' ";
		ParamObject po=ParamObject.new_NP_NC().addSQLParam("appuser_id", appuser_id);
		long count=jdbcBaseDao.jdbcQueryCount(sql,po);
		return count>0?true:false;
	}
}


