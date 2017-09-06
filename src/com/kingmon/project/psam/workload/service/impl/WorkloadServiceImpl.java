package com.kingmon.project.psam.workload.service.impl;

import static org.elasticsearch.index.query.FilterBuilders.missingFilter;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sound.midi.SysexMessage;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.lucene.search.PrefixFilter;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.PrefixFilterBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryFilterBuilder;
import org.elasticsearch.index.query.TermsFilterBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggegator;
import org.elasticsearch.search.aggregations.metrics.stats.StatsBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.nustaq.serialization.annotations.Serialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.OrgUtilX;
import com.kingmon.project.auth.organization.mapper.OrganizationMapper;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organizationuser.mapper.OrganizationUserMapper;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.workload.service.IWorkloadService;
import com.kingmon.project.psam.workload.util.WorkLoadUtil;
//GET /psam/mlph/_search
//{
//  "size": 0, 
// "aggs": {
//   "group_by_state": {
//      "terms": {
//      "field": 
//        "SSPCS"
//     
//    }
//   }
// },
//  "query": {
//    "terms": {
//      "SSPCS": [
//        "370103240000"
//      ]
//    }
//  }
//}
@Service
public class WorkloadServiceImpl extends BaseService implements IWorkloadService{
	@Autowired
	private ElasticService elasticService;
	
	@Autowired
	private OrganizationMapper orgMapper;
	@Autowired
	private OrganizationUserMapper orgUserMapper;
	@Autowired
	private JwqMapper jwqMapper;
	
	@Value("${dev.data.process}")
	private String devDataProcess;
	
