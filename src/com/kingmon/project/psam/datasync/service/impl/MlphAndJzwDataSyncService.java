package com.kingmon.project.psam.datasync.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BaseFilterBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoPolygonFilterBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.elastic.model.Polygon;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticTypes;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.datasync.model.JwqBjzbChangeModel;
import com.kingmon.project.psam.datasync.service.IMlphAndJzwDataSyncService;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jwq.service.IJwqService;
import com.kingmon.project.psam.jwq.service.impl.JwqServiceImpl;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.mlph.dao.MlphMapper;

@Service
public class MlphAndJzwDataSyncService extends BaseService implements IMlphAndJzwDataSyncService {
	@Autowired
	private MlphMapper mlphMapper;
	@Autowired
	private ElasticService elasticService;
	@Autowired
	private JzwjbxxMapper jzwjbxxMapper;
	

	//@Async
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void asyncForJwqBjzbChange(Jwq jwq, String newBjzbz, String oldBjzbz) {
		List<JwqBjzbChangeModel> ayscCaseList=Lists.newArrayList();
		ayscCaseList.add(JwqBjzbChangeModel.newMo("mlph", "YWLSH", "dz_mlph"));
		ayscCaseList.add(JwqBjzbChangeModel.newMo("jzwjbxx", "DZBM", "dz_jzwjbxx"));
		
		if(!StringUtils.hasText(newBjzbz)||!StringUtils.hasText(oldBjzbz)||newBjzbz.equals(oldBjzbz)){
			return;
		}
		for(JwqBjzbChangeModel changeM:ayscCaseList){
			List<String> oldList = Lists.newArrayList();
			List<String> newList = Lists.newArrayList();
			 oldList = polygonsSearch(ElasticUtil.splitStrToPolygon(oldBjzbz),changeM.getSearchFrom(),changeM.getSearchField());
			 newList = polygonsSearch(ElasticUtil.splitStrToPolygon(newBjzbz),changeM.getSearchFrom(),changeM.getSearchField());
			
			 oldList.removeAll(newList);// 差集---
			 
			 List<String> inOldListButFindJwq =asyncInOldListButFindJwq( oldList, changeM);
			 
			 oldList.removeAll(inOldListButFindJwq);// 差集-再次获取差集--
			 
			 if("mlph".equals(changeM.getSearchFrom())){
				 syncMlph(oldList, "0", jwq);// 差集 还原原数据状态为 0
				 syncMlph(newList,  "1", jwq);// 新数据 设置数据状态为 1
			 }else if("jzwjbxx".equals(changeM.getSearchFrom())){
				 syncJzwjbxx(oldList, "0", jwq);// 差集 还原原数据状态为 0
				 syncJzwjbxx(newList,  "1", jwq);// 新数据 设置数据状态为 1
			 }
		}
	}
	private List<String>  asyncInOldListButFindJwq(List<String> oldList,JwqBjzbChangeModel changeM){
		IJwqService jwqService=SpringBeanFacUtil.getBean(JwqServiceImpl.class);
		 List<String> inOldListButFindJwq = Lists.newArrayList();
		 List<Map<String,Object>> oldDataList = Lists.newArrayList();
		 if(oldList != null && !oldList.isEmpty()){
			  oldDataList=jdbcBaseDao.jdbcQueryMapList("select "+changeM.getSearchField()+" as ID , zxdhzb as ZXDHZB ,zxdzzb as ZXDZZB  from "+changeM.getTableName()+" where "+changeM.getSearchField()+" in (:oldList)", ParamObject.new_NP_NC().addSQLParam("oldList", oldList));
		 }else{
			 return oldList;
		 }
		 if(oldDataList==null||oldDataList.size()==0){
			 return inOldListButFindJwq;
		 }
		for (Map<String, Object> map : oldDataList) {
			Double lat = null, lng = null;
			String id = (String) map.get("ID");
			try {
				lat = Double.valueOf(""+ map.get("ZXDZZB"));
				lng = Double.valueOf(""+ map.get("ZXDHZB"));
			} catch (Exception e) {
				continue;
			}
			if (lat == null || lng == null) {
				continue;
			}
			List<Map<String, Object>> jwqList = jwqService.findJwqByPoint(lat, lng);
			if (jwqList == null || jwqList.isEmpty()) {
				continue;
			}
			for (Map<String, Object> mapx : jwqList) {
				if ("1".equals(mapx.get("SFYX"))) {
					String jwqid = (String) mapx.get("JWQID");
					String jwqbh = (String) mapx.get("JWQBH");
					String jwqmc = (String) mapx.get("JWQMC");
					Jwq findJwq = new Jwq(jwqid, jwqbh, jwqmc);
					if ("mlph".equals(changeM.getSearchFrom())) {
						syncOneMlph(id, "1", findJwq);//
					} else if ("jzwjbxx".equals(changeM.getSearchFrom())) {
						syncOneJzwjbxx(id, "1", findJwq);//
					}
					inOldListButFindJwq.add(id);
					break;
				}
			}
		}
		return inOldListButFindJwq;
	}
	private List<String> polygonsSearch(List<Polygon> polygons,String searchFrom,String searchField) {
		String searchFindex = "psam";
		BaseFilterBuilder filterBuilder = FilterBuilders.boolFilter();
		for (Polygon p : polygons) {
			BaseFilterBuilder filterBuilderx = FilterBuilders.geoPolygonFilter("LOCATION");
			for (GeoPoint point : p.getPoints()) {
				((GeoPolygonFilterBuilder) filterBuilderx).addPoint(point.getLat(), point.getLon());
			}
			((BoolFilterBuilder) filterBuilder).should(filterBuilderx);
		}
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
		SearchResponse response1 = elasticService.getClient().prepareSearch(searchFindex).setTypes(searchFrom)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(boolQueryBuilder).setPostFilter(filterBuilder)
				.execute().actionGet();
		int size = (int) response1.getHits().getTotalHits();
		SearchResponse response = elasticService.getClient().prepareSearch(searchFindex).setTypes(searchFrom).addField(searchField)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(boolQueryBuilder).setPostFilter(filterBuilder)
				.setFrom(0).setSize(size).execute().actionGet();
		List<String> list = ElasticUtil.searchResponseToIdStrList(response,searchField);
		return list;
	}
	/**
	 * 通过业务流水号修改门楼牌的所属警务区
	 * 
	 * @param list
	 * @param state
	 * @param jwq
	 */
	private void syncMlph(List<String> list, String state, Jwq jwq) {
		int totle = list.size();
		int batchSize = 100;
		int pages = (totle % batchSize == 0) ? (totle / batchSize) : (totle / batchSize) + 1;
		for (int i = 1; i <= pages; i++) {
			int fromIndex = (i - 1) * batchSize;
			int toIndex = (i == pages) ? totle : i * batchSize - 1;
			for (int j = fromIndex; j < toIndex; j++) {
				Map<String, Object> mapEs = Maps.newHashMap();
				mapEs.put("ORIGINALJWQBH", jwq.getJwqbh());
				mapEs.put("JWQBH", "1".equals(state) ? jwq.getJwqbh() : null);
				mapEs.put("JWQMC", "1".equals(state) ? jwq.getJwqmc() : null);
				elasticService.updateDocument(ElasticTypes.MLPH, list.get(j), ElasticUtil.mlphMapToDocument(mapEs));
			}
			Map<String,Object> paramMap=Maps.newHashMap();
			paramMap.put("idList", list.subList(fromIndex, toIndex));
			paramMap.put("jwqmc", "1".equals(state) ? jwq.getJwqmc() : null);
			paramMap.put("jwqbh", "1".equals(state) ? jwq.getJwqbh() : null);
			paramMap.put("originaljwqbh",jwq.getJwqbh());
			mlphMapper.updateMlphJwqBatchByYwlshs(paramMap);
		}
	}
	/**
	 * 通过业务流水号修改门楼牌的所属警务区
	 * 
	 * @param list
	 * @param state
	 * @param jwq
	 */
	private void syncOneMlph(String ywlsh, String state, Jwq jwq) {
		Map<String, Object> mapEs = Maps.newHashMap();
		mapEs.put("ORIGINALJWQBH", jwq.getJwqbh());
		mapEs.put("JWQBH", "1".equals(state) ? jwq.getJwqbh() : null);
		mapEs.put("JWQMC", "1".equals(state) ? jwq.getJwqmc() : null);
		elasticService.updateDocument(ElasticTypes.MLPH, ywlsh, ElasticUtil.mlphMapToDocument(mapEs));
		Map<String,Object> paramMap=Maps.newHashMap();
		List<String> kList = Lists.newArrayList();
		kList.add(ywlsh);
		paramMap.put("idList", kList);
		paramMap.put("jwqmc", "1".equals(state) ? jwq.getJwqmc() : null);
		paramMap.put("jwqbh", "1".equals(state) ? jwq.getJwqbh() : null);
		paramMap.put("originaljwqbh",jwq.getJwqbh());
		mlphMapper.updateMlphJwqBatchByYwlshs(paramMap);
	}
	/**
	 * 通过地址编码修改建筑物的所属警务区
	 * @param list
	 * @param statek
	 * @param jwq
	 */
	private void syncJzwjbxx(List<String> list, String state, Jwq jwq) {
		int totle = list.size();
		int batchSize = 100;
		int pages = (totle % batchSize == 0) ? (totle / batchSize) : (totle / batchSize) + 1;
		for (int i = 1; i <= pages; i++) {
			int fromIndex = (i - 1) * batchSize;
			int toIndex = (i == pages) ? totle : i * batchSize - 1;
			for (int j = fromIndex; j < toIndex; j++) {
				Map<String, Object> mapEs = Maps.newHashMap();
				mapEs.put("ORIGINALJWQBH", jwq.getJwqbh());
				mapEs.put("ZAGLSSJWZRQDM", "1".equals(state) ? jwq.getJwqbh() : null);
				mapEs.put("ZAGLSSJWZRQMC", "1".equals(state) ? jwq.getJwqmc() : null);
				elasticService.updateDocument(ElasticTypes.JZWJBXX, list.get(j), ElasticUtil.mlphMapToDocument(mapEs));
			}
			Map<String,Object> paramMap=Maps.newHashMap();
			paramMap.put("idList", list.subList(fromIndex, toIndex));
			paramMap.put("jwqmc", "1".equals(state) ? jwq.getJwqmc() : null);
			paramMap.put("jwqbh", "1".equals(state) ? jwq.getJwqbh() : null);
			paramMap.put("originaljwqbh",jwq.getJwqbh());
			jzwjbxxMapper.updateJzwjbxxBatchByDzbm(paramMap);
		}
	}
	
