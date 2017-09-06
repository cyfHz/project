package com.kingmon.project.psam.acquisition.controller;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BaseFilterBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoPolygonFilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.FastJsonDataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.elastic.model.Polygon;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.jlx.service.IJlxService;
import com.kingmon.project.psam.jwq.service.IJwqService;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;
import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
import com.kingmon.project.psam.jzw.serivice.IJzwjgPicService;
import com.kingmon.project.psam.jzw.serivice.IJzwjgService;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.mlph.service.MlphService;
import com.kingmon.project.psam.orgarea.service.IOrgPgisAreaService;
import com.kingmon.project.psam.xzqh.model.Xzqh;
import com.kingmon.project.psam.xzqh.service.XzqhService;

//import static org.elasticsearch.index.query.QueryBuilders.*;
//import static org.elasticsearch.index.query.FilterBuilders.*;
@Controller
@RequestMapping("/psam/acquisition")
public class AcquisitionController extends KBaseController{
	private static final String prefix="psam/acquisition/";
	@Autowired
	private IJzwjbxxService jzwjbxxService;
	@Autowired
	private MlphService mlphService;
	@Autowired
	private IJlxService jlxService;
	@Autowired
	private IJzwjgPicService jzwjgPicService;
	@Autowired
	private IOrganizationService orgService;
	@Autowired
	private IOrganizationUserService orgUserService;
	@Autowired
	private XzqhService xzqhService;
	
	@Autowired
	private IJwqService jwqService;
	@Autowired
	private IOrgPgisAreaService orgPgisAreaService;
	
