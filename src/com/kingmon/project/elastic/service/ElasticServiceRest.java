package com.kingmon.project.elastic.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingmon.project.elastic.model.Document;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.elastic.model.Json;
import com.kingmon.project.elastic.model.Polygon;

@Service
public class ElasticServiceRest {
	@Autowired
	private RestTemplate restTemplate;
	@Value("${elastic.url}")
	private String elasticUrl;

	private HttpHeaders headers;

	@PostConstruct
	public void init() {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	}

	/**
	 * @param from （用于分页）
	 * @param size （用于分页）
	 * @param field 字段名称
	 * @param val  匹配内容
	 * @param levelName sj,fj,pcs,jwq
	 * @param levelVal 370001
	 * @return
	 */
	public JSONObject searchByMatch(long from, long size, String field, String val,String levelName,String levelVal) {
		Json query = new Json().add("query", new Json().add("match", new Json(field, val)));
		return search(from, size, query);

	}

	/**
	 * @param from
	 * @param size
	 * @param field
	 * @param val
	 * @param radius 半径
	 * @param point  中心点
	 * @param levelName sj,fj,pcs,jwq
	 * @param levelVal 370001
	 * @return
	 */
	public JSONObject searchByCircle(long from, long size, String field, String val, double radius, GeoPoint point,String levelName,String levelVal) {
		Json filtered = new Json().add(
				"filter",
				new Json().add("geo_distance",
						new Json().add("distance", radius).add("location", new Json().add("lat", point.getLat()).add("lon", point.getLon()))));
		if (field != null && val != null) {
			filtered.add("query", new Json().add("match", new Json(field, val)));
		}
		Json query = new Json().add("query", new Json().add("filtered", filtered));

		return search(from, size, query);
	}

	/**
	 * @param from 从第from条返回（用于分页）
	 * @param size 返回条数（用于分页）
	 * @param field 字段名称
	 * @param val 需要匹配的内容
	 * @param topleft 矩形左上点
	 * @param bottomright 矩形右下点
	 * @param levelName sj,fj,pcs,jwq
	 * @param levelVal 370001
	 * @return
	 */
	public JSONObject searchByRect(long from, long size, String field, String val, GeoPoint topleft, GeoPoint bottomright,String levelName,String levelVal) {
		Json filtered = new Json().add("filter", new Json().add(
				"geo_bounding_box",
				new Json().add("location", new Json().add("top_left", new Json().add("lat", topleft.getLat()).add("lon", topleft.getLon())).add(
						"bottom_right", new Json().add("lat", bottomright.getLat()).add("lon", bottomright.getLon())))));
		if (field != null && val != null) {
			filtered.add("query", new Json().add("match", new Json(field, val)));
		}
		Json query = new Json().add("query", new Json().add("filtered", filtered));

		return search(from, size, query);
	}
	
	/**
	 * @param from
	 * @param size
	 * @param field
	 * @param val
	 * @param polygon 多边形对象
	 * @param levelName sj,fj,pcs,jwq
	 * @param levelVal 370001
	 * @return
	 */
	public JSONObject searchByPolygon(long from, long size, String field, String val, Polygon polygon,String levelName,String levelVal) {
		Json filtered = new Json().add("filter", new Json().add("geo_polygon",new Json().add("location", new Json().add("points", polygon.getPoints()))));
		if (field != null && val != null) {
			filtered.add("query", new Json().add("match", new Json(field, val)));
		}
		Json query = new Json().add("query", new Json().add("filtered", filtered));

		return search(from, size, query);
	}
	

	/**
	 * @param from
	 * @param size
	 * @param query 符合elastic语法的 json obj 
	 * @return
	 */
	public JSONObject search(long from, long size, JSONObject query) {
		HttpEntity<String> req = new HttpEntity<String>(query.toJSONString(), headers);
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("from", from + "");
		vars.put("size", size + "");
		ResponseEntity<String> entity = restTemplate.postForEntity(elasticUrl + "_search?from={from}&size={size}", req, String.class, vars);
		return JSON.parseObject(entity.getBody());
	}

	/**
	 * 从索引中删除
	 * @param id
	 */
	public void delete(String id) {
		restTemplate.delete(elasticUrl + id);
	}

	/**
	 * 根据doc id取索引中的doc
	 * @param id
	 * @return
	 */
	public JSONObject get(String id) {
		ResponseEntity<String> entity = restTemplate.getForEntity(elasticUrl + id + "/_source", String.class);
		return JSON.parseObject(entity.getBody());
	}

	/**
	 *  加入到elastic 索引
	 * @param doc
	 * @return
	 */
	public ResponseEntity<String> add(Document doc) {
		if (doc == null) {
			return null;
		}
		String id = doc.getId();
		if (id == null || id.isEmpty()) {
			throw new RuntimeException("ID不能为空");
		}
		HttpEntity<String> req = new HttpEntity<String>(JSON.toJSONString(doc), headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(elasticUrl + id, HttpMethod.PUT, req, String.class);
		return responseEntity;
	}

	/**
	 * 修改索引中的文档
	 * @param doc
	 * @return
	 */
	public ResponseEntity<String> update(Document doc) {
		return this.add(doc);
	}

}
