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
import com.kingmon.project.psam.datasync.model.DzDataSyncConfig;
import com.kingmon.project.psam.datasync.service.IDzDataSyncConfigService;
@Controller
@RequestMapping("/psam/datasync/dzDataSyncConfig")
public class DzDataSyncConfigController extends KBaseController{
	
	private static final String prefix="psam/datasync/dzDataSyncConfig/";
	
	@Autowired
	private IDzDataSyncConfigService  dzDataSyncConfigService;
	
//	@AuthWidgetRule(value="dzDataSyncConfig",desc="",operateType="R",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value={"/",""})
	public Object enterDzDataSyncConfig() {
		return prefix+"dzDataSyncConfigDataSet";
	}
//	@AuthWidgetRule(value="dzDataSyncConfig.loadDataGrid",desc="xxxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/loadDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public Object loadDataGrid(@ModelAttribute("paramObject")ParamObject po) {
		DataSet dataSet = dzDataSyncConfigService.loadDzDataSyncConfigDataset(po);
		return FastjsonUtil.convert(dataSet);
	}
//-------------------add-------------------------------------------------------------------------
//	@AuthWidgetRule(value="dzDataSyncConfig.addDzDataSyncConfig",desc="xxx添加",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddDzDataSyncConfig",method=RequestMethod.GET)
	public String enterAddDzDataSyncConfig() {
		return prefix + "dzDataSyncConfigAdd";
	} 
//	@AuthWidgetRule(value="dzDataSyncConfig.addDzDataSyncConfig",desc="xxx添加",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/addDzDataSyncConfig",method=RequestMethod.POST)
	@ResponseBody
	public Object addDzDataSyncConfig(@ModelAttribute("dzDataSyncConfig")DzDataSyncConfig dzDataSyncConfig) {
		dzDataSyncConfigService.addDzDataSyncConfig(dzDataSyncConfig);
		return ajaxDoneSuccess("数据添加成功 ");
	}
//---------------------------------update-----------------------------------------------------------

	//@AuthWidgetRule(value="dzDataSyncConfig.updateDzDataSyncConfig",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateDzDataSyncConfig", method = RequestMethod.GET)
	public String enterUpdateDzDataSyncConfig(String id, Model model) {
		DzDataSyncConfig dzDataSyncConfig = dzDataSyncConfigService.findDzDataSyncConfigById(id);
		setDataAttribute(model, dzDataSyncConfig, "dzDataSyncConfig");
		return prefix + "dzDataSyncConfigEdit";
	}
	//@AuthWidgetRule(value="dzDataSyncConfig.updateDzDataSyncConfig",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/updateDzDataSyncConfig", method = RequestMethod.POST)
	@ResponseBody
	public Object updateDzDataSyncConfig(@ModelAttribute("dzDataSyncConfig") DzDataSyncConfig DzDataSyncConfig) {
		dzDataSyncConfigService.updateDzDataSyncConfig(DzDataSyncConfig);
		return ajaxDoneSuccess("数据修改成功 ");
	}
	//---------------------------------delete-----------------------------------------------------------	
	//@AuthWidgetRule(value="dzDataSyncConfig.updateDzDataSyncConfig",desc="xxx",operateType="W",refTable="DZ_DATA_SYNC_BIZ",crudType=KConstants.OPER_DELETE)
	@RequestMapping(value = "/deleteDzDataSyncConfig", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteDzDataSyncConfig(String id) {
		dzDataSyncConfigService.deleteDzDataSyncConfigById(id);
		return ajaxDoneSuccess("数据删除成功 ");
	}
}
