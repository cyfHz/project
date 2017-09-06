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
import com.kingmon.project.psam.datasync.model.DzDataSyncBiz;
import com.kingmon.project.psam.datasync.service.IDzDataSyncBizService;
@Controller
@RequestMapping("/psam/datasync/dzDataSyncBiz")
public class DzDataSyncBizController extends KBaseController{
	
	private static final String prefix="psam/datasync/dzDataSyncBiz/";
	
	@Autowired
	private IDzDataSyncBizService  dzDataSyncBizService;
	
//	@AuthWidgetRule(value="dzDataSyncBiz",desc="",operateType="R",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value={"/",""})
	public Object enterDzDataSyncBiz() {
		return prefix+"dzDataSyncBizData";
	}
//	@AuthWidgetRule(value="dzDataSyncBiz.loadDataGrid",desc="xxxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object loadDataGrid(@ModelAttribute("paramObject")ParamObject po) {
		DataSet dataSet = dzDataSyncBizService.loadDzDataSyncBizDataset(po);
		return FastjsonUtil.convert(dataSet);
	}
//-------------------add-------------------------------------------------------------------------
//	@AuthWidgetRule(value="dzDataSyncBiz.addDzDataSyncBiz",desc="xxx添加",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddDzDataSyncBiz",method=RequestMethod.GET)
	public String enterAddDzDataSyncBiz() {
		return prefix + "dzDataSyncBizAdd";
	} 
//	@AuthWidgetRule(value="dzDataSyncBiz.addDzDataSyncBiz",desc="xxx添加",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/addDzDataSyncBiz",method=RequestMethod.POST)
	@ResponseBody
	public Object addDzDataSyncBiz(@ModelAttribute("dzDataSyncBiz")DzDataSyncBiz dzDataSyncBiz) {
		dzDataSyncBizService.addDzDataSyncBiz(dzDataSyncBiz);
		return ajaxDoneSuccess("数据添加成功 ");
	}
//---------------------------------update-----------------------------------------------------------

	//@AuthWidgetRule(value="dzDataSyncBiz.updateDzDataSyncBiz",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateDzDataSyncBiz", method = RequestMethod.GET)
	public String enterUpdateDzDataSyncBiz(String id, Model model) {
		DzDataSyncBiz dzDataSyncBiz = dzDataSyncBizService.findDzDataSyncBizById(id);
		setDataAttribute(model, dzDataSyncBiz, "dzDataSyncBiz");
		return prefix + "dzDataSyncBizEdit";
	}
	//@AuthWidgetRule(value="dzDataSyncBiz.updateDzDataSyncBiz",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/updateDzDataSyncBiz", method = RequestMethod.POST)
	@ResponseBody
	public Object updateDzDataSyncBiz(@ModelAttribute("dzDataSyncBiz") DzDataSyncBiz DzDataSyncBiz) {
		dzDataSyncBizService.updateDzDataSyncBiz(DzDataSyncBiz);
		return ajaxDoneSuccess("数据修改成功 ");
	}
	//---------------------------------delete-----------------------------------------------------------	
	//@AuthWidgetRule(value="dzDataSyncBiz.updateDzDataSyncBiz",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_DELETE)
	@RequestMapping(value = "/deleteDzDataSyncBiz", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteDzDataSyncBiz(String id) {
		dzDataSyncBizService.deleteDzDataSyncBizById(id);
		return ajaxDoneSuccess("数据删除成功 ");
	}
}
