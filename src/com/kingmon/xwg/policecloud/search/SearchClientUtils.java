package com.kingmon.xwg.policecloud.search;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class SearchClientUtils {
	private static final String SERVER_IP = "10.49.11.139"; 
	private static final String SERVER_IPS = "10.49.11.139";//10.48.144.55,10.48.144.54
	private static final String ELASTIC_NAME="elastic_psam_test";//elastic_psam
	private static final String SEARCH_INDEX="psam";
	private static Client client ;
	private static TransportClient transportClient;
	
	//此类整合时需要添加日志
	
			
	/**
	 * 此类整合时需要添加日志
	 * @param searchFrom  type，默认当成表名
	 * @param from  起点
	 * @param size  返回数量
	 */
	public static synchronized SearchResultBean queryDATA(String searchFrom,int from,int size,Map<String,String> params){
		//构造查询器，具体查询组合参考API
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
		String dzmc = params.get("dzmc");
		if(dzmc!=null && !dzmc.equals("")){
		 QueryBuilder querybuilder = QueryBuilders.matchPhraseQuery("DZMC", dzmc);
		 boolQueryBuilder.must(querybuilder);
		}
		String xzqh = params.get("xzqh");
		if(xzqh!=null && !xzqh.equals("")){
			 QueryBuilder prefixquerybuilder = QueryBuilders.prefixQuery("ZAGLSSJWZRQDM", xzqh);
			 boolQueryBuilder.must(prefixquerybuilder);
		}
		
		SearchRequestBuilder responseBuilder = getTransportClient()
				.prepareSearch(SEARCH_INDEX)//查询索引（psam） 类似数据库概念
				.setTypes(searchFrom)//查询type，类似表的概念
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		responseBuilder.setQuery(boolQueryBuilder);//查询构造器
		responseBuilder.setFrom(from).setSize(size);
		SearchResponse response =responseBuilder.execute().actionGet();
		
		SearchHits hits = response.getHits();
		if(hits==null || hits.getHits()==null || hits.getTotalHits()==0){
			//空返回
			return new SearchResultBean(null,0L,searchFrom);
		}
		SearchHit hitx=hits.getAt(0);
		String type=hitx.getType();
		//迭代器
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<SearchHit> it  = hits.iterator();
		while(it.hasNext()){
			 Map<String, Object>  map=it.next().getSource();
			 list.add(map);
		}
		return new SearchResultBean(list,hits.getTotalHits(),type);
	}
	
	
	/**
	 * 此类整合时需要添加日志
	 * @param searchFrom  type，默认当成表名
	 * @param from  起点
	 * @param size  返回数量
	 */
	public static synchronized SearchResultBean queryDATA(String searchFrom,int from,int size){
		//构造查询器，具体查询组合参考API
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
		
		SearchResponse response = getTransportClient()
				.prepareSearch(SEARCH_INDEX)//查询索引（psam） 类似数据库概念
				.setTypes(searchFrom)//查询type，类似表的概念
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder)//查询构造器
				.setFrom(from).setSize(size).execute()
				.actionGet();
		
		SearchHits hits = response.getHits();
		if(hits==null || hits.getHits()==null){
			//空返回
			return new SearchResultBean(null,0L,searchFrom);
		}
		SearchHit hitx=hits.getAt(0);
		String type=hitx.getType();
		//迭代器
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<SearchHit> it  = hits.iterator();
		while(it.hasNext()){
			 Map<String, Object>  map=it.next().getSource();
			 list.add(map);
		}
		return new SearchResultBean(list,hits.getTotalHits(),type);
	}
	
	/** 单机版允许测试  自定义查询、新建 */
	public static synchronized Client getClient() {
		if(client==null){
			//单机版：集群默认名称   elasticsearch
			client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(SERVER_IP, 9300));
		}
		return client;
	}


	private static synchronized TransportClient getTransportClient() {
		if(transportClient==null){
			//集群版：集群默认名称 修改过。需要使用Settings设置
			Settings setting = ImmutableSettings.settingsBuilder().put("cluster.name", ELASTIC_NAME).build();
			transportClient = new TransportClient(setting);
			//循环添加 IP
			//transportClient.addTransportAddress(new InetSocketTransportAddress(SERVER_IP, 9300));
			String[] ips = SERVER_IPS.split(",");
			for (String ip : ips) {
				transportClient.addTransportAddress(new InetSocketTransportAddress(ip, 9300));
			}
		}
		return transportClient;
	}

}