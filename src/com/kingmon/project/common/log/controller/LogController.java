//package com.kingmon.project.common.log.controller;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.kingmon.base.common.KConstants;
//import com.kingmon.base.data.DataSet;
//import com.kingmon.base.util.FastjsonUtil;
//import com.kingmon.base.web.KBaseController;
//import com.kingmon.common.annon.AuthWidgetRule;
//import com.kingmon.project.common.log.model.Log;
//import com.kingmon.project.common.log.service.ILogService;
//
//@Controller
//@RequestMapping("/common/log/log")
//public class LogController extends KBaseController{
//
//private static final String prefix="common/log/log/";
//	
//	@Autowired
//	private ILogService logService;
//	
//	@AuthWidgetRule(value="log",desc="系统日志管理",operateType="R",refTable="APP_LOG",crudType=KConstants.OPER_SEARCH)
//	@RequestMapping(value={"/",""},method=RequestMethod.GET)
//    public String enterLogPage(){
//		return prefix+"logDataGrid";
//    }
//	
//	@AuthWidgetRule(value="log.enterLogDetail",desc="系统日志详细",operateType="W",refTable="APP_LOG",crudType=KConstants.OPER_SEARCH)
//    @RequestMapping(value="/enterLogDetail")
//    @ResponseBody
//    public Object enterLogDetail(Model model,String id){
//		Log log=logService.findLogByid(id);
//		setDataAttribute(model, log, "log");
//	   return prefix+"logDetail";
//    }
//	
//	@AuthWidgetRule(value="log.loadLogDataGrid",desc="系统日志列表",operateType="W",refTable="APP_LOG",crudType=KConstants.OPER_SEARCH)
//    @RequestMapping(value="/loadLogDataGrid")
//    @ResponseBody
//    public Object loadLogDataGrid(@RequestParam Map<String,String> param){
//    	DataSet dataSet= logService.loadLogDataSet(param);
//	   return FastjsonUtil.convert(dataSet);
//    }
//}
