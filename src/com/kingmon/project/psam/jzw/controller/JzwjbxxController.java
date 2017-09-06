package com.kingmon.project.psam.jzw.controller;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BaseFilterBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoPolygonFilterBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.FastJsonDataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.util.GeoUtils;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.elastic.model.Polygon;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.jlx.model.Jlx;
import com.kingmon.project.psam.jlx.service.IJlxService;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;
import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
import com.kingmon.project.psam.jzw.serivice.IJzwjgPicService;
import com.kingmon.project.psam.mlph.service.MlphService;
import com.kingmon.project.psam.xzqh.model.Xzqh;
import com.kingmon.project.psam.xzqh.service.XzqhService;
@Controller
@RequestMapping("/psam/jzwjbxx")
public class JzwjbxxController extends KBaseController{
	private static final String prefix="psam/jzw/";
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
	
	@AuthWidgetRule(value="jzwjbxx",desc="建筑物基本信息管理",operateType="R",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String index() {
		return "psam/jzw/jzwjbxx";
	}
	@AuthWidgetRule(value="jzwjbxx.jzwjbxxList",desc="建筑物信息数据列表",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/jzwjbxxList" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  DataSet jzwjbxxList(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		return jzwjbxxService.loadJzwjbxxDataSet(params);
	}
	/**
	 * 进入建筑物基本信息增加页面
	 * @return
	 */
	@AuthWidgetRule(value="jzwjbxx.addJzwJbxx",desc="建筑物基本信息添加",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/enteraddJzwJbxx",method=RequestMethod.GET)
	public String enterAddJzwJbxx(){
		return prefix+"jzwjbxxAdd";
	}
	/*@AuthWidgetRule(value="jzwjbxx.addJzwJbxx",desc="建筑物基本信息添加",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/addjzwjbxx",method=RequestMethod.POST)
	@ResponseBody
	public Object addjzwjbxx(@ModelAttribute("jzwjbxx") Jzwjbxx jzwjbxx,BindingResult bindingResult) {
		jzwjbxxService.addJzwJbxx(jzwjbxx);
		return ajaxDoneSuccess("建筑物基本信息添加成功");
		
	}*/
	
	@AuthWidgetRule(value="jzwjbxx.updateJzwjbxx",desc="建筑物基本信息更新",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value="/enterUpdateJzwjbxx",method=RequestMethod.GET)
	public String enterEditJzwJbxx(String dzbm,Model model){
		Jzwjbxx jzwjbxx=jzwjbxxService.getJzwJbxxById(dzbm);
		if(jzwjbxx==null||"".equals(jzwjbxx)){
			String msg="信息不存在";
			setDataAttribute(model, msg, "msg");
			return prefix+"info";
		}
	    String jlxdzdm=jzwjbxx.getSsjlxxq_dzbm();
     	Jlx jlx=jlxService.getJlxById(jlxdzdm);
     	
     	Organization org=orgService.findOrgByUserId(SecurityUtils.getUserId());
		String orgCodeSubSix=org.getOrgna_code().substring(0,6);//当前登录人员的行政区划
		Xzqh xzqh=xzqhService.selectXzqhBydm(orgCodeSubSix);
		setDataAttribute(model, xzqh, "xzqh");
		
		setDataAttribute(model, jzwjbxx, "jzwjbxx");
		setDataAttribute(model, jlx, "jlx");
		return prefix+"jzwjbxx.edit";
	}
	@AuthWidgetRule(value="jzwjbxx.updateJzwjbxx",desc="建筑物基本信息更新",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/updateJzwjbxx", method = RequestMethod.POST)
	@ResponseBody
	public Object updateJzwjbxx(@ModelAttribute("jzwjbxx") Jzwjbxx jzwjbxx,
			BindingResult bindingResult) {
		jzwjbxxService.updateJzwjbxx(jzwjbxx);
		return ajaxDoneSuccess("建筑物基本信息修改成功 ");
	}
	
	@AuthWidgetRule(value="jzwjbxx.Cancellation",desc="建筑物基本信息注销",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value="/Cancellation",method=RequestMethod.POST)
	@ResponseBody
	public Object cancellation(String[] ids,String flag){
		jzwjbxxService.cancelJzw(ids,flag);
		return ajaxDoneSuccess("注销成功");
	}
	//@AuthWidgetRule(value="jzwjbxx.activeJzw",desc="建筑物基本信息启用",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value="/activeJzw",method=RequestMethod.POST)
	@ResponseBody
	public Object activeJzw(String[] ids,String flag){
		jzwjbxxService.activeJzw(ids,flag);
		return ajaxDoneSuccess("修改成功");
	}
	
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="建筑物信息加载",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadJzwInfo")
	@ResponseBody
	public Object loadJzwInfo(String jzwjgId) {
		Jzwjbxx jzw=jzwjbxxService.getJzwJbxxById(jzwjgId);
		List<String> jzwjgPicList=jzwjgPicService.loadjgPicIdsByjgId(jzwjgId);
		Map<String,Object> data=Maps.newHashMap();
		data.put("jzw", jzw);
		data.put("jzwjgPicList", jzwjgPicList);
		KJSONMSG msg=new KJSONMSG(200, "数据加载成功", data);
		msg.setData(data);
		return msg;
	}
	@AuthWidgetRule(value="jzwjbxx.marMark",desc="建筑物坐标标注",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value="/enterLocMark",method=RequestMethod.GET)
	public String enterMark(String dzbm,Model model){
		setDataAttribute(model, dzbm, "dzbm");
		return prefix+"jzwjbxx.mark";
	}	

	@AuthWidgetRule(value="jzwjbxx.marMark",desc="建筑物坐标标注",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value="/updateLocMark",method=RequestMethod.POST)
	public @ResponseBody Object updateLocMark(String dzbm,String location){
		jzwjbxxService.updateJzwLocation(dzbm,GeoUtils.pgis2Point(location));
		return ajaxDoneSuccess("标注成功");
	}
	
	//---------------------------开放接口
	@Autowired
	private ElasticService elasticService;
	
	@RequestMapping(value="/openApiJzw")
	public @ResponseBody Object openApiJzw(String dzmc,String jzwmc,boolean isMatchPhrase){
		 String searchFindex="psam";
		 String searchFrom="jzwjbxx";
		int from=0,size=200;
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
		
		if(dzmc!=null&&!dzmc.isEmpty()){
			boolQueryBuilder.must(matchPhraseQuery("DZMC", dzmc.trim()));
			if(isMatchPhrase){
				boolQueryBuilder.must(matchPhraseQuery("DZMC", dzmc.trim()));
			}else{
				boolQueryBuilder.must(matchQuery("DZMC", dzmc.trim()));
			}
		}
		if(jzwmc!=null && !jzwmc.isEmpty()){
			if(isMatchPhrase){
				boolQueryBuilder.must(matchPhraseQuery("JZWMC", jzwmc.trim()));
			}else{
				boolQueryBuilder.must(matchQuery("JZWMC", jzwmc.trim()));
			}
		}
		
		SearchResponse response = elasticService.getClient()
				.prepareSearch(searchFindex)
				.setTypes(searchFrom)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder)
				.setFrom(from).setSize(size).execute()
				.actionGet();
		DataSet ds=ElasticUtil.searchResponse2Dataset(response);
		return ds;
	}
	
	@RequestMapping(value="/openApiFj")
	public @ResponseBody Object openApiFj(String jzwid){
		List<Map<String,Object>> data=jzwjbxxService.openApiFj(jzwid);
		return data;
	}
	
}
