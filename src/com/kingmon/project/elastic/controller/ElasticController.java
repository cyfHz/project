//package com.kingmon.project.elastic.controller;
//
//import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
//import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
//import static org.elasticsearch.index.query.QueryBuilders.termQuery;
//import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
//import static org.elasticsearch.index.query.FilterBuilders.*;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.math.NumberUtils;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchType;
//import org.elasticsearch.index.query.BaseFilterBuilder;
//import org.elasticsearch.index.query.BoolFilterBuilder;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.FilterBuilders;
//import org.elasticsearch.index.query.GeoBoundingBoxFilterBuilder;
//import org.elasticsearch.index.query.GeoDistanceFilterBuilder;
//import org.elasticsearch.index.query.GeoPolygonFilterBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.kingmon.base.common.KConstants;
//import com.kingmon.base.data.DataSet;
//import com.kingmon.base.data.FastJsonDataSet;
//import com.kingmon.base.data.KJSONMSG;
//import com.kingmon.base.data.ParamObject;
//import com.kingmon.base.util.PaginationUtil;
//import com.kingmon.base.util.UUIDUtil;
//import com.kingmon.base.web.KBaseController;
//import com.kingmon.common.annon.AuthWidgetRule;
//import com.kingmon.project.elastic.model.GeoPoint;
//import com.kingmon.project.elastic.model.Polygon;
//import com.kingmon.project.elastic.model.SearchPoint;
//import com.kingmon.project.elastic.model.SearchCircle;
//import com.kingmon.project.elastic.model.SearchReactangle;
//import com.kingmon.project.elastic.service.ElasticService;
//import com.kingmon.project.elastic.service.ElasticServiceRest;
//import com.kingmon.project.elastic.util.DataCovertUtil;
//import com.kingmon.project.elastic.util.ElasticUtil;
//import com.kingmon.project.psam.jlx.model.Jlx;
//import com.kingmon.project.psam.jlx.service.IJlxService;
//import com.kingmon.project.psam.jzw.model.Jzwjbxx;
//import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
//import com.kingmon.project.psam.jzw.serivice.IJzwjgPicService;
//import com.kingmon.project.psam.mlph.model.Mlph;
//import com.kingmon.project.psam.mlph.service.MlphService;
//import com.kingmon.project.psam.sqrxx.model.Sqrxx;
//@Controller
//@RequestMapping("/psam/elastic")
//public class ElasticController extends KBaseController{
//	private static final String prefix="psam/elastic/";
//	@Autowired
//	private IJzwjbxxService jzwjbxxService;
//	@Autowired
//	private MlphService mlphService;
//	@Autowired
//	private IJlxService jlxService;
//	
//	@Autowired
//	private IJzwjgPicService jzwjgPicService;
//	
//	@Autowired
//	private ElasticServiceRest elasticServiceRest;
//	@Autowired
//	private ElasticService elasticService;
////----------------------信息采集-----------------------------------------------	
////	@AuthWidgetRule(value="elastic",desc="信息检索管理",operateType="R",refTable="",crudType=KConstants.OPER_SEARCH)
////	@RequestMapping(value = {"/",""})
////	public String enterElasticManage() {
////		return prefix + "elasticDataGrid";
////	}
//	@AuthWidgetRule(value="elastic.synchronizeElastic",desc="数据同步",operateType="W",refTable="",crudType=KConstants.OPER_SEARCH)
//	@RequestMapping(value = {"/synchronizeElastic" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
//	@ResponseBody
//	public Object synchronizeElastic(String type){
//		
//		
//		return null;
//	}
//	private final List<String> searchFromList=Arrays.asList(new String[]{"mlph","jzwjbxx"});
//	String searchFiled="DZMC";
//	String searchFindex="psam";
//	
//	@AuthWidgetRule(value="elastic.search",desc="信息检索",operateType="W",refTable="",crudType=KConstants.OPER_SEARCH)
//	@RequestMapping(value = {"/search" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
//	@ResponseBody
//	public  Object search(@RequestParam Map<String,Object> param) {
//		String type=(String) param.get("type");
//		String val=(String) param.get("val");
//		String searchFrom=(String) param.get("searchFrom");
//		if(!searchFromList.contains(""+searchFrom)){
//			return  DataSet.newDs();
//		}
//		int page=Integer.valueOf((String)param.get("page"));
//		int size=Integer.valueOf((String)param.get("rows"));
//		int from =(page-1)*size;
//		BaseFilterBuilder filterBuilder=null;
//		
//		if("circle".equals(type)){
//			Double radius=Double.valueOf((String)param.get("radius"));
//			Double lat=Double.valueOf((String)param.get("lat"));
//			Double lon=Double.valueOf((String)param.get("lon"));
//			//GeoDistanceFilterBuilder
//			filterBuilder=FilterBuilders.geoDistanceFilter("LOCATION").point(lat,lon).distance(""+radius);
//			
//		}else if("react".equals(type)){
//			
//			Double ltlat=Double.valueOf((String)param.get("ltlat"));
//			Double ltlon=Double.valueOf((String)param.get("ltlon"));
//			
//			Double rblat=Double.valueOf((String)param.get("rblat"));
//			Double rblon=Double.valueOf((String)param.get("rblon"));
//			//GeoBoundingBoxFilterBuilder
//			filterBuilder=FilterBuilders.geoBoundingBoxFilter("LOCATION").topLeft(ltlat, ltlon).bottomRight(rblat, rblon);
//			
//		}else if("polygon".equals(type)){
//			
////			BoolFilterBuilder filters = boolFilter();
////			
////			for(Polygon p : polygons){
////				filters.should(ElasticUtil.addPolygonToFilter(p,geoPolygonFilter("LOCATION")));
////			}
////			
////			SearchResponse response = elasticService.getClient()
////					.prepareSearch(searchFindex)
////					.setTypes(searchFrom)
////					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
////					.setQuery(matchQuery(searchFiled, val))
////					.setPostFilter(filters)
////					.setFrom(from).setSize(size).execute()
////					.actionGet();
////			
//////			QueryBuilder buildersx=FilterBuilders.geoPolygonFilter("LOCATION");
////			
//////			boolQueryBuilder.should(buildersx).should(buildersx);
////			
////			
////			List<GeoPoint> points=Lists.newArrayList();
////			for(GeoPoint point:points){
////				((GeoPolygonFilterBuilder)filterBuilder).addPoint(point.getLat(), point.getLon());
////			}
//		}else{
//			return  FastJsonDataSet.newDs(); 
//		}
//		SearchResponse response = elasticService.getClient()
//				.prepareSearch(searchFindex)
//				.setTypes(searchFrom)
//				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//				.setQuery(matchQuery(searchFiled, val))
//				.setPostFilter(filterBuilder)
//				.setFrom(from).setSize(size).execute()
//				.actionGet();
//		DataSet ds=ElasticUtil.searchResponse2Dataset(response);
//		return ds;
//	}
//}