	/**
	 * 通过地址编码修改建筑物的所属警务区
	 * @param list
	 * @param statek
	 * @param jwq
	 */
	private void syncOneJzwjbxx(String dzbm, String state, Jwq jwq) {
		Map<String, Object> mapEs = Maps.newHashMap();
		mapEs.put("ORIGINALJWQBH", jwq.getJwqbh());
		mapEs.put("ZAGLSSJWZRQDM", "1".equals(state) ? jwq.getJwqbh() : null);
		mapEs.put("ZAGLSSJWZRQMC", "1".equals(state) ? jwq.getJwqmc() : null);
		elasticService.updateDocument(ElasticTypes.JZWJBXX, dzbm, ElasticUtil.mlphMapToDocument(mapEs));
		Map<String,Object> paramMap=Maps.newHashMap();
		List<String> kList = Lists.newArrayList();
		kList.add(dzbm);
		paramMap.put("idList", kList);
		paramMap.put("jwqmc", "1".equals(state) ? jwq.getJwqmc() : null);
		paramMap.put("jwqbh", "1".equals(state) ? jwq.getJwqbh() : null);
		paramMap.put("originaljwqbh",jwq.getJwqbh());
		jzwjbxxMapper.updateJzwjbxxBatchByDzbm(paramMap);
	}
}
