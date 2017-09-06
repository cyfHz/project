/*package com.kingmon.project.elastic.service;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.kingmon.base.common.KConstants;
import com.kingmon.project.elastic.util.ElasticTypes;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
import com.kingmon.project.psam.mlph.model.Mlph;

@Service
public class SynchronizeService {
	@Autowired
	private ElasticService elasticService;
	
	@Inject
	private JzwjbxxMapper jzwjbxxMapper;

	@Inject
	private MlphMapper mlphMapper;
	@Inject
	private JwqMapper jwqMapper;
	
	public Object synchronizeElastic(String type){
		if("mlph".equals(type)){
			return synchronizeMlph();
		}else if("jzwjbxx".equals(type)){
			 synchronizeJzwjbxx();
		}else if("jwq".equals(type)){
			synchronizeJwq();
		}
		return null;
	}
	
	private long synchronizeMlph(){
		int bulkSize=1000;
		Map<String,String> params = new HashMap<String, String>();
		params.put("sort", "YWLSH");
		params.put("order", "asc");

		long total = mlphMapper.mlphListCount_for_es_index(params);
		
		if(total<1){
			return total ;
		}
		for(long i=0;i<total;i+=bulkSize){
			params.put("pagestart", i+"");
			params.put("pageend", (i+bulkSize)+"");
			params.put("sort", "YWLSH");
			params.put("order", "asc");
			params.put("IS_INDEXED", "0");
			//params.put("CREATE_TIME", "0");
			i++;
			List<Mlph> xzqhList = mlphMapper.mlphList_for_es_index(params);
			List<String> ywlshList=Lists.newArrayList();
			for(Mlph mlph : xzqhList){
				elasticService.indexDocument(ElasticTypes.MLPH, (String) params.get("YWLSH"), ElasticUtil.toDocument(mlph));
				ywlshList.add(mlph.getYwlsh());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ywlshList",ywlshList);
			map.put("is_indexed", "1");
			jzwjbxxMapper.batchCancellation(map);
		}
		return total ;
	
	}
	private long synchronizeJzwjbxx(){
		int bulkSize=1000;
		Map<String,String> params = new HashMap<String, String>();
		params.put("sort", "YWLSH");
		params.put("order", "asc");
		params.put("IS_INDEXED", "0");
		long total = jzwjbxxMapper.jzwjbxxListCount_for_es_index(params);
		
		if(total<1){
			return total ;
		}
		for(long i=0;i<total;i+=bulkSize){
			params.put("pagestart", i+"");
			params.put("pageend", (i+bulkSize)+"");
			params.put("sort", "DZBM");
			params.put("order", "asc");
			params.put("IS_INDEXED", "0");
			//params.put("CREATE_TIME", "0");
			i++;
			List<Jzwjbxx> jzwjbxxList = jzwjbxxMapper.jzwjbxxList_for_es_index(params);
			List<String> dzbmList=Lists.newArrayList();
 			for(Jzwjbxx jzwjbxx : jzwjbxxList){
				elasticService.indexDocument(ElasticTypes.JZWJBXX, (String) params.get("DZBM"), ElasticUtil.toDocument(jzwjbxx));
				dzbmList.add(jzwjbxx.getDzbm());
 			}
 			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dzbmList",dzbmList);
			map.put("is_indexed", "1");
			jzwjbxxMapper.batchUpdateIndex(map);	
		}
		return total ;
	}
	private long synchronizeJwq(){
		int bulkSize=1000;
		Map<String,String> params = new HashMap<String, String>();
		params.put("sort", "JWQID");
		params.put("order", "asc");
		params.put("IS_INDEXED", "0");
		long total = jwqMapper.jwqListCount_for_es_index(params);
		
		if(total<1){
			return total ;
		}
		for(long i=0;i<total;i+=bulkSize){
			params.put("pagestart", i+"");
			params.put("pageend", (i+bulkSize)+"");
			params.put("sort", "JWQID");
			params.put("order", "asc");
			params.put("IS_INDEXED", "0");
			//params.put("CREATE_TIME", "0");
			i++;
			List<Jwq> jwqList = jwqMapper.jwqList_for_es_index(params);
			List<String> jwqidList=Lists.newArrayList();
 			for(Jwq jwq : jwqList){
				elasticService.indexDocument(ElasticTypes.JWQ, (String) params.get("JWQID"), ElasticUtil.toDocument(jwq));
				jwqidList.add(jwq.getJwqid());
 			}
 			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("jwqidList",jwqidList);
			map.put("is_indexed", "1");
			jwqMapper.batchUpdateIndex(map);	
		}
		return total ;
	}
}
*/