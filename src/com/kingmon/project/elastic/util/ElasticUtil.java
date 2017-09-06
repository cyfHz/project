package com.kingmon.project.elastic.util;

import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.sql.TIMESTAMP;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.geo.builders.PolygonBuilder;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.GeoPolygonFilterBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.data.DataSet;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.elastic.model.Polygon;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;
import com.kingmon.project.psam.mlph.model.Mlph;

public class ElasticUtil {

	public static Map<String, Object> toDocument(Jzwjbxx jzwjbxx) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (jzwjbxx != null) {
			map.put("DZBM", jzwjbxx.getDzbm());
			map.put("DZYSLXDM", jzwjbxx.getDzyslxdm());
			map.put("DZMC", jzwjbxx.getDzmc() == null ? "" : jzwjbxx.getDzmc());
			map.put("JZWMC",
					jzwjbxx.getJzwmc() == null ? "" : jzwjbxx.getJzwmc());
			map.put("BMJC", jzwjbxx.getBmjc());
			map.put("ZAGLSSJWZRQDM", jzwjbxx.getZaglssjwzrqdm());
			map.put("SSZDYJXZQY_DZBM", jzwjbxx.getSszdyjxzqy_dzbm());
			map.put("SSJLXXQ_DZBM", jzwjbxx.getSsjlxxq_dzbm());
			map.put("DZZCZBZ", jzwjbxx.getDzzczbz());
			map.put("DZZZYBS", jzwjbxx.getDzzzybs());
			map.put("GXSJ", jzwjbxx.getGxsj());
			map.put("QYRQ", jzwjbxx.getQyrq());
			map.put("TYRQ", jzwjbxx.getTyrq());
			map.put("DJDW_GAJGJGDM", jzwjbxx.getDjdw_gajgjgdm());
			map.put("DJDW_GAJGMC", jzwjbxx.getDjdw_gajgmc());
			map.put("DJR_XM", jzwjbxx.getDjr_xm());
			map.put("MOVESIGN", jzwjbxx.getMovesign());
			map.put("ZJF", jzwjbxx.getZjf());
			map.put("DJR", jzwjbxx.getDjr());
			map.put("DJDW", jzwjbxx.getDjdw());
			map.put("DJSJ", jzwjbxx.getDjsj());
			map.put("XGR", jzwjbxx.getXgr());
			map.put("XGDW", jzwjbxx.getXgdw());
			map.put("ENABLE", jzwjbxx.getEnable());
			map.put("ZAGLSSJWZRQMC", jzwjbxx.getZaglssjwzrqmc());
			map.put("DELTIME", jzwjbxx.getDeltime());
			map.put("IS_INDEXED", jzwjbxx.getIsIndexed());
			map.put("CREATE_TIME", jzwjbxx.getCreateTime());
			
			map.put("MAC_ADDRESS",jzwjbxx.getMacAddress());
			map.put("TF_CARD_NUM",jzwjbxx.getTfCardNum());
			map.put("IMEI_NUM",jzwjbxx.getImeiNum());
			map.put("SIM_NUM",jzwjbxx.getSimNum());

			if (jzwjbxx.getZxdhzb() != null) {
				map.put("ZXDHZB", jzwjbxx.getZxdhzb().doubleValue());
			}

			if (jzwjbxx.getZxdzzb() != null) {
				map.put("ZXDZZB", jzwjbxx.getZxdzzb().doubleValue());
			}

			if (jzwjbxx.getChildcount() != null) {
				map.put("CHILDCOUNT", jzwjbxx.getChildcount().longValue());
			}

			if (jzwjbxx.getZxdhzb() != null && jzwjbxx.getZxdzzb() != null) {
				map.put("LOCATION", toLocationString(jzwjbxx.getZxdzzb(),jzwjbxx.getZxdhzb()));
			}
		}

