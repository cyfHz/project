package com.kingmon.project.psam.orgarea.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.impl.OrganizationServiceImpl;
import com.kingmon.project.psam.jwq.UtilX;
import com.kingmon.project.psam.orgarea.mapper.OrgPgisAreaMapper;
import com.kingmon.project.psam.orgarea.model.OrgPgisArea;
import com.kingmon.project.psam.orgarea.service.IOrgPgisAreaService;

@Service
public class OrgPgisAreaServiceImpl extends BaseService implements IOrgPgisAreaService{

	@Autowired
	private OrgPgisAreaMapper orgPisAreaMapper;

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public DataSet loadOrgPgisAreaDataSet(ParamObject po) {
		StringBuilder sql = new StringBuilder("").append(" SELECT ").append(" opa.CREATED, ")
				.append(" opa.AREA, ").append(" opa.ENABLED, ").append(" opa.MAP_AREA, ").append(" ouser.USER_NAME as CREATEDBY ");
		sql.append(" @from ENT_ORGANIZATION_PGIS_AREA opa ");
		sql.append(" left join APP_ORGANIZATION_USER ouser on ouser.APPUSER_ID=opa.CREATEDBY ");
		sql.append(" where 1=1 ");

		if (po.hasOrder()) {
			sql.append(" order by ").append("opa.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public int deleteByOrgnaId(String orgnaId) {
//		KAssert.hasText(orgnaId, "所属组织机构ID不能为空");
//		int res=orgPisAreaMapper.deleteByPrimaryKey(orgnaId);
//		return res;
//	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int addOrgPisArea(OrgPgisArea record) {
		KAssert.hasText(record.getOrgnaId(), "所属组织机构ID不能为空");
		KAssert.hasText(record.getMapArea(), "警务区区域坐标不能为空");
		record.setCreated(ZDateUtil.getCurrentDateStr(ZDateStyle.MM_DD_HH_MM_SS));
		record.setCreatedby(SecurityUtils.getUserId());
		int res=orgPisAreaMapper.insertSelective(record);
		return res;
	}
	
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public OrgPgisArea findByOrgnaId(String orgnaId) {
		if(StringUtils.hasText(orgnaId)){
			return orgPisAreaMapper.selectByPrimaryKey(orgnaId);
		}
		return null;
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int updateOrgPisArea(OrgPgisArea record) {
		KAssert.hasText(record.getOrgnaId(), "所属组织机构ID不能为空");
		KAssert.hasText(record.getMapArea(), "警务区区域坐标不能为空");
		int res=orgPisAreaMapper.updateByPrimaryKeySelective(record);
		return res;
	}

	@Override
	public OrgPgisArea loadUserOrgPgisArea() {
		String userid=SecurityUtils.getUserId();
		Organization org=SpringBeanFacUtil.getBean(OrganizationServiceImpl.class).findOrgByUserId(userid);
		if(org==null){
			return null;
		}
		OrgPgisArea orgPgisArae=orgPisAreaMapper.selectByPrimaryKey(org.getOrgna_id());
		return orgPgisArae;
	}
	 @Transactional(rollbackFor = Exception.class,readOnly=true)
	 @Override
	 public List<Map<String,Object>> loadUserOrgPgisBianjie(String app_userId) {
		 if(!StringUtils.hasText(app_userId)){
			 return Collections.emptyList();
		 }
		 Organization org=SpringBeanFacUtil.getBean(OrganizationServiceImpl.class).findOrgByUserId(app_userId);
		 if(org==null){
			 return Collections.emptyList();
		 }
		 OrgPgisArea area=orgPisAreaMapper.selectByPrimaryKey(org.getOrgna_id());
		 if(area==null||StringUtils.hasText(area.getArea())){
			 return Collections.emptyList();
		 }	
	 	 List<Map<String,Object>> dataList=Lists.newArrayList();
	 				Map<String,Object> map =Maps.newHashMap();
	 				map.put("type", "org");
	 				map.put("id", area.getOrgnaId());
	 				map.put("bh", org.getOrgna_code());
	 				map.put("mc", org.getOrgna_name());
	 				map.put("bjzbz", area.getMapArea());
	 				dataList.add(map);
	 	return dataList;
	 	}
	  
	//116.99526,36.66628,117.0021,36.66305,116.99215,36.65829,116.98385,36.66457,116.99526,36.66628,116.99526,36.66628;NaN,NaN;NaN,NaN;NaN,NaN;NaN,NaN;117.01009,36.66232,117.01626,36.65731,117.0032,36.65182,117.01009,36.66232;NaN,NaN;117.00668,36.6675,117.01144,36.66665,117.00771,36.66213,117.00076,36.66348,117.00668,36.6675;NaN,undefined;116.98321,36.6567,116.99023,36.65237,116.98144,36.64913,116.97552,36.65103,116.98321,36.6567;
	//116.99526,36.66628,117.0021,36.66305,116.99215,36.65829,116.98385,36.66457,116.99526,36.66628,116.99526,36.66628;NaN,NaN;NaN,NaN;NaN,NaN;NaN,NaN;117.01009,36.66232,117.01626,36.65731,117.0032,36.65182,117.01009,36.66232;NaN,NaN;117.00668,36.6675,117.01144,36.66665,117.00771,36.66213,117.00076,36.66348,117.00668,36.6675;NaN,undefined;116.98321,36.6567,116.99023,36.65237,116.98144,36.64913,116.97552,36.65103,116.98321,36.6567;
	@Override
	public void saveOrgPgisArea(String orgid, String mapArea) {
		KAssert.hasText(orgid, "未选择组织机构");
		mapArea=""+mapArea;
		mapArea=mapArea.replaceAll("NaN,", "");
		mapArea=mapArea.replaceAll("undefined;", "");
		mapArea=mapArea.replaceAll("NaN;", "");
		String valRes= UtilX.validatePolygonIgnorNull(mapArea);
		if(valRes!=null){
			AlertSLEUtil.Error(valRes);
		}
		OrgPgisArea area=orgPisAreaMapper.selectByPrimaryKey(orgid);
		if(area==null){
			area=new OrgPgisArea();
			area.setCreated(ZDateUtil.getCurrentDateStr(ZDateStyle.MM_DD_HH_MM_SS));
			area.setCreatedby(SecurityUtils.getUserId());
			area.setMapArea(mapArea);
			area.setOrgnaId(orgid);
			area.setEnabled("1");
			orgPisAreaMapper.insertSelective(area);
		}else{
			area.setMapArea(mapArea);
			orgPisAreaMapper.updateByPrimaryKeySelective(area);
		}
		
	}


}