	@Autowired
	private IJzwjgService jzwjgService;
	/**
	 * 进入信息采集页面
	 * @return
	 */
	@AuthWidgetRule(value="acquisition",desc="信息采集",operateType="R",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String inforAcquisition(Model model) {
		return prefix+"inforAcquisition";
	}
	/**
	 * 进入建筑物信息采集的添加页面
	 */
	@AuthWidgetRule(value="acquisition.acquisition",desc="信息采集",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/enterAcquisition",method=RequestMethod.GET)
	public String enterAcquisition(Model model,@RequestParam  Map<String,String> params){
		//37150246000007 - 36.65435 117.00118
//		List<Map<String,Object>> jwqList=jwqService.findJwqByPoint(point.getLat(), point.getLon());
		double lat = Double.parseDouble(params.get("lat"));
		double lon = Double.parseDouble(params.get("lon"));
		GeoPoint point = new GeoPoint(lat, lon);
		List<Map<String,Object>> jwqList=jwqService.findJwqByPointAndUserPerm(SecurityUtils.getUserId(),point.getLat(), point.getLon());
		if(jwqList==null||jwqList.size()==0){
			return prefix+"info";
		}
		String jwqid=(String) jwqList.get(0).get("JWQID");
		
		Jzwjbxx jzw =new Jzwjbxx();
		jzw.setZxdzzb(BigDecimal.valueOf(point.getLat()));
		jzw.setZxdhzb(BigDecimal.valueOf(point.getLon()));
		Organization org=orgService.findOrgByUserId(SecurityUtils.getUserId());
		OrganizationUser orgUser=orgUserService.findByUserId(SecurityUtils.getUserId());
		if(org!=null){
			String orgCodeSubSix=org.getOrgna_code().substring(0,6);
			Xzqh xzqh=xzqhService.selectXzqhBydm(orgCodeSubSix);
			setDataAttribute(model, xzqh, "xzqh");
		}
		setDataAttribute(model, org, "org");
		setDataAttribute(model, orgUser, "orguser");
		setDataAttribute(model, jzw, "jzw");
		setDataAttribute(model, jwqid, "jwqid");
		//caijiType 01: 采集建筑物 02：采集楼门牌
		String caijiType = params.get("type");
		if(caijiType != null && !caijiType.isEmpty() && caijiType.equals("01")){
			return prefix+"inforAcquisitionAdd";
		}else{
			return prefix+"inforAcquisitionAddForMlph";
		}
	}
	
	/**
 	@ModelAttribute("jzwjbxx") Jzwjbxx jzwjbxx,
	@ModelAttribute("mlph") Mlph mlph,
	@ModelAttribute("sqrxx") Sqrxx sqrxx,
	Boolean isJZW,
	String fromby,
	@ModelAttribute("accqUser")AccqUserInfo accqUser,
	BindingResult bindingResult
 */
	/**
	 * 建筑物基本信息添加
	 * @param jzwjbxx
	 * @param bindingResult
	 * @return
	 */
	@AuthWidgetRule(value="acquisition.acquisition",desc="信息采集",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/addJzwInfoAcquisition",method=RequestMethod.POST)
	@ResponseBody
	public Object addJzwInfoAcquisition(@RequestBody Map<String,Object> view) {

	//	processValidateResult(bindingResult);
		String jzwjbxxId = jzwjbxxService.addInfoAcquisition(view);
		Map<String ,Object> map=Maps.newHashMap();
		Boolean isJZW = Boolean.parseBoolean((String)view.get("isJZW")==null?"false":(String)view.get("isJZW"));
		if(isJZW){
			Jzwjbxx jzw=jzwjbxxService.getJzwJbxxById(jzwjbxxId);
			map.put("jzwid", jzwjbxxId);
			map.put("lat", jzw.getZxdzzb());
			map.put("lon", jzw.getZxdhzb());
			map.put("jzwmc", jzw.getJzwmc());
			map.put("jzwlx","jzw");
		}else{
			Mlph mlphx=mlphService.findMlphByYwlsh(jzwjbxxId);
			map.put("jzwid", jzwjbxxId);
			map.put("lat", mlphx.getZxdzzb());
			map.put("lon", mlphx.getZxdhzb());
//			map.put("jzwmc",mlphx.getDzmc());
			map.put("jzwmc",mlphx.getQhnxxdz());
			map.put("jzwlx","mlph");
		}
		KJSONMSG msg=new KJSONMSG(200,"信息采集添加成功 ",map);
		return msg;
	}
	
	
//-------------------------------------------------------------------------------------------------------
	@Autowired
	private ElasticService elasticService;
	
	private final List<String> searchFromList=Arrays.asList(new String[]{"mlph","jzwjbxx"});
	String searchFiled="DZMC";
	String searchFindex="psam";
	
	@AuthWidgetRule(value="acquisition.search",desc="信息检索",operateType="W",refTable="",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/search" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public  Object search(@RequestParam Map<String,Object> param) {
		String type=(String) param.get("type");
		String val=(String) param.get("val");
		String searchFrom=(String) param.get("searchFrom");
		String matchPhrase=(String) param.get("matchPhrase");
		
		if(!searchFromList.contains(""+searchFrom)){
			return  DataSet.newDs();
		}
		int page=Integer.valueOf((String)param.get("page"));
		int size=Integer.valueOf((String)param.get("rows"));
		int from =(page-1)*size;
		BaseFilterBuilder filterBuilder=null;
		//type="polygon";
		if("circle".equals(type)){
			Double radius=Double.valueOf((String)param.get("radius"));
			Double lat=Double.valueOf((String)param.get("lat"));
			Double lon=Double.valueOf((String)param.get("lon"));
			filterBuilder=FilterBuilders.geoDistanceFilter("LOCATION").point(lat,lon).distance(""+radius);
			
		}else if("react".equals(type)){
			
			Double ltlat=Double.valueOf((String)param.get("ltlat"));
			Double ltlon=Double.valueOf((String)param.get("ltlon"));
			
			Double rblat=Double.valueOf((String)param.get("rblat"));
			Double rblon=Double.valueOf((String)param.get("rblon"));
			filterBuilder=FilterBuilders.geoBoundingBoxFilter("LOCATION").topLeft(ltlat, ltlon).bottomRight(rblat, rblon);
			
		}else if("polygon".equals(type)){
		//-----------------one geoPolygon------------------------------------------------------------
//			List<GeoPoint> points=genPoints();
//			List<GeoPoint> points=Lists.newArrayList();
//			filterBuilder=FilterBuilders.geoPolygonFilter("LOCATION");
//			for(GeoPoint point:points){
//				((GeoPolygonFilterBuilder) filterBuilder).addPoint(point.getLat(), point.getLon());
//			}
		//-----------------mulit geoPolygon--------------------------------------------------------
			
		 //List<Polygon> polygons=genPolygon();
			String pp="116.950038,36.604882,116.950009,36.604487,116.949887,36.604487,116.950499,36.603818,116.951766,36.603669,116.951698,36.603288,116.950997,36.601778,116.950723,36.600779,116.951037,36.600165,116.953393,36.600308,116.955177,36.599701,116.956203,36.59828,116.956389,36.59752,116.956917,36.596435,116.957374,36.596006,116.958588,36.595278,116.958795,36.594989,116.958559,36.594138,116.958666,36.593924,116.960544,36.593189,116.960144,36.592639,116.959809,36.591768,116.959787,36.59149,116.960144,36.590653,116.960158,36.590139,116.959222,36.589583,116.959129,36.589226,116.957973,36.589126,116.957337,36.588755,116.956945,36.588327,116.956953,36.587527,116.955467,36.586314,116.954939,36.586085,116.954496,36.585536,116.954139,36.585314,116.953347,36.584965,116.952448,36.58475,116.947816,36.583983,116.946705,36.584048,116.945924,36.584355,116.94594,36.584567,116.945148,36.584834,116.945342,36.585715,116.943993,36.585655,116.943326,36.585861,116.943769,36.590639,116.943423,36.590611,116.943239,36.591847,116.941723,36.591933,116.941368,36.592095,116.940898,36.592588,116.938846,36.592874,116.93818,36.592767,116.937644,36.592446,116.93715,36.592321,116.93655,36.592321,116.935788,36.592588,116.935351,36.593031,116.937426,36.595698,116.939889,36.597661,116.940412,36.599137,116.940662,36.600588,116.945302,36.602444,116.946733,36.603434,116.947295,36.604167,116.948972,36.603945,116.948994,36.604823,116.949643,36.604757,116.949672,36.604906,116.950038,36.604882;";
			pp+="116.95525,36.60259,116.96905,36.6065,116.95782,36.61626,116.94695,36.61028,116.96355,36.59246,116.96941,36.60662,116.96844,36.60686,116.95525,36.60259;";
			List<Polygon> polygons=ElasticUtil.splitStrToPolygon(pp);
			
			//List<Polygon> polygons=Lists.newArrayList();
		 filterBuilder = FilterBuilders.boolFilter();
			for(Polygon p : polygons){
				BaseFilterBuilder filterBuilderx=FilterBuilders.geoPolygonFilter("LOCATION");
					for(GeoPoint point:p.getPoints()){
						((GeoPolygonFilterBuilder) filterBuilderx).addPoint(point.getLat(), point.getLon());
					}
				((BoolFilterBuilder)filterBuilder).should(filterBuilderx);
			}
		}else{
			return  FastJsonDataSet.newDs(); 
		}
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
		QueryStringQueryBuilder queryStringQueryBuilder = null;
		QueryBuilder queryBuilder = null;
		
		if(val!=null&&!val.isEmpty()){
			if(matchPhrase!=null&&"1".equals(matchPhrase)){
				boolQueryBuilder.must(matchPhraseQuery(searchFiled, val));
//				boolQueryBuilder.must(termQuery(searchFiled, val));
			}else{
//				boolQueryBuilder.must(matchQuery(searchFiled, val));
				val = "\""+val+"\"";
				queryStringQueryBuilder = QueryBuilders.queryStringQuery(val);
				queryBuilder = queryStringQueryBuilder.field(searchFiled);
				boolQueryBuilder.must(queryBuilder);
			}
		}
		SearchResponse response = elasticService.getClient()
				.prepareSearch(searchFindex)
				.setTypes(searchFrom)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder)
				.setPostFilter(filterBuilder)
				.setFrom(from).setSize(size).execute()
				.actionGet();
		DataSet ds=ElasticUtil.searchResponse2Dataset(response);
		return ds;
	}
	
	@RequestMapping(value ="/checkIsHaveJg",method=RequestMethod.POST)
	@ResponseBody
	public Object checkIsHaveJg(String jzwId,Model model) {
		KJSONMSG msg=jzwjgService.validateJzwjgByJzwId(jzwId);
		return msg;
	}
	
//	private List<GeoPoint> genPoints(){
//		List<GeoPoint> points=Lists.newArrayList();
//		points.add(new GeoPoint( 36.71783,116.86544));
//		points.add(new GeoPoint(36.57799,116.86683));
//		points.add(new GeoPoint(36.55129,117.01683));
//		points.add(new GeoPoint(36.59035,117.13888));
//		points.add(new GeoPoint(36.62566,117.16983));
//		points.add(new GeoPoint(36.70608,117.20077));
//		points.add(new GeoPoint(36.71638,117.14421));
//		points.add(new GeoPoint(36.71783,116.86544));
//		points.add(new GeoPoint(36.71783,116.86544));
//		return points;
//	}
//	
//	private List<Polygon> genPolygon(){
//		List<Polygon> polygons=Lists.newArrayList();
//			List<GeoPoint> points=Lists.newArrayList();
//			points.add(new GeoPoint( 36.71783,116.86544));
//			points.add(new GeoPoint(36.57799,116.86683));
//			points.add(new GeoPoint(36.55129,117.01683));
//			points.add(new GeoPoint(36.59035,117.13888));
//			points.add(new GeoPoint(36.62566,117.16983));
//			points.add(new GeoPoint(36.70608,117.20077));
//			points.add(new GeoPoint(36.71638,117.14421));
//			points.add(new GeoPoint(36.71783,116.86544));
//			points.add(new GeoPoint(36.71783,116.86544));
//		Polygon polygon=new Polygon(points);
//		polygons.add(polygon);
//		
//		List<GeoPoint> points2=Lists.newArrayList();
//		points2.add(new GeoPoint(36.66795,117.22879));
//		points2.add(new GeoPoint(36.65251,117.28367));
//		points2.add(new GeoPoint(36.60352,117.25763));
//		points2.add(new GeoPoint(36.6156,117.191));
//		points2.add(new GeoPoint(36.66795,117.22879));
//	Polygon polygon2=new Polygon(points2);
//	polygons.add(polygon2);
//		return polygons;
//	}
}