	/**
	 * 定时缓存数据
	 */
	public void scheduledWorkLoadTask(){
		try {
			String porg_code = "370000000000";//最高层次(省厅)
			loadDzWorkLoadFromDb(porg_code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 统计门楼牌，建筑物，总数
	 *  无坐标，无警务区数量
	 *   警务区 总数，无坐标数量
	 *  
	 *  @param porg_code 
	 */
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	public DataSet loadDzWorkLoadDataFromEs(String porg_code){
		if(porg_code==null){
			porg_code="370000000000";
		}
		List<Map<String,Object>> dataRows=WorkLoadUtil.genDataRows(porg_code,orgMapper,orgUserMapper,jwqMapper);
		
		if(dataRows==null||dataRows.size()<=0){
			return DataSet.newDs();
		}
		for(Map<String,Object> map : dataRows){
			String prefixMatch_code=(String) map.get("code");
			prefixMatch_code=SubApStrUtil.removeLastChars(prefixMatch_code, "0");
			if(prefixMatch_code.length()==3){
				prefixMatch_code=prefixMatch_code+"0";
			}
			
//			long  mlph_totle=queryCountFromEsByOrgCode("mlph","SJGSDWDM",prefixMatch_code, "total","JWQBH");
//			long  jzwjbxx_totle=queryCountFromEsByOrgCode("jzwjbxx","ZAGLSSJWZRQDM",prefixMatch_code, "total","ZAGLSSJWZRQDM");
//			long  jwq_totle=queryCountFromEsByOrgCode("jwq","PCSID",prefixMatch_code, "total","JWQBH");
			
			long mlph_totle=aggregationCountTotle("mlph","SJGSDWDM",prefixMatch_code,"total","JWQBH");
			long mlph_nozb=aggregationCountTotle("mlph","SJGSDWDM",prefixMatch_code, "nozb","JWQBH");
			long mlph_jwq=aggregationCountTotle("mlph","SJGSDWDM",prefixMatch_code, "nojwq","JWQBH");
			
			map.put("mlph_totle", mlph_totle);
			map.put("mlph_nozb", mlph_nozb);
			map.put("mlph_jwq", mlph_jwq);
			
			
			long jzwjbxx_totle=aggregationCountTotle("jzwjbxx","ZAGLSSJWZRQDM",prefixMatch_code,"total","ZAGLSSJWZRQDM");
			long jzwjbxx_nozb=aggregationCountTotle("jzwjbxx","ZAGLSSJWZRQDM",prefixMatch_code, "nozb","ZAGLSSJWZRQDM");
			long jzwjbxx_jwq=aggregationCountTotle("jzwjbxx","ZAGLSSJWZRQDM",prefixMatch_code, "nojwq","ZAGLSSJWZRQDM");
			
			map.put("jzwjbxx_totle", jzwjbxx_totle);
			map.put("jzwjbxx_nozb", jzwjbxx_nozb);
			map.put("jzwjbxx_jwq", jzwjbxx_jwq);
			
			
			long jwq_totle=aggregationCountTotle("jwq","PCSID",prefixMatch_code,"total","JWQBH");
			long jwq_nozb=aggregationCountTotle("jwq","PCSID",prefixMatch_code, "nozb","JWQBH");

			map.put("jwq_totle", jwq_totle);
			map.put("jwq_nozb", jwq_nozb);
		}
		DataSet ds=DataSet.newDs().setRows(dataRows).setTotal((long) dataRows.size());
		return ds;
	}
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Cacheable(value="workLoadCashe",key="'WorkloadServiceImpl_loadDzWorkLoadFromDb'+#porg_code")
	public DataSet loadDzWorkLoadFromDb(String porg_code){
		if(porg_code==null){
			porg_code="370000000000";
		}
		List<Map<String,Object>> dataRows=WorkLoadUtil.genDataRows(porg_code,orgMapper,orgUserMapper,jwqMapper);
		
		if(dataRows==null||dataRows.size()<=0){
			return DataSet.newDs();
		}
		for(Map<String,Object> map : dataRows){
			String prefixMatch_code=(String) map.get("code");
			prefixMatch_code=SubApStrUtil.removeLastChars(prefixMatch_code, "0");
			if(prefixMatch_code.length()==3){
				prefixMatch_code=prefixMatch_code+"0";
			}
			
			long  mlph_totle=DbCountNoZb("dz_mlph","SJGSDWDM",prefixMatch_code, "total","JWQBH");
			long mlph_nozb=DbCountNoZb("dz_mlph","SJGSDWDM",prefixMatch_code, "nozb","JWQBH");
			long mlph_jwq=DbCountNoZb("dz_mlph","SJGSDWDM",prefixMatch_code, "nojwq","JWQBH");
			
			map.put("mlph_totle", mlph_totle);
			map.put("mlph_nozb", mlph_nozb);
			map.put("mlph_jwq", mlph_jwq);
			
			
			long jzwjbxx_totle=DbCountNoZb("dz_jzwjbxx","ZAGLSSJWZRQDM",prefixMatch_code, "total","ZAGLSSJWZRQDM");
			long jzwjbxx_nozb=DbCountNoZb("dz_jzwjbxx","ZAGLSSJWZRQDM",prefixMatch_code, "nozb","ZAGLSSJWZRQDM");
			long jzwjbxx_jwq=DbCountNoZb("dz_jzwjbxx","ZAGLSSJWZRQDM",prefixMatch_code, "nojwq","ZAGLSSJWZRQDM");
			
			map.put("jzwjbxx_totle", jzwjbxx_totle);
			map.put("jzwjbxx_nozb", jzwjbxx_nozb);
			map.put("jzwjbxx_jwq", jzwjbxx_jwq);
			
			
			long  jwq_totle=DbCountNoZb("ent_jwq","PCSID",prefixMatch_code, "total","JWQBH");
			long jwq_nozb=DbCountNoZb("ent_jwq","PCSID",prefixMatch_code, "nozb","JWQBH");

			map.put("jwq_totle", jwq_totle);
			map.put("jwq_nozb", jwq_nozb);
		}
		DataSet ds=DataSet.newDs().setRows(dataRows).setTotal((long) dataRows.size());
		return ds;
	}
	
    private long aggregationCountTotle(String index,String prefixFiled,String prefixFiledvalue,String countCategory,String jwq){
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
		if(index.equals("jwq")){
			boolQueryBuilder.must(matchPhraseQuery("SFYX", "1"));
		}
		FilteredQueryBuilder filteredQueryBuilder=QueryBuilders.filteredQuery(boolQueryBuilder, null);
		
		FilterBuilder filterBuilder=new PrefixFilterBuilder(prefixFiled, prefixFiledvalue);

		FilterAggregationBuilder aggregationBuilder=AggregationBuilders.filter("group_by_state");
		
		if(countCategory.equals("total")){
			aggregationBuilder.filter(filterBuilder);
		}else if(countCategory.equals("nozb")){
			if(!index.equals("jwq")){
				aggregationBuilder.filter(FilterBuilders.andFilter(filterBuilder).add(missingFilter("ZXDZZB")).add(missingFilter("ZXDHZB")));
			}else{
				aggregationBuilder.filter(FilterBuilders.andFilter(filterBuilder).add(missingFilter("BJZB")));
			}
		}else if(countCategory.equals("nojwq")){
			aggregationBuilder.filter(FilterBuilders.andFilter(missingFilter(jwq)).add(filterBuilder));
		}
		
		SearchRequestBuilder searchRequestBuilder=elasticService.getClient().prepareSearch("psam").setTypes(index).setSearchType(SearchType.COUNT).setSize(0);
		
		searchRequestBuilder.addAggregation(aggregationBuilder).setQuery(filteredQueryBuilder);
		SearchResponse searchResponse =searchRequestBuilder.execute().actionGet();
		Map<String, Aggregation> aggMap=searchResponse.getAggregations().asMap();
		InternalFilter filter=(InternalFilter) aggMap.get("group_by_state");
		long res=filter.getDocCount();
		return res;
	}
	
	
	

	private long DbCountNoZb(String table,String prefixFiled,String prefixFiledvalue,String countCategory,String jwqBh){
		if(prefixFiledvalue!=null&&prefixFiledvalue!=""){
			prefixFiledvalue+="%";
		}
		String sql="";
		if(countCategory.equals("total")){
			if(!table.equals("ent_jwq")){
				sql="SELECT COUNT(1) FROM "+table+" m where m."+prefixFiled+" LIKE '"+prefixFiledvalue+"'";
			}else if(table.equals("ent_jwq")){
				sql="SELECT COUNT(1) FROM "+table+" m where m.SFYX=1 AND m."+prefixFiled+" LIKE '"+prefixFiledvalue+"'";
			}
		}else if(countCategory.equals("nozb")){
			if(!table.equals("ent_jwq")){
				sql="SELECT COUNT(1) FROM "+table+" m WHERE (m.ZXDHZB IS NULL or m.ZXDZZB IS NULL) AND m."+prefixFiled+" LIKE '"+prefixFiledvalue+"'";
			}else if(table.equals("ent_jwq")){
				sql="SELECT COUNT(1) FROM "+table+" m WHERE m.BJZBZ IS NULL AND m.SFYX=1 AND m."+prefixFiled+" LIKE '"+prefixFiledvalue+"'";
			}
			
		}else if(countCategory.equals("nojwq")){
			sql="SELECT COUNT(1) FROM "+table+" m WHERE m."+jwqBh+" IS NULL AND m."+prefixFiled+" LIKE '"+prefixFiledvalue+"'";
		}
		
		long count=jdbcBaseDao.jdbcQueryCount(sql, ParamObject.new_NP_NC());//.addSQLParam("prefix", prefixFiledvalue)
		return count;
	}


//	@Transactional(rollbackFor = Exception.class,readOnly=true)
//	@Override
//	public DataSet loadDzWorkLoadDataSet(String porg_code){
//		DataSet dataSet=DataSet.newDs();
//		if(porg_code==null){
//			porg_code="370000000000";
//		}
//		List<Map<String,Object>> dataRows=WorkLoadUtil.genDataRows(porg_code,orgMapper,orgUserMapper,jwqMapper);
//		
//		if(KConstants.DEV_DATA_PROCESS_CHECK.equals(devDataProcess)){
//			dataSet=loadDzWorkLoadDataFromEs(dataRows);
//		}else{
//			dataSet=loadDzWorkLoadFromDb(dataRows);
//		}
//		return dataSet;
//	}
   
	
//	private long queryCountFromEsByOrgCode(String index,String prefixFiled,String prefixFiledvalue,String countCategory,String jwq){
//		long count =0;
//	  	PrefixQueryBuilder  boolQueryBuilder = QueryBuilders.prefixQuery(prefixFiled, prefixFiledvalue);
//		SearchRequestBuilder searchRequestBuilder = elasticService.getClient()
//				.prepareSearch("psam")
//				.setTypes(index)
//				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//				.setQuery(boolQueryBuilder);
//		if(countCategory.equals("total")){
//			
//		}else if(countCategory.equals("nozb")){
//			if(!index.equals("jwq")){
//				searchRequestBuilder.setPostFilter(FilterBuilders.orFilter(missingFilter("ZXDHZB")).add(missingFilter("ZXDZZB")));
//			}else{
//				searchRequestBuilder.setPostFilter(FilterBuilders.orFilter(missingFilter("BJZB")));
//			}
//			
//		}else if(countCategory.equals("nojwq")){
//			searchRequestBuilder.setPostFilter(FilterBuilders.andFilter(missingFilter(jwq)));
//		}
//		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
//		count = searchResponse.getHits().totalHits();
//		return count;
//	 }

//	/** @param type
//	 * @param index
//	 * @param aggsKeys
//	 * @return
//	 */
//	private List<Map<String,Object>> esQueryCount(String type,String index,List<String> aggsKeys ){
//		List<Map<String,Object>> data=Lists.newArrayList();
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
//		TermsFilterBuilder termsFilterBuilder = new TermsFilterBuilder("SSPCS",aggsKeys);
//		FilteredQueryBuilder filteredQueryBuilder=QueryBuilders.filteredQuery(boolQueryBuilder, termsFilterBuilder);
//		TermsBuilder aggregationBuilder=AggregationBuilders.terms("group_by_state").field("SSPCS").size(100);
//		SearchRequestBuilder searchRequestBuilder=elasticService.getClient()
//				.prepareSearch("psam").setTypes(index)
//				.setSearchType(SearchType.COUNT)
//				//.addSort("DZBM", SortOrder.ASC),
//				//.setFrom(NumberUtils.toInt(params.get("from")))
//				.setSize(0);
//		
//		searchRequestBuilder.addAggregation(aggregationBuilder).setQuery(filteredQueryBuilder);
//	//	searchRequestBuilder.addAggregation(aggregationBuilder).setQuery(boolQueryBuilder).setPostFilter(termsBuilder);
//		SearchResponse searchResponse =searchRequestBuilder.execute().actionGet();
//		Map<String, Aggregation> aggMap=searchResponse.getAggregations().asMap();
//		StringTerms gradeTerms = (StringTerms) aggMap.get("group_by_state");
//		Iterator<Bucket> gradeBucketIt = gradeTerms.getBuckets().iterator();
//		while(gradeBucketIt.hasNext()){
//			Bucket gradeBucket = gradeBucketIt.next();
//			//System.out.println(gradeBucket.getKey() + "||" + gradeBucket.getDocCount() +"。");
//
//			Map<String,Object> map=Maps.newHashMap();
//			map.put(gradeBucket.getKey(), gradeBucket.getDocCount());
//			data.add(map);
//		}
//		//System.out.println(searchResponse);
//		return data;
//	}
	
}
