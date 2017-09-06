package com.kingmon;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticTypes;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.mlph.dao.MlphMapper;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class SearchTest {
	@Autowired
	private ElasticService elasticService;
	
	@Inject
	private XzqhMapper xzqhMapper;
	
	@Inject
	private JzwjbxxMapper jzwjbxxMapper;
	
	@Inject
	private MlphMapper mlphMapper;

	@Test
	public void testUpdate() throws Throwable {
		GetResponse response = elasticService.get(ElasticTypes.MLPH, "1");
		System.out.println(response.getSourceAsString());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("address", "山东省济南市市中区七贤街道办事处南辛庄西路" + Math.random() + "号");
		elasticService.updateDocument(ElasticTypes.MLPH, "1", map);

		GetResponse response2 = elasticService.get(ElasticTypes.MLPH, "1");
		System.out.println(response2.getSourceAsString());
	}

	@Test
	public void testDelete() throws Throwable {
		elasticService.deleteDocument( ElasticTypes.MLPH, "2");
	}

	@Test
	public void testAdd() throws Throwable {

		String dzbm = UUID.randomUUID().toString();
		Mlph mlph = new Mlph();
		mlph.setDzmc("山东省济南市市中区七贤街道办事处南辛庄西路336号");
		mlph.setZxdzzb(new BigDecimal(36.96121674));
		mlph.setZxdhzb(new BigDecimal(115.35322187));
		mlph.setJzwmc("济南大学西校区");
		mlph.setDzbm(dzbm);
		
		
		
		System.out.println(dzbm);
		elasticService.indexDocument(ElasticTypes.MLPH, mlph.getDzbm(), ElasticUtil.toDocument(mlph));

	}

	@Test
	public void testSearchByBoundingBox() throws Throwable {
		SearchResponse response = elasticService.getClient().prepareSearch("psam").setTypes("psam").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(matchQuery("address.py", "jinan"))
				.setPostFilter(FilterBuilders.geoBoundingBoxFilter("location").topLeft(40, 110).bottomRight(30, 120))
				.setFrom(0).setSize(20).execute()
				.actionGet();
		System.out.println(response);
	}
	@Test
	public void testSearchByCircle() throws Throwable {
		SearchResponse response = elasticService.getClient().prepareSearch("psam").setTypes("psam").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(matchQuery("address.py", "jinan"))
				.setPostFilter(FilterBuilders.geoDistanceFilter("location").point(36.9612,115.3532).distance("1000m")).setFrom(0).setSize(20).execute()
				.actionGet();
		System.out.println(response);
	}
	
	
	@Test
	public void testSearchByPolygon() throws Throwable {
		SearchResponse response = elasticService.getClient().prepareSearch("psam").setTypes("psam").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(matchQuery("address.py", "jinan"))
				.setPostFilter(FilterBuilders.geoPolygonFilter("location").addPoint(40, 115).addPoint(30, 110).addPoint(30, 120)).setFrom(0).setSize(20).execute()
				.actionGet();
		System.out.println(response);
	}
	
	@Test
	public void testImport() throws Throwable{
		Map<String,String> params = new HashMap<String, String>();
		
		long total = xzqhMapper.xzqhListCount(params);
		
		if(total<1){
			return;
		}
		BulkProcessor bulkProcessor = BulkProcessor.builder(elasticService.getClient(), new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				System.out.println("导入 " + request.numberOfActions() + " 条记录");
			}
			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				failure.printStackTrace();
			}
		}).setBulkActions(5000).setConcurrentRequests(2).build();
		
		for(long i=0;i<total;i++){
			params.put("pagestart", i+"");
			params.put("pageend", (i+1000)+"");
			List<Map<String, Object>> xzqhList = xzqhMapper.xzqhList(params);
			i+=1000;
			
			for (int j = 0; j<xzqhList.size(); j++) {
				Map<String, Object> map = xzqhList.get(j);
				IndexRequest indexRequest = new IndexRequest("psam", "psam", String.valueOf(map.get("DZBM")));
				indexRequest.source(jsonBuilder().startObject()
						.field("address",map.get("XZQHMC"))
						//.field("location", "")
						.field("fullname", map.get("XZQHMC"))
						.field("shortname", map.get("BMJC"))
						.field("zoneCode", "")
						.field("type", "XZQH")
						.field("fid", map.get("SJXZQY_DZBM"))
						.field("status", map.get("CXBJ"))
						.endObject());
				bulkProcessor.add(indexRequest);
			}
			System.out.println(xzqhList.size()+"条记录加入bulk-processor");
		}
		bulkProcessor.flush();
		bulkProcessor.awaitClose(50, TimeUnit.SECONDS);
	}
	
	private final static int PAGESIZE = 100000;
	
	@Test public void testImportJZW() throws Throwable{
		
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
			}
			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				
				failure.printStackTrace();
			}
		}).setBulkActions(1000).setFlushInterval(TimeValue.timeValueSeconds(20)).setConcurrentRequests(0).build();
		
		for(long i=1;i<total;i++){
			params.put("pagestart", ((i-1)*PAGESIZE+1)+"");
			params.put("pageend", i*PAGESIZE+"");
			List<Map<String, Object>> xzqhList = jzwjbxxMapper.selectJzwjbxxList(params);
			System.out.println("xzqhList.size() --- "+xzqhList.size());
			System.out.println("xzqhList.get(0) --- "+xzqhList.get(0));
			for (int j = 0; j<xzqhList.size(); j++) {
				Map<String, Object> map = xzqhList.get(j);
				String jwqdm  = (String) map.get("ZAGLSSJWZRQDM");
				IndexRequest indexRequest = new IndexRequest("psam", "psam", String.valueOf(map.get("DZBM")));
				XContentBuilder src = jsonBuilder().startObject()
						.field("jzwmc",map.get("JZWMC"))
						
						.field("dzmc", map.get("DZMC")==null?"":map.get("DZMC"))
						//TODO index out of range
						.field("sj", jwqdm==null? "":jwqdm.subSequence(0, 4))
						.field("fj", jwqdm==null? "":jwqdm.subSequence(0, 6))
						.field("pcs", jwqdm==null? "":jwqdm.subSequence(0, 10))
						.field("jwq", jwqdm)
						
						.field("type", "JZW")
						.field("status", 1);
						
						if(map.get("ZXDHZB")!=null&&map.get("ZXDZZB")!=null){
							src.field("location", map.get("ZXDZZB")+","+map.get("ZXDHZB"));
						}
						indexRequest.source(src.endObject());
				bulkProcessor.add(indexRequest);
			}
			System.out.println(xzqhList.size()+"条记录加入bulk-processor");
		}
		bulkProcessor.flush();
		bulkProcessor.awaitClose(50, TimeUnit.SECONDS);
	}
	
	
	
	@Test public void testImportMLPH() throws Throwable{
		Map<String,String> params = new HashMap<String, String>();
		long total = mlphMapper.mlphListCount(params);
		
		
		if(total<1){
			return;
		}
		BulkProcessor bulkProcessor = BulkProcessor.builder(elasticService.getClient(), new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				System.out.println("导入 " + request.numberOfActions() + " 条记录");
			}
			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				failure.printStackTrace();
			}
		}).setBulkActions(5000).setConcurrentRequests(2).build();
		
		for(long i=0;i<total;i++){
			params.put("pagestart", i+"");
			params.put("pageend", (i+1000)+"");
			List<Map<String, Object>> xzqhList = mlphMapper.mlphList(params);
			i+=1000;
			
			for (int j = 0; j<xzqhList.size(); j++) {
				Map<String, Object> map = xzqhList.get(j);
				if(map.get("ZXDHZB")==null&&map.get("ZXDZZB")==null){
					continue;
				}
				IndexRequest indexRequest = new IndexRequest("psam", "psam", String.valueOf(map.get("DZBM")));
				indexRequest.source(jsonBuilder().startObject()
						.field("jzwmc",map.get("JZWMC"))
						.field("location", map.get("ZXDZZB")+","+map.get("ZXDHZB"))
						.field("dzmc", map.get("DZMC"))
						
						.field("sj", map.get("SSSJ"))
						.field("fj", map.get("SSFJ"))
						.field("pcs", map.get("SSSJ"))
						.field("jwq", map.get("SJGSDWDM"))
						
						.field("type", "MLPH")
						.field("status", 1)
						.endObject());
				bulkProcessor.add(indexRequest);
			}
			System.out.println(xzqhList.size()+"条记录加入bulk-processor");
			
		}
		bulkProcessor.flush();
		bulkProcessor.awaitClose(50, TimeUnit.SECONDS);
	}
	
	
	
	
	@Test public void testRest(){
		RestTemplate rt = new RestTemplate();
		SearchResponse response = rt.getForObject("http://192.168.201.192:9200/psam/psam/1", SearchResponse.class);
		System.out.println(response);
	}
	
	
	@Test public void testNumber(){
		BigDecimal num = new BigDecimal(115.26565);
		System.out.println(num.doubleValue());
		System.out.println(num);
		
		System.out.println();
	}
	
	
}
