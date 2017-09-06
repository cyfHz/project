package com.kingmon.project.auth.resource.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.google.common.collect.Lists;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.POType;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.project.auth.resource.mapper.ResourceMapper;
import com.kingmon.project.auth.resource.model.Resource;
import com.kingmon.project.auth.resource.service.IResourceService;
@Service
public class ResourceServiceImpl extends BaseService implements IResourceService{

	@Autowired
	private ResourceMapper resourceMapper;
	
	//@Cacheable(value="authTmpResultCache",key="'ResourceServiceImpl_findResourceListByByUserId'+#userId")
	@Override
	public List<Resource> findResListByUserId(String userId){
		if(StringUtils.hasText(userId)){
			return resourceMapper.selectResourceListByByUserId(userId);
		}
		return null;
	}
	@Cacheable(value="authTmpResultCache",key="'ResourceServiceImpl_findResourceCodeListByByUserId'+#userId")
	@Override
	public List<String> findResCodeListByUserId(String userId){
		if(StringUtils.hasText(userId)){
			return resourceMapper.selectResourceCodeListByByUserId(userId);
		}
		return null;
	}

	@Override
	public DataSet loadResourceTreeGrid(ParamObject po) {
		po.setInitType(POType.S_NO_C);
		StringBuffer sql=new StringBuffer("select")
		.append("  r.res_id as \"id\",")
		.append("  r.area_id,")
		.append("  r.res_name,")
		.append("  r.res_desc,")
		.append("  r.res_type,")
		.append("  r.res_pathtype,")
		.append("  r.res_pvalue,")
		.append("  r.res_order,")
		.append("  r.res_code,")
		.append("  r.res_pid as \"_parentId\",")
		.append("  r.dhsxs,")
		.append("  r.sfyz,")
		.append("  r.enabled,")
		.append("  r.opratetime,")
		.append("  r.is_href,")
		.append("  r.is_map_op")
		.append("  @from app_resource r")
		.append(" where 1 = 1 ")
		.append(" and r.MODULE_TAG='PSAM' ");
		//.append(" and ( r.RES_CODE like 'PSAM%' or r.RES_PID like 'PSAM%' )" );
		String res_name = (String) po.getWebParam("res_name");
		if (!SubApStrUtil.isEmptyAfterTrimE(res_name)) {
			sql.append("and r.res_name like:res_name ");
			po.addSQLParam("res_name", "%"+res_name+"%");
		}
		String res_code = (String) po.getWebParam("res_code");
		if (!SubApStrUtil.isEmptyAfterTrimE(res_code)) {
			sql.append("and r.res_code like:res_code ");
			po.addSQLParam("res_code", "%"+res_code+"%");
		}
		if(po.hasOrder()){
			sql.append(" order by ").append("r.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		DataSet ds=getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
		if(ds==null||ds.getRows()==null){
			return DataSet.newDs();
		}
		List<Map<String, Object>> list=ds.getRows();
		for(Map<String, Object> map:list){
			if("PSAM_RESOURCE_PARENT_ROOT".equals(map.get("id"))){
				map.put("_parentId", null);
			}
		}
		ds.setRows(list);
		return ds;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	public DataSet loadResourceTree(String pid) {
		if(!StringUtils.hasText(pid)){//根节点,此时加载用户所在组织结构节点
			String psamResourceRootCode=KConstants.PSAM_RESOURCE_ROOTCODE;
			List<Map<String, Object>> list=Lists.newArrayList();
			list =resourceMapper.selectResTreeNodeByRescode(psamResourceRootCode);
			return new DataSet(1L, list);
		}
		
		DataSet ds = DataSet.newDs();
		ds.setRows(resourceMapper.selectResChildList(pid));
		ds.setTotal(resourceMapper.selectResChildCount(pid));
		return ds;
	}
	
//	@Override
//	public void scanResource(String packagz) {
//
//		Set<Class<?>> calssSet=packageScanUtil.scanAllController(packagz);
//		
//		if(calssSet==null||calssSet.size()==0){
//			throw new ServiceLogicalException("未扫描到任何类");
//		}
//		List<String> urlList=Lists.newArrayList();
//		List<AuthWidgetRule> authWRuleList=Lists.newArrayList();
//		List<Resource> resList=Lists.newArrayList();
//		List<Widgetrule> widgeRuleList=Lists.newArrayList();
//		
//		for(Class<?> calzz:calssSet){
//			//扫描类上面的RequestMapping
//			Method[] methods=calzz.getDeclaredMethods();
//			String clazzUrl="";
//			if(calzz.isAnnotationPresent(RequestMapping.class)){
//				RequestMapping requestMapping=calzz.getAnnotation(RequestMapping.class);
//				String[] clazzUrlArray=requestMapping.value();
//				clazzUrl=clazzUrlArray[0];
//			}
//			if(methods==null||methods.length==0){
//				continue;
//			}
//			String resId=UUIDUtil.uuid();
//			for(Method method:methods){
//				String reuqestUrl="";
//				AuthWidgetRule widgetRuleAnn=null;
//				
//			
//				if(method.isAnnotationPresent(RequestMapping.class)){
//					RequestMapping mrp=method.getAnnotation(RequestMapping.class);
//					String[] methodUrl=mrp.value();
//					reuqestUrl=clazzUrl+methodUrl[0];
//				}
//				urlList.add(reuqestUrl);
//				
//				if(method.isAnnotationPresent(AuthWidgetRule.class)){
//					 widgetRuleAnn=method.getAnnotation(AuthWidgetRule.class);
//					authWRuleList.add(widgetRuleAnn);
//					String value= widgetRuleAnn.value();
//					String desc= widgetRuleAnn.desc();
//					String refTable= widgetRuleAnn.refTable();
//					String operateType= widgetRuleAnn.operateType();
//					
//					if("R".equals(operateType)){
//						Resource res=new Resource();
//						res.setRes_id(resId);
//						res.setArea_id("1");
//						res.setRes_name(desc);
//						res.setRes_desc(desc);
//						res.setRes_type(null);
//						res.setRes_pathtype(null);
//						res.setRes_pvalue(reuqestUrl);
//						res.setRes_order("");
//						res.setRes_code(value);
//						res.setRes_pid(null);
//						res.setEnabled("1");
//						resList.add(res);
//					}else if("R".equals(operateType)){
//						Widgetrule wrule=new Widgetrule();
//						wrule.setRes_id(UUIDUtil.uuid());
//						wrule.setRes_id(resId);
//						wrule.setRule_name(desc);
//						wrule.setArea_id("1");
//						wrule.setRule_defvalue(null);
//						wrule.setRule_values(reuqestUrl);
//						wrule.setRule_desc(desc);
//						wrule.setRule_code(value);
//						wrule.setEnabled("1");
//						wrule.setOpratetime(ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS));
//						widgeRuleList.add(wrule);
//					}
//				
//				}
//				
//			}
//		}
//		System.out.println(urlList);
//		System.out.println(authWRuleList);
//	}


}
