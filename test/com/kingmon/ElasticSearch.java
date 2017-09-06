package com.kingmon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
import com.kingmon.project.psam.mlph.service.MlphService;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class ElasticSearch {
	@Autowired
	private ElasticService elasticService;
	private final static int PAGESIZE = 5000;
	
	@Inject
	private JzwjbxxMapper jzwjbxxMapper;
	
	@Inject
	private MlphMapper mlphMapper;
	@Autowired
	private MlphService mlphService;
	
	
	@Test public void test_temp (){
		Map<String,String> params = new HashMap<String,String>();
		params.put("ZBISNULL", "1");
		//ZBISNULL
		DataSet mlphList = mlphService.mlphList(params);
	}
	
	@Test
	public void import_Mlph() throws Throwable{
		
		Map<String,String> params = new HashMap<String, String>();
		long total = mlphMapper.mlphListCount(params);
		
		
		if(total<1){
			return;
		}
		BulkProcessor bulkProcessor = BulkProcessor.builder(elasticService.getClient(), new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				System.out.println("beforeBulk  " + request.numberOfActions() );
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				System.out.println("导入 " + request.numberOfActions() + " 条记录");
				System.out.println(response.hasFailures()+"  "+response.getTookInMillis());
				System.out.println(response.buildFailureMessage());
			}
			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				
				failure.printStackTrace();
			}
		}).setBulkActions(1000).setFlushInterval(TimeValue.timeValueSeconds(20)).setConcurrentRequests(0).build();
		
		for(long i=11750000;i<total;i+=PAGESIZE){
			params.put("pagestart", (i+1)+"");
			params.put("pageend", i+PAGESIZE+"");
			
			List<Map<String, Object>> mlphList = mlphMapper.mlphList(params);
			
			for (int j = 0; j<mlphList.size(); j++) {
				Map<String, Object> map = mlphList.get(j);
				IndexRequest indexRequest = new IndexRequest("psam", "mlph", String.valueOf(map.get("YWLSH")));
				indexRequest.source(ElasticUtil.mlphMapToDocument(mlphList.get(j)));
				bulkProcessor.add(indexRequest);
			}
			System.out.println(mlphList.size()+"条记录加入bulk-processor");
		}
		bulkProcessor.flush();
		bulkProcessor.awaitClose(50, TimeUnit.SECONDS);
		
		
		//elasticService.indexDocument("mlph", mlph.getYwlsh(), document);
	}
	
	
	@Test
	public void import_Jzwjbxx() throws Throwable{
		
		Map<String,String> params = new HashMap<String, String>();
		long total = jzwjbxxMapper.selectJzwjbxxCount(params);
		
		
		if(total<1){
			return;
		}
		BulkProcessor bulkProcessor = BulkProcessor.builder(elasticService.getClient(), new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				System.out.println("beforeBulk  " + request.numberOfActions() );
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				System.out.println("导入 " + request.numberOfActions() + " 条记录");
				System.out.println(response.hasFailures()+"  "+response.getTookInMillis());
				System.out.println(response.buildFailureMessage());
			}
			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				failure.printStackTrace();
			}
		}).setBulkActions(1000).setFlushInterval(TimeValue.timeValueSeconds(20)).setConcurrentRequests(0).build();
		
		for(long i=8829251;i<total;i+=PAGESIZE){
			params.put("pagestart", (i+1)+"");
			params.put("pageend", i+PAGESIZE+"");
			
			List<Map<String, Object>> jzwjbxxList = jzwjbxxMapper.selectJzwjbxxList(params);
			
			for (int j = 0; j<jzwjbxxList.size(); j++) {
				Map<String, Object> map = jzwjbxxList.get(j);
				IndexRequest indexRequest = new IndexRequest("psam", "jzwjbxx", String.valueOf(map.get("DZBM")));
				indexRequest.source(ElasticUtil.jzwMapToDocument(jzwjbxxList.get(j)));
				bulkProcessor.add(indexRequest);
			}
			System.out.println(jzwjbxxList.size()+"条记录加入bulk-processor");
		}
		bulkProcessor.flush();
		bulkProcessor.awaitClose(50, TimeUnit.SECONDS);
	}
	
	
	@Test public void test() throws Throwable{
		Map<String,String> params = new HashMap<String, String>();
		params.put("pagestart", 0+"");
		params.put("pageend", 50+"");
		params.put("sort", "YWLSH");
		params.put("order", "ASC");
		List<Map<String, Object>> mlphList = mlphMapper.mlphList(params);
		System.out.println(mlphList);
		//elasticService.indexDocument("mlph", "371626307039827200", ElasticUtil.mlphMapToDocument(mlphList.get(0)));
	}
	
	
	
	
}
