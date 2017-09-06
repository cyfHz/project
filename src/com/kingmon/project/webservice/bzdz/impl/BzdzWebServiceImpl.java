package com.kingmon.project.webservice.bzdz.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.geo.builders.PolygonBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.webservice.ServiceAuthUtil;
import com.kingmon.project.webservice.bzdz.BzdzQuery;
import com.kingmon.project.webservice.bzdz.BzdzWebService;
import com.kingmon.project.webservice.common.token.AuthToken;

@WebService(endpointInterface = "com.kingmon.project.webservice.bzdz.BzdzWebService")
public class BzdzWebServiceImpl implements BzdzWebService {
	@Autowired
	private ElasticService elasticService;
	@Resource
	private WebServiceContext wsContext;

	@Override
	public String jybzdzSearch(String token,BzdzQuery bzdzQuery, int pageindex, int pageSize) {

		Map<String, Object> resp = new HashMap<String, Object>();
		resp=ServiceAuthUtil.validateAuthToken(token);
		if(!"0".equals(resp.get("result"))){
			resp.put("data", new ArrayList<Object>());
			resp.put("sumNum", "0");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("page", pageindex + "");
		params.put("rows", pageSize + "");
		PaginationUtil.initElasticPageAndSort(params);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
		if (bzdzQuery != null) {
			
			String dzmc = bzdzQuery.getKeyword();
			if (dzmc != null && !dzmc.isEmpty()) {
				boolQueryBuilder.must(matchQuery("DZMC", dzmc));
			}

			String jlxdzbm = bzdzQuery.getJlx();
			if (jlxdzbm != null && !jlxdzbm.isEmpty()) {
				boolQueryBuilder.must(termQuery("SSJLXXQ_DZBM", jlxdzbm));
			}

			String xzqhdm = bzdzQuery.getXzqh();
			if (xzqhdm != null && !xzqhdm.isEmpty()) {
				boolQueryBuilder.must(termQuery("SSZDYJXZQY_DZBM", xzqhdm));
			}
			
			String bjzbz = bzdzQuery.getBjzbz();
			if (bjzbz != null && !bjzbz.isEmpty()) {
				PolygonBuilder poly = ElasticUtil.pgisPolygonStringToElastic(bjzbz);
				if(poly!=null){
					boolQueryBuilder.must(geoShapeQuery("LOCATION",poly ));
				}
			}
			
		}
		
		
		SearchResponse searchResponse = elasticService.getClient().prepareSearch("psam")
				.setTypes("mlph").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder).addSort("YWLSH", SortOrder.ASC)
				.setFrom(NumberUtils.toInt(params.get("from")))
				.setSize(NumberUtils.toInt(params.get("size")))
				.addFields("YWLSH","DZMC","MLPH","JZWMC","ZXDHZB","ZXDZZB")
				.execute().actionGet();
		
		if(searchResponse==null||searchResponse.getHits()==null||searchResponse.getHits().getHits()==null||searchResponse.getHits().getHits().length==0){
			resp.put("result", "0");
//			resp.put("msg", "");
			resp.put("data",Lists.newArrayList());
			resp.put("sumNum", 0);
			return JSON.toJSONString(resp);
		}
		
		SearchHits hits = searchResponse.getHits();
		List<Map<String, Object>> list =Lists.newArrayList();
		Iterator<SearchHit> it  = hits.iterator();
		if(!it.hasNext()){
			resp.put("result", "0");
//			resp.put("msg", "");
			resp.put("data",Lists.newArrayList());
			resp.put("sumNum", 0);
			return JSON.toJSONString(resp);
		}
		while(it.hasNext()){
			SearchHit hit=it.next();
			Map<String, SearchHitField> map=hit.getFields();
			if(map==null||map.isEmpty()){
				resp.put("result", "0");
//				resp.put("msg", "");
				resp.put("data",Lists.newArrayList());
				resp.put("sumNum", 0);
				return JSON.toJSONString(resp);
			}
			//.addFields("YWLSH","DZMC","MLPH","JZWMC","ZXDHZB","ZXDZZB")
			SearchHitField field_YWLSH=map.get("YWLSH");
			SearchHitField field_DZMC=map.get("DZMC");
			SearchHitField field_MLPH=map.get("MLPH");
			SearchHitField field_JZWMC=map.get("JZWMC");
			SearchHitField field_ZXDHZB=map.get("ZXDHZB");
			SearchHitField field_ZXDZZB=map.get("ZXDZZB");
			 Map<String, Object>  mapRes =Maps.newHashMap();
			 mapRes.put("YWLSH", field_YWLSH==null?"":field_YWLSH.getValue());
			 mapRes.put("DZMC", field_DZMC==null?"":field_DZMC.getValue());
			 mapRes.put("MLPH", field_MLPH==null?"":field_MLPH.getValue());
			 mapRes.put("JZWMC", field_JZWMC==null?"":field_JZWMC.getValue());
			 mapRes.put("ZXDHZB", field_ZXDHZB==null?"":field_ZXDHZB.getValue());
			 mapRes.put("ZXDZZB", field_ZXDZZB==null?"":field_ZXDZZB.getValue());
			 list.add(mapRes);
		}
		resp.put("result", "0");
//		resp.put("msg", "");
		resp.put("data", list);
		resp.put("sumNum", hits.getTotalHits());
		return JSON.toJSONString(resp);
	}
	
	@Override
	public String bzdzSearchByPk(String token,String dzbm) {
		Map<String, Object> resp = new HashMap<String, Object>();
		resp=ServiceAuthUtil.validateAuthToken(token);
		if(!"0".equals(resp.get("result"))){
			resp.put("data", new ArrayList<Object>());
			resp.put("sumNum", "0");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
		}
		
		if(StringUtils.isEmpty(dzbm)){
			resp.put("result", "7");
			resp.put("msg", "参数为空");
			resp.put("data", new ArrayList<Object>());
			resp.put("sumNum", 0);
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
		}
		GetResponse response = elasticService.getClient().prepareGet("psam", "mlph", dzbm).execute().actionGet();
		Map<String, Object> map= response.getSource();
//		resp.put("msg", "");
		resp.put("data", map);
		resp.put("sumNum",(map==null||map.isEmpty())?0:1);
		return JSON.toJSONString(resp,KConstants.serializerFeatures);
	}

	

}
