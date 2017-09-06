package com.kingmon;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.geoShapeQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.elasticsearch.action.bulk.BulkItemResponse.Failure;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticTypes;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class TestImportJwq {
	@Autowired
	private ElasticService elasticService;
	
	@Inject
	private JwqMapper jwqMapper;
	
	
	@Test public void testClob(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("IS_INDEXED", "0");
		params.put("pagestart", 0 + "");
		params.put("pageend", 10 + "");
		 List<Map<String, Object>> jwqListElastic = jwqMapper.jwqListElastic(params);
		 for(Map<String, Object> map :jwqListElastic){
			 	Map<String, Object> map2 = ElasticUtil.jwqMapToDocument(map);
			 	System.out.println(map2.get("BJZB"));
		 }
		 System.out.println(jwqListElastic);
		
	}
	
	@Test public void testLine(){
		String line = "'968ab681-a98f-4d69-8d2f-84f8a5c99d79', '370683330000', '370683', '370683330011', '光安社区警务室', '辖区公安小区、华旗绿城、广场公寓、西北隅新村部分、新悦大酒店、莱州广场', 30.00, 1.00, 0.00, '1', '1', '2015-08-01', '', '', '', '', ''";
		String[] split = line.split(", ");
		
		for(int i= 0;i<split.length;i++){
			System.out.println("i :"+ i+"\t "+toNormal(split[i]));
			
		}
	}
	private String toNormal(String term){
		if(term==null||term.isEmpty()){
			return null;
		}else if(term.startsWith("'")&&term.endsWith("'")){
			return term.substring(1,term.length()-1);
		}
		return null;
	}
	//JWQID, PCSID, SJXZQYID, JWQBH, JWQMC, JWQJJ, JWQMJ, JWHSL, NCGQSL, JWQXZ, SFYX, QYRQ, SXRQ, XGSJ, BZ, BJZBZ, MOVESIGN
	@Test public void testReadFile(){
		BufferedReader br = null;
		File file = new File("D:/work/project/psam/test/jwq.sql");
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(", ");
				Jwq jwq = new Jwq();
				jwq.setJwqid(toNormal(split[0]));
				jwq.setPcsid(toNormal(split[1]));
				jwq.setSjxzqyid(toNormal(split[2]));
				jwq.setJwqbh(toNormal(split[3]));
				jwq.setJwqmc(toNormal(split[4]));
				jwq.setJwqjj(toNormal(split[5]));
				
				String mj = toNormal(split[6]);
				jwq.setJwqmj(mj==null? null: new BigDecimal(mj));
				String jwhsl = toNormal(split[7]);
				jwq.setJwhsl(jwhsl==null? null :new BigDecimal(jwhsl) );
				String ncgqsl = toNormal(split[6]);
				jwq.setNcgqsl(ncgqsl==null?null:new BigDecimal(ncgqsl));
				jwq.setJwqxz(toNormal(split[9]));
				jwq.setSfyx(toNormal(split[10]));
				jwq.setQyrq(toNormal(split[11]));
				jwq.setSxrq(toNormal(split[12]));
				jwq.setXgsj(toNormal(split[13]));
				jwq.setBz(toNormal(split[14]));
				jwq.setBjzbz(toNormal(split[15]));
				jwq.setMovesign(toNormal(split[16]));
				
				jwqMapper.insertSelective(jwq);
				
			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private final static int PAGESIZE = 1000;
	@Test
	public void import_Jwq() throws Throwable{
		
		Map<String,String> params = new HashMap<String, String>();
		long total = jwqMapper.jwqListElasticCount(params);
		
		
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
				/*response.forEach((item) -> {
					if(item.isFailed()){
						Failure failure = item.getFailure();
						System.out.println("item.getId(): "+item.getId());
						System.out.println("failure.getStatus(): "+failure.getStatus());
						System.out.println("failure.getMessage(): "+failure.getMessage());
					}
				});*/
			}
			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				
				failure.printStackTrace();
			}
		}).setBulkActions(1000).setFlushInterval(TimeValue.timeValueSeconds(20)).setConcurrentRequests(0).build();
		
		for(long i=0;i<total;i+=PAGESIZE){
			params.put("pagestart", (i+1)+"");
			params.put("pageend", i+PAGESIZE+"");
			
			List<Map<String, Object>> jwqList = jwqMapper.jwqListElastic(params);
			
			for (int j = 0; j<jwqList.size(); j++) {
				 Map<String, Object> map = jwqList.get(j);
				IndexRequest indexRequest = new IndexRequest("psam", "jwq", String.valueOf(map.get("JWQID")));
				indexRequest.source(ElasticUtil.jwqMapToDocument(map));
				bulkProcessor.add(indexRequest);
			}
			System.out.println(jwqList.size()+"条记录加入bulk-processor");
		}
		bulkProcessor.flush();
		bulkProcessor.awaitClose(50, TimeUnit.SECONDS);
		
	}
	
	@Test public void testJson(){
		Jwq jwq1 = new Jwq();
		Jwq jwq2 = new Jwq();
		
		
		jwq1.setBjzbz("117.60031,37.70542,117.60443,37.70439,117.60375,37.69829,117.61087,37.69804,117.61465,37.69958,117.61696,37.70027,117.62134,37.69915,117.62349,37.70198,117.62889,37.70044,117.63301,37.70164,117.6361,37.70147,117.63799,37.69975,117.64048,37.69735,117.64649,37.69683,117.64632,37.72387,117.61919,37.73305,117.61413,37.73048,117.61061,37.73108,117.6101,37.73014,117.60392,37.73005,117.6034,37.71812,117.60332,37.71417,117.60031,37.70542;117.62314,37.74541,117.60314,37.74456,117.60383,37.73855,117.62812,37.73983,117.63954,37.72619,117.62263,37.73168,117.61078,37.73091,117.60297,37.72962,117.62314,37.74541;117.62314,37.74541,117.60314,37.74456,117.60383,37.73855,117.62812,37.73983,117.63954,37.72619,117.62263,37.73168,117.61078,37.73091,117.60297,37.72962,117.62314,37.74541;117.60169,37.75983,117.60048,37.7564,117.60246,37.75185,117.60332,37.74619,117.60409,37.7376,117.60452,37.73202,117.60314,37.7273,117.60306,37.71494,117.6004,37.70507,117.6046,37.70421,117.60426,37.69889,117.60907,37.69786,117.61456,37.69872,117.61713,37.70027,117.6228,37.69906,117.62434,37.70284,117.6264,37.70112,117.62932,37.70061,117.63173,37.70164,117.63413,37.70164,117.63482,37.70164,117.63636,37.70044,117.63653,37.69906,117.63773,37.69924,117.63962,37.69649,117.6458,37.69649,117.64632,37.72396,117.63945,37.72567,117.62314,37.73134,117.61937,37.75005,117.60169,37.75983");
		
		jwq2.setBjzbz("113.3125,46.30078,121.875,44.17578,115.875,38.30078,107.25,37.48828,102.5,47.23828,113.3125,46.30078");
		
		
		System.out.println(JSON.toJSON(ElasticUtil.toDocument(jwq1)));
		System.out.println(JSON.toJSON(ElasticUtil.toDocument(jwq2)));
		
	}
	
	
	@Test public void testBjzbz(){
		BufferedReader br = null;
		File file = new File("D:/work/project/psam/test/jwq.sql");
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(", ");
				
				String bjzb = toNormal(split[15]);
				String [] polys = bjzb.split(";");
				for(String poly:polys){
					System.out.println(poly);
				}
			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	@Test public void testSearchJwq(){
		SearchResponse actionGet = elasticService.getClient().prepareSearch("psam").setTypes("jwq").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		.setQuery( geoShapeQuery("BJZB", ShapeBuilder.newPoint( 115.982128, 36.411653)) )
		.setFrom(0).setSize(20).execute()
		.actionGet();
		
		SearchHits hits = actionGet.getHits();
		System.out.println(hits.totalHits());
		hits.forEach(new Consumer<SearchHit>() {

			@Override
			public void accept(SearchHit t) {
				System.out.println(t.getSourceAsString());
			}
		});
	}
}
