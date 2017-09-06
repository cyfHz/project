package com.kingmon.project.elastic.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.action.count.CountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.web.KBaseController;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.service.ElasticSyncService;

import static org.elasticsearch.index.query.QueryBuilders.*;
@Controller
@RequestMapping("/psam/elasticSync")
public class ElasticSyncController extends KBaseController {
	@Autowired
	private ElasticService elasticService;
	@Autowired
	private ElasticSyncService elasticSyncService;
	
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String index(Model model) {
		//TODO data
		CountResponse mlphCntResp = elasticService.getClient().prepareCount("psam").setQuery(termQuery("_type", "mlph")).execute().actionGet();
		CountResponse jzwCntResp = elasticService.getClient().prepareCount("psam").setQuery(termQuery("_type", "jzwjbxx")).execute().actionGet();
		CountResponse jwqCntResp = elasticService.getClient().prepareCount("psam").setQuery(termQuery("_type", "jwq")).execute().actionGet();
		
		
		setDataAttribute(model, mlphCntResp.getCount(), "mlphCnt");
		setDataAttribute(model, jzwCntResp.getCount(), "jzwCnt");
		setDataAttribute(model, jwqCntResp.getCount(), "jwqCnt");
		setDataAttribute(model, elasticSyncService.getTASKS(), "tasks");
		setDataAttribute(model, elasticSyncService.isEnableAutoSync(), "enableAutoSync");
		return "psam/elastic/elastic";
	}
	
	
	@RequestMapping(value = { "/create" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG cancel(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		if(params==null||params.size()==0){
			ajaxDoneError("参数有误");
		}
		String type = params.get("type");
		if(!"MLPH".equals(type)||!"JZW".equals(type)||!"JWQ".equals(type)){
			ajaxDoneError("任务类型有误");
		}
		String taskName = params.get("taskName");
		if(taskName==null||taskName.isEmpty()){
			ajaxDoneError("任务名称有误");
		}
		boolean  notIndexedOnly = true;
		String onlyNew = params.get("onlyNew");
		if(!"on".equals(onlyNew)){
			notIndexedOnly = false;
		}
		boolean stopOnError= false;
		String errorStop = params.get("errorStop");
		if("on".equals(errorStop)){
			stopOnError = true;
		}
		
		elasticSyncService.createAndRunTask(taskName,type,notIndexedOnly,stopOnError);
		
		return ajaxDoneSuccess("同步任务创建成功");
	}
	
	
	@RequestMapping(value = { "/stop" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG stop(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		if(params==null||params.size()==0||params.get("taskId")==null){
			ajaxDoneError("参数有误");
		}
		String taskId = params.get("taskId");
		elasticSyncService.stopTask(taskId);
		return ajaxDoneSuccess("操作成功");
	}
	
	@RequestMapping(value = { "/toogleAutoSync" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG toogleAutoSync(HttpServletRequest request) {
		elasticSyncService.setEnableAutoSync(!elasticSyncService.isEnableAutoSync());
		return ajaxDoneSuccess("操作成功");
	}
}
