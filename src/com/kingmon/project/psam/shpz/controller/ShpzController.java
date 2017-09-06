package com.kingmon.project.psam.shpz.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.shpz.model.Shpz;
import com.kingmon.project.psam.shpz.service.IShpzService;

@Controller
@RequestMapping("/psam/shpz")
public class ShpzController extends KBaseController{
	
	private static final String prefix = "psam/shpz/";
	
	@Autowired
	private IShpzService shpzService;

	
	@AuthWidgetRule(value="shpz",desc="审批配置管理",operateType="R",refTable="DZ_SHPZ",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String enterShpzPage() {
		return prefix+"shpz";
	}

	@AuthWidgetRule(value="shpz.loadShpzDataGrid",desc="审批配置数据列表",operateType="W",refTable="DZ_SHPZ",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/loadShpzDataGrid" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public  DataSet loadShpzDataGrid(@ModelAttribute("paramObject") ParamObject paramObject) {
		return shpzService.loadShpzDataSet(paramObject);
	}
//-----------------------------add------------------------------------------------		
	@AuthWidgetRule(value="shpz.add",desc="门楼牌号添加",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddShpz",method=RequestMethod.GET)
	public String enterAddMlph(Model model) {
		return prefix+"shpz.add";
	}
	
	@AuthWidgetRule(value="shpz.add",desc="审批配置添加",operateType="W",refTable="DZ_SHPZ",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG add(@ModelAttribute("shpz")Shpz shpz) {
		shpzService.addShpz(shpz);
		return ajaxDoneSuccess("添加成功");
	}
	
//-------------------------------update----------------------------------------------		
	@AuthWidgetRule(value="shpz.update",desc="审批配置修改",operateType="W",refTable="DZ_SHPZ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG save(@ModelAttribute("shpz")Shpz shpz) {
		shpzService.updateShpz(shpz);
		return ajaxDoneSuccess("修改成功");
	}
	
	@AuthWidgetRule(value="shpz.update",desc="审批配置修改",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterEditShpz",method=RequestMethod.GET)
	public String enterEditMlph(String pzid, Model model) {
		Shpz shpz=shpzService.selectShpzById(pzid);
		setDataAttribute(model, shpz, "shpz");
		return prefix+"shpz.edit";
	}

}
