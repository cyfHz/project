package com.kingmon.project.psam.sjjy.service.impl;

import static org.elasticsearch.index.query.FilterBuilders.missingFilter;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.math.NumberUtils;import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.apache.lucene.search.PrefixQuery;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisFieldQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryFilterBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticTypes;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.jwq.service.IJwqService;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.sjjy.service.CheckMlphService;

@Service
public class CheckMlphServiceImpl implements CheckMlphService{
		@Autowired
		private MlphMapper mlphMapper;
		
		@Autowired
		private ElasticService elasticService;
		
		@Value("${dev.data.check}")
		private String devDataProcess;
		
		@Transactional(rollbackFor = Exception.class, readOnly = true)
		@Override
		public DataSet mlphList(Map<String, String> params) {
			String orgId = params.get("ORGID");
			if(orgId == null || orgId.isEmpty()){
//				orgId = "370000000000";
				orgId = SecurityUtils.getUserLevelStr();
			}
			if(orgId.length()>6){
				orgId = SubApStrUtil.removeLastChars(orgId,"0");
			}
			if (KConstants.DEV_DATA_PROCESS_CHECK.equals(devDataProcess)) {
				PaginationUtil.initElasticPageAndSort(params);
				PrefixQueryBuilder  prefixQueryBuilder = QueryBuilders.prefixQuery("SJGSDWDM", orgId);
				
				BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
				String dzmc = params.get("DZMC");
				if (dzmc != null && !dzmc.isEmpty()) {
					boolQueryBuilder.must(matchPhraseQuery("DZMC", dzmc));
				}
				String mlpph = params.get("MLPH");
				if (mlpph != null && !mlpph.isEmpty()) {
					boolQueryBuilder.must(matchPhraseQuery("MLPH", mlpph));
				}
				boolQueryBuilder.must(prefixQueryBuilder);
				SearchRequestBuilder builder = elasticService.getClient().prepareSearch("psam").setTypes("mlph")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(boolQueryBuilder)
				.addSort("YWLSH", SortOrder.ASC).setFrom(NumberUtils.toInt(params.get("from")))
				.setSize(NumberUtils.toInt(params.get("size")));
				builder.setPostFilter(FilterBuilders.orFilter(missingFilter("ZXDHZB")).add(missingFilter("ZXDZZB")).add(missingFilter("JWQBH")));
				SearchResponse searchResponse = builder.execute().actionGet();
				return ElasticUtil.searchResponse2Dataset(searchResponse);
			} else {
				PaginationUtil.initPageAndSort(params);
				params.put("SJGSDWDM",  orgId + "%");
				String dzmc = params.get("DZMC");
				if (dzmc != null && !dzmc.isEmpty()) {
					params.put("DZMC", "%" + dzmc + "%");
		
				}
				String mlpph = params.get("MLPH");
				if (mlpph != null && !mlpph.isEmpty()) {
					params.put("MLPH", "%" + mlpph + "%");
				}
				String sjgsdwmc = params.get("SJGSDWMC");
				if (sjgsdwmc != null && !sjgsdwmc.isEmpty()) {
					params.put("SJGSDWMC", "%" + sjgsdwmc + "%");
				}
				String jlxdzbm = params.get("jlxdzbm");
				if (jlxdzbm != null && !"".equals(jlxdzbm)) {
					params.put("SSJLXXQ_DZBM", "%" + jlxdzbm + "%");
				}
				return new DataSet(mlphMapper.selectCheckCount(params), mlphMapper.checkmlphList(params));
			}

		}

//		@Transactional(rollbackFor = Exception.class)
//		@Override 
//		public boolean updateLocation(String ywlsh, GeoPoint point) {
//			if(point==null||point.getLon()==0||point.getLat()==0){
//				AlertSLEUtil.Error("经纬度有误");
//			}
//			Map<String, Object> map = new HashMap<String, Object>();
//			//检索该点所属警务区 （ES）
//			List<Map<String,Object>>  list = jwqService.findJwqByPointAndUserPerm(SecurityUtils.getUserId(),point.getLat(), point.getLon());
//			//有警务区
//			if(list != null && list.size() > 0){
//				map.put("JWQMC", list.get(0).get("JWQMC"));//警务区名称
//				map.put("JWQBH", list.get(0).get("JWQBH"));//警务区编号
//				Mlph ml =  mlphMapper.selectMlphByYwlsh(ywlsh);
//				//Ssjzw_dzbm 所属建筑物地址编码  ！= null 即为 建筑物
//				if(ml.getSsjzw_dzbm() != null && ml.getSsjzw_dzbm().isEmpty()){
//					updateJzwLocation(ml.getDzbm(),point,map);
//				}
//				map.put("YWLSH", ywlsh);
//				map.put("ZXDHZB", new BigDecimal(point.getLon()).setScale(8,RoundingMode.CEILING));
//				map.put("ZXDZZB", new BigDecimal(point.getLat()).setScale(8,RoundingMode.CEILING));
//				mlphMapper.updateMlphPointAndJwq(map);
//				// 更新ELastic
//				elasticService.updateDocument(ElasticTypes.MLPH, ywlsh, ElasticUtil.mlphMapToDocument(map));
//				return true;
//			}else{
//				return false;
//				//AlertSLEUtil.Error("该点不属于任何警务区,请重新选点!");
//			}
//		}
//		/**
//		 * 修改建筑物坐标数据
//		 * @param dzbm
//		 * @param point
//		 */
//		private void updateJzwLocation(String dzbm, GeoPoint point,Map<String, Object> map) {
//			if(dzbm==null||dzbm.isEmpty()){
//				AlertSLEUtil.Error("数据有误地址编码为空");
//			}
//			map.put("DZBM", dzbm);
//			map.put("ZXDHZB", new BigDecimal(point.getLon()));
//			map.put("ZXDZZB", new BigDecimal(point.getLat()));
//			jzwMapper.updateJzwPointAndJwq(map);
//			// 更新ELastic
//			elasticService.updateDocument(ElasticTypes.JZWJBXX, dzbm, ElasticUtil.jzwMapToDocument(map));
//		}
}
