package com.kingmon.project.auth.rule.service.impl;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.auth.resource.mapper.ResourceMapper;
import com.kingmon.project.auth.resource.model.Resource;
import com.kingmon.project.auth.rule.mapper.WidgetruleMapper;
import com.kingmon.project.auth.rule.model.Widgetrule;
import com.kingmon.project.auth.rule.service.IWidgetruleService;
import com.kingmon.project.auth.util.packageScanUtil;
@Service
public class WidgetruleServiceImpl extends BaseService implements IWidgetruleService{
	
	@Autowired
	private WidgetruleMapper widgetruleMapper;

	@Autowired
	private ResourceMapper resourceMapper;
	
	@Cacheable(value="authTmpResultCache",key="'WidgetruleServiceImpl_loadWidgetRuleListByUserId'+#appuser_id")
	@SuppressWarnings("unchecked")
	@Override
	public List<Widgetrule> loadWidgetRuleListByUserId(String appuser_id) {
		if(!StringUtils.hasText(appuser_id)){
			return Collections.emptyList();
		}
		List<Widgetrule> list=widgetruleMapper.selectWidgetRuleListByUserId(appuser_id);
		return (List<Widgetrule>) (list==null?Collections.emptyList():list);
	}
	
	@Cacheable(value="authTmpResultCache",key="'WidgetruleServiceImpl_loadWidgetRuleCodeListByUserId'+#appuser_id")
	@SuppressWarnings("unchecked")
	@Override
	public List<String> loadWidgetRuleCodeListByUserId(String appuser_id) {
		if(!StringUtils.hasText(appuser_id)){
			return Collections.emptyList();
		}
		List<String> list=widgetruleMapper.selectWidgetRuleCodeListByUserId(appuser_id);
		return (List<String>) (list==null?Collections.emptyList():list);
	}
	
//	@Cacheable(value="authTmpResultCache",key="'WidgetruleServiceImpl_loadWidgetRuleValueListByUserId'+#appuser_id")
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<String> loadWidgetRuleValueListByUserId(String appuser_id) {
//		if(!StringUtils.hasText(appuser_id)){
//			return Collections.emptyList();
//		}
//		List<String> list=widgetruleMapper.selectWidgetRuleValueListByUserId(appuser_id);
//		return (List<String>) (list==null?Collections.emptyList():list);
//	}
	
	
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DataSet loadWidgetRuleDataset(ParamObject po) {
		String rule_name=(String) po.getWebParam("rule_name");
		
		StringBuilder sql = new StringBuilder("SELECT");
		sql.append(" w.RULE_ID, ");
		sql.append(" w.RES_ID, ");
		sql.append(" w.AREA_ID, ");
		sql.append(" w.RULE_NAME, ");
		sql.append(" w.RULE_DEFVALUE, ");
		sql.append(" w.RULE_VALUES, ");
		sql.append(" w.RULE_CODE, ");
		sql.append(" w.ENABLED, ");
		sql.append(" w.OPRATETIME  ");
		sql.append(" @from APP_WIDGETRULE w, app_resource r ");
		sql.append(" where 1=1 and w.RES_ID=r.RES_ID  ");
		sql.append(" and w.RULE_CODE like '%.%'  ");
		String resourceid=(String) po.getWebParam("resourceid");
		if(StringUtils.hasText(resourceid)){
			sql.append("  and w.RES_ID=:resourceid  ");
			po.addSQLParam("resourceid", resourceid);
		}
//		sql.append("  and w.RES_ID=:resourceid  ");
//		po.addSQLParam("resourceid", resourceid);
		
		if(StringUtils.hasText(rule_name)){
			sql.append(" and w.RULE_NAME like:rule_name");
			po.addSQLParam("rule_name", "%"+rule_name+"%");
		}
		String rule_code=(String) po.getWebParam("rule_code");
		if(StringUtils.hasText(rule_code)){
			sql.append(" and  w.RULE_NAME like:rule_code");
			po.addSQLParam("rule_code", "%"+rule_code+"%");
		}
		if (po.hasOrder()) {
			sql.append(" order by ").append("w.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}else{
			sql.append(" order by w.RULE_ID ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	
	@Override
	public void scanWidgetrule(String packagz) {

		Set<Class<?>> calssSet=packageScanUtil.scanAllController(packagz);
		
		if(calssSet==null||calssSet.size()==0){
			throw new ServiceLogicalException("未扫描到任何类");
		}
		List<String> urlList=Lists.newArrayList();
		List<AuthWidgetRule> authWRuleList=Lists.newArrayList();
		List<Resource> resList=Lists.newArrayList();
		List<Widgetrule> widgeRuleList=Lists.newArrayList();
		String rootRes=KConstants.PSAM_RESOURCE_ROOTCODE;
		String pubRes=KConstants.PSAM_RESOURCE_PUBLICCODE;
		String ignorRes=KConstants.PSAM_RESOURCE_IGNORCODE;
		
		 String widget_ignor ="widget_ignor";
		 String widget_public ="widget_public";
		
		resList=genrootpubignor( resList);
		
		for(Class<?> calzz:calssSet){
			//扫描类上面的RequestMapping
			Method[] methods=calzz.getDeclaredMethods();
			String clazzUrl="";
			if(calzz.isAnnotationPresent(RequestMapping.class)){
				RequestMapping requestMapping=calzz.getAnnotation(RequestMapping.class);
				String[] clazzUrlArray=requestMapping.value();
				clazzUrl=clazzUrlArray[0];
			}
			if(methods==null||methods.length==0){
				continue;
			}
			String resId=UUIDUtil.uuid();
		
			for(Method method:methods){
				String requestUrl="";
				AuthWidgetRule widgetRuleAnn=null;
			
				if(method.isAnnotationPresent(RequestMapping.class)){
					RequestMapping mrp=method.getAnnotation(RequestMapping.class);
					String[] methodUrl=mrp.value();
					requestUrl=clazzUrl+methodUrl[0];
				}
				urlList.add(requestUrl);
				
				if(method.isAnnotationPresent(AuthWidgetRule.class)){
					 widgetRuleAnn=method.getAnnotation(AuthWidgetRule.class);
					authWRuleList.add(widgetRuleAnn);
					String value= widgetRuleAnn.value();
					String resCode=org.apache.commons.lang3.StringUtils.substringBefore(value, ".");
					String desc= widgetRuleAnn.desc();
					String refTable= widgetRuleAnn.refTable();
					String operateType= widgetRuleAnn.operateType();
					String crudType=widgetRuleAnn.crudType();
					if(crudType==null||crudType.length()==0){
						System.out.println("------------crudType==null-------------------: "+value);
					}
					if(resCode==null||resCode.length()==0){
						System.out.println("-------------resCode==null------------------: "+value);
					}
					if(KConstants.WIDGET_PUBLIC.equals(value)){
						continue;
					}
					if("R".equals(operateType)){
						Resource res=new Resource();
						res.setRes_id(resId);
						res.setArea_id("1");
						res.setRes_name(desc);
						res.setRes_desc(desc);
						res.setRes_type(null);
						res.setRes_pathtype(null);
						res.setRes_pvalue(requestUrl);
						res.setRes_order("");
						res.setRes_code(value);
						
						res.setRes_pid(rootRes);
						
						res.setEnabled("1");
						resList.add(res);
					}else if("W".equals(operateType)){
						
						Widgetrule wrule=new Widgetrule();
						wrule.setRule_id(UUIDUtil.uuid());
						
//						if(KConstants.WIDGET_PUBLIC.equals(value)){
//							wrule.setRes_id(pubRes);
//						}else if(KConstants.WIDGET_IGNOR.equals(value)){
//							wrule.setRes_id(ignorRes);
//						}else{
//							wrule.setRes_id(resId);
//						}
						
						wrule.setRule_name(desc);
						wrule.setArea_id("1");
						wrule.setRule_defvalue(null);
						wrule.setRule_values(requestUrl);
						wrule.setRule_desc(desc);
						wrule.setRule_code(value);
						wrule.setEnabled("1");
						wrule.setOpratetime(ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS));
						widgeRuleList.add(wrule);
					}
				
				}
				
			}
		}
		System.out.println(urlList.size());
		System.out.println(authWRuleList.size());
		for(Resource res:resList){
			long  count=jdbcBaseDao.jdbcQueryCount("select count(1) from app_resource  r where r.RES_CODE = '"+res.getRes_code()+"'", ParamObject.new_NP_NC());
			if(count<1){
				resourceMapper.insertSelective(res);
			}
		}
		for(Widgetrule rule:widgeRuleList){
			long  count=jdbcBaseDao.jdbcQueryCount("select count(1) from app_widgetrule  w where w.RULE_CODE = '"+rule.getRule_code()+"'", ParamObject.new_NP_NC());
			if(count<1){
				String ruleCode=rule.getRule_code();
				String resCode=org.apache.commons.lang3.StringUtils.substringBefore(ruleCode, ".");
				String res_id=(String) jdbcBaseDao.jdbcQueryOneRowByUnque("select RES_ID from app_resource  where RES_CODE =:resCode",ParamObject.new_NP_NC().addSQLParam("resCode", resCode));
				rule.setRes_id(res_id);
				if(KConstants.WIDGET_PUBLIC.equals(ruleCode)){
					rule.setRes_id(pubRes);
				}else if(KConstants.WIDGET_IGNOR.equals(ruleCode)){
					rule.setRes_id(ignorRes);
				}else{
					rule.setRes_id(res_id);
				}
				widgetruleMapper.insertSelective(rule);
				
			}
		}
	}

	private List<Resource> genrootpubignor(List<Resource> resList){
		String rootRes=KConstants.PSAM_RESOURCE_ROOTCODE;
		String pubRes=KConstants.PSAM_RESOURCE_PUBLICCODE;
		String ignorRes=KConstants.PSAM_RESOURCE_IGNORCODE;
//		Resource resroot=new Resource();
//		resroot.setRes_id(rootRes);
//		resroot.setArea_id("1");
//		resroot.setRes_name("系统菜单");
//		resroot.setRes_desc("系统菜单");
//		resroot.setRes_type(null);
//		resroot.setRes_pathtype(null);
//		resroot.setRes_pvalue("/");
//		resroot.setRes_order("");
//		resroot.setRes_code(rootRes);
//		resroot.setRes_pid(null);
//		resroot.setEnabled("1");
//		resList.add(resroot);
		
		Resource respub=new Resource();
		respub.setRes_id(pubRes);
		respub.setArea_id("1");
		respub.setRes_name("公共资源");
		respub.setRes_desc("公共资源");
		respub.setRes_type(null);
		respub.setRes_pathtype(null);
		respub.setRes_pvalue("/");
		respub.setRes_order("");
		respub.setRes_code(pubRes);
		respub.setRes_pid(rootRes);
		respub.setEnabled("1");
		resList.add(respub);
		
	
//		Resource resignor=new Resource();
//		resignor.setRes_id(ignorRes);
//		resignor.setArea_id("1");
//		resignor.setRes_name("系统菜单");
//		resignor.setRes_desc("系统菜单");
//		resignor.setRes_type(null);
//		resignor.setRes_pathtype(null);
//		resignor.setRes_pvalue("/");
//		resignor.setRes_order("");
//		resignor.setRes_code(ignorRes);
//		resignor.setRes_pid(rootRes);
//		resignor.setEnabled("1");
//		resList.add(resignor);
		return resList;
	}
}
