package com.kingmon.project.psam.workload.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.workload.service.IWorkloadService;

@Controller
@RequestMapping("/psam/workload")
public class WorkloadController extends KBaseController{

	
	@Autowired
	private IWorkloadService workloadService;
	@Value("${dev.data.process}")
	private String devDataProcess;
	
	/**
	 * 跳转数据类型统计模块页面
	 * @return
	 */
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String index() {
		return "psam/workload/workload";
	}
	/**
	 * 加载查询数据类型统计列表信息
	 * @param params
	 * @param request
	 * @return
	 */
	@AuthWidgetRule(value="workload.workLoadList",desc="数据类型统计",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/workLoadList" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  DataSet workLoadList(String porg_code,HttpServletRequest request) {
		DataSet workList=DataSet.newDs();
		if(KConstants.DEV_DATA_PROCESS_ELASTIC.equals(devDataProcess)){
			workList=workloadService.loadDzWorkLoadDataFromEs(porg_code);
		}else{
			workList=workloadService.loadDzWorkLoadFromDb(porg_code);
		}
		return workList;
	}
}
