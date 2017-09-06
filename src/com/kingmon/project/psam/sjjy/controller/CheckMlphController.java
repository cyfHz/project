package com.kingmon.project.psam.sjjy.controller;

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
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.util.GeoUtils;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.mlph.service.MlphService;
import com.kingmon.project.psam.sjjy.service.CheckMlphService;

@Controller
@RequestMapping("/psam/sjjy")
public class CheckMlphController extends KBaseController{
private static final String prefix = "psam/xjjy/";
	
	@Autowired
	private CheckMlphService checkMlphService;
	
	@Autowired
	private MlphService  mlphService;
	
	
	@AuthWidgetRule(value="checkMlph.list",desc="门楼牌数据校验",operateType="R",refTable="DZ_MLPH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String  index() {
		return prefix+"check.mlph";
	}
	@AuthWidgetRule(value="checkMlph.list",desc="门楼牌号校验数据列表",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/list" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  DataSet mlphList(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		return checkMlphService.mlphList(params);
	}
	
	@AuthWidgetRule(value="checkMlph.mark",desc="门楼牌号校验数据标记",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterMark",method=RequestMethod.GET)
	public String enterMark(String YWLSH,Model model) {
		setDataAttribute(model, YWLSH, "YWLSH");
		return prefix+"mlph.mark";
	}
	
	@AuthWidgetRule(value="checkMlph.mark",desc="门楼牌号校验数据标记保存",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = { "/mark/save" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG savemark(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		String ywlsh = params.get("YWLSH");
		String location = params.get("location");
		if(ywlsh==null||ywlsh.isEmpty()){
			ajaxDoneError("数据有误（YWLSH为空）");
		}
		if(location==null||location.isEmpty()||location.indexOf(',')<0){
			ajaxDoneError("经纬度有误");
		}
		mlphService.updateMlphLocation(ywlsh, GeoUtils.pgis2Point(location));
		return ajaxDoneSuccess("操作成功");
	}
}
