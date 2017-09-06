package com.kingmon.project.psam.datasync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.project.psam.datasync.model.DzDataSyncTask;
import com.kingmon.project.psam.datasync.service.IDzDataSyncTaskService;
@Controller
@RequestMapping("/psam/datasync/dzDataSyncTask")
public class DzDataSyncTaskController extends KBaseController{
	
	private static final String prefix="psam/datasync/dzDataSyncTask";
	
	@Autowired
	private IDzDataSyncTaskService  dzDataSyncTaskService;
	
//	@AuthWidgetRule(value="dzDataSyncTask",desc="",operateType="R",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value={"/",""})
	public Object enterDzDataSyncTask() {
		return prefix+"dzDataSyncTask";
	}
//	@AuthWidgetRule(value="dzDataSyncTask.loadDataGrid",desc="xxxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object loadDataGrid(@ModelAttribute("paramObject")ParamObject po) {
		DataSet dataSet = dzDataSyncTaskService.loadDzDataSyncTaskDataset(po);
		return FastjsonUtil.convert(dataSet);
	}
//-------------------add-------------------------------------------------------------------------
//	@AuthWidgetRule(value="dzDataSyncTask.addDzDataSyncTask",desc="xxx添加",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddDzDataSyncTask",method=RequestMethod.GET)
	public String enterAddDzDataSyncTask() {
		return prefix + "dzDataSyncTaskAdd";
	} 
//	@AuthWidgetRule(value="dzDataSyncTask.addDzDataSyncTask",desc="xxx添加",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/addDzDataSyncTask",method=RequestMethod.POST)
	@ResponseBody
	public Object addDzDataSyncTask(@ModelAttribute("dzDataSyncTask")DzDataSyncTask dzDataSyncTask) {
		dzDataSyncTaskService.addDzDataSyncTask(dzDataSyncTask);
		return ajaxDoneSuccess("数据添加成功 ");
	}
//---------------------------------update-----------------------------------------------------------

	//@AuthWidgetRule(value="dzDataSyncTask.updateDzDataSyncTask",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateDzDataSyncTask", method = RequestMethod.GET)
	public String enterUpdateDzDataSyncTask(String id, Model model) {
		DzDataSyncTask dzDataSyncTask = dzDataSyncTaskService.findDzDataSyncTaskById(id);
		setDataAttribute(model, dzDataSyncTask, "dzDataSyncTask");
		return prefix + "dzDataSyncTaskEdit";
	}
	//@AuthWidgetRule(value="dzDataSyncTask.updateDzDataSyncTask",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/updateDzDataSyncTask", method = RequestMethod.POST)
	@ResponseBody
	public Object updateDzDataSyncTask(@ModelAttribute("dzDataSyncTask") DzDataSyncTask DzDataSyncTask) {
		dzDataSyncTaskService.updateDzDataSyncTask(DzDataSyncTask);
		return ajaxDoneSuccess("数据修改成功 ");
	}
	//---------------------------------delete-----------------------------------------------------------	
	//@AuthWidgetRule(value="dzDataSyncTask.updateDzDataSyncTask",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_DELETE)
	@RequestMapping(value = "/deleteDzDataSyncTask", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteDzDataSyncTask(String id) {
		dzDataSyncTaskService.deleteDzDataSyncTaskById(id);
		return ajaxDoneSuccess("数据删除成功 ");
	}
}
