package com.kingmon;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.kingmon.project.elastic.model.Document;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.elastic.model.Polygon;
import com.kingmon.project.elastic.service.ElasticServiceRest;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/applicationContext.xml" })
public class SearchRestTest {
	@Autowired
	private ElasticServiceRest elasticServiceRest;
	
	
	@Test
	public void testGetByID() {
		System.out.println(elasticServiceRest.get("6113fa14-ce35-4fe4-aaed-d807c0f8000d"));
	}

	@Test
	public void testSearch() {
		//elasticServiceRest.search( 0, 10, JSON.parseObject("{\"query\": {\"match\": {\"address\": \"济\"}}}"));
	}
	
	@Test
	public void testSearch2() {
		elasticServiceRest.searchByMatch(0, 10, "jzwmc", "济南","","");
	}

	@Test
	public void testSearchByPinyin() {
		//{"_shards":{"total":10,"failed":0,"successful":10},"hits":{"hits":[{"_index":"psam","_type":"psam","_source":{"organ":{"pcs":"37010203","fj":"370102","jwq":"3701020304","sj":"3701"},"dzmc":"南辛庄西路337号","jzwmc":"山东省济南市市中区七贤街道办事处南辛庄西路337号","location":"36.96121674,115.35322187","type":"JZW","status":"1"},"_id":"6113fa14-ce35-4fe4-aaed-d807c0f8000d","_score":1.287682},{"_index":"psam","_type":"psam","_source":{"organ":{"pcs":"37010203","fj":"370102","jwq":"3701020304","sj":"3701"},"jzwmc":"山东省济南市市中区七贤街道办事处南辛庄西路336号","dzmc":"南辛庄西路336号","location":"36.96121674,115.35322187","type":"JZW"},"_id":"1","_score":1.0}],"total":2,"max_score":1.287682},"took":3,"timed_out":false}

		System.out.println(elasticServiceRest.searchByMatch(0, 10, "jzwmc.py", "jinan","",""));
	}

	@Test
	public void testUpdate() throws Throwable {
		Document doc = new Document();
		doc.setLocation("36.96121674,115.35322187");
		doc.setJzwmc("山东省济南市市中区七贤街道办事处南辛庄西路337号");
		doc.setDzmc("南辛庄西路337号");
		doc.setType("JZW");
		doc.setStatus("1");
		
		doc.setSj("3701").setFj("370102").setPcs("37010203").setJwq("3701020304");
		doc.setId("6113fa14-ce35-4fe4-aaed-d807c0f8000d");
		System.out.println(JSON.toJSONString(doc));
		
		elasticServiceRest.update(doc);
	}

	@Test
	public void testDelete() throws Throwable {
		elasticServiceRest.delete("6113fa14-ce35-4fe4-aaed-d807c0f8000d");
	}

	
	
	
	
	
	@Test
	public void testAdd() throws Throwable {
		
		Document doc = new Document();
		doc.setLocation("36.96121674,115.35322187");
		doc.setJzwmc("山东省济南市市中区七贤街道办事处南辛庄西路336号");
		doc.setDzmc("南辛庄西路336号");
		doc.setType("JZW");
		doc.setStatus("1");
		doc.setSj("3701").setFj("370102").setPcs("37010203").setJwq("3701020304");
		doc.setId("6113fa14-ce35-4fe4-aaed-d807c0f8000d");
		System.out.println(JSON.toJSONString(doc));
		elasticServiceRest.add(doc);

	}

	@Test
	public void testSearchByBoundingBox() throws Throwable {
		GeoPoint topleft = new GeoPoint(33, 115);
		GeoPoint bottomright = new GeoPoint(31, 119);
		System.out.println( elasticServiceRest.searchByRect(0, 10, "jzwmc", "济南", topleft, bottomright,"",""));
	}
	@Test
	public void testSearchByCircle() throws Throwable {
		GeoPoint center = new GeoPoint(33, 115);
		System.out.println(elasticServiceRest.searchByCircle(0, 10, "jzwmc", "济南",  10000, center,"",""));
	}
	
	
	@Test
	public void testSearchByPolygon() throws Throwable {
		GeoPoint topleft = new GeoPoint(33, 115);
		GeoPoint bottomright = new GeoPoint(31, 119);
		GeoPoint leftbot =  new GeoPoint(31, 115);
		GeoPoint start = new GeoPoint(33, 115);
		
		Polygon polygon = new Polygon(Arrays.asList(topleft,leftbot,bottomright,start));
		
		System.out.println(elasticServiceRest.searchByPolygon(0, 10, "jzwmc", "济南", polygon,"",""));
	}
	
}
