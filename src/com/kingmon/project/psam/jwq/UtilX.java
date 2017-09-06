package com.kingmon.project.psam.jwq;

import static org.elasticsearch.index.query.QueryBuilders.geoShapeQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.project.elastic.service.ElasticService;

public class UtilX {
	public static String validatePolygon(String bjzbz){
		if(!StringUtils.hasText(bjzbz)){
			return "边界值为空";
		}
		String[] array=bjzbz.split(";");
		for(String strX : array){
			if(strX==null||strX.isEmpty()){
				return "边界值不符合规范";
			}
			String[] pointArray=strX.split(",");
			if(pointArray==null||pointArray.length<6){
				return "边界值不符合规范";
			}
			for(String v:pointArray){
				try{
					Double.valueOf(v);
				}catch(Exception e){
					return "边界值不符合规范";
				}
			}
			try{
				Double lon1=Double.valueOf(pointArray[0]);
				//Double lat1=Double.valueOf(pointArray[1]);
				Double lon2=Double.valueOf(pointArray[2]);
				//Double lat2=Double.valueOf(pointArray[4]);
				if(lon1>lon2){
					//return "边界值未按照顺时针选取";
				}
			}catch(Exception e){
				return "边界值不符合规范";
			}
			
		}
		return null;
	}
	public static void main(String[] sd){
		String xx="117.15002,36.7076,117.15897,36.64487,117.2641,36.64387,117.29074,36.68057,117.28671,36.71704,117.15002,36.7076,117.15002,36.7076;";
		String xxxx="117.15002,36.7076,117.15897,36.64487,117.2641,36.64387,117.29074,36.68057,117.28671,36.71704,117.15002,36.7076,117.15002,36.7076";
		String xxx= removeRepeatLastPoints(xx+";"+xxxx);
	 System.out.println(xxx);
	 System.out.println("-----");
	}
	public static String removeRepeatLastPoints(String bjzbz){
		if(bjzbz==null||bjzbz.isEmpty()){
			return bjzbz;
		}
		//117.15002,36.7076,117.15897,36.64487,117.2641,36.64387,117.29074,36.68057,117.28671,36.71704,117.15002,36.7076,117.15002,36.7076;
		String[] array=bjzbz.split(";");
		StringBuffer sb=new StringBuffer();
		for(String strX : array){
			if(strX==null||strX.isEmpty()){
				continue;
			}
			String[] pointArray=strX.split(",");
			String lastLon=pointArray[pointArray.length-2];
			String lastLat=pointArray[pointArray.length-1];
			String lastPoint=lastLon+","+lastLat;
			
			List<String> list = Lists.newArrayList();
			for(int i=0;i<pointArray.length-1;i+=2){
				list.add(pointArray[i]+","+pointArray[i+1]);
			}
			int i=list.size()-1;
			while(list.get(i).equals(lastPoint)&&i>0){
				strX=SubApStrUtil.substringBeforeLast(strX, lastPoint);i--;
			}
			strX+=lastPoint;
			sb.append(strX).append(";");
		}
		return sb.toString();
	}
	
	public static String validatePolygonIgnorNull(String bjzbz){
		if(!StringUtils.hasText(bjzbz)){
			return null;
		}
		String[] array=bjzbz.split(";");
		for(String strX : array){
			if(strX==null||strX.isEmpty()){
				return "边界值不符合规范";
			}
			String[] pointArray=strX.split(",");
			if(pointArray==null||pointArray.length<6){
				return "边界值不符合规范";
			}
			for(String v:pointArray){
				try{
					Double.valueOf(v);
				}catch(Exception e){
					return "边界值不符合规范";
				}
			}
			try{
				Double lon1=Double.valueOf(pointArray[0]);
				//Double lat1=Double.valueOf(pointArray[1]);
				Double lon2=Double.valueOf(pointArray[2]);
				//Double lat2=Double.valueOf(pointArray[4]);
				if(lon1>lon2){
					return "边界值未按照顺时针选取";
				}
			}catch(Exception e){
				return "边界值不符合规范";
			}
			
		}
		return null;
	}
	
	public static  String getRbm(String xzqhdm, List<String> listMap) {
		if (listMap == null || listMap.isEmpty()) {
			return xzqhdm + "001";
		}
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				for (int z = 1; z < 10; z++) {
					String str = xzqhdm + x + y + z;
					if (!listMap.contains(str)) {
						return str;
					}
				}
			}
		}
		return null;
	}
	
	public static List<Map<String, Object>> findJwqByPointFromElastic(ElasticService elasticService,double lat, double ln) {
		SearchResponse searchResponse = elasticService.getClient().prepareSearch("psam").setTypes("jwq")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(geoShapeQuery("BJZB", ShapeBuilder.newPoint(ln, lat))).setFrom(0).setSize(100).execute()
				.actionGet();
		if (searchResponse == null || searchResponse.getHits() == null || searchResponse.getHits().getHits() == null
				|| searchResponse.getHits().getHits().length == 0) {
			return null;
		}
		SearchHits hits = searchResponse.getHits();
		List<Map<String, Object>> resList = Lists.newArrayList();
		Iterator<SearchHit> it = hits.iterator();
		while (it.hasNext()) {
			Map<String, Object> resMap = Maps.newHashMap();

			Map<String, Object> map = it.next().getSource();
		if(map.get("SFYX")!=null&&map.get("SFYX").equals("1")){
			resMap.put("JWQID", map.get("JWQID"));
			resMap.put("JWQMC", map.get("JWQMC"));
			resMap.put("JWQBH", map.get("JWQBH"));
			resMap.put("SFYX", map.get("SFYX"));
			resList.add(resMap);
		 }
		}
		return resList;
	}
}
