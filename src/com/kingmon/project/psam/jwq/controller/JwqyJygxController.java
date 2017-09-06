package com.kingmon.project.psam.jwq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jwq.service.IJwqService;
import com.kingmon.project.psam.jwq.service.IJwqyJygxService;

@Controller
@RequestMapping("/psam/jwqyJygx")
public class JwqyJygxController extends KBaseController {
	
	private static final String prefix = "psam/jwqyJygx/";

	@Autowired
	private IJwqyJygxService jwqyJygxService;
	@Autowired
	private IJwqService jwqService;
	

		@AuthWidgetRule(value="jwq.enterJyAssign",desc="警务区人员分配",operateType="W",refTable="ENT_JWQYJYGX",crudType=KConstants.OPER_ADD)
		@RequestMapping(value = "/enterJyAssign", method = RequestMethod.GET)
		public String enterJyAssign(String jwqid, Model model) {
			Jwq jwq = jwqService.getJwqById(jwqid);
			setDataAttribute(model, jwq, "jwq");
			return prefix + "jwqJyAssign";
		}
		//警员列表
		@AuthWidgetRule(value="jwq.pcsJyNotInJwqList",desc="未分配警务区人员数据列表",operateType="W",refTable="ENT_JWQYJYGX",crudType=KConstants.OPER_SEARCH)
		@RequestMapping(value = "/pcsJyNotInJwqList", method = RequestMethod.POST)
		@ResponseBody
		public Object pcsJyNotInJwqList( @ModelAttribute("paramObject") ParamObject paramObject,String jwqid) {
			DataSet dataSet = jwqyJygxService.loadPcsJyNotInJwqDataSet(paramObject,jwqid);
			return FastjsonUtil.convert(dataSet);
		}
		
		@AuthWidgetRule(value="jwq.pcsJyInJwqList",desc="已分配警务区警员关系数据列表",operateType="W",refTable="ENT_JWQYJYGX",crudType=KConstants.OPER_SEARCH)
		@RequestMapping(value = "/pcsJyInJwqList", method = RequestMethod.POST)
		@ResponseBody
		public Object pcsJyInJwqList(@ModelAttribute("paramObject") ParamObject paramObject,String jwqid) {
			DataSet dataSet = jwqyJygxService.loadPcsJyInJwqDataSet(paramObject,jwqid);
			return FastjsonUtil.convert(dataSet);
		}
		
//---------------------------------------------------------------------------------------------			
		//分配警员
		@AuthWidgetRule(value="jwq.addJyToJwq",desc="警务区人员分配",operateType="W",refTable="ENT_JWQYJYGX",crudType=KConstants.OPER_ADD)
		@RequestMapping(value = "/addJyToJwq", method = RequestMethod.POST)
		@ResponseBody
		public Object addJyToJwq(String[] app_userids, String jwqid) {
			jwqyJygxService.addJyToJwq(app_userids, jwqid);
			return ajaxDoneSuccess("操作成功!");
		}
		
//		@AuthWidgetRule(value="jwqyJygx.removeJyFromJwq",desc="警务区人员删除",operateType="W",refTable="ENT_JWQYJYGX")
//		@RequestMapping(value = "/removeJyFromJwq", method = RequestMethod.POST)
//		@ResponseBody
//		public Object removeJyFromJwq(String[] app_userids, String jwqid) {
//			jwqyJygxService.removeJyFromJwq(app_userids, jwqid);
//			return ajaxDoneSuccess("操作成功!");
//		}
//---------------------------------------------------------------------------------------------		
		@AuthWidgetRule(value="jwq.disableJwqyJygx",desc="警务区人员禁用",operateType="W",refTable="ENT_JWQYJYGX",crudType=KConstants.OPER_UPDATE)
		@RequestMapping(value = "/disableJwqyJygx", method = RequestMethod.POST)
		@ResponseBody
		public Object disableJwqyJygx(String app_userid, String jwqid) {
			String[] app_userIds=new String[]{""+app_userid};
//			jwqyJygxService.disableJwqyJygx(app_userid, jwqid);
			jwqyJygxService.removeJyFromJwq(app_userIds, jwqid);
			return ajaxDoneSuccess("操作成功!");
		}	
//		@AuthWidgetRule(value="jwqyJygx.enableJwqyJygx",desc="警务区人员启用",operateType="W",refTable="ENT_JWQYJYGX")
//		@RequestMapping(value = "/enableJwqyJygx", method = RequestMethod.POST)
//		@ResponseBody
//		public Object enableJwqyJygx(String app_userid, String jwqid) {
//			jwqyJygxService.enableJwqyJygx(app_userid, jwqid);
//			return ajaxDoneSuccess("操作成功!");
//		}	
//---------------------------------------------------------------------------------------------			
}
