package com.kingmon.project.psam.dataStatistic.service.impl;
import static org.elasticsearch.index.query.FilterBuilders.missingFilter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacet.ComparatorType;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.dao.JdbcBaseDao;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.mapper.OrganizationMapper;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.psam.dataStatistic.service.IDataStatisticService;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
@Service
public class DataStatisticServiceImpl extends BaseService implements IDataStatisticService {
	@Autowired
	private  OrganizationMapper organizationMapper;
	
	@Autowired
	private  MlphMapper mlphMapper;
	
	@Autowired
	private ElasticService elasticService;
	
	@Resource
	private Map<String, Map<String,String>> statisticTableConfig;
	@Resource
	private String sql_statistic_totle;
	@Resource
	private String sql_statistic_good;
	@Resource
	private String sql_statistic_not_good;
	
	@Resource
	private String sql_statistic_haveNoZB;
	@Resource
	private String sql_statistic_haveNoJWQ;
	@Resource
	private String sql_statistic_haveNoBoth;
	
	//=========================================//
	@Resource
	private String sql_statistic_totle_new;
	@Resource
	private String sql_statistic_good_new;
	@Resource
	private String sql_statistic_not_good_new;
	
	@Resource
	private String sql_statistic_haveNoZB_new;
	@Resource
	private String sql_statistic_haveNoJWQ_new;
	@Resource
	private String sql_statistic_haveNoBoth_new;
	
	
	 public void scheduledStatisticTask(){
		IDataStatisticService s = (DataStatisticServiceImpl) SpringBeanFacUtil.getBean("dataStatisticServiceImpl"); 
		try{
			s.loadSjStatisticData();
		}catch(Exception e){
			e.printStackTrace();
		}
		for (Map.Entry<String, Map<String,String>> entry : statisticTableConfig.entrySet()){
			Map<String,String> oneDSOrg=entry.getValue();
			String orgName=oneDSOrg.get("orgName");
			try{
				s.loadFjStatisticData(orgName);
			}catch(Exception e){e.printStackTrace();}
			
		}
		
	}
	 public void scheduledNewStatisticTask(){
		 /**
			 * 定时缓冲所有的数据
			 */
			try {
				String orgCode = "370000000000";//最高层次(省厅)
				queryChildAll(orgCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
	
	@Cacheable(value="dataStatistic")
	@Override
	public List<Map<String,Object>> loadSjStatisticData(){
		List<Map<String,Object>> datax=Lists.newArrayList();
		for (Map.Entry<String, Map<String,String>> entry : statisticTableConfig.entrySet()){
			
			Map<String,String> oneDSOrg=entry.getValue();
			String tableName=oneDSOrg.get("tableName");
			
			if(tableName==null||tableName.isEmpty()){
				continue;
			}
			//tableName="dz_mlph";
			String orgName=oneDSOrg.get("orgName");
			String orgCode=oneDSOrg.get("orgCode");
			String sql_special_and=oneDSOrg.get("sql_statistic_special")==null?"":oneDSOrg.get("sql_statistic_special");
			
			String sql_totle=sql_statistic_totle.replace("$table", tableName)+sql_special_and;
			String sql_good=sql_statistic_good.replace("$table", tableName)+sql_special_and;
			String sql_not_good=sql_statistic_not_good.replace("$table", tableName)+sql_special_and;
			
			String sql_haveNoZB=sql_statistic_haveNoZB.replace("$table", tableName)+sql_special_and;
			String sql_haveNoJWQ=sql_statistic_haveNoJWQ.replace("$table", tableName)+sql_special_and;
			String sql_haveNoBoth=sql_statistic_haveNoBoth.replace("$table", tableName)+sql_special_and;
			
			long count_totle=jdbcBaseDao.jdbcQueryCount(sql_totle, ParamObject.new_NP_C());
			long count_good=jdbcBaseDao.jdbcQueryCount(sql_good, ParamObject.new_NP_C());
			long count_not_good=jdbcBaseDao.jdbcQueryCount(sql_not_good, ParamObject.new_NP_C());
			
			long count_haveNoZB=jdbcBaseDao.jdbcQueryCount(sql_haveNoZB, ParamObject.new_NP_C());
			long count_haveNoJWQ=jdbcBaseDao.jdbcQueryCount(sql_haveNoJWQ, ParamObject.new_NP_C());
			long count_haveNoBoth=jdbcBaseDao.jdbcQueryCount(sql_haveNoBoth, ParamObject.new_NP_C());
			
			Map<String,Object> map=Maps.newHashMap();
			map.put("count_totle", count_totle);
			map.put("count_good", count_good);
			map.put("count_not_good", count_not_good);
			
			//long totlexx=count_haveNoZB+count_haveNoJWQ+count_haveNoBoth;
			long totlexx=count_not_good;
			if(count_not_good==0){
				map.put("count_haveNoZB", 0);
				map.put("count_haveNoJWQ", 0);
				map.put("count_haveNoBoth", 0);
			}else{
				double haveNoZB_pate=(count_haveNoZB*1.0)/totlexx;
				double haveNoJWQ_pate=(count_haveNoJWQ*1.0)/totlexx;
				double haveNoBoth=(count_haveNoBoth*1.0)/totlexx;
				
				map.put("count_haveNoZB", haveNoZB_pate);
				map.put("count_haveNoJWQ", haveNoJWQ_pate);
				map.put("count_haveNoBoth", haveNoBoth);
			}
		
			map.put("orgCode", orgCode);
			map.put("orgName", orgName);
			datax.add(map);
		}
		return datax;
	}
	@Cacheable(value="dataStatistic",key="'DataStatisticServiceImpl_barDataStatisticBySj'+#sjName")
	@Override
	public List<Map<String,Object>> loadFjStatisticData(String sjName){
		List<Map<String,Object>> datax=Lists.newArrayList();
		if(sjName==null||sjName.isEmpty()){
			return datax;
		}
		String sjCode=null;
		String tableName=null;
		String sql_special_and=null;
		for (Map.Entry<String, Map<String, String>> entry : statisticTableConfig.entrySet()) {
			Map<String, String> oneDSOrg = entry.getValue();
			String orgName = oneDSOrg.get("orgName");
			if (sjName.equals(orgName)) {
				sjCode = oneDSOrg.get("orgCode");
				tableName=oneDSOrg.get("tableName");
				 sql_special_and=oneDSOrg.get("sql_statistic_special")==null?"":oneDSOrg.get("sql_statistic_special");
				
			}
		}
		if(sjCode==null||sjCode.isEmpty()||tableName==null||tableName.isEmpty()){
			return datax;
		}
//		String sql="select concat(substr(t.sjgsdwdm,1,6),'000000') as fjCode from "+tableName+" t group by substr(t.sjgsdwdm,1,6)";
//		List<Map<String, Object>> list= jdbcBaseDao.jdbcQueryMapList(sql,ParamObject.new_NP_NC());
//		if(list==null||list.size()==0){
//			return datax;
//		}
		Organization sj=organizationMapper.selectOrgByCode(sjCode);
		if(sj==null){
			return datax;
		}
		List<Organization> listFromOrg =  organizationMapper.selectOrgByPorgId(sj.getOrgna_id());
		//String fjAnd=" and substr(t.sjgsdwdm,1,6) = '$fjCode' ";
		 
		for(Organization fj:listFromOrg){
			String fjAnd=" and t.sjgsdwdm like '$fjCode%' ";
			String orgName=fj.getOrgna_name();
			String orgCode=fj.getOrgna_code();
			fjAnd=fjAnd.replace("$fjCode", orgCode.substring(0, 6));
			
			String sql_totle=sql_statistic_totle.replace("$table", tableName)+fjAnd+sql_special_and;
			String sql_good=sql_statistic_good.replace("$table", tableName)+fjAnd+sql_special_and;
			String sql_not_good=sql_statistic_not_good.replace("$table", tableName)+fjAnd+sql_special_and;
			
			String sql_haveNoZB=sql_statistic_haveNoZB.replace("$table", tableName)+fjAnd+sql_special_and;
			String sql_haveNoJWQ=sql_statistic_haveNoJWQ.replace("$table", tableName)+fjAnd+sql_special_and;
			String sql_haveNoBoth=sql_statistic_haveNoBoth.replace("$table", tableName)+fjAnd+sql_special_and;
			
			long count_totle=jdbcBaseDao.jdbcQueryCount(sql_totle, ParamObject.new_NP_C());
			long count_good=jdbcBaseDao.jdbcQueryCount(sql_good, ParamObject.new_NP_C());
			long count_not_good=jdbcBaseDao.jdbcQueryCount(sql_not_good, ParamObject.new_NP_C());
			
			long count_haveNoZB=jdbcBaseDao.jdbcQueryCount(sql_haveNoZB, ParamObject.new_NP_C());
			long count_haveNoJWQ=jdbcBaseDao.jdbcQueryCount(sql_haveNoJWQ, ParamObject.new_NP_C());
			long count_haveNoBoth=jdbcBaseDao.jdbcQueryCount(sql_haveNoBoth, ParamObject.new_NP_C());
			
			Map<String,Object> map=Maps.newHashMap();
			map.put("count_totle", count_totle);
			map.put("count_good", count_good);
			map.put("count_not_good", count_not_good);
			
			//long totlexx=count_haveNoZB+count_haveNoJWQ+count_haveNoBoth;
			long totlexx=count_not_good;
			if(count_not_good==0){
				map.put("count_haveNoZB", 0);
				map.put("count_haveNoJWQ", 0);
				map.put("count_haveNoBoth", 0);
			}else{
				double haveNoZB_pate=(count_haveNoZB*1.0)/totlexx;
				double haveNoJWQ_pate=(count_haveNoJWQ*1.0)/totlexx;
				double haveNoBoth=(count_haveNoBoth*1.0)/totlexx;
				
				map.put("count_haveNoZB", haveNoZB_pate);
				map.put("count_haveNoJWQ", haveNoJWQ_pate);
				map.put("count_haveNoBoth", haveNoBoth);
			}
		
			map.put("orgCode", orgCode);
			map.put("orgName", orgName);
			datax.add(map);
		
		}
//		List<String> listFromMlph=Lists.newArrayList();
//		for(Map<String, Object> map:list){
//			listFromMlph.add((String) map.get("fjCode"));
//		}
//		
		
		return datax;
	}
	
	@Cacheable(value="dataStatistic",key="'DataStatisticServiceImpl_barDataStatisticByCode'+#orgCode+#isQueryChild")
	@Override
	public List<Map<String,Object>> loadNewStatisticData(String orgCode,int level,boolean isQueryChild){
		List<Map<String,Object>> datax=Lists.newArrayList();
		//父porgId = orgId 查询子类
		List<Organization> listFromOrg =  new ArrayList<Organization>();
		if(level == 2){
			listFromOrg =  organizationMapper.selectOrgByPorgId(orgCode);
		}else if(level == 12){
			return datax;
		}else{
			if(isQueryChild == true){
				listFromOrg.add(organizationMapper.selectOrgById(orgCode));
			}else{
				listFromOrg =  organizationMapper.selectOrgByPorgId(orgCode);
			}
		}
		
		for(int i = 0; i < listFromOrg.size(); i++){
			String orgnaCode = listFromOrg.get(i).getOrgna_code();
			String orgnaName = listFromOrg.get(i).getOrgna_name();
			String orgnaCodeStr = SubApStrUtil.removeLastChars(orgnaCode,"0");
			if(orgnaCodeStr.length()==3){
				orgnaCodeStr=orgnaCodeStr+"0";
			}
			
			String sql_totle=sql_statistic_totle_new.replace("$orgId", "'"+orgnaCodeStr +"%'");
			String sql_good=sql_statistic_good_new.replace("$orgId", "'"+orgnaCodeStr +"%'");
			String sql_not_good=sql_statistic_not_good_new.replace("$orgId", "'"+orgnaCodeStr +"%'");
			
			String sql_haveNoZB=sql_statistic_haveNoZB_new.replace("$orgId", "'"+orgnaCodeStr +"%'");
			String sql_haveNoJWQ=sql_statistic_haveNoJWQ_new.replace("$orgId", "'"+orgnaCodeStr +"%'");
			String sql_haveNoBoth=sql_statistic_haveNoBoth_new.replace("$orgId", "'"+orgnaCodeStr +"%'");
			
			long count_totle=jdbcBaseDao.jdbcQueryCount(sql_totle, ParamObject.new_NP_C());
			long count_good=jdbcBaseDao.jdbcQueryCount(sql_good, ParamObject.new_NP_C());
			long count_not_good=jdbcBaseDao.jdbcQueryCount(sql_not_good, ParamObject.new_NP_C());
			
			long count_haveNoZB=jdbcBaseDao.jdbcQueryCount(sql_haveNoZB, ParamObject.new_NP_C());
			long count_haveNoJWQ=jdbcBaseDao.jdbcQueryCount(sql_haveNoJWQ, ParamObject.new_NP_C());
			long count_haveNoBoth=jdbcBaseDao.jdbcQueryCount(sql_haveNoBoth, ParamObject.new_NP_C());
			
			Map<String,Object> map=Maps.newHashMap();
			map.put("count_totle", count_totle);
			map.put("count_good", count_good);
			map.put("count_not_good", count_not_good);
			
			//long totlexx=count_haveNoZB+count_haveNoJWQ+count_haveNoBoth;
			long totlexx=count_not_good;
			if(count_not_good==0){
				map.put("count_haveNoZB", 0);
				map.put("count_haveNoJWQ", 0);
				map.put("count_haveNoBoth", 0);
			}else{
				double haveNoZB_pate=(count_haveNoZB*1.0)/totlexx;
				double haveNoJWQ_pate=(count_haveNoJWQ*1.0)/totlexx;
				double haveNoBoth=(count_haveNoBoth*1.0)/totlexx;
				
				map.put("count_haveNoZB", haveNoZB_pate);
				map.put("count_haveNoJWQ", haveNoJWQ_pate);
				map.put("count_haveNoBoth", haveNoBoth);
			}
		
			map.put("orgCode", orgnaCode);
			map.put("orgName", orgnaName);
			datax.add(map);
		}
		return datax;
	}
	@Override
	public List<Map<String,Object>> loadStatisticDataFromEs(String orgCode,int level,boolean isQueryChild){
		List<Map<String,Object>> datax=Lists.newArrayList();
		List<Organization> listFromOrg =  new ArrayList<Organization>();
		if(level == 2){
			listFromOrg =  organizationMapper.selectOrgByPorgId(orgCode);
		}else if(level == 12){
			return datax;
		}else{
			if(isQueryChild == true){
				listFromOrg.add(organizationMapper.selectOrgById(orgCode));
			}else{
				listFromOrg =  organizationMapper.selectOrgByPorgId(orgCode);
			}
		}
		for(int i = 0; i < listFromOrg.size(); i++){
			String orgnaCode = listFromOrg.get(i).getOrgna_code();
			String orgnaName = listFromOrg.get(i).getOrgna_name();
			String orgnaCodeStr = SubApStrUtil.removeLastChars(orgnaCode,"0");
			if(orgnaCodeStr.length()==3){
				orgnaCodeStr=orgnaCodeStr+"0";
			}
			
			long count_totle = queryCountFromEsByOrgCode(orgnaCodeStr, "totle");
			long count_good = queryCountFromEsByOrgCode(orgnaCodeStr, "good");
			long count_not_good = queryCountFromEsByOrgCode(orgnaCodeStr, "not_good");
			
			long count_haveNoZB = queryCountFromEsByOrgCode(orgnaCodeStr, "nozb");
			long count_haveNoJWQ = queryCountFromEsByOrgCode(orgnaCodeStr, "nojwq");
			long count_haveNoBoth = queryCountFromEsByOrgCode(orgnaCodeStr, "noboth");
			
			
			Map<String,Object> map=Maps.newHashMap();
			map.put("count_totle", count_totle);
			map.put("count_good", count_good);
			map.put("count_not_good", count_not_good);
			
			//long totlexx=count_haveNoZB+count_haveNoJWQ+count_haveNoBoth;
			long totlexx=count_not_good;
			if(count_not_good==0){
				map.put("count_haveNoZB", 0);
				map.put("count_haveNoJWQ", 0);
				map.put("count_haveNoBoth", 0);
			}else{
				double haveNoZB_pate=(count_haveNoZB*1.0)/totlexx;
				double haveNoJWQ_pate=(count_haveNoJWQ*1.0)/totlexx;
				double haveNoBoth=(count_haveNoBoth*1.0)/totlexx;
				
				map.put("count_haveNoZB", haveNoZB_pate);
				map.put("count_haveNoJWQ", haveNoJWQ_pate);
				map.put("count_haveNoBoth", haveNoBoth);
			}
		
			map.put("orgCode", orgnaCode);
			map.put("orgName", orgnaName);
			datax.add(map);
		}
		return datax;
	}
	private void queryChildAll(String code){
		//父porgId = orgId 查询子类
		List<Map<String, Object>> mlist = loadNewStatisticData(code,2,true);
		for(int i = 0; i < mlist.size(); i++){
			String orgCode = mlist.get(i).get("orgCode").toString();
			queryChildAll(orgCode);
		}
	}
	private long queryCountFromEsByOrgCode(String orgCode,String countCategory){
			long count =0;
			
		  	PrefixQueryBuilder  boolQueryBuilder = QueryBuilders.prefixQuery("SJGSDWDM", orgCode);
			
			SearchRequestBuilder searchRequestBuilder = elasticService.getClient()
					.prepareSearch("psam")
					.setTypes("mlph")
					.setSearchType(org.elasticsearch.action.search.SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(boolQueryBuilder);
			if(countCategory.equals("totle")){
				
			}else if(countCategory.equals("not_good")){
				searchRequestBuilder.setPostFilter(FilterBuilders.orFilter(missingFilter("ZXDHZB")).add(missingFilter("ZXDZZB")).add(missingFilter("JWQBH")));
			}else if(countCategory.equals("good")){
				searchRequestBuilder.setPostFilter(FilterBuilders.existsFilter("ZXDHZB").filterName("ZXDZZB").filterName("JWQBH"));
			}else if(countCategory.equals("nozb")){
				searchRequestBuilder.setPostFilter(FilterBuilders.orFilter(missingFilter("ZXDHZB")).add(missingFilter("ZXDZZB")));
			}else if(countCategory.equals("nojwq")){
				searchRequestBuilder.setPostFilter(FilterBuilders.andFilter(missingFilter("JWQBH")));
			}else if(countCategory.equals("noboth")){
				searchRequestBuilder.setPostFilter(FilterBuilders.andFilter(missingFilter("ZXDHZB")).add(missingFilter("ZXDZZB")).add(missingFilter("JWQBH")));
			}
			SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
			
			count = searchResponse.getHits().totalHits();
			
		 return count;
	}
public static void main(String[] sd){
	long count_haveNoZB=2L;
	long count_haveNoJWQ=5L;
	System.out.println((count_haveNoZB*1.0)/((count_haveNoZB+count_haveNoJWQ)));
}

}
