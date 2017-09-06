package com.kingmon.project.psam.xzqh.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.xzqh.model.Xzqh;
import com.kingmon.project.psam.xzqh.service.XzqhService;

@Controller
@RequestMapping("/psam/xzqh")
public class XzqhController extends KBaseController{
	
	@Autowired
	private XzqhService xzqhService;
	
	/**
	 * 进入行政区划模块管理页面
	 * @return 页面路径
	 */
	@AuthWidgetRule(value="xzqh",desc="行政区划管理",operateType="R",refTable="DZ_XZQH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String index() {
		return "psam/xzqh/xzqh";
	}
	
	/**
	 * 加载行政区划数据列表
	 * @param params 区划名称 区划代码
	 * @param request 
	 * @return 查询结果信息
	 */
	@AuthWidgetRule(value="xzqh.list",desc="行政区划数据列表查看",operateType="W",refTable="DZ_XZQH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/list" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  DataSet xzqhList(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		return xzqhService.xzqhList(params);
	}
	
	/**
	 * 行政区划查看详情信息
	 * @param DZBM 行政区划地址编码
	 * @param model
	 * @return 地址页面
	 */
	@SuppressWarnings("rawtypes")
	@AuthWidgetRule(value="xzqh.detail",desc="行政区划查看详细",operateType="W",refTable="DZ_XZQH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailXzqh",method=RequestMethod.GET)
	public String enterDetailMlph(String DZBM, Model model) {
	/*	Map xzqh = new  HashMap();
		if(DZBM!=null&&!DZBM.isEmpty()){
			Map<String,String> params = new HashMap<String,String>();
			params.put("DZBM", DZBM);
			DataSet xzqhList = xzqhService.xzqhList(params);
			if(xzqhList.getTotal()>0){
				xzqh = xzqhList.getRows().get(0);
			}
		}*/
		Map<String,Object> xzqh=xzqhService.selectDetailByPrimaryKey(DZBM);
		setDataAttribute(model, xzqh, "xzqh");
		return "psam/xzqh/xzqh.detail";
	}
	
	/**
	 * 行政区划添加操作
	 * @param params 行政区划信息
	 * @param request
	 * @return 提示信息
	 */
	@AuthWidgetRule(value="xzqh.add",desc="行政区划添加",operateType="W",refTable="DZ_XZQH",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG add(@RequestParam  Map<String,Object> params ,HttpServletRequest request) {
		xzqhService.addXzqh(params);
		return ajaxDoneSuccess("行政区划添加成功");
	}
	@AuthWidgetRule(value="xzqh.add",desc="行政区划添加",operateType="W",refTable="DZ_XZQH",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = { "/enterAddXzqh" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String enterAddXzqh(){
		return "psam/xzqh/xzqhAdd";
	}
	/**
	 * 行政区划更新操作
	 * @param params 行政区划信息
	 * @param request
	 * @return 提示信息
	 */
	@AuthWidgetRule(value="xzqh.save",desc="行政区划更新",operateType="W",refTable="DZ_XZQH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG save(@RequestParam  Map<String,Object> params ,HttpServletRequest request) {
		xzqhService.saveXzqh(params);
		return ajaxDoneSuccess("行政区划保存成功");
	}
	@AuthWidgetRule(value="xzqh.save",desc="行政区划更新",operateType="W",refTable="DZ_XZQH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = { "/enterEditXzqh" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String enterEditXzqh(String dzbm,Model model){
		Map<String,Object> xzqh = Maps.newHashMap();
		if(dzbm!=null&&!dzbm.isEmpty()){
			Map<String,String> params = new HashMap<String,String>();
			params.put("DZBM", dzbm);
			DataSet xzqhList = xzqhService.xzqhList(params);
			if(xzqhList.getTotal()>0){
				xzqh = xzqhList.getRows().get(0);
			}
		}
		setDataAttribute(model, xzqh, "xzqh");
		return "psam/xzqh/xzqhEdit";
	}
	
	/**
	 * 行政区划注销操作
	 * @param params  行政区划信息
	 * @param request
	 * @return 提示信息
	 */
	@AuthWidgetRule(value="xzqh.cancel",desc="行政区划注销",operateType="W",refTable="DZ_XZQH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = { "/cancel" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG cancel(@RequestParam  Map<String,Object> params ,HttpServletRequest request) {
		xzqhService.cancelXzqh(params);
		return ajaxDoneSuccess("行政区划注销成功");
	}
	
	/**
	 * 跳转行政区划注销页面
	 * @param dzbm 行政区划地址编码
	 * @param model
	 * @return 页面信息
	 * @throws Exception
	 */
	@AuthWidgetRule(value="xzqh.cancel",desc="行政区划注销",operateType="W",refTable="DZ_XZQH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterCancleXzqh",method=RequestMethod.GET)
    public Object enterCancleXzqh(String dzbm,Model model) throws Exception{
		Xzqh xzqh=xzqhService.selectXzqhByDzbm(dzbm);
		setDataAttribute(model, xzqh, "xzqh");
		return "psam/xzqh/cancleXzqh";
	}

	/**
	 * 行政区划启用操作
	 * @param params 行政区划信息
	 * @param request
	 * @return
	 */
	@AuthWidgetRule(value="xzqh.activate",desc="行政区划启用",operateType="W",refTable="DZ_XZQH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = { "/activate" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG activate(@RequestParam  Map<String,Object> params ,HttpServletRequest request) {
		xzqhService.activateXzqh(params);
		return ajaxDoneSuccess("行政区划启用成功");
	}
	
	/**
	 * 行政区划导入操作
	 * @param dzbm 行政区划地址编码
	 * @return
	 */
	@AuthWidgetRule(value="xzqh.importdata",desc="行政区划导入",operateType="W",refTable="DZ_XZQH")
	@RequestMapping(value = { "/importdata" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG importdata(String dzbm) {
		
		return ajaxDoneSuccess("行政区划保存成功");
	}
	
//-----------------------------------公共-回调-查找-带回-----------------------------------------------	
	/**
	 * 行政区域查找带回
	 * @param model
	 * @param showXzjd 
	 * @param showSqjcwh
	 * @return
	 */
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="行政区域查找带回",operateType="W",refTable="DZ_JLX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadBingbackPage")
	public Object loadBingbackPage(Model model,String showXzjd,String showSqjcwh) {
		setDataAttribute(model, showXzjd, "showXzjd");
		setDataAttribute(model, showSqjcwh, "showSqjcwh");
		if("false".equals(showXzjd)){
			return "psam/xzqh/xzqh.bringback.dialog.false";
		}
		if("false".equals(showSqjcwh)){
			return "psam/xzqh/xzqh.bringback.dialog.false";
		}
		return "psam/xzqh/xzqh.bringback.dialog";
	}
	
	/**
	 * 行政区划 树形数据加载
	 * @param id
	 * @param showXzjd
	 * @param showSqjcwh
	 * @param request
	 * @return
	 */
	@AuthWidgetRule(value=KConstants.WIDGET_PUBLIC,desc="行政区划 树形数据加载",operateType="W",refTable="DZ_XZQH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/tree" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  DataSet tree(@RequestParam( required = false)  String id ,@RequestParam( required = false) String showXzjd,@RequestParam( required = false) String showSqjcwh,HttpServletRequest request) {
		String type ="XZQH";
		if(id!=null&&!id.isEmpty()){
			int i = id.indexOf('_');
			if(i<0){
				throw new ServiceLogicalException("数据格式有误");
			}
			type = id.substring(0,i);
			id = id.substring(i+1);
		}
		//null被缓
		if(id==null||id.isEmpty()){
			id=KConstants.cacheable_prefix+UUIDUtil.uuid();
		}
		DataSet a=xzqhService.getChild(id,"true".equals(showXzjd),"true".equals(showSqjcwh),type);
		
		return a;
	}
}
