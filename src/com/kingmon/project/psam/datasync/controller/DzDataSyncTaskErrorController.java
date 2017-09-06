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
import com.kingmon.project.psam.datasync.model.DzDataSyncTaskError;
import com.kingmon.project.psam.datasync.service.IDzDataSyncTaskErrorService;
@Controller
@RequestMapping("/psam/datasync/dzDataSyncTaskError")
public class DzDataSyncTaskErrorController extends KBaseController{
	
	private static final String prefix="psam/datasync/dzDataSyncTaskError";
	
	@Autowired
	private IDzDataSyncTaskErrorService  dzDataSyncTaskErrorService;
	
//	@AuthWidgetRule(value="dzDataSyncTaskError",desc="",operateType="R",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value={"/",""})
	public Object enterDzDataSyncTaskError() {
		return prefix+"dzDataSyncTaskError";
	}
//	@AuthWidgetRule(value="dzDataSyncTaskError.loadDataGrid",desc="xxxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object loadDataGrid(@ModelAttribute("paramObject")ParamObject po) {
		DataSet dataSet = dzDataSyncTaskErrorService.loadDzDataSyncTaskErrorDataset(po);
		return FastjsonUtil.convert(dataSet);
	}
//-------------------add-------------------------------------------------------------------------
//	@AuthWidgetRule(value="dzDataSyncTaskError.addDzDataSyncTaskError",desc="xxx添加",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddDzDataSyncTaskError",method=RequestMethod.GET)
	public String enterAddDzDataSyncTaskError() {
		return prefix + "dzDataSyncTaskErrorAdd";
	} 
//	@AuthWidgetRule(value="dzDataSyncTaskError.addDzDataSyncTaskError",desc="xxx添加",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/addDzDataSyncTaskError",method=RequestMethod.POST)
	@ResponseBody
	public Object addDzDataSyncTaskError(@ModelAttribute("dzDataSyncTaskError")DzDataSyncTaskError dzDataSyncTaskError) {
		dzDataSyncTaskErrorService.addDzDataSyncTaskError(dzDataSyncTaskError);
		return ajaxDoneSuccess("数据添加成功 ");
	}
//---------------------------------update-----------------------------------------------------------

	//@AuthWidgetRule(value="dzDataSyncTaskError.updateDzDataSyncTaskError",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateDzDataSyncTaskError", method = RequestMethod.GET)
	public String enterUpdateDzDataSyncTaskError(String id, Model model) {
		DzDataSyncTaskError dzDataSyncTaskError = dzDataSyncTaskErrorService.findDzDataSyncTaskErrorById(id);
		setDataAttribute(model, dzDataSyncTaskError, "dzDataSyncTaskError");
		return prefix + "dzDataSyncTaskErrorEdit";
	}
	//@AuthWidgetRule(value="dzDataSyncTaskError.updateDzDataSyncTaskError",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/updateDzDataSyncTaskError", method = RequestMethod.POST)
	@ResponseBody
	public Object updateDzDataSyncTaskError(@ModelAttribute("dzDataSyncTaskError") DzDataSyncTaskError DzDataSyncTaskError) {
		dzDataSyncTaskErrorService.updateDzDataSyncTaskError(DzDataSyncTaskError);
		return ajaxDoneSuccess("数据修改成功 ");
	}
	//---------------------------------delete-----------------------------------------------------------	
	//@AuthWidgetRule(value="dzDataSyncTaskError.updateDzDataSyncTaskError",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_DELETE)
	@RequestMapping(value = "/deleteDzDataSyncTaskError", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteDzDataSyncTaskError(String id) {
		dzDataSyncTaskErrorService.deleteDzDataSyncTaskErrorById(id);
		return ajaxDoneSuccess("数据删除成功 ");
	}
}