		return map;
	}

	public static Map<String, Object> toDocument(Mlph mlph) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (mlph != null) {
			map.put("YWLSH", mlph.getYwlsh());
			map.put("DZBM", mlph.getDzbm());
			map.put("DZYSLXDM", mlph.getDzyslxdm());
			map.put("DZMC", mlph.getDzmc());
			map.put("SSXQDM", mlph.getSsxqdm());
			map.put("QHNXXDZ", mlph.getQhnxxdz());
			map.put("SSJLXXQ_JLXXQMC",mlph.getSsjlxxq_jlxxqmc());
			map.put("SSJLXXQ_DZBM", mlph.getSsjlxxq_dzbm());
			map.put("SSJZW_DZBM", mlph.getSsjzw_dzbm());
			map.put("MLPH", mlph.getMlph());
			map.put("LSMLPBS", mlph.getLsmlpbs());
			map.put("BLDW_GAJGJGDM", mlph.getBldw_gajgjgdm());
			map.put("BLDW_GAJGMC", mlph.getBldw_gajgmc());
			map.put("BLR_XM", mlph.getBlr_xm());
			
			map.put("SJGSDWDM", mlph.getSjgsdwdm());
			map.put("SJGSDWMC", mlph.getSjgsdwmc());
			map.put("MOVESIGN", mlph.getMovesign());
			map.put("DJR", mlph.getDjr());
			map.put("DJDW", mlph.getDjdw());
			
			map.put("XGR", mlph.getXgr());
			map.put("XGDW", mlph.getXgdw());
			
			map.put("SQRID", mlph.getSqrid());
			map.put("DELTAG", mlph.getDeltag());
			map.put("DELUSER", mlph.getDeluser());
			
			map.put("SSZDYJXZQY_DZBM",mlph.getSszdyjxzqy_dzbm());
			map.put("JZWMC", mlph.getJzwmc());
			map.put("JWQBH", mlph.getJwqbh());
			map.put("JWQMC", mlph.getJwqmc());
			map.put("FROMBY", mlph.getFromby());
			map.put("SBID", mlph.getSbid());
			map.put("MLPHLXID", mlph.getMlphlxid());
			map.put("MLPHLXMC", mlph.getMlphlxmc());
			map.put("DRSJ", mlph.getDrsj());
			map.put("SSPCS", mlph.getSspcs());
			map.put("SSFJ", mlph.getSsfj());
			map.put("SSSJ", mlph.getSssj());
			map.put("SPZT", mlph.getSpzt());
			map.put("SHPZDM", mlph.getShpzdm());
			map.put("IS_INDEXED", mlph.getIsIndexed());
			
			
			map.put("DJSJ", mlph.getDjsj());
			map.put("GXSJ", mlph.getGxsj());
			map.put("DELTIME", mlph.getDeltime());
			map.put("BLSJ", mlph.getBlsj());
			map.put("CREATE_TIME", mlph.getCreateTime());

			map.put("MAC_ADDRESS",mlph.getMacAddress());
			map.put("TF_CARD_NUM",mlph.getTfCardNum());
			map.put("IMEI_NUM",mlph.getImeiNum());
			map.put("SIM_NUM",mlph.getSimNum());
			
			map.put("CXYY", mlph.getCxyy());
			
			if (mlph.getZxdhzb() != null) {
				map.put("ZXDHZB", mlph.getZxdhzb().doubleValue());
			}

			if (mlph.getZxdzzb() != null) {
				map.put("ZXDZZB", mlph.getZxdzzb().doubleValue());
			}

			if (mlph.getGpsx() != null) {
				map.put("GPSX", mlph.getGpsx().doubleValue());
			}

			if (mlph.getGpsy() != null) {
				map.put("GPSY", mlph.getGpsy().doubleValue());
			}

			if (mlph.getChildcount() != null) {
				map.put("CHILDCOUNT", mlph.getChildcount().longValue());
			}

			if (mlph.getZxdhzb() != null && mlph.getZxdzzb() != null) {
				map.put("LOCATION",toLocationString( mlph.getZxdzzb(),mlph.getZxdhzb()));
			}
		}
		return map;
	}
	
	
	
	public static Map<String, Object> mlphMapToDocument(Map<String, Object> mlph) {
		if(mlph!=null){
			if (mlph.get("ZXDHZB") != null && NumberUtils.isDigits(String.valueOf(mlph.get("ZXDHZB")))) {
				mlph.put("ZXDHZB", doubleValue(mlph.get("ZXDHZB")));
			}

			if (mlph.get("ZXDZZB") != null && NumberUtils.isDigits(String.valueOf(mlph.get("ZXDZZB")))) {
				mlph.put("ZXDZZB", doubleValue(mlph.get("ZXDZZB")));
			}

			if (mlph.get("GPSX") != null) {
				mlph.put("GPSX", doubleValue(mlph.get("GPSX")));
			}

			if (mlph.get("GPSY") != null) {
				mlph.put("GPSY", doubleValue(mlph.get("GPSY")));
			}

			if (mlph.get("CHILDCOUNT") != null) {
				mlph.put("CHILDCOUNT",longValue(mlph.get("CHILDCOUNT")));
			}

			if (mlph.get("ZXDHZB") != null && mlph.get("ZXDZZB") != null) {
				mlph.put("LOCATION",mlph.get("ZXDZZB")+","+mlph.get("ZXDHZB") );
			}
			if (mlph.get("CREATE_TIME") != null) {
				
				mlph.put("CREATE_TIME",time2Date(mlph.get("CREATE_TIME")));
			}
			mlph.remove("QR_CODE");
		}
		return mlph;
	}
	
	public static Map<String, Object> jzwMapToDocument(Map<String, Object> jzwjbxx) {
		if(jzwjbxx!=null){
			if (jzwjbxx.get("ZXDHZB") != null) {
				jzwjbxx.put("ZXDHZB", doubleValue(jzwjbxx.get("ZXDHZB")));
			}

			if (jzwjbxx.get("ZXDZZB") != null) {
				jzwjbxx.put("ZXDZZB", doubleValue(jzwjbxx.get("ZXDZZB")));
			}

			if (jzwjbxx.get("CHILDCOUNT") != null) {
				jzwjbxx.put("CHILDCOUNT", longValue(jzwjbxx.get("CHILDCOUNT")));
			}

			if (jzwjbxx.get("ZXDHZB") != null && jzwjbxx.get("ZXDZZB") != null) {
				jzwjbxx.put("LOCATION", jzwjbxx.get("ZXDZZB")+","+jzwjbxx.get("ZXDHZB"));
			}
			
			if (jzwjbxx.get("CREATE_TIME") != null) {
				jzwjbxx.put("CREATE_TIME",time2Date(jzwjbxx.get("CREATE_TIME")));
			}
			jzwjbxx.remove("QR_CODE");
		}
		return jzwjbxx;
	}
	
	private static Date time2Date(Object o){
		Date dateValue = null;
		try {
			dateValue = ((TIMESTAMP)o).dateValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dateValue;
	}
	
	public static String toLocationString(BigDecimal lat ,BigDecimal lon){
		return lat.doubleValue()+","+lon.doubleValue();
	}
	
	private static double doubleValue(Object obj){
		if(obj instanceof String){
			return new BigDecimal((String)obj).doubleValue();
		}else if(obj instanceof  BigDecimal){
			return ((BigDecimal)obj).doubleValue();
		}else{
			return NumberUtils.toDouble(String.valueOf(obj));
		}
		
	}
	private static double longValue(Object obj){
		return ((BigDecimal)obj).longValue();
	}

	public static DataSet searchResponse2Dataset(SearchResponse searchResponse) {
		DataSet ds  = DataSet.newDs();
		if(searchResponse==null||searchResponse.getHits()==null||searchResponse.getHits().getHits()==null||searchResponse.getHits().getHits().length==0){
			return  ds;
		}
		SearchHits hits = searchResponse.getHits();
		SearchHit hitx=hits.getAt(0);
		String type=hitx.getType();
		ds.setTotal(hits.getTotalHits());
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator<SearchHit> it  = hits.iterator();
		while(it.hasNext()){
			 Map<String, Object>  map=it.next().getSource();
			 map.put("_type", type);
			 list.add(map);
		}
		ds.setRows(list);
		return ds;
	}
	/**
	 * 
	 * @param searchResponse
	 * @param idName : YWLSH or DZBM
	 * @return
	 */
	public static List<String> searchResponseToIdStrList(SearchResponse searchResponse,String idName) {
		List<String> list =Lists.newArrayList();
		if(searchResponse==null||searchResponse.getHits()==null||searchResponse.getHits().getHits()==null||searchResponse.getHits().getHits().length==0){
			return  list;
		}
		SearchHits hits = searchResponse.getHits();
		SearchHit hitx=hits.getAt(0);
		//String type=hitx.getType();
		
		Iterator<SearchHit> it  = hits.iterator();
		while(it.hasNext()){
			SearchHit hit=it.next();
			String id=hit.getId();
			
//			Map<String, SearchHitField> fieldMap=hit.getFields();
//			SearchHitField field=fieldMap.get(idName);
//			String idx=field.getValue();
//			Map<String, Object>  map=Maps.newHashMap();
//			map.put(idName, idx);
			 list.add(id);
		}
		return list;
	}
	public static FilterBuilder addPolygonToFilter(Polygon p,
			GeoPolygonFilterBuilder geoPolygonFilter) {
		for(GeoPoint point: p.getPoints() ){
			geoPolygonFilter.addPoint(point.getLat(), point.getLon());
		}
		return null;
	}

	public static  Map<String, Object>  toDocument(Jwq jwq) {
		Map<String, Object> map = new HashMap<String, Object>();
		//JWQID, PCSID, SJXZQYID, JWQBH, JWQMC, JWQJJ, JWQMJ, JWHSL, NCGQSL, JWQXZ, SFYX, QYRQ, SXRQ, XGSJ, BZ, BJZBZ, MOVESIGN
		if (jwq != null) {
			map.put("JWQID", jwq.getJwqid());
			map.put("PCSID", jwq.getPcsid());
			map.put("SJXZQYID", jwq.getSjxzqyid());
			map.put("JWQBH", jwq.getJwqbh());
			map.put("JWQMC", jwq.getJwqmc());
			map.put("JWQJJ", jwq.getJwqjj());
			map.put("JWQMJ", jwq.getJwqmj());
			map.put("JWHSL", jwq.getJwhsl());
			map.put("NCGQSL", jwq.getNcgqsl());
			map.put("JWQXZ", jwq.getJwqxz());
			map.put("SFYX", jwq.getSfyx());
			map.put("QYRQ", jwq.getQyrq());
			map.put("SXRQ", jwq.getSxrq());
			map.put("XGSJ", jwq.getXgsj());
			map.put("BZ", jwq.getBz());
			map.put("MOVESIGN", jwq.getMovesign());
			String  bjzbz = jwq.getBjzbz();
			if(bjzbz !=null&&!bjzbz.isEmpty()&&!"null".equals(bjzbz)){
				try{
					Map<String, Object> zb = new HashMap<String, Object>();
					String[] array = bjzbz.split(";");
					zb.put("type", array.length>1? "multipolygon": "polygon");
					zb.put("coordinates", pgisPolygonToElastic(array));
					map.put("BJZB", zb);
				}catch(Throwable t){
					t.printStackTrace();
					map.remove("BJZB");
				}
			}
			
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	private static List pgisPolygonToElastic(String[] array) throws Exception {
		if(array.length==1){
			List<List<Double[]>> coor = new ArrayList<List<Double[]>>();
			List<Double[]> list =polygonStringToList(array[0]);
			coor.add(list);
			return coor;
		}else{
			List<List<List<Double[]>>> coor = new ArrayList<List<List<Double[]>>>();
			for(String s:array){
				List<Double[]> list =polygonStringToList(s);
				List<List<Double[]>> a = new ArrayList<List<Double[]>>();
				a.add(list);
				coor.add(a);
			}
			return coor;
		}
	}
	
	/**
	 * @param str (117.60031,37.70542,117.60443,37.70439,117.60375,37.69829,117.61087,37.69804)
	 * @return  [[117.60031,37.70542],[117.60443,37.70439] 。。。。]
	 * @throws Exception
	 */
	public static List<Double[]> polygonStringToList(String str) throws Exception{
		String[] split = str.split(",");
		List<Double[]> list = new ArrayList<Double[]>();
		if(split.length<6){
			throw new Exception("多边形坐标有误");
		}
		for(int i=0;i<split.length;i++){
			list.add(new Double[]{Double.valueOf(split[i]),Double.valueOf(split[i+1])});
			i++;
		}
		return list;
	}

	/**
	 * @param bjzbz  (117.60031,37.70542,117.60443,37.70439,117.60375,37.69829,117.61087,37.69804)
	 * @return  Elastic PolygonBuilder
	 */
	public static PolygonBuilder pgisPolygonStringToElastic(String bjzbz) {
		try{
			PolygonBuilder polygon = ShapeBuilder.newPolygon();
			
			List<Double[]> list =polygonStringToList(bjzbz);
			
			for(Double[] point : list){
				polygon.point(point[0], point[1]);
			}
			return polygon;
		}catch(Throwable t){
			return null;
		}
	}

	public static Map<String, Object> jwqMapToDocument(Map<String, Object> jwq) {
		//JWQID, PCSID, SJXZQYID, JWQBH, JWQMC, JWQJJ, JWQMJ, JWHSL, NCGQSL, JWQXZ, SFYX, QYRQ, SXRQ, XGSJ, BZ, BJZBZ, MOVESIGN
		if (jwq != null) {
			String  bjzbz ="";
			try{
				Clob bjzbzClob = null;
				Object bjzbzObj =   jwq.remove("BJZBZ");
				if(bjzbzObj instanceof com.alibaba.druid.proxy.jdbc.ClobProxyImpl){
					bjzbzClob = ((com.alibaba.druid.proxy.jdbc.ClobProxyImpl) bjzbzObj).getRawClob();
				}else {
					bjzbzClob =(Clob) bjzbzObj;
				}
				Reader reader = bjzbzClob.getCharacterStream();
				StringWriter sw = new StringWriter();
				IOUtils.copy(reader, sw);
				reader.close();
				bjzbz = sw.toString();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(bjzbz !=null&&!bjzbz.isEmpty()&&!"null".equals(bjzbz)){
				try{
					Map<String, Object> zb = new HashMap<String, Object>();
					String[] array = bjzbz.split(";");
					zb.put("type", array.length>1? "multipolygon": "polygon");
					zb.put("coordinates", pgisPolygonToElastic(array));
					jwq.put("BJZB", zb);
				}catch(Throwable t){
					t.printStackTrace();
					jwq.remove("BJZB");
				}
			}
		}
		return jwq;
	}
	/**
	 * 拆分坐标值
	 * @param str
	 */
	public static List<Polygon> splitStrToPolygon(String str){
		List<Polygon> polygonList = Lists.newArrayList();
		if(str == null ||str.isEmpty()){
			return polygonList;
		}
		String[] qyzbz = str.split(";");
		Polygon polygon =null;
		for (int i = 0; i < qyzbz.length; i++) {
			polygon = new Polygon();
				List<GeoPoint> listPoint = Lists.newArrayList();
				if(qyzbz[i]==null||qyzbz[i].isEmpty()){
					continue;
				}
				String[] zb = qyzbz[i].split(",");
				for(int j =0; j < zb.length; j++){
					GeoPoint point = new GeoPoint();
					point.setLon((Double.parseDouble(zb[j])));// 经度
					point.setLat((Double.parseDouble(zb[j+1])));// 纬度
					j++;
					listPoint.add(point);
				}
				polygon.setPoints(listPoint);
				polygonList.add(polygon);
			}
			return polygonList;
	}
}
