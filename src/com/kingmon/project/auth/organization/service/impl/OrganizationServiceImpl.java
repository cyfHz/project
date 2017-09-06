package com.kingmon.project.auth.organization.service.impl;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.google.common.collect.Lists;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.OrgUtilX;
import com.kingmon.project.auth.organization.mapper.OrganizaionChangeMapper;
import com.kingmon.project.auth.organization.mapper.OrganizationMapper;
import com.kingmon.project.auth.organization.model.OrganizaionChange;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.auth.organization.view.FromOrgChildOrg;
import com.kingmon.project.auth.organization.view.OrgChaiFenPostView;
import com.kingmon.project.auth.organization.view.TargetOrg;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticTypes;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.datasync.DataSyncConst;
import com.kingmon.project.psam.datasync.model.DzDataSyncBiz;
import com.kingmon.project.psam.datasync.service.impl.DataSyncService;
import com.kingmon.project.psam.datasync.view.SyncParam;
import com.kingmon.project.psam.datasync.view.SyncParamItem;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jwq.model.Jwq;
@Service
public class OrganizationServiceImpl  extends BaseService implements IOrganizationService{
	@Autowired
	private OrganizaionChangeMapper organizaionChangeMapper;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private JwqMapper jwqMapper;
	@Autowired
	private DataSyncService dataSyncService;
	@Autowired
	private ElasticService elasticService;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateJwqToDocument(Map jwq){
		try {
			elasticService.updateDocument(ElasticTypes.JWQ, (String) jwq.get("JWQID"), ElasticUtil.jwqMapToDocument(jwq));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public Organization findOrganById(String orgna_id) {
		if(!StringUtils.hasText(orgna_id)){
			return null;
		}
		return organizationMapper.selectOrgById(orgna_id);
	}
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public List<Organization> findOrgByIds(List<String> ids) {
		if(ids==null||ids.isEmpty()){
			return null;
		}
		return organizationMapper.selectOrgByIds(ids);
	}
	@Cacheable(value="authTmpResultCache",key="'OrganizationServiceImpl_findOrganizationByUserId'+#appuser_id")
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public Organization findOrgByUserId(String appuser_id) {
		if(!StringUtils.hasText(appuser_id)){
			return null;
		}
		Organization organization=organizationMapper.selectOrgByUserId(appuser_id);
		return organization;
	}


	@Cacheable(value="authTmpResultCache",key="'OrganizationServiceImpl_loadChildOrgans'+#id")
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	public DataSet loadChildOrgans(String id) {
		if(!StringUtils.hasText(id)||id.startsWith(KConstants.cacheable_prefix)){//根节点,此时加载用户所在组织结构节点	//null会被缓存住，导致 多次数据错误
			String orgid=SecurityUtils.getUserOrgId();
			List<Map<String, Object>> list=Lists.newArrayList();
			list =organizationMapper.selectOrgTreeNodeById(orgid);
			return new DataSet(0l, list);
		}
		
		DataSet ds = DataSet.newDs();
		ds.setRows(organizationMapper.selectChildList(id));
		ds.setTotal(organizationMapper.selectChildCount(id));
		
		//如果市局或分局没有下级 则改变type =3 --20160704
		for(int i=0;i<ds.getTotal();i++){
			String porgna_id=(String)ds.getRows().get(i).get("NODEID");
			String ORGNA_TYPE =(String)ds.getRows().get(i).get("NODETYPE");
			long count1=organizationMapper.selectChildCount(porgna_id);
			Map<String, Object> map=Maps.newHashMap();
			if(count1<=0 && !ORGNA_TYPE.equals("3")){
				ds.getRows().remove(i);
				map=organizationMapper.selectChildListType(porgna_id);
				ds.getRows().add(i, map);
			}
		}
		return ds;
	}

	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DataSet loadOrganizationDataset(ParamObject po) {
		StringBuilder sql = new StringBuilder("SELECT");
		sql.append(" org.ORGNA_ID, ");
		sql.append(" org.ORGNA_CODE, ");
		sql.append(" org.ORGNA_NAME, ");
		sql.append(" org.ORGNA_JC, ");
		sql.append(" org.ENABLED, ");
		sql.append(" org.ORGNA_TYPE, ");
		sql.append(" org.ORGNA_PROPERTY, ");
		sql.append(" org.ORGNA_LEVEL, ");
		sql.append(" org.ORGNA_ADDRESS, ");
		sql.append(" org.ORGNA_ZIPCODE, ");
		sql.append(" org.ORGNA_TEL, ");
		sql.append(" org.ORGNA_FAX, ");
		sql.append(" org.ORGNA_EMAIL, ");
		sql.append(" org.PORGNA_ID, ");
		sql.append(" org.ENTORGNA_ID, ");
		sql.append(" org.AREA_ID, ");
		sql.append(" org.ORGNA_VALIDITY_START, ");
		sql.append(" org.ORGNA_VALIDITY_END, ");
		sql.append(" org.CCH, ");
		sql.append(" org.CCBZ, ");
		sql.append(" org.HZBZ, ");
		sql.append(" org.OPRATETIME, ");
		sql.append(" org.ORGNA_ORDER, ");
		sql.append(" org.ORGNA_HISCHILDNODE, ");
		sql.append(" org.ORGNA_YXX, ");
		sql.append(" org.SFZSJ, ");
		sql.append(" org.BZ0, ");
		sql.append(" org.BZ1, ");
		sql.append(" org.BZ2 ");
		sql.append(" @from APP_ORGANIZATION org left join APP_ORGANIZATION porg on org.PORGNA_ID=porg.ORGNA_ID ");
		sql.append(" where 1=1 and org.ENABLED='1' ");
		sql.append(" and (org.ORGNA_TYPE ='0' or org.ORGNA_TYPE ='1' or org.ORGNA_TYPE ='2' or org.ORGNA_TYPE ='3') ");//or org.ORGNA_TYPE ='3'
		//sql.append(" and  O.ENABLED='1' ");
		String orgnaName=(String) po.getWebParam("orgnaName");
		if(StringUtils.hasText(orgnaName)){
			sql.append(" and org.ORGNA_NAME like:orgnaName");
			po.addSQLParam("orgnaName", "%"+orgnaName+"%");
		}
		String orgnaCode=(String) po.getWebParam("orgnaCode");
		if(StringUtils.hasText(orgnaCode)){
			sql.append(" and org.ORGNA_CODE like:orgnaCode");
			po.addSQLParam("orgnaCode", "%"+orgnaCode+"%");
		}
		String porgid=(String) po.getWebParam("porgid");
		if(StringUtils.hasText(porgid)){
			sql.append(" and (porg.ORGNA_ID=:porgid or org.ORGNA_ID=:porgid)");
			po.addSQLParam("porgid", porgid);
		}
		
		String levelStr="37";
		String orgCode=SecurityUtils.getUserOrgCode();
		int level=SecurityUtils.getUserLevel();
		if(level==12){
			levelStr= orgCode.substring(0,10);
		}else{
			levelStr=orgCode.substring(0,level);
		}
		sql.append(" and org.ORGNA_CODE like:data_auth ");
		po.addSQLParam("data_auth", ""+levelStr+"%");
		
		if (po.hasOrder()) {
			sql.append(" order by ").append("org.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}else{
			sql.append(" order by org.ORGNA_CODE");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

//------------------------OrgChaiFen-----------------------------------------------	
	/**
	 * orgna_id
	 * orgna_code
	 * orgna_name
	 * orgna_jc
	 * orgna_type
	 * orgna_level
	 * enabled
	 * porgna_id
	 * sfzsj
	 * --------------type:sj,fj,pcs
	 */
	@CacheEvict(value="authTmpResultCache",allEntries=true) 
	@Override
	public void addOrganization(Organization organization,String type) {
		KAssert.notNull(organization,"未填写组织机构信息");
		KAssert.hasText(organization.getOrgna_name(), "组织机构名称不能为空");
		KAssert.hasText(organization.getOrgna_jc(), "组织机构简称不能为空");
//		KAssert.hasText(organization.getOrgna_type(), "组织机构类型不能为空");
		
//		List<String> orgTypeList=Arrays.asList(new String[]{"0","1","2"});
//		if(!orgTypeList.contains(organization.getOrgna_type())){
//			AlertSLEUtil.Error("本系统中不支持添加该类型组织机构");
//		}
		KAssert.hasText(organization.getOrgna_name(), "组织机构名称不能为空");
		
		String pOrgId=organization.getPorgna_id();
		KAssert.hasText(pOrgId,"未选择父级组织机构");
		Organization pOrg=organizationMapper.selectOrgByCode(pOrgId);
		KAssert.notNull(pOrg,"未查询到父级组织机构");
		String pOrgCode=pOrg.getOrgna_code();
		
		List<String> childCodeList=organizationMapper.selectOrgCodeByPorgId(pOrgId);
		String orgCode="";
		String targetCode="";
		//int userLevel = DataRuleUtil.loadSubLenFromUserLevel(SecurityUtils.getUserId());
		int userLevel = SecurityUtils.getUserLevel();//-zht-20160219
		switch (type) {
		case "sj":
			if(userLevel>=4){
				throw new ServiceLogicalException("当前用户级别没有添加市局的权限");
			}
			if(pOrgCode==null||pOrgCode.length()!=12||!pOrgCode.endsWith("0000000000")){
				AlertSLEUtil.Error("新添加的组织机构所属上级组织机构不是【省厅】");
			}
			targetCode=pOrgCode.substring(0, 2);
			orgCode=OrgUtilX.genOrgCode(targetCode,type,childCodeList);
//			organization.setOrgna_type("0");
			organization.setOrgna_type("1");
			break;
		case "fj":
			if(userLevel>=6){
				throw new ServiceLogicalException("当前用户级别没有添加分局的权限");
			}
			if(pOrgCode==null||pOrgCode.length()!=12||!pOrgCode.endsWith("00000000")){
				AlertSLEUtil.Error("新添加的组织机构所属上级组织机构不是【市局】");
			}
			targetCode=pOrgCode.substring(0, 4);
			orgCode=OrgUtilX.genOrgCode(targetCode,type,childCodeList);
//			organization.setOrgna_type("1");
			organization.setOrgna_type("2");
			break;
		case "pcs":
			if(userLevel>=9){
				throw new ServiceLogicalException("当前用户级别没有添加派出所的权限");
			}
			if(pOrgCode==null||pOrgCode.length()!=12||!pOrgCode.endsWith("000000")){
				AlertSLEUtil.Error("新添加的组织机构所属上级组织机构不是【分局】");
			}
			targetCode=pOrgCode.substring(0, 6);
			orgCode=OrgUtilX.genOrgCode(targetCode,type,childCodeList);
//			organization.setOrgna_type("2");
			organization.setOrgna_type("3");
			break;
		default:
			orgCode="";
			break;
		}
		KAssert.hasText(orgCode, "生成组织机构编码错误");
		
		organization.setEnabled("1");
		organization.setOrgna_code(orgCode);
		//organization.setOrgna_id(UUIDUtil.uuid());
		organization.setOrgna_id(orgCode);
		organizationMapper.insertSelective(organization);
		
	}
	
//------------------------OrgChaiFen-----------------------------------------------	
	
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DataSet loadOrgChaiFenDataGrid(String fromOrgId){
		Organization fromOrg=organizationMapper.selectOrgById(fromOrgId);
		if(fromOrg==null){
			return DataSet.newDs();
		}
		String pOrgId=fromOrg.getPorgna_id();
		Organization pOrg=organizationMapper.selectOrgById(pOrgId);
		if(pOrg==null){
			return DataSet.newDs();
		}
		String porgnaId=pOrg.getOrgna_id();
		if(porgnaId==null||porgnaId.isEmpty()){
			return DataSet.newDs();
		}
		StringBuilder sql = new StringBuilder("SELECT");
		sql.append(" org.ORGNA_ID, ");
		sql.append(" org.ORGNA_CODE, ");
		sql.append(" org.ORGNA_NAME, ");
		sql.append(" org.PORGNA_ID, ");
		sql.append(" org.ORGNA_JC ");
		sql.append(" @from APP_ORGANIZATION org ");
		sql.append(" where 1=1 ");
		ParamObject po=ParamObject.new_NP_C();
		if(StringUtils.hasText(porgnaId)){
			sql.append(" and org.PORGNA_ID =:porgnaId");
			po.addSQLParam("porgnaId",porgnaId);
		}
		if (po.hasOrder()) {
			sql.append(" order by ").append("org.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public KJSONMSG checkOrgChaiFen(String fromId,String[] toIds) {
		
		if(toIds==null||toIds.length<2){
			return new  KJSONMSG(300,"请选择多于一条要拆分到的数据");
		}
		List<String> toOrgIdList=Arrays.asList(toIds);
		
		List<String> toOrgCodeList=organizationMapper.selectOrgCodeByIds(toOrgIdList);
		if(toOrgCodeList==null||toOrgCodeList.size()<2){
			return new  KJSONMSG(300,"未查询到选取的数据");
		}
		
		String orgType=OrgUtilX.checkOrg(toOrgCodeList.get(0));
		if(orgType==null){
			AlertSLEUtil.Error("所选要拆分到的数据,不支持该类型组织机构");
		}
		for(String str:toOrgCodeList){
			if(!orgType.equals(OrgUtilX.checkOrg(str))){
				AlertSLEUtil.Error("所选要拆分的数据,与拆分到目标机构类型不同");
			}
		}
		
		Organization fromOrg=organizationMapper.selectOrgById(fromId);//.selectOrgByICode(codeList.get(0));
		KAssert.notNull(fromOrg, "未查询到要拆分的数据");
		List<String> childCodeList=organizationMapper.selectOrgCodeByPorgId(fromOrg.getPorgna_id());
		KAssert.notEmpty(childCodeList, "所选要拆分到的目标机构数据不属于同一父机构");
		for(String toOrgId:toIds){
			if(!childCodeList.contains(toOrgId)){
				KAssert.notEmpty(childCodeList, "所选要拆分到的数据不属于同一父机构");
			}
		}
		DataSet targetOrgs=loadChaiFenToOrg(toIds);
		DataSet sourceChildOrgs=loadChaiFenFromOrgChild(fromId);
		Map<String,Object> data=Maps.newHashMap();
			data.put("type", orgType);//拆分的类型
			data.put("fromId", fromId);//拆分的哪一个组织机构
			data.put("targetOrgs", targetOrgs);//拆分到那几个组织机构
			data.put("sourceChildOrgs", sourceChildOrgs);//要被拆分的组织机构的子机构列表
		return new KJSONMSG(200,"可以拆分",data);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void  processOrgChaiFen( OrgChaiFenPostView orgChaiFenPostView){
		KAssert.notNull(orgChaiFenPostView, "参数错误");
		String fromId=orgChaiFenPostView.getFromId();
		String type= orgChaiFenPostView.getType();
		List<TargetOrg> targetOrgs=orgChaiFenPostView.getTargetOrgs();
		List<FromOrgChildOrg> fromOrgChildOrgs=orgChaiFenPostView.getFromOrgChildOrgs();
		//----------------validate
		KAssert.hasText(fromId, "要拆分的组织机构为空");
		if(targetOrgs==null||targetOrgs.isEmpty()){AlertSLEUtil.Error("拆分到的目标组织机构为空");}
		Organization fromOrg=organizationMapper.selectOrgById(fromId);
		KAssert.notNull(fromOrg, "未查询到要拆分的组织机构");
		if(!KConstants.org_only_type.contains(type)){AlertSLEUtil.Error("组织机构类型不支持拆分");}
		
		//???
		if(!type.equals(OrgUtilX.checkOrg(fromOrg.getOrgna_code()))){
			Organization org=organizationMapper.selectOrgByCode(fromOrg.getOrgna_code());
			AlertSLEUtil.Error("要拆分的组织机构【"+org.getOrgna_name()+"】不是"+OrgUtilX.getOrgLevlName(type));
		}
		
		List<String> toIds=orgChaiFenPostView.genToOrgIds();
		if(toIds==null||toIds.isEmpty()){AlertSLEUtil.Error("请选择要拆分到的目标数据");}
		
		List<Organization> toOrgList= organizationMapper.selectOrgByIds(toIds);
		if(toOrgList==null||toOrgList.isEmpty()){AlertSLEUtil.Error("未查询到要拆分到的目标数据");}
		//???
		for(Organization toOrg:toOrgList){
			if(!type.equals(OrgUtilX.checkOrg(toOrg.getOrgna_code()))){
				AlertSLEUtil.Error("要拆分到的组织机构【"+toOrg.getOrgna_name()+"】不是"+OrgUtilX.getOrgLevlName(type));
			}
		}
		
		if(fromOrgChildOrgs!=null&&!fromOrgChildOrgs.isEmpty()){
			for(FromOrgChildOrg childOrg:fromOrgChildOrgs){
				String id=childOrg.getORGNA_ID();
				String pId=childOrg.getORGNA_PID();
				String childType=OrgUtilX.checkOrg(childOrg.getORGNA_CODE());
				if("jwq".equals(childType)){
					Jwq jwq=new Jwq();
					jwq.setJwqid(id);
					jwq.setPcsid(pId);
					jwqMapper.updateByPrimaryKeySelective(jwq);
					
					Map<String,Object> map=Maps.newHashMap();
					map.put("JWQID", id);
					map.put("PCSID", pId);
					updateJwqToDocument(map);
				}else{
					Organization org=new Organization();
					org.setOrgna_id(id);
					org.setPorgna_id(pId);
					organizationMapper.updateByPrimaryKeySelective(org);
				}
			}
		}
		if(!toIds.contains(fromId)){
			Organization org=new Organization();
			org.setOrgna_id(fromId);
			org.setEnabled("0");
			organizationMapper.updateByPrimaryKeySelective(org);
		}
		for(String toId:toIds){
			OrganizaionChange change=new OrganizaionChange(UUIDUtil.uuid(),fromId,toId, "2",SecurityUtils.getUserId(),new Date());
			organizaionChangeMapper.insertSelective(change);
		}
		
		//---------------数据同步???-----------------------------------------------------------
		String fromOrgType = OrgUtilX.checkOrg(fromOrg.getOrgna_code());
		String syncBiz = "";
		if ("pcs".equals(fromOrgType)) {
			syncBiz = DataSyncConst.PSAM_SYNC_ORG_HEBING_PCS;
		} else if ("fj".equals(fromOrgType)) {
			syncBiz = DataSyncConst.PSAM_SYNC_ORG_HEBING_FJ;
		} else if ("sj".equals(fromOrgType)) {
			syncBiz = DataSyncConst.PSAM_SYNC_ORG_HEBING_SJ;
		} else {
			return;
		}
		List<SyncParamItem> syncParamItemList = Lists.newArrayList();
		for (Organization toOrg : toOrgList) {
			SyncParamItem item = new SyncParamItem(toOrg.getOrgna_code(),fromOrg.getOrgna_code(), toOrg.getOrgna_code());
			syncParamItemList.add(item);
		}
		SyncParam syncParam = SyncParam.newSP().setSyncParamItemList(syncParamItemList).setCraeteUserId(SecurityUtils.getUserId());
		dataSyncService.createAndExecuteSyncTask(new DzDataSyncBiz(syncBiz),syncParam);
	}
//------------------------------------------OrgHebing-------------------------------------------------------------	
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public KJSONMSG checkOrgHebing(String[] orgnaIds) {
		if(orgnaIds==null||orgnaIds.length<2){
			return new  KJSONMSG(300,"请选择多于一条要合并的数据");
		}
		List<String> listssx=Arrays.asList(orgnaIds);
		List<String> codeList=organizationMapper.selectOrgCodeByIds(listssx);
		if(codeList==null||codeList.size()<2){
			return new  KJSONMSG(300,"未查询到选取的数据");
		}
		String orgType=OrgUtilX.checkOrg(codeList.get(0));
		if(orgType==null){
			AlertSLEUtil.Error("所选要合并的数据,不支持该类型组织机构合并");
		}
		for(String str:codeList){
			if(!orgType.equals(OrgUtilX.checkOrg(str))){
				AlertSLEUtil.Error("所选要合并的数据,与合并到目标机构类型不同");
				//AlertSLEUtil.Error("所选要合并的数据,不支持该类型组织机构合并");
			}
		}
		Organization organ=organizationMapper.selectOrgById(orgnaIds[0]);//.selectOrgByICode(codeList.get(0));
		KAssert.notNull(organ, "未查询到要合并的数据");
		List<String> childCodeList=organizationMapper.selectOrgCodeByPorgId(organ.getPorgna_id());
		KAssert.notEmpty(childCodeList, "所选要合并的数据不属于同一父机构，或该父机构没有子机构");
		for(String orgId:orgnaIds){
			if(!childCodeList.contains(orgId)){
				KAssert.notEmpty(childCodeList, "所选要合并的数据不属于同一父机构");
			}
		}
		//----------------------query
		String porgnaId=organ.getPorgna_id();
		KAssert.notNull(porgnaId, "未查询到要合并数据的父级机构");
		
		StringBuilder sql = new StringBuilder("SELECT");
		sql.append(" org.ORGNA_ID, ");
		sql.append(" org.ORGNA_CODE, ");
		sql.append(" org.ORGNA_NAME, ");
		sql.append(" org.PORGNA_ID, ");
		sql.append(" org.ORGNA_JC ");
		sql.append(" @from APP_ORGANIZATION org ");
		sql.append(" where 1=1 ");
		ParamObject po=ParamObject.new_NP_C();
		if(StringUtils.hasText(porgnaId)){
			sql.append(" and org.PORGNA_ID =:porgnaId");
			po.addSQLParam("porgnaId",porgnaId);
		}
		if (po.hasOrder()) {
			sql.append(" order by ").append("org.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		DataSet ds=getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
		return new KJSONMSG(200,"数据加载成功", ds);
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void processOrgHeBing(String[] fromIds, String toId) {
		//--自身逻辑
		if(fromIds==null||fromIds.length<2){
			AlertSLEUtil.Error("请选择要合并的数据");
		}
		Organization toOrg =organizationMapper.selectOrgById(toId);
		KAssert.notNull(toOrg, "未查询到合并到的组织机构");
		String toOrgCode=toOrg.getOrgna_code();
		
		String toOrgType=OrgUtilX.checkOrg(toOrgCode);
		if(!KConstants.org_type.contains(toOrgType)){
			AlertSLEUtil.Error("选择的机构【"+toOrg.getOrgna_name()+"】类型不在【省厅】，【市局】，【分局】，【派出所】,【警务区】类型范围内");
		}
		List<Organization> fromOrgs= organizationMapper.selectOrgByIds(Arrays.asList(fromIds));
		if(fromOrgs==null||fromOrgs.isEmpty()){
			AlertSLEUtil.Error("未查询到要合并的组织机构");
		}
		
		for(Organization fromOrg :fromOrgs){
			String fromOrgCode=fromOrg.getOrgna_code();
			String fromOrgType=OrgUtilX.checkOrg(fromOrgCode);
			if(!KConstants.org_type.contains(toOrgType)){
				AlertSLEUtil.Error("选择的机构【"+fromOrg.getOrgna_name()+"】类型不在【省厅】，【市局】，【分局】，【派出所】,【警务区】类型范围内");
			}
			if(!(toOrgType).equals(fromOrgType)){
				AlertSLEUtil.Error("合并目标机构和选择的机构类型不同，请检查数据");
			}
			
			//----------save changes------------------------------------
			OrganizaionChange change=new OrganizaionChange(UUIDUtil.uuid(),fromOrg.getOrgna_id(),toId, "1",SecurityUtils.getUserId(),new Date());
			organizaionChangeMapper.insertSelective(change);
			
			Organization fromOrgx=new Organization(fromOrg.getOrgna_id());
			if(fromOrg.getOrgna_id().equals(toId)){
				fromOrgx.setEnabled("1");
			}else{
				fromOrgx.setEnabled("0");
			}
			organizationMapper.updateByPrimaryKeySelective(fromOrgx);
		}
		
//---------------数据同步???-----------------------------------------------------------
		String syncBiz="";
		if("pcs".equals(toOrgType)){
			syncBiz=DataSyncConst.PSAM_SYNC_ORG_HEBING_PCS;
		}else if("fj".equals(toOrgType)){
			syncBiz=DataSyncConst.PSAM_SYNC_ORG_HEBING_FJ;
		}else if("sj".equals(toOrgType)){
			syncBiz=DataSyncConst.PSAM_SYNC_ORG_HEBING_SJ;
		}else{
			return;
		}
		List<SyncParamItem> syncParamItemList=Lists.newArrayList();
		for(Organization fromOrg :fromOrgs){
			SyncParamItem item=new SyncParamItem(fromOrg.getOrgna_code(), toOrg.getOrgna_code(), fromOrg.getOrgna_code());
			syncParamItemList.add(item);
		}
		SyncParam syncParam=SyncParam.newSP().setSyncParamItemList(syncParamItemList).setCraeteUserId(SecurityUtils.getUserId());
		dataSyncService.createAndExecuteSyncTask(new DzDataSyncBiz(syncBiz),syncParam);
	}

	private DataSet loadChaiFenToOrg(String[] toIds){
		if(toIds==null||toIds.length==0){
			return DataSet.newDs();
		}
		StringBuilder sql = new StringBuilder("SELECT");
		sql.append(" org.ORGNA_ID as T_ORGNA_ID, ");
		sql.append(" org.ORGNA_CODE as T_ORGNA_CODE, ");
		sql.append(" org.ORGNA_NAME as T_ORGNA_NAME, ");
		sql.append(" org.PORGNA_ID as T_PORGNA_ID, ");
		sql.append(" org.ORGNA_JC as T_ORGNA_JC");
		sql.append(" @from APP_ORGANIZATION org ");
		sql.append(" where 1=1 ");
		sql.append(" and org.ORGNA_ID in(:toIds)");
		List<String> toIdList=Arrays.asList(toIds);
		ParamObject po=ParamObject.new_NP_C();
		po.addSQLParam("toIds",toIdList);
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	
	private DataSet loadChaiFenFromOrgChild(String fromId){
		if(fromId==null||fromId.length()==0){
			return DataSet.newDs();
		}
		Organization org=organizationMapper.selectOrgById(fromId);
		if(org==null){
			return DataSet.newDs();
		}
		String porgnaId=org.getPorgna_id();
		String orgType=OrgUtilX.checkOrg(org.getOrgna_code());
		
		if("pcs".equals(orgType)){
			StringBuilder sql = new StringBuilder(" SELECT ");
			sql.append(" j.JWQID as ORGNA_ID, ");
			sql.append(" j.JWQBH as ORGNA_CODE, ");
			sql.append(" j.JWQMC as ORGNA_NAME ");
			sql.append(" @from ENT_JWQ j ,APP_ORGANIZATION org ");
			sql.append(" where 1=1 and org.ORGNA_ID=j.PCSID");
			sql.append(" and org.ORGNA_ID =:fromId ");
			ParamObject po=ParamObject.new_NP_C();
			po.addSQLParam("fromId",fromId);
			return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
		}else{
			StringBuilder sql = new StringBuilder("SELECT");
			sql.append(" org.ORGNA_ID, ");
			sql.append(" org.ORGNA_CODE, ");
			sql.append(" org.ORGNA_NAME, ");
			sql.append(" org.PORGNA_ID, ");
			sql.append(" org.ORGNA_JC ");
			sql.append(" @from APP_ORGANIZATION org ");
			sql.append(" where 1=1 ");
			ParamObject po=ParamObject.new_NP_C();
			if(StringUtils.hasText(porgnaId)){
				sql.append(" and org.PORGNA_ID =:porgnaId");
				po.addSQLParam("porgnaId",porgnaId);
			}
			return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
		}
	
	}
}
